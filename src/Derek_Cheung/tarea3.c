#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>

/* Compile and link with -lpthread */

int recurso = 20; // recurso compartido

/////////////////////////
//
// Ejemplo de hilos
//
////////////////

/**
 * Consume el recurso
 */
void *consumador() {
	int i;
	for(i=0;i<10;i++) { // número de recursos que cada consumador consume
		printf("Actualmente recurso es %i\n", recurso);
		recurso--; // consume

		usleep(rand() % 5); // sleep para dejar chance a otros consumadores
	}
}

void ejemplo_hilos() {

	pthread_t hilo1, hilo2; // asignar memoria

	// pointer a hilo, opciones, función, attr
	pthread_create(&hilo1, NULL, consumador, NULL);
	pthread_create(&hilo2, NULL, consumador, NULL);

	// esperar hasta que el hilo termina
	pthread_join(hilo1, NULL);
	pthread_join(hilo2, NULL);

}

/////////////////////////
//
// Ejemplo de syncronización
//
////////////////

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

/**
 * Consume el recurso seguramente
 */
void *consumador_mutex() {

	int i;
	for(i=0;i<10;i++) {

		pthread_mutex_lock(&mutex); // adquirir exclusividad

		// sección crítico

		printf("Actualmente recurso es %i\n", recurso);
		recurso--;

		// fin de sección crítico

		pthread_mutex_unlock(&mutex); // liberar exclusividad

		usleep(rand() % 5);

	}

}

void ejemplo_mutex() {

	pthread_t hilo1, hilo2;

	pthread_create(&hilo1, NULL, consumador_mutex, NULL);
	pthread_create(&hilo2, NULL, consumador_mutex, NULL);

	pthread_join(hilo1, NULL);
	pthread_join(hilo2, NULL);

}

/////////////////////////
//
// Ejemplo de una cola compartida
//
////////////////

#define COLA_LEN 10

int cola[COLA_LEN];		// declarar cola
int cola_indice = -1;	// define cuales valores en la cola son válidos


/**
 * Consume valores validos en la cola
 */
void *consumador_cola() {

	int i;
	for(i=0;i<COLA_LEN;i++) {

		while(1==1) { // esperar hasta que un valor sea válido
			pthread_mutex_lock(&mutex); // adquirir exclusividad

			// sección crítico
			
			// checa para ver si hay un valor válido
			if(cola_indice >= 0) {
				// sí hay, deja de esperar
				break;
			} else {

				// fin sección crítico
				
				// no hay, librar la exclusividad y esperar
				pthread_mutex_unlock(&mutex);
				usleep((rand() % 5) * 100000); // microsegundos
				// continue
			}
		}

		// todavía sección crítico

		// leer el valor
		printf("Mira! Hay un %i\n", cola[cola_indice]);
		// consumirlo
		cola_indice--;

		// fin sección crítico

		// librar la exclusividad
		pthread_mutex_unlock(&mutex);
	}

}

/**
 * Generar valores validos para la cola
 */
void *generador_cola() {

	int i;
	for(i=0;i<COLA_LEN;i++) {

		pthread_mutex_lock(&mutex);

		// sección crítico
		
		cola[++cola_indice] = rand() % 100; // generar valor

		// fin de sección crítico

		pthread_mutex_unlock(&mutex);

		usleep((rand() % 5) * 100000); // microsegundos
	}

}

void ejemplo_cola() {

	pthread_t hilo1, hilo2;

	pthread_create(&hilo1, NULL, consumador_cola, NULL); // un consumador
	pthread_create(&hilo2, NULL, generador_cola, NULL); // un generador

	pthread_join(hilo1, NULL);
	pthread_join(hilo2, NULL);

}

///////////
//
// Main
//
//////

int main() {

	ejemplo_hilos();
	ejemplo_mutex();
	ejemplo_cola();

}
