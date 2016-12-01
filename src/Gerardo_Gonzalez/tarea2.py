"""
Iteradores en Python.
"""
#Cuando se agota el iterador se lanza una excepcion.

def iterar_lista(lista):
	mi_iterador_lista=iter(lista)
	try:
		while(True):
			print mi_iterador_lista.next()
	except StopIteration:
		print "Se agoto el iterador sobre la lista"

def iterar_cadena(cadena):
	mi_iterador_cadena=iter(cadena)
	try:
		while(True):
			print mi_iterador_cadena.next()
	except StopIteration:
		print "Se agoto el iterador sobre la cadena"
"""
Generadores en Python.
"""
def mi_primer_generador():
	yield 1
	yield 2

def naturales_generador():
	n = 0
	while True:
		yield n
		n = n + 1

"""
Excepciones en Python
"""
def ejemplo_excepciones():
	try:
		print 10 * (1/0)
	except ZeroDivisionError:
		print "Se realizo una division entre cero"

class MiError(Exception):
	def __init__(self, valor):
		self.valor = valor
	def __str__(self):
		return repr(self.valor)

def usando_mi_excepcion():
	try:
		raise  MiError(2*2)
	except MiError as err:
		print "El valor en el error es",err.valor
		print err

if __name__ == "__main__":
	print "#######ITERADORES##########"
	numeros=[1,2,3,4,5]
	mi_iterador=iter(numeros)
	print mi_iterador.next()
	print mi_iterador.next()
	#Iterar lista
	iterar_lista(numeros)
	#Iterar cadena
	mi_cadena="Hola mundo"
	iterar_cadena(mi_cadena)
	print "#######GENERADORES#########"
	gen=mi_primer_generador()
	print gen.next()
	print gen.next()
	naturales=naturales_generador()
	for i in naturales:
		if i!=100:
			print i
		else:
			break
	print naturales.next()
	print "#######EXCEPCIONES#########"
	ejemplo_excepciones()
	usando_mi_excepcion()