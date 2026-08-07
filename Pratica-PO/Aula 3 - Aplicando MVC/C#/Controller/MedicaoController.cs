using System;                       // necessário para usar o tipo Action
using System.Collections.Generic;   // necessário para usar List<int>
using System.Diagnostics;           // necessário para usar Stopwatch (cronômetro)
using MvcExemplo.Model;             // para enxergar ListaModel e ResultadoMedicao
using MvcExemplo.View;              // para enxergar MedicaoView

namespace MvcExemplo.Controller
{
    /// <summary>
    /// CONTROLLER
    /// É o intermediário: chama o Model para gerar os dados, mede o tempo
    /// de cada rotina e manda a View exibir o resultado.
    /// Não guarda dados (isso é do Model) e não imprime nada diretamente (isso é da View).
    /// </summary>
    public class MedicaoController
    {
        // "readonly" significa que, depois de definida no construtor, essa referência
        // não pode mais ser trocada por outra (protege contra troca acidental da View).
        private readonly MedicaoView _view;

        // Construtor: o Controller recebe a View pronta de fora (isso é chamado de
        // "injeção de dependência" -- o Controller não cria a View sozinho, ele a recebe).
        public MedicaoController(MedicaoView view)
        {
            _view = view;
        }

        // Método principal que executa todo o fluxo do programa.
        public void Executar()
        {
            // Duas listas vazias que serão preenchidas pelo Model.
            List<int> listaAleatoria = new List<int>();
            List<int> listaSequencial = new List<int>();

            // ROTINA 1: popular uma lista com 100000 números aleatórios entre 100 e 100000.
            // "() => ListaModel.PopularLista(...)" é uma expressão lambda: cria uma função
            // sem nome que será executada depois, dentro do método Medir.
            long tempo1 = Medir(() => ListaModel.PopularLista(listaAleatoria, 100000, 100, 100000, true));

            // Empacota o resultado (nome + lista + tempo) e manda a View exibir.
            _view.ExibirTempo(new ResultadoMedicao("rotina 1", listaAleatoria, tempo1));

            // ROTINA 2: popular uma lista com 100000 números sequenciais.
            long tempo2 = Medir(() => ListaModel.PopularLista(listaSequencial, 100000, 1, 100000, false));
            _view.ExibirTempo(new ResultadoMedicao("rotina 2", listaSequencial, tempo2));

            // Se quiser ver o conteúdo das listas no console, é só descomentar as linhas abaixo:
            // _view.ExibirLista(listaAleatoria, "Lista aleatoria");
            // _view.ExibirLista(listaSequencial, "Lista sequencial");
        }

        // Método utilitário privado (só usado dentro desta classe) que mede quanto tempo
        // uma rotina qualquer leva para rodar.
        // "Action" é um tipo do C# que representa "uma função sem retorno, sem parâmetros"
        // -- ou seja, aceita qualquer bloco de código para executar.
        private static long Medir(Action rotina)
        {
            // Stopwatch é a classe do C# feita especificamente para medir tempo de execução
            // (mais indicada que pegar a hora manualmente, como fazíamos em Java/Python).
            Stopwatch cronometro = Stopwatch.StartNew(); // já começa o cronômetro imediatamente

            rotina(); // executa de fato a rotina recebida como parâmetro (ex: PopularLista)

            cronometro.Stop(); // para o cronômetro assim que a rotina termina

            return cronometro.ElapsedMilliseconds; // devolve quantos milissegundos se passaram
        }
    }
}