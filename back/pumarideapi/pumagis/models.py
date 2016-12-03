from django.db import models
import sys
from qgis.core import *
from qgis.networkanalysis import *
import os
import sys
import socket
import path_server
import postgis_connect

def sendRequest(a_x, a_y, b_x, b_y):

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect(('127.0.0.1', 8011))

    message = "%s %s %s %s" % (a_x, a_y, b_x, b_y)

    path_server.replyWith(sock, message)
    reply = path_server.recieveMessage(sock)

    return(reply)


class Point(models.Model):
	lon=models.FloatField(default=0.0)
	lat=models.FloatField(default=0.0)

	class Meta:
		verbose_name = "Point"
        	verbose_name_plural = "Points"


class Line(models.Model):
	
	p_origen=models.CharField(max_length=50,default="(0.0,0.0)")
	p_destino=models.CharField(max_length=50,default="(0.0,0.0)")
	camino_mas_corto=models.CharField(max_length=250,default="")
	tipo_transporte=models.CharField(max_length=50,default=1)

	class Meta:
		verbose_name = "Line"
	        verbose_name_plural = "Lines"
	
	@property
	def shortest_path(self):
		a_lat=float(self.p_origen.split(",")[0].split("(")[1])
		a_lon=float(self.p_origen.split(",")[1].split(")")[0])
		b_lat=float(self.p_destino.split(",")[0].split("(")[1])
		b_lon=float(self.p_destino.split(",")[1].split(")")[0])
		#print a_lat,a_lon,b_lat,b_lon
		#print "HERE"
		poly_line=sendRequest(a_lat,a_lon,b_lat,b_lon)
		#print poly_line

		self.camino_mas_corto=poly_line

		poly_linef=[]
		poly_line=poly_line.split(";")
		for point in poly_line:
		    points=point.split(",")
		    if len(points)==2:
			poly_linef.append([float(points[0]),float(points[1])])
		

		if len(poly_linef) > 0:
			poly_linet = self.camino_mas_corto.replace(",", " ")
			poly_linet = poly_linet.replace(";", ",")

			(con, c) = postgis_connect.connect()
			postgis_connect.agregar_ruta(c, self.id, "%s %s" % (a_lat, a_lon), "%s %s" % (b_lat, b_lon), poly_linet, self.tipo_transporte)
			con.commit()

		return poly_linef

	@property			
	def origin_point(self):
		#print "Yo estoy aqui"
		#print self.p_origen.split(",")
		return [float(self.p_origen.split(",")[0].split("(")[1]),float(self.p_origen.split(",")[1].split(")")[0])]
	def destination_point(self):
		return [float(self.p_destino.split(",")[0].split("(")[1]),float(self.p_destino.split(",")[1].split(")")[0])]

	def save(self, *args, **kwargs):
		super(Line,self).save(*args,**kwargs)

class Match(models.Model):

	class Meta:
		verbose_name = "Match"
        	verbose_name_plural = "Matches"

	@property			
	def matches(self):
		return ["abcde"] #dict({"test":"a"})

