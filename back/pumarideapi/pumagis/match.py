import sys, os, getopt, re
import psycopg2

def line_transform(ruta_raw, strip=False):
	points = re.findall("(-?[0-9\.]+) (-?[0-9\.]+)", ruta_raw)

	if strip:
		end = points[-1]
		ruta = points[0::2]
		ruta = ruta + [end]
		return ruta
	else:
		return points

def get_matches(cur, r_id):

	cur.execute("select modo from ruta where id_ruta=%s", [r_id])

	(modo,) = cur.fetchone()

	if modo == 1 or modo == 2:

		try:
		    cur.execute(""" select tabla.id_match, tabla.path_match from (select tabla_rutas.id_original as id_original, tabla_rutas.id_match as id_match, tabla_rutas.path_match as path_match,
				horario_ruta.origen_hora as horario_inicio_original from (select a.id_ruta as id_original, b.id_ruta as id_match, ST_AsText(ST_Intersection(a.puntos,b.puntos)) as path_match, ((ST_Length(ST_Intersection(a.puntos,b.puntos)))*100)/(LEAST(ST_Length(a.puntos),ST_Length(b.puntos))) as porcentaje from ruta a, ruta b where 
				(((ST_Length(ST_Intersection(a.puntos,b.puntos)))*100)/(LEAST(ST_Length(a.puntos),ST_Length(b.puntos))) > 40) and a.id_ruta != b.id_ruta and a.id_ruta = %s and a.modo = %s and a.modo = b.modo order by porcentaje desc) as tabla_rutas inner join horario_ruta on tabla_rutas.id_original = horario_ruta.id_ruta) as tabla inner join horario_ruta on tabla.id_match = horario_ruta.id_ruta where abs(extract(epoch from horario_ruta.origen_hora)-extract(epoch from tabla.horario_inicio_original)) < 20 limit 5;""", (r_id, modo))
		except:
			print("Couldn't execute second query.")

	rows = cur.fetchall()

	data = []
	for row in rows:

		ruta_raw = row[1][16:-1]
		data.append({
			'ruta_id': row[0],
			'ruta': line_transform(ruta_raw, True)
		})

	return data

if __name__ == '__main__':
	r_id = str(sys.argv[1])

	conn = psycopg2.connect("dbname='pumaride' user='pumaride' host='localhost' password='puma_ride123.'")
	cur = conn.cursor()

	data = get_matches(cur, r_id)
	for d in data:
		print(d[0], d[1])
