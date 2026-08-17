using System;
using System.Collections.Generic;
using System.Diagnostics;
using OrdenacaoConsole.Models;
using OrdenacaoConsole.Views;

class Principal
{
    public static void Main(string[] args)
    {
        List<int> listaAgitacao = new List<int>();
        List<int> listaSort = new List<int>();
        Util.popular(listaAgitacao, 100000);
        Util.popular(listaSort, 100000);

        Stopwatch sw = new Stopwatch();

        sw.Start();
        Ordenacao.agitacao(listaAgitacao);
        sw.Stop();
        Util.exibirTempo(sw, "Ordenacao por agitacao");
        sw.Reset();

        sw.Start();
        listaSort.Sort();
        sw.Stop();
        Util.exibirTempo(sw, "Ordenacao por sort nativo");
        sw.Reset();

        //Util.exibir(lista);
    }
}