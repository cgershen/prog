#!/usr/bin/python
# -*- coding: utf-8 -*-

import itertools 

def fibonacci():
    a,b = 0,1
    while True:
        yield a
        b = a+b
        yield b
        a = a+b

print("Suceción de Fibonacci\n")

generador = fibonacci()

try: 
    cuantos = int(raw_input('¿Cuántos elementos de la serie deseas imprimir? '))

    for x in itertools.islice(generador, cuantos):
        print x

except ValueError:
    print("Es imprescindible que escribas un número mayor a cero\n")

except:
    print("Error inesperado:", sys.exc_info()[0])
    raise

siguiente =''
while (siguiente != 'n'):
	print next(generador)
	siguiente = raw_input("¿Deseas obtener otro elemento? [S/n]")


Referencia: http://stackoverflow.com/questions/3953749/python-fibonacci-generator
