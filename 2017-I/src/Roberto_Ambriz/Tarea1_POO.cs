/*************************************
*		ROBERTO AMBRIZ MIRANDA		 *
* 				TAREA 1				 *
*		PROGRAMACION AVANZADA 		 *
*************************************/

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TareaProgramacionAvanzada
{
    interface Idetalle
    {
       String estadoActual();
    }

    class Ropa : Idetalle
    {
        protected String material;
        protected int talla;

        public Ropa() { }
        public Ropa(String material, int talla)
        {
            this.material = material;
            this.talla = talla;
        }
        String Idetalle.estadoActual()
        {
            return String.Format("\nEstoy hecha de {0} y mi talla es {1}\n", material, talla);
        }
    }

    class Playera : Ropa,Idetalle
    {
        protected String estilo;

        public Playera(String material, int talla, String estilo)
        {
            this.material = material;
            this.talla = talla;
            this.estilo = estilo;
        }

        String Idetalle.estadoActual() 
        {
            return "Soy la playera del momento";
        }

        public String detalle()
        {
            return String.Format("Material : {0}\nTalla : {1}\nEstilo : {2}", material, talla, estilo);
        }
 
    }

    class Principal
    {
        static void Main(string[] args)
        {
            Playera p = new Playera("algodon", 24, "sin manga");
            Console.WriteLine("Datos de la Playera\n\n" + p.detalle());
            Console.WriteLine(((Idetalle)p).estadoActual());

            Ropa pantalon = new Ropa("mezclilla", 34);
            Console.WriteLine("\nDatos de la prenda: " + ((Idetalle)pantalon).estadoActual());

            Console.ReadLine();
        }
    }

    /**********SALIDA DEL PROGRAMA************/

    //Datos de la Playera

    //Material : algodon
    //Talla : 24
    //Estilo : sin manga
    //Soy la playera del momento

    //Datos de la prenda:
    //Estoy hecha de mezclilla y mi talla es 34
}
