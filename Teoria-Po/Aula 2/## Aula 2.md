## Aula 2
## 03/08 

---

## Padrão arquitetural - projeto de software 

    M odel -> 
    V iew -> 
    C controller -> 

    Documentação do java
        - java doc 

    Caminho do codigo para estudar: 
        - https://github.com/alexandrezamberlan/estruturasDeDados/tree/master/00%20-%20anosAnteriores/exemploJava_usoLista_MVC

## Atividade de fixação 
    - Pesquisar sobre os métodos de ordenação e caracteriza-los em: 
        - algoritmo de memória interna ou memória externa 
        - estabilidade (estável ou instável) 
        - complexidade 
        - porções de ordenação 

    
     - seleção (selection sort)
        - memória interna
        - instável
        - O(nˆ2)
        - porção ordenada: início

    - inserção (inserction sort)
        - memória interna
        - estável
        - O(nˆ2)
        - porção ordenada: início

    - pente (combsort)
        - memória interna 
        - instável 
        - O(n²) no pior caso, em média (n log n)
        - porção ordenada: não há porção fixa

    - agitação (shakesort ou cocktailsort)
        - memória interna 
        - estável 
        - O(n²) no pior/médio caso, O(n) no melhor caso
        - porção ordenada: ambas as pontas (início e final)

    - shellsort
        - memória interna 
        - instável
        - varia conforme a sequência de incrementos 
        - porção ordenada  

    - bucketsort
        - memória interna 
        - estável 
        - O(n + k) no caso médio, O(n²) no pior caso 
        - porção ordenada: não aplicável 
    
    - radix
        - memória interna 
        - estável 
        - O(d x (n +K))
        - porção ordenada: não aplicável 

    - heapsort
        - memória interna 
        - instável 
        - O(n log n)
        - porção ordenada: final

    - mergesort
        - memória interna 
        - estável 
        - O(n log n) em todos os casos 
        - porção ordenada: não há porção fixa comtinua

    - quicksort
        - memória interna 
        - instável 
        - O(n log n) no caso médio/melhor, O(n²) no pior caso 
        - porção ordenada: não há porção fixa 

