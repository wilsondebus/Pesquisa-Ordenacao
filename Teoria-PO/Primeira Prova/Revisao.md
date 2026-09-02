## Revisão para a prova 

### Memória Secundaria (HD, SSD, PenDrive)
    - Os dados precisam ser ordenados por partes 
        - Pois não conseguem ler um único byte de maneira eficiente

### Memória Principal (RAM)
    - O conjunto inteiro de dados é ordenado junto 
        - Mais velocidade e facilidade para a ordenação 
    - Cache embutido (da mais velocidade)

### O que é a Ordenação e Porque é importante
    - Ordenar é organizar uma estrutura de dados, como uuma lista ou vetor... 
    - Pode ser ordenada de forma cresente, decrescente, utilizando uma ou mais chaves de controle
    - Isso tudo, deixa a busca mais eficiênte e mais fácil, assim exibir e mostrar resultados fica mais organizado e profissional 

### O que é pesquisar 
    - Localizar/buscar um dado dentro de uma estrutura utilizando alguma chave como referência 

### Qual a diferença de pesquisar e ordenar 
    - Ordenação organiza os dados dentro de uma estrutura utiliando uma ou mais chaves, já a pesquisa localiza algum dado específico por meio de uma chave

### O que são Chaves 
    - Chave primária: a principal chave para a ordenação
    - Chave secundária: usada quando existe algum tipo de empate na chave primária, então ela diferencia por outra chave

### O que é o CompateTo 
    - Utilizado para definir como os objetos serão comparados/ordenados 
```java
public int compareTo(Pessoa o) {
   int resultado = this.getNome().compareTo(o.getNome());

   if(resultado != 0){
       return resultado;
   }

   return Integer.compare(this.idade, o.getIdade());
}
```

1. compara o nome. 
2. verifica se houve diferença - if(resultado != 0). Se houve diferença, ja existe uma ordem definida.
3. se empatou, compara a idade - Integer.compare(this.idade, o.getIdade()).

### Algoritmos Estável ou Instável 
    - Estável:
    Ele mantem a ordem original após a ordenação em caso de empate 
|Algoritmo|
|---------|
|  Bolha  |
| Inserção|
| Agitação|
|Mergesort|

    - Instável:
    Não mantem a ordem original após a ordenação em caso de empate 
    Se o método tem distância é instável
|Algoritmo|
|---------|
| Seleção |
|  Pente  |
|Shellsort|
|Quicksort|

    - Bolha, seleção, inserção
    São considerados simples, por causa de sua complexidade 

### Complexidade 
    - É analisado o esforço computacional 
    - Mais esforço -> maior complexidade
    - Menos esforço -> menor complexidade 

    - Quantidade de comparações * Quantidade de trocas

    | Maior complexidade |
        - O(n!)
        - O(n^x)       ← depende do valor de x
        - O(n log n)
        - O(n)
        - O(log n)
        - O(1)
    | Menor complexidade |

### Comparações 
    - Quando um algoritmo verifica valores, é uma comparação 
    EX: 10 > 2?

### Troca 
    - Após a comparação, quando os elementos mudam de posição [indice]

### Qual método funciona melhor se a lista de entrada já estiver completamente ordenada?
    - Bolha ou inserção (N-1)

    - Ordenar algo que ja esta ordenado 
    métodos mais complexos podem se sair melhor (bolha)
    método inserção 

    - Método agitação 
    diminiu os "elementos tartarugas" 
    ao ir da esquerda pra direita ele leva os maiores e ao ir da direita pra esquerda ele leva os menores 

