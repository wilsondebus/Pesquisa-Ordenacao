## 24/09
## Últimos métodos de ordenação 


### SHELL
    - É baseado no inserção, ou seja, é uma tentativa de mehoria via o uso de análise a distância (tipo pente)
    - Instável 
    - Não é para listas encadeadas 


```java
void shell(Lista<> lista) {
    int i, j;
    int tmp;
    int qtdComparacoes = 0, qtdTrocas = 0;
    int distancia = 1;

    int referenciaTamanho = 3;

    do {
        distancia = referenciaTamanho * distancia + 1;
    } while (distancia < n);
    
    do {
        distancia = (int)(distancia / referenciaTamanho);
        
        for (i = distancia; i < n; i++) {
            tmp = vetor[i];
            for (j = i - distancia; j >= 0; j = j - distancia) {
                qtdComparacoes++;
                if (tmp < vetor[j]) {
                    vetor[j + distancia] = vetor[j];
                    qtdTrocas++;
                } else break;
            }
            vetor[j + distancia] = tmp;
            qtdTrocas++;
        }
    } while (distancia > 1);
}
```


```c#
void shell(Lista<> lista) {
    int i, j;
    int tmp;
    int qtdComparacoes = 0, qtdTrocas = 0;
    int distancia = 1;

    int referenciaTamanho = 3;

    do {
        distancia = referenciaTamanho * distancia + 1;
    } while (distancia < n);
    
    do {
        distancia = (int)(distancia / referenciaTamanho);
        
        for (i = distancia; i < n; i++) {
            tmp = vetor[i];
            for (j = i - distancia; j >= 0; j = j - distancia) {
                qtdComparacoes++;
                if (tmp < vetor[j]) {
                    vetor[j + distancia] = vetor[j];
                    qtdTrocas++;
                } else break;
            }
            vetor[j + distancia] = tmp;
            qtdTrocas++;
        }
    } while (distancia > 1);
}
```

### HEAPSORT 
    - Método baseado na distância de arvores binárias, contido em lista
    - Heapmaximo: 
        - garantir que a raiz da subarvore seja maior ou igual que seus filhos. Se isso não ocorrer, fazer as trocas:
        - filhoEsquerda = posicaoRaiz * 2
        - filhoDireita = posicaoRaiz * 2 + 1
    - Troca do primeiro com o último e diminui uma posição 

    ATENÇÃO: a lista deve começar na posição 1

    - Teste para filho esquerda 
        -> if (lista[raiz] < lista[raiz * 2]) { troca }
    - Teste para o filho da direita (antes de testar, é preciso ter certeza que o filho exista)
        -> if (raiz * 2 + 1 < n && lista[raiz] < lista[raiz * 2 + 1]) { troca }

### BUCKET 


### RADIX