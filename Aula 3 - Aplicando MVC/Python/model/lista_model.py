import random
 
 
class ListaModel:
    """
    MODEL
    Responsável apenas pelos dados e pelas regras de negócio.
    Não sabe nada sobre como os dados serão exibidos (isso é papel da View)
    nem sobre quem chama o método (isso é papel do Controller).
    """
 
    @staticmethod
    def popular_lista(lista: list, quantidade_numeros: int, inicio: int, fim: int, aleatorio: bool) -> None:
        """
        Popula lista ou com números aleatórios ou de forma sequencial dentro de uma faixa.
 
        :param lista: lista a ser populada
        :param quantidade_numeros: quantidade de números a gerar
        :param inicio: início da faixa
        :param fim: fim da faixa
        :param aleatorio: se True os números devem ser aleatórios
        """
        if aleatorio:
            for _ in range(quantidade_numeros):
                # randint é inclusivo nos dois extremos, por isso fim - 1
                # para se comportar como o nextInt(origin, bound) do Java (bound exclusivo)
                lista.append(random.randint(inicio, fim - 1))
        else:
            for i in range(inicio, quantidade_numeros):
                lista.append(i)