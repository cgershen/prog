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

PG_PORT = 1337
PG_USER = "user"
PG_PASS = "hunter2"
PG_TABLE = "calles"
PG_COL = "the_geometry"

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
    #
    # director = QgsLineVectorLayerDirector(layer, -1, '', '', '', 3)
    director = QgsLineVectorLayerDirector(layer, -1, '', '', '', 3)

    properter = QgsDistanceArcProperter()
    director.addProperter(properter)

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

    # Load layer from postgis
    uri = QgsDataSourceURI()
    uri.setConnection("localhost", PG_PORT, PG_DB, PG_USER, PG_PASS)
    usi.setDataSource("public", PG_TABLE, PG_COL)
    layer = QgsVectorLayer(uri.uri(False), "calles", "postgres")

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.bind(('127.0.0.1', 8011))
    sock.listen(10)

    # Query

    print "Listening"

    while True:

        (client, addr) = sock.accept()

        message = recieveMessage(client)
        parts = message.split(" ")

        if len(parts) == 4:

            pointA = (float(parts[0]), float(parts[1]))
            pointB = (float(parts[2]), float(parts[3]))

            path = getShortestPath(layer, pointA, pointB)
            replyWith(client, path)

            print "Handled request for %s" % message

        client.close()

    # TODO put this finish under sigint
    #qgs.exitQgis()
