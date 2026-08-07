using System; 
using System.Collections.Generic; 

namespace MvcExemplo.Model
{
    /// <summary 
    /// MODEL 
    /// Responsável apenas pelos dados e pelas regras de negócio 
    /// Não sabe nada sobre como os dados serão exibidos (isso é papel do view)
    /// Nem sobre quem chama o método (isso é papel do controller)
    /// </summary>
    /// "static" significa que a classe não precisa ser instaciada 
    public static class ListaModel
    {
        ///<summary>
        /// Popula lista ou com numeros aleatórios ou de forma sequencial dentro de uma faixa
        /// </summary>
        /// <param name="lista"> lista a ser populada</param>
        /// <param name="quantidadeNumeros"> quantidade de numeros a gerar</param>
        /// <param name="inicio"> inicio da faixa</param?
        /// <param name="fim"> fim da faixa</param>
        /// <param name="aleatorio"> se true os numeros devem ser aleatorios</param>
        public static void PopularLista(List<int> lista, long quantidadeNumeros, int inicio, int fim, bool aleatorio)
        {
            // Cria um gerador de numeros aleatórios
            // Cada chamada de gerador.Next(...) devolve um número diferente
            Random gerador = new Random(); 

            if(aleatorio)
            {
                // Repete "quantidadeNumeros" vezes
                for(long i = 0; i < quantidadeNumeros; i++)
                {
                    lista.Add(gerador.Next(inicio, fim)); 
                }
            }
            else
            {
                for (long i = inicio; i < quantidadeNumeros; i++)
                {
                    lista.Add((int)i); 
                }
            }
        }
    }
}