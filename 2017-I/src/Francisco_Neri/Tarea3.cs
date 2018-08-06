using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;
using System.IO;

/****************************************************************/
/*PROGRAMA    : HILOS Y SINCRONIZACION                          */
/*AUTOR       : José Francisco Neri González                    */
/*FECHA       : 01/Septiembre/2016                              */
/*DESCRIPCIÓN : Ejemplificación de Hilos y sincronización de los*/
/*mismos en lenguaje C#.                                        */
/****************************************************************/

namespace Hilos_Sincronizacion
{
    class Program
    {
        static int count = 0;
        static object ThisLock = new object();
        static void Main(string[] args)
        {
            var Hilo1 = new Thread(Incrementa_Contador); // Hilo 1
            var Hilo2 = new Thread(Incrementa_Contador); // Hilo 2
            
            //Ejecución de ambos hilos
            Hilo1.Start(); 
            Hilo2.Start();
        }

        //Metodo para incrementar la variable Contador (Count)
        static void Incrementa_Contador()
        {
            while (true)
            {
                //Lock permite proteger el código hasta que lo libere un Hilo, con el fin de 
                //sincronizar los hilos y no afecten alguna variable compartida.
                lock (ThisLock)
                {
                    int temp = count; //Temp variable local que genera cada Hilo
                    Thread.Sleep(1000);
                    count = temp + 1; //Se incrementa la variable compartida (Count) por ambos hilos
                    //Se imprime el contador
                    Console.WriteLine("Hilo ID " + Thread.CurrentThread.ManagedThreadId + " incrementa contador a " + count + " temp " + temp);
                }
                Thread.Sleep(1000);
            }
        }
    }
}
