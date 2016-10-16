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

# fix gqis.core import errors
# sys.path.append('/usr/share/qgis/python')

from qgis.core import *
from qgis.networkanalysis import *

"""
Configuration
"""

CALLES_ARCHIVO = "CallesCoyoacan.geojson"

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
def getShortestPath(builder, director, start, end):

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
            path.append(graph.vertex(arc.inVertex()).point())
            cursor = arc.outVertex()

    return path

"""
Script
"""

# Prepare connection to qgis
QgsApplication.setPrefixPath("/usr/bin/qgis", True)

# Initialize
qgs = QgsApplication([], False) # False for no GUI
qgs.initQgis()

# Load layer
layer = QgsVectorLayer(CALLES_ARCHIVO, "calles", "ogr")
if not layer.isValid():
    print "error loading geojson: invalid"
    sys.exit(1)

# Prepare for conversion 
# TODO fix road directions
director = QgsLineVectorLayerDirector(layer, -1, '', '', '', 3)
properter = QgsDistanceArcProperter()
director.addProperter(properter)

# TODO set proper reference system
crs = QgsCoordinateReferenceSystem(4326, QgsCoordinateReferenceSystem.PostgisCrsId)
builder = QgsGraphBuilder(crs)

# Query
# TODO accept pointA and pointB from cli args
pointA = (-99.116625,19.33599)
pointB = (-99.201833,19.309861)
path = getShortestPath(builder, director, pointA, pointB)
print(path)

# Finish
qgs.exitQgis()
