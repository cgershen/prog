#!/usr/bin/python

"""
Basic script using qgis to return the shortest route via python

Prerequisites (Debian):
    apt-get install qgis python-qgis qgis-plugin-grass

Instructions:
    1. Run this script
    2. Connect over the port specified
    3. Send 4 comma separated strings as the lat,long coordinates
        for start, end over that connection. Alternatively
        send 5 strings, the first string is an identifier for the
        map to use

Tested on QGIS 2.4.0
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

DF_Calles = "Capas/CallesDF.shp"
DF_Metro_Vialidad = "Capas/DF_a metro_Vialidad_polyline.shp"
DF_Bicis = "Capas/Rutas Bicis_polyline.shp"

MAX_BUFFER = 8192 # Maximum message length

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

    #print(path)

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

    try:
    	sock.shutdown(1)
    except:
	pass

def loadLayerSafely(Filename, Name):
    layer = QgsVectorLayer(Filename, Name, "ogr")
    if layer.isValid():
        print "Loaded %s" % Name
    else:
        print "error loading layer %s: %s invalid" % (Name, Filename)
        #sys.exit(1)
    return layer

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
    layer_calles = loadLayerSafely(DF_Calles, "DF_Calles")
    layer_bicis = loadLayerSafely(DF_Bicis, "DF_Bicis")
    layer_metro_vialidad = loadLayerSafely(DF_Metro_Vialidad, "DF_Metro_Vialidad")

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.bind(('127.0.0.1', 8011))
    sock.listen(10)

    # Query

    print "Listening"

    while True:

        (client, addr) = sock.accept()

        message = recieveMessage(client)
        parts = message.split(" ")

	layer_id=1

        if len(parts) == 5:

            try: 
                layer_id = int(parts[0])
            except ValueError:
                print "Bad client sent %s as layer id, expected int" % layer_id
                client.close()
                continue

            if layer_id == 3:
                layer = layer_metro_vialidad
            elif layer_id == 2:
                layer = layer_bicis
            else:
                layer = layer_calles

            pointA = (float(parts[1]), float(parts[2]))
            pointB = (float(parts[3]), float(parts[4]))
            
        if len(parts) == 4:
            # Legacy
            layer = layer_calles

            pointA = (float(parts[0]), float(parts[1]))
            pointB = (float(parts[2]), float(parts[3]))

        if len(parts) >= 4:
            path = getShortestPath(layer, pointB, pointA)
            replyWith(client, path)
            print "Handled request for %s on layer %s" % (message, layer_id)

        client.close()

    # TODO put this finish under sigint
    #qgs.exitQgis()
