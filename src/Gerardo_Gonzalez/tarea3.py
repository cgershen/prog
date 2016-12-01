import threading
import time

class mi_hilo (threading.Thread):
    def __init__(self, ID, nombre, contador):
        threading.Thread.__init__(self)
        self.ID = ID
        self.nombre = nombre
        self.contador = contador
    def run(self):
        print "Iniciando " + self.name
        threadLock.acquire()
        imprimir_tiempo(self.nombre, self.contador, 3)
        threadLock.release()

def imprimir_tiempo(nombre, espera, contador):
    while contador:
        time.sleep(espera)
        print "%s: %s" % (nombre, time.ctime(time.time()))
        contador -= 1

threadLock = threading.Lock()
#Lista de hilos
hilos = []

# Creacion de hilos nuevos
hilo1 = mi_hilo(1, "Thread-1", 1)
hilo2 = mi_hilo(2, "Thread-2", 2)

# Iniciar los hilos creados
hilo1.start()
hilo2.start()

# Agregar los hilos a la lista
hilos.append(hilo1)
hilos.append(hilo2)

#Esperar a que todos los hilos terminen.
#join([time]): The join() waits for threads to terminate.
for hilo in hilos:
    hilo.join()
