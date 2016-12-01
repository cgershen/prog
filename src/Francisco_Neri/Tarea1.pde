/****************************************************************/
/*PROYECTO    : Tarea 1 (Programación Avanzada)                 */
/*AUTOR       : José Francisco Neri González                    */
/*FECHA       : 16/Agosto/2016                                  */
/*DESCRIPCIÓN : Ejemplo en OOP(Processing) utilizando clases,   */
/*objetos, método, herencia,interfaz,etc.Despliega 3 rectangulos*/
/*simulando 2 autos y 1 trailer avanzando a distinta velocidad. */
/****************************************************************/

//Declaración de los Objetos
Auto AutoAzul; 
Auto AutoRojo;
Trailer AutoVerde;

void setup() {
  size(500, 500);
  AutoAzul  = new Auto(color(0, 0, 255), 0, 100, 2); //Inicialización de los Objetos
  AutoRojo  = new Auto(color(255, 0, 0), 0, 150, 3); 
  AutoVerde = new Trailer(color(0, 255, 0), 0, 200, 1);
}

void draw() {
  background(255);
  textSize(20);
  text("¡Hacer un clic izquierdo sostenido!", 10, 30); 
  fill(0, 102, 153);
  if (mousePressed) 
  {
    //Llamado de los métodos de los Objetos
    AutoRojo.drive();
    AutoRojo.display();
    
    AutoAzul.drive();
    AutoAzul.display();
    
    AutoVerde.displayTrailer();
    AutoVerde.drive();
    fill(0);
  }
}

// Clase Auto
class Auto { 
  color c;
  float pos_x;
  float pos_y;
  float velocidad;

  // Constructor definido con argumentos
  Auto(color temp_c, float temp_pos_x, float temp_pos_y, float temp_velocidad) { 
    c = temp_c;
    pos_x = temp_pos_x;
    pos_y = temp_pos_y;
    velocidad = temp_velocidad;
  }

  void display() {
    stroke(0);
    fill(c);
    rectMode(CENTER);
    rect(pos_x, pos_y, 20, 10);
  }

  void drive() {
    pos_x = pos_x + velocidad;
    if (pos_x > width) {
      pos_x = 0;
    }
  }
}

//Clase Trailer (Hereda métodos de Auto)
class Trailer extends Auto {
  Trailer(color c, float pos_x, float pos_y, float velocidad) {
    super(c, pos_x, pos_y, velocidad);
  }

  void displayTrailer() {
    stroke(200);
    fill(c);
    rectMode(CENTER);
    rect(pos_x, pos_y, 60, 10);
  }
}
