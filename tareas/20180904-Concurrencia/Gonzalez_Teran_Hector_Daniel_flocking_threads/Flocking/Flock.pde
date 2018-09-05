// Flock class (parbada de Boids)
class Flock {
  ArrayList<Boid> boids; //La lista de los Boids

  //constructor
  Flock() { 
    boids = new ArrayList<Boid>(); 
  }

  //inicializa la parvada
  void run() {
    for (Boid b : boids) b.run(boids);  
  }

  //funcion agrega un Boid a la parvada
  void addBoid(Boid b) {
    boids.add(b);
  }
  
  //funcion que cambia el tamaño de los boids
  void changeSize(int value) {
    for (Boid b : boids) b.boid_size+=value;
  }
  
  //funcion que cambia la velocidad maxima de los boids
  void changeSpeed(int value){
    for (Boid b : boids) b.maxspeed+=value;
  }
  
  //funcion que reinicia la velocidad maxima de los boids
  void resetSpeed() {
    for (Boid b : boids) b.maxspeed=2;
  }
}
