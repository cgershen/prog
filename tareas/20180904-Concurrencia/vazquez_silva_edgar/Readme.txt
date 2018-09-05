Tarea 2

En esta tarea se muestra una aplicación en c++ de hilos enfocado a resover el problema
de actividades multiagente. Se optó por programar un patrón de siguimiento para una
toruga la cual se muestra como el objetivo a seguir para demás tortugas.

Se generón un coportamiento aleatorio para la tortuga lider, y se inicializón una población
de 20 tortugas, las cuales ejecutaban una función de control de posición para seguir a la toruga
lider.

***Como se puede apreciar en las imagens anexas.

La tarea se desarrolló utilizando la paquetería de ROS. y lenguaje C++.

Basta con ejecutar desde una terminal en linux:

  $ roslaunch turtles turtles_init_world.launch

Posteriormente en otra terminal:

  $ rosrun turtles turtles_threads

Y con ello incializará la simulación.
