class ResultadoMedicao:
    """
    MODEL
    Representa o resultado de uma rotina: o nome dela, a lista gerada
    e o tempo que ela levou para rodar. É só uma estrutura de dados,
    sem lógica de exibição.
    """
 
    def __init__(self, nome_rotina: str, lista: list, tempo_ms: float):
        self.nome_rotina = nome_rotina
        self.lista = lista
        self.tempo_ms = tempo_ms