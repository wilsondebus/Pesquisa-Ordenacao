import time
 
from model.lista_model import ListaModel
from model.resultado_medicao import ResultadoMedicao
from view.medicao_view import MedicaoView
 
 
class MedicaoController:
    """
    CONTROLLER
    É o intermediário: chama o Model para gerar os dados, mede o tempo
    de cada rotina e manda a View exibir o resultado.
    Não guarda dados (isso é do Model) e não imprime nada diretamente (isso é da View).
    """
 
    def __init__(self, view: MedicaoView):
        self.view = view
 
    def executar(self) -> None:
        lista_aleatoria = []
        lista_sequencial = []
 
        # rotina 1 - popular uma lista com 100000 aleatórios na faixa 100 a 100000
        tempo1 = self._medir(lambda: ListaModel.popular_lista(lista_aleatoria, 100000, 100, 100000, True))
        self.view.exibir_tempo(ResultadoMedicao("rotina 1", lista_aleatoria, tempo1))
 
        # rotina 2 - popular uma lista com 100000 de forma crescente
        tempo2 = self._medir(lambda: ListaModel.popular_lista(lista_sequencial, 100000, 1, 100000, False))
        self.view.exibir_tempo(ResultadoMedicao("rotina 2", lista_sequencial, tempo2))
 
        # Se quiser ver o conteúdo das listas, descomente:
        # self.view.exibir_lista(lista_aleatoria, "Lista aleatoria")
        # self.view.exibir_lista(lista_sequencial, "Lista sequencial")
 
    @staticmethod
    def _medir(rotina) -> float:
        inicio = time.perf_counter()
        rotina()
        fim = time.perf_counter()
        return round((fim - inicio) * 1000, 3)