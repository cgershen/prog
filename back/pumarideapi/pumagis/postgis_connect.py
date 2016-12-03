""" Raw queries to postgis """

import psycopg2

db = "pumaride"
usr = "pumaride"
password = "puma_ride123."

def query(c):
    """ Be wary of SQL injection, nothing here is stopping it"""
    c.execute('SELECT name FROM table')
    return c.fetchall()

def connect():
    conn = psycopg2.connect(database=db, user=usr, host='localhost', password=password)
    c = conn.cursor()
    return (conn, c)

def agregar_ruta(c, id, origen_text, destino_text, puntos_text, capa):
    """ Be wary of SQL injection, nothing here is stopping it"""

    query = "insert into ruta values(%s, ST_GeomFromText('LINESTRING(%s)',4326), %s)" % (id, puntos_text, capa)
    c.execute(query)

    query = "insert into horario_ruta values (%s, ST_GeomFromText('POINT(%s)'), ST_GeomFromText('POINT(%s)'), ('2000-01-01 00:00:00'), ('2000-01-01 00:00:00'))" % (id, origen_text, destino_text)
    c.execute(query)

def borrar(c, id):
    c.execute("DELETE FROM ruta WHERE id_ruta=%s", [id])

def consultar_rutas(c):
    c.execute("SELECT id_ruta,ST_AsText(puntos),modo FROM ruta")
    return c.fetchall()

# create table ruta (id_ruta integer primary key, puntos geography(LINESTRING,4326), modo integer);
# 
# create table horario_ruta (id_ruta integer references ruta(id_ruta), origen geography(POINT,4326), destino geography(POINT,4326), origen_hora timestamp, destino_hora timestamp);
# 
# ------------------ INSERTS ------------------
