# Desafio Técnico – Sistema de Processamento de Pedidos Assíncrono

Este é um sistema de processamento de pedidos desenvolvido como parte do desafio técnico do BTG Pactual. A aplicação recebe pedidos via API REST, os publica em uma fila para processamento assíncrono e permite a consulta de status.

A arquitetura utiliza uma abordagem de microsserviços com comunicação via mensageria, focada em boas práticas de desenvolvimento, Clean Code e princípios SOLID.

**Tecnologias:**
* Java 21
* Spring Boot 3
* RabbitMQ
* Docker & Docker Compose
* Maven

## Pré-requisitos

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas na sua máquina:

* [Docker](https://www.docker.com/get-started/)
* [Docker Compose](https://docs.docker.com/compose/install/) (geralmente já vem com o Docker Desktop)
* Java 21 (ou superior)
* Apache Maven 3.9 (ou superior)

## Como Construir a Aplicação

Para que o Docker possa criar a imagem da aplicação, primeiro é necessário empacotar o projeto em um arquivo JAR.

Na raiz do projeto, execute o seguinte comando Maven:

```bash
# No Windows (usando o Maven Wrapper)
./mvnw clean package

# No Linux/macOS
./mvnw clean package
```

Este comando irá limpar o projeto, rodar os testes e gerar o arquivo `orders-0.0.1-SNAPSHOT.jar` dentro da pasta `target/`.

## Como Executar o Ambiente Completo

Com o Docker e Docker Compose instalados, você pode iniciar toda a aplicação (API de Pedidos + RabbitMQ) com um único comando.

1.  **Clone o repositório** (se ainda não o fez):
    ```bash
    git clone <url-do-seu-repositorio>
    cd <nome-da-pasta-do-projeto>
    ```

2.  **Construa o JAR** conforme o passo anterior.

3.  **Inicie os contêineres** usando Docker Compose:
    ```bash
    docker-compose up --build
    ```
    * O comando `up` inicia os serviços definidos no arquivo `docker-compose.yml`.
    * A flag `--build` força o Docker a reconstruir a imagem da sua aplicação `orders` usando o `Dockerfile`, garantindo que as últimas alterações do código sejam incluídas.

## Acessando a Aplicação

Após a inicialização, os seguintes serviços estarão disponíveis:

* **API de Pedidos:**
    * A API estará rodando em `http://localhost:8080`.


* **Painel de Gerenciamento do RabbitMQ:**
    * Acesse em: `http://localhost:15672`
    * **Login:** `guest`
    * **Senha:** `guest`

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
Inicialmente, o status será `PENDENTE`. Após alguns segundos, ao consultar novamente, o status mudará para `PROCESSADO`.

## Parando a Aplicação

Para parar todos os contêineres e remover a rede criada pelo Compose, pressione `Ctrl + C` no terminal onde o `docker-compose up` está rodando e, em seguida, execute:

```bash
docker-compose down
```
