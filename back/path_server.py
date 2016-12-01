#!/usr/bin/python

"""
Basic script using qgis to return the shortest route via python

Usage: python shortest-path.py

Prerequisites (Debian):
    apt-get install qgis python-qgis qgis-plugin-grass

Tested on QGIS 2.4.0

TODO initializing qgis for every query is inefficient, better to use
a daemon pattern

TODO Regenerating the graph from geojson may also be inefficient, but
may be inextricably linked with start and end points

"""

import sys
import signal
import socket

# fix gqis.core import errors
# sys.path.append('/usr/share/qgis/python')

from qgis.core import *
from qgis.networkanalysis import *

"""
Configuration
"""

# Use ogr2ogr to convert geojson to shp as needed
CALLES_ARCHIVO = "CallesDF/OGRGeoJSON.shp"
#CALLES_ARCHIVO = "Capas/Metrobus std.MAP"
MAX_BUFFER = 2056 # Maximum message length

"""
Functions
"""

"""
Return the shortest path between two points given a builder and director

director    (Director)  Director
start       (tuple)     Coordinates
end         (tuple)     Coordinates

@return Array of points
"""
def getShortestPath(layer, start, end):

    # layer, field_idx, one way, one way reverse, bidirectional, default
    # layer, field_idx, 'yes', '1', 'no', 3
    # where 'yes' is expected for one way, '1' for one way reverse, etc
    director = QgsLineVectorLayerDirector(layer, 2, '1', '', '2', 3)

    # si no importa el sentido
    # director = QgsLineVectorLayerDirector(layer, -1, '', '', '', 3)

    properter = QgsDistanceArcProperter()
    director.addProperter(properter)

    # TODO set proper reference system
    crs = QgsCoordinateReferenceSystem(4326, QgsCoordinateReferenceSystem.PostgisCrsId)
    builder = QgsGraphBuilder(crs)

    startPoint = QgsPoint(start[0], start[1])
    endPoint = QgsPoint(end[0], end[1])

    # Create graph
    tiedPoints = director.makeGraph(builder, [startPoint, endPoint])
    graph = builder.graph()

    # Get start and end ids
    startId = graph.findVertex(tiedPoints[0])
    endId = graph.findVertex(tiedPoints[1])

    # Compute dijkstra
    (tree, cost) = QgsGraphAnalyzer.dijkstra(graph, startId, 0)

    path = []

    # If a path exists
    if tree[endId] != -1:
        # Walk the tree to extract the path
        cursor = endId
        while cursor != startId:
            arc = graph.arc(tree[cursor])

            # Save in array format
            # path.append(graph.vertex(arc.inVertex()).point())

            # Save in packed format
            point = graph.vertex(arc.inVertex()).point()
            path.append("%s,%s" % (point.x(), point.y()))
            cursor = arc.outVertex()

    return ';'.join(path)

def recieveMessage(sock):

    # read from socket
    data = []
    recieved = 0
    while recieved < MAX_BUFFER:
        part = sock.recv(MAX_BUFFER - recieved)
        if part == '':
            break
        else:
            recieved = recieved + len(part)
            data.append(part)

    request = ''.join(data)
    return request

def replyWith(sock, message):

    msglen = len(message)

    sent = 0
    while sent < msglen:
        part = sock.send(message[sent:])
        if part == 0:
            print("connection lost")
            return
        sent = sent + part

    sock.shutdown(1)

if __name__ == '__main__':

    print "Initializing"

    """
    Sigint
    """

    # def quit(s, f):
    #     print 'Quit'
    #     exit()

    # signal.signal(signal.SIGINT, quit)

    """ Script """

    # Prepare connection to qgis
    QgsApplication.setPrefixPath("/usr/bin/qgis", True)

    # Initialize
    qgs = QgsApplication([], False) # False for no GUI
    qgs.initQgis()

    # Load layer
    layer_a = QgsVectorLayer(CALLES_ARCHIVO, "calles_a", "ogr")
    if not layer_a.isValid():
        print "error loading layer: invalid"
        sys.exit(1)

    layer_b = QgsVectorLayer(CALLES_ARCHIVO, "calles_b", "ogr")
    if not layer_b.isValid():
        print "error loading layer: invalid"
        sys.exit(1)

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.bind(('127.0.0.1', 8011))
    sock.listen(10)

    # Query

    print "Listening"

    while True:

        (client, addr) = sock.accept()

        message = recieveMessage(client)
        parts = message.split(" ")

        if len(parts) == 5:

            layer_id = parts[0]
            if layer_id == 2:
                layer = layer_b
            else:
                layer = layer_a

            pointA = (float(parts[1]), float(parts[2]))
            pointB = (float(parts[3]), float(parts[4]))
            
        if len(parts) == 4:
            # Legacy
            layer = layer_a

            pointA = (float(parts[0]), float(parts[1]))
            pointB = (float(parts[2]), float(parts[3]))

        if len(parts) >= 4:
            path = getShortestPath(layer, pointB, pointA)
            replyWith(client, path)
            print "Handled request for %s" % message
        client.close()

    # TODO put this finish under sigint
    #qgs.exitQgis()
