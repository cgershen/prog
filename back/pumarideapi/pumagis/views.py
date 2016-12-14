from django.db import connection
from rest_framework_swagger.renderers import OpenAPIRenderer, SwaggerUIRenderer
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import viewsets
from .models import Point
from .serializers import PointSerializer
from .models import Line
from .serializers import LineSerializer
import match

"""
class PointViewSet(viewsets.ModelViewSet):
    queryset = Point.objects.all()
    serializer_class = PointSerializer
"""
@api_view(['GET'])
def test(request):
	return Response({
		'id': request.session['user_id'],
		'token': request.session['token'],
	})


@api_view(['GET','POST'])
def points_list(request):
	if request.method=='GET':
		print "GEEET"
		points=Point.objects.all()
		serializer=PointSerializer(points,many=True)
		return Response(serializer.data)
	elif request.method=='POST':
		serializer=PointSerializer(data=request.data)
		if serializer.is_valid():
			serializer.save()
			return Response(serializer.data, status=status.HTTP_201_CREATED)
		return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET','POST','DELETE'])
def lines_list(request,p_ori,p_des,tipo_transporte):
	if request.method=='GET':
		#p_ori=request.query_params.get('p_origen')
		#p_des=request.query_params.get('p_destino')
		#lines=Line.objects.filter(p_origen=p_ori,p_destino=p_des)
		#serializer=LineSerializer(lines,many=True)
		#return Response(serializer.data)
		return Response({})
	elif request.method=='POST':

		if 'user_id' in request.session:
			request.data.user_id = request.user.id

		serializer=LineSerializer(data=request.data)
		if serializer.is_valid():
			serializer.save()
			return Response(serializer.data, status=status.HTTP_201_CREATED)
		return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET','POST'])
def matches(request):

	if "ruta_id" in request.data:
		ruta_id = request.data["ruta_id"]

		data = []
		with connection.cursor() as cursor:
			data = match.get_matches(cursor, ruta_id)

	 	return Response(data, status=status.HTTP_201_CREATED)
	else:
		return Response(status=status.HTTP_400_BAD_REQUEST)

	#matches = dict()
	# serializer=MatchSerializer(data=request.data)
	# if serializer.is_valid():
	# 	return Response(serializer.data, status=status.HTTP_201_CREATED)
	#return Response([{"name":"Brad", "path":"-99.146132,19.272014;-99.18043,19.560310", "match_percent":99, "start_time": "16:15"},{"name":"Arthur", "path":"-99.186096,19.478058;-99.18697,19.478362", "match_percent":80, "start_time":"16:05"}])


@api_view(['GET','POST'])
def line(request):
	if request.method=='GET': # and 'user_id' in request.session:

		with connection.cursor() as cursor:
			#cursor.execute("SELECT id_ruta,ST_AsText(puntos),modo FROM ruta WHERE user_id = %s", [request.session['user_id']])
			cursor.execute("SELECT id_ruta,user_id,ST_AsText(puntos),modo FROM ruta ORDER BY id_ruta")
			rows = cursor.fetchall()

			data = []
			for row in rows:

				ruta_raw = row[2][10:-1]
				data.append({
					'ruta_id': row[0],
					'user_id': row[1],
					'ruta': "...", #match.line_transform(ruta_raw)
				})

		return Response(data)

	elif request.method=='POST':

		data = request.data.copy()

		if 'user_id' in request.session:
			data['user_id'] = request.session['user_id']
		#else:
		#	data['user_id'] = 0 # don't let params set this

		if "ruta_id" in data and "borrar" in data:

			id = data['ruta_id']

			with connection.cursor() as cursor:
				cursor.execute("DELETE FROM horario_ruta WHERE id_ruta=%s", [id])
				cursor.execute("DELETE FROM ruta WHERE id_ruta=%s", [id])

			return Response({"success":1})
		else:
			serializer=LineSerializer(data=data)
			if serializer.is_valid():

				serializer.save()

				if "guardar" in request.data:
					return Response(serializer.data, status=status.HTTP_201_CREATED)
				else:
					serializer.save()
					return Response(serializer.data)

			return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

