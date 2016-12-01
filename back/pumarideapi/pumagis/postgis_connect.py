""" Raw queries to postgis """

import psycopg2

db = "database"
user = "user"
password = "hunter2"

conn = psycopg2.connect(database=db, user=usr, password=pass)
c = conn.cursor()

def query(c):
    """ Be wary of SQL injection, nothing here is stopping it"""
    c.execute('SELECT name FROM table')
    return c.fetchall()
