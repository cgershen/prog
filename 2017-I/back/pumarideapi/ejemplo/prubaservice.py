import requests
import json
import folium


#Define parameters
headers = {"Accept":"application/json"}

data = {
  'p_origen': '(12,30)',
  'p_destino': '(0,0)'
}

r=requests.post('http://35.160.229.64:8000/api/lines/',headers=headers,data=data)

#Request to json
data=r.json()

#print "El camino mas corto es:"

points=[]
for point in data['shortest_path'].split(";"):
    point_par=point.split(",")
    points.append(tuple([float(point_par[1]),float(point_par[0])]))

ave_lat = sum(p[0] for p in points)/len(points)
ave_lon = sum(p[1] for p in points)/len(points)

my_map = folium.Map(location=[ave_lat, ave_lon], zoom_start=14)


#add a markers
folium.Marker(points[0]).add_to(my_map)
folium.Marker(points[-1]).add_to(my_map)

#add polyline
folium.PolyLine(points, color="red", weight=2.5, opacity=1).add_to(my_map)

#Save map
my_map.save("./prueba.html")