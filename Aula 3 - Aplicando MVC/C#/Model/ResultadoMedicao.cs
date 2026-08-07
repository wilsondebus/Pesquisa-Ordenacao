using System.Collections.Generic; 

namespace MvcExemplo.Model
{
    /// <summary>
    /// MODEL
    /// Representa o resultado de uma rotina: o nome dela, a lista gerada 
    /// e o tempo em que ela demorou pra rodar. É só uma estrutura de dados 
    /// sem lógica de exibição
    /// </summary>
    public class ResultadoMedicao
    {
        public String NomeRotina { get; }
        public List<int> Lista { get; }
        public long TempoMs { get; }

        // Construtor 
        public ResultadoMedicao(string nomeRotina, List<int> lista, long tempoMs)
        {
            NomeRotina = nomeRotina;
            Lista = lista;
            TempoMs = tempoMs;
        }
    }
}