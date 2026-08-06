from controller.medicao_controller import MedicaoController
from view.medicao_view import MedicaoView
 
"""
Ponto de entrada. Só monta as peças (View + Controller) e inicia.
Não contém lógica de negócio nem de exibição.
"""
 
if __name__ == "__main__":
    view = MedicaoView()
    controller = MedicaoController(view)
    controller.executar()