# Game of Life
Esta implementación de Java del Juego de la Vida (Game of Life) de Conway fue hecha como tarea para la clase de Programación Avanzada, del PCIC del IIMAS, UNAM.

## Pre-Requisitos

Este proyecto hace uso de diversas librerías pertenecientes a las librerías estándar de los cursos de algoritmos del Profesor Robert Sedgewick. La única razón de
elegir a estas librerías como ayuda para la programación del proyecto fue mi propia familiaridad con las mismas. Para instalar dichas librerías, siga el siguiente
[link](https://introcs.cs.princeton.edu/java/stdlib/). En particular, se hace uso de StdDraw, StdRandom y StdIn.

## Introducción
Este proyecto se construyó para cumplir con tres propósitos:
1. Simulación del Juego de la Vida, dada una determinada parrilla inicial de n x n, y ciertas reglas configurables.
2. Investigar el comportamiento y evolución del Juego de la Vida, dadas diferentes condiciones iniciales, a partir de una serie de experimentos de Monte Carlo.
3. Encontrar patrones de comportamiento dentro de pequeños cúmulos de celdas dentro de una simulación de un Juego de la Vida.

## Descripción de la API
Hay cuatro clases en este proyecto, mismas que se describen a continuación.
1. **GLGrid.java**: Representa el núcleo del Juego de la Vida. Se conforma por una parrilla de n x n celdas (configurable), y los métodos esenciales para la manipulación de la misma.
2. **GLUtilities.java**: Representa un conjunto de métodos diversos para realizar simulaciones de Juegos de la Vida, encontrar patrones, etcétera.
3. **GLStats.java**: Se conforma de los métodos necesarios para realizar los experimentos Monte Carlo.
4. **GLVisualizer.java**: Se conforma de los métodos necesarios para mostrar en pantalla, en tiempo real, las simulaciones del Juego de la Vida.

Todas estas clases contienen código comentado en cada uno de sus métodos.

## Funcionamiento
### Primer Propósito: Simulación del Juego de la Vida
La clase *GLVisualizer* puede correr simulaciones del Juego de la Vida tomando como parrilla de partida tres distintas fuentes:
1. Parrilla de n x n aleatoria
2. Parrilla de n x n proveniente de un archivo de texto
3. Parrilla de n x n generada desde algún método de *GLUtilities* (esta forma fue usada específicamente para los experimentos llevados a cabo para encontrar patrones)

Dado que aún no se ha implementado una HMI de funcionamiento completo que permita al usuario cambiar entre uno de los tres métodos de obtención de parrilla inicial anteriores,
para usar una u otra, se debe modificar el código presente en el método _main_ de la clase _GLVisualizer_, y comentar/descomentar la línea apropiada:

```javascript
    public static void main (String[] args) {
        showRandomGrid(100, 0.5, true);
        //showStdInputGrid(4, false, args);
        //showInterestingGrid(30, 3, 115, true);
        //showPatterns(30, 6, true, args);
    }
```

El método por defecto es _showRandomGrid_ que realiza una simulación del Juego de la Vida con una parrilla inicial aleatoria. En el código mostrado arriba, se aprecia que la
configuración por defecto es una parrilla de 100 x 100 celdas, con una probabilidad de que cada una de ellas esté viva al inicio de la simulación del 50%. Para correr esta simulación, sólo hace falta escribir en el _bash_:

```
$ java-algs4 GLVisualizer
```

Para observar el funcionamiento de todas estas formas de simulación, se han incluido diversas animaciones dentro de la carpeta del proyecto llamada _resultados_.

### Segundo Propósito: Experimentos Monte Carlo
La clase *GLStats* puede correr varias simulaciones del Juego de la Vida, dados ciertos parámetros iniciales. El objetivo es verificar a qué densidades (porcentaje de probabilidad de que una celda esté viva al inicio de la simulación) de celdas vivas existe mayor actividad en la parrilla (o bien, a qué densidades el Juego de la Vida tarda más tiempo en terminar). Específicamente toma como input los siguientes parámetros:
* *n*: Tamaño de la Parrilla
* *mc_trials*: Número de simulaciones por densidad.
* *pmin*: Densidad inicial (valor entre [0..100]).
* *pmin*: Densidad final (valor entre [0..100]).
* *step*: Incremento de la densidad por experimento (valor entre [0..100])

Para modificar dichos parámetros, éstos deben cambiarse directamente el el constructor de la clase:

```javascript
public GLStats(int n, int mc_trials, int pmin, int pmax, int step) {
  .
  .
  .
}
```
Una vez que el método _main_ es ejecutado, los resultados del experimento se escriben hacia el _standard output_. Para correr el experimento y redirigir los resultados hacia un archivo de texto, basta con escribir en el _bash_:

```
$ java-algs4 GLStats > estadisticas.txt
```
Una compilación de resultados se incluye dentro de una hoja de cálculo incluida dentro de la carpeta _resultados_.

### Tercer Propósito: Encontrar patrones
Por especificación de la tarea, se buscan tres clases de comportamientos:
* Arreglos de celdas estáticos: aquel cúmulo de celdas que no cambia su estado.
* Patrones oscilatorios: aquel cúmulo de celdas que oscila en un patrón de periodo determinado.
* Patrones de movimiento: aquel cúmulo de celdas que se mueve constantemente a través de la parrilla.

Los métodos para encontrar cada uno de estos patrones se encuentran dentro de la clase _GLUtilities_ y éstos son _staticPatternSearch()_, _oscillatoryPatternSearch()_ y _movementPatternSearch()_, respectivamente. Para ejecutar uno u otro, debe comentarse/descomentarse la línea apropiada dentro del método _main_ de esta clase:

```javascript
public static void main(String[] args) {
    GLUtilities utilities = new GLUtilities();
    //utilities.staticPatternSearch(20, 4);
    //utilities.oscillatoryPatternSearch(20, 4, 2);
    utilities.movementPatternSearch(30, 3, 100);
}
```

Todos los métodos anteriores se basan en la inicialización controlada de la parrilla inicial. Para la inicialización de las parrillas que estos métodos usan, se hace uso del método _interestingGrid_. Este método consiste en generar parrillas cuyas celdas vivas iniciales se confinen exclusivamente a una pequeña área determinada de m x m, ubicada al centro de la parrilla de n x n (m < n).

Una vez que el método _main_ es ejecutado, los métodos mencionados escribirán todos aquellos patrones encontrados hacia el _standard output_. Para correr una búsqueda y redirigir los resultados hacia un archivo de texto, bastará con escribir en el _bash_:

```
$ java-algs4 GLUtilities > archivopatrones.txt
```
### Visualización de los patrones encontrados
Para visualizar los patrones encontrados, uno tras otro, primero debe descomentarse el método _showPatterns_ dentro del método _main_ de la clase _GLVisualizer_:

```javascript
    public static void main (String[] args) {
        //showRandomGrid(100, 0.5, true);
        //showStdInputGrid(4, false, args);
        //showInterestingGrid(30, 3, 115, true);
        showPatterns(30, 6, true, args);
    }
```

Dado que el archivo se modificó, antes de correrse, primero debe compilarse. Para hacer esto, hace falta escribir en el _bash_:

```
$ javac-algs4 GLVisualizer.java
```

Por último, bastará con escribir en el _bash_:

```
$ java-algs4 GLVisualizer archivopatrones.txt
```
Para observar el funcionamiento de todas estos métodos de búsqueda de patrones (junto con la visualización de los patrones encontrados), se han incluido diversas animaciones dentro de la carpeta _resultados_.
