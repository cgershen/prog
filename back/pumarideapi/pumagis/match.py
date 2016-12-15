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
		    cur.execute(""" select matches.user_id, matches.id_match, matches.ruta_match, ST_AsText(matches.origen_match), ST_AsText(matches.destino_match), sesiones_pumausuario.first_name, sesiones_pumausuario.last_name from (select tabla.user_id as user_id, tabla.id_original as id_original, tabla.id_match as id_match, tabla.ruta_match as ruta_match, origen as origen_match, destino as destino_match, tabla.porcentaje as porcentaje from (select user_id, tabla_rutas.id_original, tabla_rutas.id_match as id_match, tabla_rutas.ruta_match as ruta_match, horario_ruta.origen_hora, tabla_rutas.porcentaje as porcentaje from (select b.user_id, a.id_ruta as id_original, b.id_ruta as id_match, ST_AsText(b.puntos) as ruta_match, ((ST_Length(ST_Intersection(a.puntos,b.puntos)))*100)/(LEAST(ST_Length(a.puntos),ST_Length(b.puntos))) as porcentaje from ruta a, ruta b where (((ST_Length(ST_Intersection(a.puntos,b.puntos)))*100)/(LEAST(ST_Length(a.puntos),ST_Length(b.puntos))) > 40) and a.id_ruta != b.id_ruta and a.id_ruta = %s and a.modo = %s and a.modo = b.modo and a.user_id != b.user_id) as tabla_rutas inner join horario_ruta on tabla_rutas.id_original = horario_ruta.id_ruta) as tabla inner join horario_ruta on tabla.id_match = horario_ruta.id_ruta where abs(extract(epoch from horario_ruta.origen_hora)-extract(epoch from tabla.origen_hora)) <  extract(epoch from (20*interval '1 minute'))) as matches inner join sesiones_pumausuario on matches.user_id = sesiones_pumausuario.id and user_id != id_original order by matches.porcentaje desc limit 5;  """, (r_id, modo))
		except:
			print("Couldn't execute second query.")
	elif modo == 3:
		
		try:
		    cur.execute("select matches.user_id, matches.id_match, ST_AsText(matches.ruta_match) as ruta_match, ST_AsText(matches.origen_match) as origen_match, ST_AsText(matches.destino_match) as destino_match, sesiones_pumausuario.first_name, sesiones_pumausuario.last_name from (select matches_raw.id_match as id_match, matches_raw.user_id as user_id, matches_raw.ruta_match as ruta_match, matches_raw.origen_match as origen_match, matches_raw.destino_match as destino_match, abs(extract(epoch from horario_ruta.origen_hora)-extract(epoch from matches_raw.origen_hora_match)) as tiempo from (select tabla_rutas.id_match as id_match, tabla_rutas.user_id as user_id, tabla_rutas.ruta_match as ruta_match, tabla_rutas.id_original as id_original, horario_ruta.origen as origen_match, horario_ruta.destino as destino_match, horario_ruta.origen_hora as origen_hora_match from (select b.id_ruta as id_match, b.user_id as user_id, b.puntos as ruta_match, a.id_ruta as id_original from ruta a, ruta b where a.modo = b.modo and  a.id_ruta = %s and a.modo = %s and b.user_id != a.user_id) as tabla_rutas inner join horario_ruta on tabla_rutas.id_match = horario_ruta.id_ruta) as matches_raw inner join horario_ruta on matches_raw.id_original = horario_ruta.id_ruta where ST_DWithin(matches_raw.origen_match,horario_ruta.origen,500) and ST_DWithin(matches_raw.destino_match,horario_ruta.destino,500) and abs(extract(epoch from horario_ruta.origen_hora)-extract(epoch from matches_raw.origen_hora_match)) <  extract(epoch from (20 * interval '1 minute')) order by tiempo desc limit 5) as matches inner join sesiones_pumausuario on sesiones_pumausuario.id = matches.user_id;", (r_id,modo))
		except:
			print("Couldn't execute third query.")
	elif modo == 4:
		
		try:
		    cur.execute("select matches.user_id, matches.id_match, ST_AsText(matches.ruta_match) as ruta_match, ST_AsText(matches.origen_match) as origen_match, ST_AsText(matches.destino_match) as destino_match, sesiones_pumausuario.first_name, sesiones_pumausuario.last_name from (select id_match as id_match, user_id, ruta_match, origen_match, destino_match from (select matches_raw.id_original as id_original, matches_raw.origen_original as origen_original, matches_raw.origen_hora as origen_hora_original, matches_raw.id_match as id_match, matches_raw.user_id as user_id, horario_ruta.origen as origen_match, horario_ruta.destino as destino_match, horario_ruta.origen_hora as origen_hora_match, matches_raw.ruta_match, matches_raw.closest_point from (select tabla_rutas.id_original as id_original, horario_ruta.origen as origen_original, horario_ruta.origen_hora as origen_hora, tabla_rutas.id_match as id_match, tabla_rutas.user_id as user_id, tabla_rutas.match_ruta as ruta_match, tabla_rutas.closest_point as closest_point from (select a.id_ruta as id_original, b.id_ruta as id_match, b.user_id as user_id, b.puntos as match_ruta, ST_ClosestPoint(a.puntos::geometry,b.puntos::geometry) as closest_point from ruta a, ruta b where ST_DWithin(a.puntos,b.puntos,500) and not ST_Touches(a.puntos::geometry,b.puntos::geometry) and (((ST_Length(ST_Intersection(a.puntos,b.puntos)))*100)/(LEAST(ST_Length(a.puntos),ST_Length(b.puntos))) > 15) and a.id_ruta = %s and a.user_id != b.user_id and a.id_ruta != b.id_ruta and b.modo = 1) as tabla_rutas inner join horario_ruta on tabla_rutas.id_original = horario_ruta.id_ruta) as matches_raw inner join horario_ruta on matches_raw.id_match = horario_ruta.id_ruta) as matches_raw_one where abs(extract(epoch from (((ST_Distance(matches_raw_one.closest_point,matches_raw_one.origen_original) / 4000) * 60) * interval '1 minute')) - extract (epoch from (((ST_Distance(matches_raw_one.closest_point,matches_raw_one.origen_match) / 11000) * 60) * interval '1 minute'))) < extract(epoch from (10 * interval '1 minute')) order by matches_raw_one.closest_point desc limit 5) as matches inner join sesiones_pumausuario on matches.user_id = sesiones_pumausuario.id;", [r_id])
		except:
			print("Couldn't execute fourth query.")

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
