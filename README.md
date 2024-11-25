<h1>AT2 prática em grupo UCB</h1>
<h2>Sistema de buscas</h2>

![WhatsApp Image 2024-11-24 at 10 11 25 PM](https://github.com/user-attachments/assets/0ff4be02-3f55-4e1c-ac1b-8f35a087f90c)

# Distributed Search System

Este projeto implementa um sistema de busca distribuído utilizando Java e sockets. O sistema é composto por dois servidores (A e B), cada um responsável por buscar em uma parte de um conjunto de dados de artigos científicos. O servidor A recebe a solicitação de busca do cliente, envia para o servidor B, e ambos realizam a busca nos dados. Após a execução, o servidor A retorna os resultados ao cliente.

## Funcionalidades

- **Servidor A**: Recebe a solicitação de busca do cliente, repassa para o Servidor B, e retorna os resultados finais.
- **Servidor B**: Realiza a busca na sua parte dos dados e retorna os resultados para o Servidor A.
- **Busca**: A busca é feita por substrings nos títulos e introduções dos artigos.
- **Cliente**: Envia uma substring para o Servidor A e recebe os resultados.

## Estrutura de Diretórios
-/src /client # Código do cliente /server # Código do Servidor A e B /utils # Funções auxiliares, como o parser do arquivo JSON /arxiv.json # Arquivo com os dados dos artigos científicos


## Como rodar

1. **Clone o repositório**:
   git clone <URL_DO_REPOSITÓRIO>
   cd <diretório_do_repositório>
   
2. **Execute os Servidores**:
    Inicie o Servidor A:
     java -cp out/production/distributedsearch com.example.distributedsearch.server.ServerA
    Inicie o Servidor B:
     java -cp out/production/distributedsearch com.example.distributedsearch.server.ServerB
3. **Execute o Cliente**:
     java -cp out/production/distributedsearch com.example.distributedsearch.client.Client

   
**Requisitos**:
Java 17
Biblioteca JSON (org.json) para manipulação de arquivos JSON



## Tecnologias Usadas
![Java](https://img.shields.io/badge/Java-0D1117?style=for-the-badge&logo=openjdk&logoColor=white)&nbsp;
![Git](https://img.shields.io/badge/-Git-black?style=flat-square&logo=git)
![Eclipse](https://img.shields.io/badge/-Eclipse-2C2255?style=flat-square&logo=eclipse&logoColor=white)
