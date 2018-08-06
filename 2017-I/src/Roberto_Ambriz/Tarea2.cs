using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tarea2Programacion
{
    class IteraGenera: System.Collections.IEnumerable
    {
        public System.Collections.IEnumerator GetEnumerator()
        {
            for (int i = 0; i < 10; i++)
            {
                yield return i;
            }
        }

        public System.Collections.IEnumerable Iterador(int a, int b)
        {
            for (int i = a; i < b; i++)
            {
                yield return i;
            }
        }
    }
    class Tarea2
    {
        static void Main(string[] args)
        {
            IteraGenera obj = new IteraGenera();

            foreach (int valor in obj)
            {
                Console.Write(valor + " ");
            }

            Console.WriteLine();

            foreach (int valor in obj.Iterador(10, 20))
            {
                Console.Write(valor + " ");
            }

            Console.WriteLine();

            int a = 100, b = 2;
            try
            {
                Console.WriteLine(a / b);

                if (b != 0)
                {
                    throw new ArgumentNullException();
                }
            }
            catch (DivideByZeroException)
            {
                Console.WriteLine("Se intento una division entre cero");
            }
            catch (ArgumentNullException e)
            {
                Console.WriteLine("Error al intentar leer un objeto NULL");
            }
            catch (Exception e)
            {
                // Representa un excepción general
            }
            finally
            {
                // Este bloque siempre se ejecuta ya sea que termine correctamente el bloque del TRY
                // o se haya ingresado en algún bloque CATCH
            }

            Console.ReadLine();
        }
    }
}
