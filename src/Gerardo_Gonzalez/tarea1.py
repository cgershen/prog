class Persona :
	def __init__(self,nombre,apellido,fecha_nacimiento,sexo):
		self.nombre=nombre
		self.apellido=apellido
		self.fecha_nacimiento=fecha_nacimiento
		self.sexo=sexo

	def get_nombre_completo(self):
		print "Mi nombre completo es: "+ self.nombre+" "+self.apellido

	def get_fecha_nacimiento(self):
		print "Mi fecha de nacimiento es: "+ self.fecha_nacimiento

	def get_genero(self):
		print "Mi sexo es: "+ self.sexo


class Profesor (Persona):
	def set_salario(self,salario):
		self.salario=salario
		print "Mi salario como Profesor es de: "+str(self.salario)


class Alumno (Persona):
	def set_numero_cuenta(self,numero_cuenta):
		self.numero_cuenta=numero_cuenta
		print "Mi numero de cuenta es: "+str(self.numero_cuenta)


class Alumno_Ayudante (Profesor,Alumno):
	pass
		
if __name__ == "__main__":
	Nuevo_Ayudante=Alumno_Ayudante("Luis","Perez","05/01/1992","Masculino")
	Nuevo_Ayudante.get_nombre_completo()
	Nuevo_Ayudante.set_salario(8300)
	Nuevo_Ayudante.set_numero_cuenta(3106046)