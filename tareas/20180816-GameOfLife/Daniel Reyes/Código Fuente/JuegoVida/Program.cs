//Juego de la vida por Daniel Reyes Guillen
//Clase de Programación Avanzada Grupo 0401
using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.Drawing;
using JuegoVida;
 
    public class Program
    {
        private int[,] Matriz;    
        private Point limite;     
         
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }

        public int[,] BuscaMatriz()
        {
            return Matriz;
        }

        public void JuegoVida(int maxX, int maxY)
        {
            Matriz = new int[maxX + 1, maxY + 1];
            limite = new Point(maxX, maxY); 
        }
       
        public void LLenarMatriz(byte lleno)
        {
            Random r = new Random();

            if (lleno > 99 | lleno < 1)
                return;
            for (int x = 0; x <= Matriz.GetUpperBound(0) - 1; x++)
            {
                for (int y = 0; y <= Matriz.GetUpperBound(1) - 1; y++)
                {
                    int i = r.Next(1, 99);
                    if ( i < lleno)
                        Matriz[x, y] = 1;
                    else
                        Matriz[x, y] = 0;
                }
            }
        }

        public void Iniciar()
        {
            int Vivos;
            int[,] Matriz2 = new int[limite.X + 1, limite.Y + 1];

            for (int x = 0; x <= Matriz.GetUpperBound(0) - 1; x++)
            { for (int y = 0; y <= Matriz.GetUpperBound(1) - 1; y++)
                {
                Vivos = EstanVivos(new Point(x, y));
                    if (Matriz[x, y] == 0)
                    {
                        if (Vivos == 3)
                            Matriz2[x, y] = 1; 
                    }
                    else if (Matriz[x, y] == 1)
                    {
                        if (Vivos == 2 || Vivos == 3)
                            Matriz2[x, y] = 1;
                    }
                }
            }

            Matriz = Matriz2; 
        }
     
        private int EstanVivos(Point vecino)
        {
            List<Point> Vecinos = ListaVecinos(vecino);
            int Vivos = 0;
            foreach (Point p in Vecinos)
                Vivos += Matriz[p.X, p.Y];
            return Vivos;
        }

        private List<Point> ListaVecinos(Point vecino)
        {
            List<Point> Vecinos = new List<Point>();
            {
                var posicion = vecino;
                if (posicion.X > 0 & posicion.Y > 0)
                    Vecinos.Add(new Point(posicion.X - 1, posicion.Y - 1));
                if (posicion.Y > 0)
                    Vecinos.Add(new Point(posicion.X, posicion.Y - 1));
                if (posicion.X < limite.X & posicion.Y > 0)
                    Vecinos.Add(new Point(posicion.X + 1, posicion.Y - 1));
                if (posicion.X > 0)
                    Vecinos.Add(new Point(posicion.X - 1, posicion.Y));
                if (posicion.X < limite.X)
                    Vecinos.Add(new Point(posicion.X + 1, posicion.Y));
                if (posicion.X > 0 & posicion.Y < limite.Y)
                    Vecinos.Add(new Point(posicion.X - 1, posicion.Y + 1));
                if (posicion.Y < limite.Y)
                    Vecinos.Add(new Point(posicion.X, posicion.Y + 1));
                if (posicion.X < limite.X & posicion.Y < limite.Y)
                    Vecinos.Add(new Point(posicion.X + 1, posicion.Y + 1));
            }

            return Vecinos;
        }

        public float Densidad()
        {
            int a = 0;
            for (int x = 0; x <= Matriz.GetUpperBound(0) - 1; x++)
            {
                for (int y = 0; y <= Matriz.GetUpperBound(1) - 1; y++)
                {
                    if (Matriz[x, y] == 1)
                        a += 1;
                }
            }
            return a / (float)(Matriz.GetUpperBound(0) * Matriz.GetUpperBound(1));
        }

    }

