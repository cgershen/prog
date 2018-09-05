# Game of Life
Esta implementación del modelo _Boids_ de C. Reynolds fue hecha como tarea para la clase de Programación Avanzada, del PCIC del IIMAS, UNAM.

## Pre-Requisitos

Este proyecto hace uso de diversas librerías pertenecientes a las librerías estándar de los cursos de algoritmos del Profesor Robert Sedgewick. La única razón de elegir a estas librerías como ayuda para la programación del proyecto fue mi propia familiaridad con las mismas. Para instalar dichas librerías, siga el siguiente
[link](https://introcs.cs.princeton.edu/java/stdlib/). En particular, se hace uso de StdDraw, StdRandom, Point2D y Vector.

## Introducción
Este proyecto se construyó para cumplir con dos objetivos:
1. Simulación del modelo _Boids_, bajo las reglas estándar que establece C. Reynolds en el siguiente [link](https://www.red3d.com/cwr/boids/), y algunas reglas extra que se explicarán más adelante.
2. Realizar alguna implementación de Threads dentro del modelo.

## Descripción de la API
Hay cuatro clases en este proyecto, mismas que se describen a continuación.
1. **Boid.java**: Representa la clase que representa a las aves individuales, y las variables y métodos que establecen su comportamiento dentro de la simulación.
2. **Flock.java**: Representa a un conjunto (parvada) de aves. Esta clase extiende la clase _Thread_ y, al ser instanciada y ejecutada, lleva a cabo la simulación del comportamiento de una parvada como un hilo independiente.
3. **Predator.java**: Representa a un ave depredador. Esta clase extiende la clase _Thread_ y, al ser instanciada y ejecutada, simula la aparición de un ave depredador de comportamiento aleatorio dentro como un hilo independiente.
4. **Simulation.java**: Es la clase que se encarga de instanciar y ejecutar a la parvada (Flock) y al depredador (Predator), y de dibujar la simulación en el plano.

Todas estas clases contienen código comentado en cada uno de sus métodos.

## Funcionamiento
### Reglas de funcionamiento de la parvada
En cada momento del tiempo, cuando a un ave se le pide actualice su velocidad dentro de la simulación, ésta lo hace a través del cálculo de un vector de aceleración que es el resultado de sumar cinco vectores distintos, cuyas funciones respectivas son las de:

1. Hacer que el ave busque igualar la dirección de su velocidad con la de sus vecinos (vector _Alignment_).
2. Hacer que el ave evite impactarse contra otras aves (vector _Separation_).
3. Hacer que el ave se dirija hacia sus vecinos (vector _Cohesion_).
4. Hacer que el ave busque siempre mantenerse cerca del origen (vector _Home_).
5. Hacer que el ave vuele lejos del depredador (vector _Predator_).

El cálculo de cada uno de estos vectores es llevado a cabo por métodos privados dentro de la clase *Boid.java*, donde cada uno toma como parámetro al conjunto de aves vecinas de la ave en cuestión. Una vez calculados estos vectores, éstos se escalan de acuerdo a unas constantes globales configurables (_C_ALIGNMENT_, _C_SEPARATION_, _C_COHESION_, _C_HOME_ y _C_PREDATOR_), se suman y su resultado es sumado a la velocidad actual del ave (dando como resultado un vector de velocidad con una nueva dirección y magnitud). Para evitar que la velocidad con la que las aves pueden trasladarse a través del plano se incremente demasiado rápido, antes de ser sumado al vector de velocidad del ave, este vector de aceleración se convierte en un vector unitario y se multiplica por una constante global configurable (_C_MAGNITUDE_) que representa la magnitud de la aceleración máxima posible del ave. Todo esto se lleva a cabo en el método _updateVelocity()_:

```javascript
  public void updateVelocity(Boid[] neighbors, Vector predator) {
      Stack<Boid> nearest = findNearest(neighbors);
      Vector separation = calculateSeparationVector(nearest).scale(C_SEPARATION);
      Vector alignment = calculateAlignmentVector(nearest).scale(C_ALIGNMENT);
      Vector cohesion = calculateCohesionVector(nearest).scale(C_COHESION);
      Vector home = calculateHomeVector().scale(C_HOME);
      Vector pred = calculatePredatorVector(predator).scale(C_PREDATOR);
      .
      .
      .
  }
```

### Funcionamiento de la simulación
Una vez que se pone en marcha una simulación a través de la clase *Simulation.java*, ésta:

1. Inicializa el plano donde se dibujará la simulación.
2. Inicializa a un ave depredaor en el origen a través de la clase *Predator.java*. Esta clase se encarga de simular a un depredador de comportamiento aleatorio en las cercanías del origen, en un hilo de procesamiento independiente.
3. Inicializa a una parvada de un determinado número de aves (configurable ) con una velocidad y una posición aleatoria dentro de una regioń confinada del plano (configurable dentro de la clase *Boid.java*, con las constantes _X_LIMIT_, _Y_LIMIT_, _VX_LIMIT_ y _VY_LIMIT_). Esta parvada es representada por un arreglo de tipo _Boid[]_ que se pasa como parámetro hacia la clase *Flock.java*. Esta clase se encarga de pedir la actualización de la posición y la velocidad de cada una de las aves dentro de la parvada de manera continua, de acuerdo a las reglas presentadas anteriormente, en un hilo de procesamiento indepentiente.
4. Inicializa a una _LinkedBlockedQueue_ que se encargará de ser el medio a través del cual el hilo de procesamiento de la clase *Predator.java* le comunicará al hilo de la clase *Flock.java* la posición del depredador en todo momento de la simulación. Esta _LinkedBlockedQueue_ se pasa como parámetro a cada una de dichas clases.
5. Actualiza los dibujos de la parvada y del depredador en el plano de forma continua.

## Puesta en marcha de la simulación
Para corre una simulación del modelo, sólo hace falta compilar los cuatro archivos, y ejecutar el programa *Simulation.java*. Desde el _bash_, esto puede lograrse de la siguiente manera:

```
$ javac-algs4 *.java
$ java-algs4 Simulation.java
```
