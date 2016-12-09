import sys, os, getopt, re
import psycopg2

def line_transform(ruta_raw, strip=False):
	points = re.findall("(-?[0-9\.]+) (-?[0-9\.]+)", ruta_raw)
	return points

def get_matches(cur, r_id):

	cur.execute("select modo from ruta where id_ruta=%s", [r_id])

	(modo,) = cur.fetchone()

	if modo == 1 or modo == 2:

		try:
		    cur.execute(""" select matches.user_id, matches.id_match, matches.ruta_match, ST_AsText(matches.origen_match), ST_AsText(matches.destino_match), sesiones_pumausuario.first_name, sesiones_pumausuario.last_name from (select tabla.user_id as user_id, tabla.id_original as id_original, tabla.id_match as id_match, tabla.ruta_match as ruta_match, origen as origen_match, destino as destino_match from (select user_id, tabla_rutas.id_original, tabla_rutas.id_match as id_match, tabla_rutas.ruta_match as ruta_match, horario_ruta.origen_hora from (select b.user_id, a.id_ruta as id_original, b.id_ruta as id_match, ST_AsText(b.puntos) as ruta_match, ((ST_Length(ST_Intersection(a.puntos,b.puntos)))*100)/(LEAST(ST_Length(a.puntos),ST_Length(b.puntos))) as porcentaje from ruta a, ruta b where (((ST_Length(ST_Intersection(a.puntos,b.puntos)))*100)/(LEAST(ST_Length(a.puntos),ST_Length(b.puntos))) > 40) and a.id_ruta != b.id_ruta and a.id_ruta = %s and a.modo = %s and a.modo = b.modo and a.user_id != b.user_id order by porcentaje desc) as tabla_rutas inner join horario_ruta on tabla_rutas.id_original = horario_ruta.id_ruta) as tabla inner join horario_ruta on tabla.id_match = horario_ruta.id_ruta where abs(extract(epoch from horario_ruta.origen_hora)-extract(epoch from tabla.origen_hora)) <  extract(epoch from (20*interval '1 minute')) limit 5) as matches inner join sesiones_pumausuario on matches.user_id = sesiones_pumausuario.id and user_id != id_original;  """, (r_id, modo))
		except:
			print("Couldn't execute second query.")

	rows = cur.fetchall()

	data = []
	for row in rows:

		ruta_raw = row[2][11:-1]
		data.append({
			'ruta': line_transform(ruta_raw, True),
			'user_id': row[0],
			'ruta_id': row[1],
			'first_name': row[5],
			'last_name': row[6],
		})

	return data

if __name__ == '__main__':
	r_id = str(sys.argv[1])

	conn = psycopg2.connect("dbname='pumaride' user='pumaride' host='localhost' password='puma_ride123.'")
	cur = conn.cursor()

	data = get_matches(cur, r_id)
	for d in data:
		print(d)
