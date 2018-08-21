##################################################
##################################################
###     Programa del juego de la vida   ##########

Este programa simula a un autómata celular.
Este código fue realizado en lenguaje c++, utilizando librerías de opencv3.1.1

Para compilar el programa se debe tener gcc 5.0  ó superior, y basta con
ejecutar las siguientes lineas:
---> $cd build
---> $cmake ..
---> $make
---> ./lifeGame

Se utilizo una malla de 100x100. Se puede iniciar la malla con una configuración
aleatoria de una densidad especifica para el caso por "default" se tiene una
densidad = 40%.

Además acepta la opción de poner celulas vivas a conveniencia del usurio utilizando
el mouse y el click derecho del mismo.

Para evolucionar la población basta con presionar la tecla 'r' del teclado.

Para mayor información de puede ejecutar el programa con la opción --h para acceder
a la ayuda de ejecución.
