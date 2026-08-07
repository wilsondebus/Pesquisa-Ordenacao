using System;
using System.Collections.Generic;
using MvcExemplo.Model;

namespace MvcExemplo.View
{
    /// <summary>
    /// VIEW 
    /// Responsável apenas por exibir informações ao usuário 
    /// Não decide nada, não calcula nada, só imprime o que recebe 
    /// </summary> 
    public class MedicaoView
    {
        // Recebe uma lista de inteiros e uma frase de cabeçalho, e imprime tudo formatado
        public void ExibirLista(List<int> lista, string frase)
        {
            Console.WriteLine(frase); //imprime o titulo/frase recebida 

            // "foreach" percorre cada item da lista, um de cada vez e imprime
            foreach (int item in lista)
            {
                Console.WriteLine(item); 
            }

            Console.WriteLine("----------------------------");
            Console.WriteLine($"Total de registros: {lista.Count}");
        }

        public void ExibirTempo(ResultadoMedicao resultado)
        {
            Console.WriteLine($"Tempo (ms) {resultado.NomeRotina}: {resultado.TempoMs}");
        }
    }
}