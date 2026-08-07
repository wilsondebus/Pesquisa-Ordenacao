from model.resultado_medicao import ResultadoMedicao
 
 
class MedicaoView:
    """
    VIEW
    Responsável apenas por exibir informação ao usuário.
    Não decide nada, não calcula nada, só imprime o que recebe.
    """
 
    def exibir_lista(self, lista: list, frase: str) -> None:
        print(frase)
        for item in lista:
            print(item)
        print("--------------------------")
        print(f"Total de registros: {len(lista)}")
 
    def exibir_tempo(self, resultado: ResultadoMedicao) -> None:
        print(f"Tempo (ms) {resultado.nome_rotina}: {resultado.tempo_ms}")