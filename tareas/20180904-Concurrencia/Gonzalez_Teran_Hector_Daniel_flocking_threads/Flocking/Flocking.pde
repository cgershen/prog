Flock flock;
Boid boid;


//setup del programa general
void setup() {
  size(900, 700);
  flock = new Flock();
  for (int i = 0; i < 150; i++) { 
    boid = new Boid(width/2,height/2);
    flock.addBoid(boid);
    boid.start();
  }
}

//funcion que dibuja todo
void draw() {
  background(0);
  flock.run();
  textSize(20);
}

//podemos agregar un boid si hacemos click en la interfaz
void mousePressed() {
  flock.addBoid(new Boid(mouseX,mouseY));
}

//comandos por teclas
// b = crea un Boid en la interfaz al azar
// s = reinicia la velocidad maxima
// Tecla de navegacion superior = aumenta el tamaño de los boids
// Tecla de navegacion inferior = disminuye el tamaño de los boids
void keyPressed() {
  if (key == 'b') {
    boid = new Boid(random(200,800),random(300,500));
    flock.addBoid(boid); 
    boid.start();
  }
  else if (key == 's') { flock.resetSpeed(); }
  if (key == CODED) {
    if (keyCode == UP) { flock.changeSize(1); }
    else if (keyCode == DOWN) { flock.changeSize(-1); }
    else if (keyCode == LEFT) { flock.changeSpeed(-1); }
    else if (keyCode == RIGHT) { flock.changeSpeed(1); }
  }
}
