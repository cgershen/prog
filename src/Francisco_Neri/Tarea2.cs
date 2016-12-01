using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/****************************************************************/
/*PROGRAMA    : Manejo de excepciones, generadores e iteradores.*/
/*AUTOR       : José Francisco Neri González                    */
/*FECHA       : 25/Agosto/2016                                  */
/*DESCRIPCIÓN : Ejemplo de excepciones, generadores e iteradores*/
/*en lenguaje c#.                                               */
/****************************************************************/

namespace Try_Generadores_Iteradores
{
    class Program
    {
        static void Main(string[] args)
        {
            //Iteradores
            foreach (int number in SomeNumbers())
            {
                Console.Write(number.ToString() + " ");
            }
        
            Console.ReadKey();

            try
            {
                string s = null;
                ProcessString(s);
            }
            // Most specific:
            catch (ArgumentNullException e)
            {
                Console.WriteLine("{0} First exception caught.", e);
            }
            // Least specific:
            catch (Exception e)
            {
                Console.WriteLine("{0} Second exception caught.", e);
            }
        }

        static void ProcessString(string s)
        {
            if (s == null)
            {
                throw new ArgumentNullException();
            }
        }

        public static System.Collections.IEnumerable SomeNumbers()
        {
            yield return 3;
            yield return 5;
            yield return 8;
        }
    }
}
