//Juego de la vida por Daniel Reyes Guillen
//Clase de Programación Avanzada Grupo 0401
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace JuegoVida
{
    public partial class Form1 : Form
    {
        Program P = new Program();
        int  ciclo = 0;
        private int[,] Matriz;
        int densidad = 0;

        public Form1()
        {
            InitializeComponent();
            P.JuegoVida(40, 40);
            IniciaMatrix(P.BuscaMatriz());
            Graficar();
            textBox1.Text = "50"; //por default es 50% de probabilidad 
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (button1.Text.Equals("Iniciar"))
            {
                
                timer1.Enabled = true; //Inicia el reloj
                button1.Text="Pausar";
               
            }
            else if(button1.Text.Equals("Pausar"))
            {
                timer1.Enabled=false;
                button1.Text="Iniciar";
             
            }
        }
 
        public void IniciaMatrix(int[,] matrix)
        {
            Matriz = matrix;
            Graficar();
        }
   
        public void Graficar()
        {
            var limiteX = Matriz.GetUpperBound(0);
            var limiteY = Matriz.GetUpperBound(1);
            Bitmap mapa = GraficarMalla(limiteX, limiteY, pictureBox1.Width, pictureBox1.Height);
            GraficarPuntos(mapa);
            pictureBox1.Image = mapa;
        }
 
        private Bitmap GraficarMalla(int x, int y, int w, int h)
        {
           
            Bitmap mapa = new Bitmap(w, h);
            Graphics grafica = Graphics.FromImage(mapa);
            Pen lineas = new Pen(Color.Black, 1);
            grafica.DrawRectangle(lineas, 0, 0, w - 1, h - 1);

            float gapX = w / (float)x;
            for (int i = 1; i <= x - 1; i++)
            {
                float lx = gapX * i;
                grafica.DrawLine(lineas, lx, 0, lx, h);
            }
            float gapY = h / (float)y;
            for (int i = 1; i <= y - 1; i++)
            {
                float ly = gapY * i;
                grafica.DrawLine(lineas, 0, ly, w, ly);
            }
            return mapa;
        }

        private void GraficarPuntos(Bitmap mapa)
        {
            Graphics grafica = Graphics.FromImage(mapa);
            Brush color = Brushes.Red;

            float gapX = mapa.Width / (float)Matriz.GetUpperBound(0);
            float gapY = mapa.Height / (float)Matriz.GetUpperBound(1);
            for (var x = 1; x <= Matriz.GetUpperBound(0); x++)
            {
                for (var y = 1; y <= Matriz.GetUpperBound(1); y++)
                {
                    if (Matriz[x - 1, y - 1] == 1)
                        grafica.FillEllipse(color, gapX * (x - 1) + 1, gapY * (y - 1) + 1, gapX - 2, gapY - 2);
                }
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            byte densidad = 0;
         
            try
            {
                if (textBox1.Text.Trim() != "")
                    densidad = Convert.ToByte(textBox1.Text);
                else
                    MessageBox.Show("La casilla no puede estar vacía");
            }
            catch (Exception){}
            P.JuegoVida(50, 50); //es de 50 x 50 porque más chico ya no se distingue bien
            P.LLenarMatriz(densidad);
            IniciaMatrix(P.BuscaMatriz());
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
           
            P.Iniciar();
            IniciaMatrix(P.BuscaMatriz());
            ciclo=ciclo+1;
            densidad = Convert.ToInt32(P.Densidad() * 100);
            string s = string.Format("Ciclo: {0}", ciclo);
            label1.Text = s;
            string d = string.Format("Densidad: {0}", densidad);
            label3.Text = d;
           
        }

        private void button3_Click(object sender, EventArgs e)
        {

            timer1.Enabled = false; //Detiene el reloj
            P.LLenarMatriz(0); //Reinicia la matriz
            P.JuegoVida(50, 50);
            IniciaMatrix(P.BuscaMatriz());
            Graficar(); //La vuelve a pintar
            ciclo = 0; //Reinicia el contador
            densidad = 0;
            string s = string.Format("Ciclo: {0}", ciclo);
            label1.Text = s;
            string d = string.Format("Densidad: {0}", densidad);
            label3.Text = d;
        }

    }
}
