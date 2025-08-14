# Desafio Técnico – Sistema de Processamento de Pedidos Assíncrono

Este é um sistema de processamento de pedidos desenvolvido como parte do desafio técnico do BTG
Pactual. A aplicação recebe pedidos via API REST, os publica em uma fila para processamento
assíncrono e permite a consulta de status.

A arquitetura utiliza uma abordagem de microsserviços com comunicação via mensageria, focada em boas
práticas de desenvolvimento.

**Tecnologias:**

* Java 21
* Spring Boot 3
* RabbitMQ
* Docker & Docker Compose
* Maven

## Pré-requisitos

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas na sua máquina:

* [Docker](https://www.docker.com/get-started/)
* [Docker Compose](https://docs.docker.com/compose/install/) * Se estiver usando Docker Desktop, o Docker Compose já está incluído
* Postman ou curl para testar a API

## Como Executar o Ambiente Completo

Com o Docker e Docker Compose instalados, você pode iniciar toda a aplicação (API de Pedidos +
RabbitMQ) com um único comando.

1.  **Clone o repositório** (se ainda não o fez):
    ```bash
    git clone https://github.com/luizeeduardo-dev/btgpactual-orders-challenge.git
    cd btgpactual-orders-challenge
    ```

3.  **Inicie os contêineres** usando Docker Compose:
    ```bash
    docker-compose up --build
    ```
    * O comando `up` inicia os serviços definidos no arquivo `docker-compose.yml`.
    * A flag `--build` força o Docker a reconstruir a imagem da aplicação usando o
      `Dockerfile`.

## Acessando a Aplicação

Após a inicialização, os seguintes serviços estarão disponíveis:

* **API de Pedidos:**
    * A API estará rodando em `http://localhost:8080`.


* **Painel de Gerenciamento do RabbitMQ:**
    * Acesse em: `http://localhost:15672`
    * **Login:** `admin`
    * **Senha:** `admin`

## Exemplos de Uso da API

Você pode usar ferramentas como `curl` ou Postman para interagir com a API.

#### 1. Criar um Novo Pedido

```bash
curl --location 'http://localhost:8080/orders' \
--header 'Content-Type: application/json' \
--data '{
    "clientId": "{{$randomUUID}}",
    "items": ["{{$randomProductName}},{{$randomProductName}}]
}'
```

#### 2. Consultar o Status de um Pedido

Copie o `id` retornado na criação do pedido e use-o na URL abaixo.

```bash
# Substitua {ORDER_ID} pelo ID do seu pedido
curl -X GET http://localhost:8080/orders/{ORDER_ID}
```

Inicialmente, o status será `PENDENTE`. Após alguns segundos, ao consultar novamente, o status
mudará para `PROCESSADO`.

## Parando a Aplicação

Para parar todos os contêineres e remover a rede criada pelo Compose, pressione `Ctrl + C` no
terminal onde o `docker-compose up` está rodando e, em seguida, execute:

```bash
docker-compose down
```
