import sys
from qgis.core import *
from qgis.networkanalysis import *
import json

CALLES_ARCHIVO = "CallesCoyoacan.geojson"

if __name__ == "__main__":
    print "sadsasad"
    qgs = QgsApplication([], False) # False for no GUI
    qgs.initQgis()
    layer = QgsVectorLayer(CALLES_ARCHIVO, "calles", "ogr")
    if not layer.isValid():
        print "error loading geojson: invalid"
        sys.exit(1)
    director = QgsLineVectorLayerDirector(layer, -1, '', '', '', 3)
    properter = QgsDistanceArcProperter()
    director.addProperter(properter)
    crs = QgsCoordinateReferenceSystem(4326, QgsCoordinateReferenceSystem.PostgisCrsId)
    builder = QgsGraphBuilder(crs)
    pointA = (-99.116625,19.33599)
    pointB = (-99.201833,19.309861)
    startPoint = QgsPoint(pointA[0], pointA[1])
    endPoint = QgsPoint(pointB[0], pointB[1])
    # Create graph
    tiedPoints = director.makeGraph(builder, [startPoint, endPoint])
    graph = builder.graph()
    # Get pointA and pointB ids
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
	print path
    
