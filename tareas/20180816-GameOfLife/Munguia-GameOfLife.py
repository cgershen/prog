#basado en http://fiftyexamples.readthedocs.io/en/latest/life.html


import sys
import turtle
import random

tamanioCelula = 10

class pantalla:

    def __init__(self, x, y):
       
        self.state = set()
        self.x, self.y = x, y

    def set(self, x, y):
        celula = (x, y)
        self.state.add(celula)

    def aleatorio(self):
        self.borrar()
        for i in range(0, self.x):
            for j in range(0, self.y):
                if random.random() > 0.5:
                    self.set(i, j)

    def cambiar(self, x, y):
        celula = (x, y)
        if celula in self.state:
            self.state.remove(celula)
        else:
            self.state.add(celula)

    def borrar(self):
        self.state.clear()

    def generacion(self):
        d = set()
        for i in range(self.x):
            x_range = range( max(0, i-1), min(self.x, i+2) )
            for j in range(self.y):
                s = 0
                viva = ((i,j) in self.state)
                for yp in range( max(0, j-1), min(self.y, j+2) ):
                    for xp in x_range:
                        if (xp, yp) in self.state:
                            s += 1

                s -= viva             
                if s == 3:
                    d.add((i,j))
                elif s == 2 and viva: 
                    d.add((i,j))
                elif viva:
                    pass

        self.state = d





    def draw(self, x, y):
        turtle.penup()
        celula = (x, y)
        if celula in self.state:
            turtle.setpos(x*tamanioCelula, y*tamanioCelula)
            turtle.color('black')
            turtle.pendown()
            turtle.setheading(0)
            turtle.begin_fill()
            for i in range(4):
                turtle.forward(tamanioCelula-1)
                turtle.left(90)
            turtle.end_fill()
            
    def display(self):
        turtle.clear()
        for i in range(self.x):
            for j in range(self.y):
                self.draw(i, j)
        turtle.update()


def display_help_window():
    from turtle import TK
    root = TK.Tk()
    frame = TK.Frame()
    canvas = TK.Canvas(root, width=350, height=150, bg="white")
    canvas.pack()
    help_screen = turtle.TurtleScreen(canvas)
    help_t = turtle.RawTurtle(help_screen)
    help_t.penup()
    help_t.hideturtle()
    help_t.speed('fastest')

    width, height = help_screen.screensize()
    line_height = 20
    y = height // 2 - 30
    for s in ("Con un click las cambias entre vivas y muertas",
              " E es para borrar todo",
              " R es para llenarlo aleatoriamente",
              " S es para siguiente generacion",
              " C es para dar generaciones continuas",
              " Q para salir"):
        help_t.setpos(-(width / 2), y)
        help_t.write(s, font=('arial', 12, 'normal'))
        y -= line_height
    

def main():
    display_help_window()

    scr = turtle.Screen()
    turtle.mode('standard')
    x, y = scr.screensize()
    turtle.setworldcoordinates(0, 0, x, y)

    turtle.hideturtle()
    turtle.speed('fastest')
    turtle.tracer(0, 0)
    turtle.penup()

    board = pantalla(x // tamanioCelula, 1 + y // tamanioCelula)

    def cambiar(x, y):
        cell_x = x // tamanioCelula
        cell_y = y // tamanioCelula
      
        board.cambiar(cell_x, cell_y)
        board.display()

    turtle.onscreenclick(turtle.listen)
    turtle.onscreenclick(cambiar)

    board.aleatorio()
    board.display()

    def borrar():
        board.borrar()
        board.display()
        
    turtle.onkey(borrar, 'e')

    def aleatorio():
        board.aleatorio()
        board.display()
        
    turtle.onkey(aleatorio, 'r')
    turtle.onkey(sys.exit, 'q')


    continuo = False
    def generacionSiguiente():
        nonlocal continuo
        continuo = False
        generar()

    def continuo():
        nonlocal continuo
        continuo = True
        generar()

    def generar():
        board.generacion()
        board.display()
        if continuo:
            turtle.ontimer(generar, 25)

    turtle.onkey(generacionSiguiente, 's')
    turtle.onkey(continuo, 'c')

   
    turtle.listen()
    turtle.mainloop()

if __name__ == '__main__':
    main()