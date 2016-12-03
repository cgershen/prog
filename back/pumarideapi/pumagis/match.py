import sys, os, getopt

import psycopg2

r_id = str(sys.argv[1])
data = []

try:
    conn = psycopg2.connect("dbname='prueba' user='postgres' host='localhost' password=''")
except:
    print("Couldn't connect to database.")

cur = conn.cursor()

try: 
	cur.execute(" select modo from ruta where id_ruta = %s ", r_id)
except: 
	print("Couldn't execute query.")

q_modo = cur.fetchall()

modo = q_modo[0][0]

if modo == 1:
	try:
	    cur.execute(""" select tabla.id_match, tabla.path_match from (select tabla_rutas.id_original as id_original, tabla_rutas.id_match as id_match, tabla_rutas.path_match as path_match,
			horario_ruta.origen_hora as horario_inicio_original from (select a.id_ruta as id_original, b.id_ruta as id_match, ST_AsText(ST_Intersection(a.puntos,b.puntos)) as path_match, ((ST_Length(ST_Intersection(a.puntos,b.puntos)))*100)/(LEAST(ST_Length(a.puntos),ST_Length(b.puntos))) as porcentaje from ruta a, ruta b where 
			(((ST_Length(ST_Intersection(a.puntos,b.puntos)))*100)/(LEAST(ST_Length(a.puntos),ST_Length(b.puntos))) > 40) and a.id_ruta != b.id_ruta and a.id_ruta = %s and a.modo = %s and a.modo = b.modo order by porcentaje desc) as tabla_rutas inner join horario_ruta on tabla_rutas.id_original = horario_ruta.id_ruta) as tabla inner join horario_ruta on tabla.id_match = horario_ruta.id_ruta where (horario_ruta.origen_hora<(tabla.horario_inicio_original+(20*interval '1 minute'))) limit 5;""", (r_id, modo))
	except:
	   	print("Couldn't execute query.")

rows = cur.fetchall()

for row in rows:
	data.append([row[0],row[1]])

for d in data:
	print(d[0], " ", d[1])