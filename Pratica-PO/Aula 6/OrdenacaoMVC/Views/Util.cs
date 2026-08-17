using System;
using System.Collections.Generic;
using System.Diagnostics;

namespace OrdenacaoConsole.Views
{
    class Util
    {
        public static void popular(List<int> lista, int quantidade)
        {
            Random gerador = new Random();
            for (int i = 0; i < quantidade; i++)
            {
                lista.Add(gerador.Next(100000));
            }
        }

        public static void exibir(List<int> lista)
        {
            foreach (var item in lista)
            {
                Console.WriteLine(item.ToString());
            }
        }

        public static void exibirTempo(Stopwatch sw, string frase)
        {
            Console.WriteLine(frase + " (ms): " + sw.ElapsedMilliseconds);
        }
    }
}