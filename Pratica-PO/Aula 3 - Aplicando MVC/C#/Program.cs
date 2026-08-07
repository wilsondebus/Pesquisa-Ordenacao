using  MvcExemplo.Controller;
using MvcExemplo.View;

namespace MvcExemplo 
{
    ///<summary>
    /// Ponto de entrada. Só monta as peças (View + Controller) e inicia
    /// Não contém lógica de negócio nem de exibição
    /// </summary>
    
    class Program
    {
        static void Main(string[] args)
        {
            // Cria o view que vai exibir as informações
            var view = new MedicaoView();
            // Cria o controller entregando o view para ele usar 
            var controller = new MedicaoController(view);
            // Fluxo principal para o programa executar 
            controller.Executar(); 
        }
    }
}