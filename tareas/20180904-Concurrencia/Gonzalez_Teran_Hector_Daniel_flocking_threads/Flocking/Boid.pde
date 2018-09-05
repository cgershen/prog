// Boid class
class Boid extends Thread{

  PVector position;
  PVector velocity;
  PVector acceleration;
  float maxforce;
  float maxspeed;
  float boid_size, boid_mass;
  float separation_value, align_value, cohesion_value;

  //Constructor
  Boid(float x, float y) {
    position = new PVector(x, y); 
    velocity = PVector.random2D();
    acceleration = new PVector(0, 0);
    boid_size = 2.0;
    boid_mass = random(1,3);
    maxspeed = 2;
    maxforce = 0.05;
    separation_value = 1.5;
    align_value = 1;
    cohesion_value = 1;
  }

  //funcion que calcula el comportamiento de los Boid con respecto a la parvada existentes
  void run(ArrayList<Boid> boids) {
    flocking(boids);
    update();
    borders();
    render();
  }

  //funcion que acumula en el vector de aceleracion las fuerzas que influyen en
  //el comportamiento del boid (siempre dependiente de la masa al boid
  //recordemos que F=m*a, entonces: a=F/m)
  void applyForce(PVector force) {
    force.div(boid_mass);
    acceleration.add(force);
  }

  //funcion que genera los vectores que dan forma a la parvada de Boids 
  //ref: https://en.wikipedia.org/wiki/Boids
  void flocking(ArrayList<Boid> boids) {
    PVector sep = separate(boids);//genera el vector de separacion entre los boids de la parvada
    PVector ali = align(boids);//genera el vector de direccion de la parvada
    PVector coh = cohesion(boids);//genera el vector de cohesion de la parvada
    sep.mult(separation_value);
    ali.mult(align_value);
    coh.mult(cohesion_value);
    applyForce(sep);
    applyForce(ali);
    applyForce(coh);
  }

  //funcion que actualiza la posición del boid
  void update() {
    velocity.add(acceleration);
    position.add(velocity);
    acceleration.mult(0); //Reseteamos el vector de aceleracion para que mantenga la cohesion
  }
  
  //funcion que genera la apariencia de espacio toroidal para la posicion de los boids en los extremos 
  void borders() {
    if (position.x < -boid_size) position.x = width+boid_size;
    if (position.y < -boid_size) position.y = height+boid_size;
    if (position.x > width+boid_size) position.x = -boid_size;
    if (position.y > height+boid_size) position.y = -boid_size;
  }

  //funcion que crea la forma de los Boids
  void render() {
    float theta = velocity.heading() + radians(90); //obtenemos el angulo de rotación de la velocidad
    fill(200, 100);
    stroke(255);
    pushMatrix();
    translate(position.x, position.y);
    rotate(theta);
    beginShape(TRIANGLES);
    vertex(0, -boid_size*2);
    vertex(-boid_size*2, boid_size*2);
    vertex(boid_size*2, boid_size*2);
    endShape();
    popMatrix();
  }

  //funcion que calcula el vector de sepacion de cada Boid con repecto a la parvada
  //evita que se formen parvadas locales
  PVector separate (ArrayList<Boid> boids) {
    float distseparation = 30;
    PVector sep = new PVector(0, 0, 0);
    int count = 0;
    for (Boid other : boids) {
      float d = PVector.dist(position, other.position);
      if ((d > 0) && (d < distseparation)) {
        PVector diff = PVector.sub(position, other.position);
        diff.normalize();
        diff.div(d);
        sep.add(diff);
        count++;
      }
    }
    if (count > 0) sep.div((float)count);
    if (sep.mag() > 0) {
      sep.normalize();
      sep.mult(maxspeed);
      sep.limit(maxforce);
    }
    return sep;
  }

  //funcion que calcula el vector de orientacion de la parvada
  //solo se saca el promedio de la posicion de una parte de la parvada dentro de una vecindad
  PVector align (ArrayList<Boid> boids) {
    float neighbordist = 50;
    PVector sum = new PVector(0, 0);
    int count = 0;
    for (Boid other : boids) {
      float d = PVector.dist(position, other.position);
      if ((d > 0) && (d < neighbordist)) {
        sum.add(other.velocity);
        count++;
      }
    }
    if (count > 0) {
      sum.div((float)count);
      sum.normalize();
      sum.mult(maxspeed);
      PVector ali = PVector.sub(sum, velocity);
      ali.limit(maxforce);
      return ali;
    } 
    else {
      return new PVector(0, 0);
    }
  }

  //calcula el centro de masa de la parvada para mantener la cohesion entre cada boid
  PVector cohesion (ArrayList<Boid> boids) {
    float ball = 50;
    PVector mass = new PVector(0, 0);
    int count = 0;
    for (Boid other : boids) {
      float d = PVector.dist(position, other.position);
      if ((d > 0) && (d < ball)) {
        mass.add(other.position);
        count++;
      }
    }
    if (count > 0) {
      mass.div(count);
      PVector desired = PVector.sub(mass, position);
      desired.normalize();
      desired.mult(maxspeed);
      PVector steer = PVector.sub(desired, velocity);
      steer.limit(maxforce);
      return steer;
    } 
    else return new PVector(0, 0);
  }
}
