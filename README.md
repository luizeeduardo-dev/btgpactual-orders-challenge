# Sistema de Processamento de Pedidos Assíncrono - Challenge BTG Pactual

---

<img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-%2304D361">
<img alt="Language: Java" src="https://img.shields.io/badge/language-java-green">
<img alt="Version: 1.0" src="https://img.shields.io/badge/version-1.0-yellowgreen">

## Visão Geral

Este é um sistema backend que simula o processamento assíncrono de pedidos, desenvolvido como parte do desafio técnico do BTG Pactual. A aplicação expõe uma API REST para criação e consulta de pedidos, utilizando RabbitMQ para gerenciar o fluxo de processamento de forma desacoplada e resiliente.

### Tecnologias
* Java
* Maven
* RabbitMQ
* Spring Boot 3
* JUnit 5 & Mockito
* Docker & Docker Compose

---

## Pré-requisitos

* Docker & Docker Compose
* Java 21 e Maven 3.9+ (Opcional, para builds manuais)
* `curl` ou Postman para testar a API

---

## Como Executar

O ambiente é 100% containerizado. Com o Docker em execução, basta um único comando para iniciar toda a aplicação.

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/luizeeduardo-dev/btgpactual-orders-challenge.git
    cd btgpactual-orders-challenge
    ```

2.  **Inicie os contêineres:**
    ```bash
    docker-compose up --build
    ```
    A flag `--build` garante que a imagem da sua aplicação seja construída a partir do `Dockerfile` multi-stage, que compila e empacota o projeto automaticamente.

---

## Acessando os Serviços

* **API de Pedidos:**
    * A API estará rodando em: **[http://localhost:8080]()**

* **Painel de Gerenciamento do RabbitMQ:**
    * Acesse em: **[http://localhost:15672]()**
    * **Login:** `admin`
    * **Senha:** `admin`

---

## Como Testar a API

A forma mais fácil de testar é através da interface do **Swagger UI**. Alternativamente, você pode usar os comandos `curl` abaixo.

#### 1. Criar um Novo Pedido

```bash
curl -X POST http://localhost:8080/orders \
-H "Content-Type: application/json" \
-d '{
    "clientId": "client-123",
    "items": ["item-A", "item-B", "item-C"]
}'
```

#### 2. Consultar o Status de um Pedido

Copie o `id` retornado na criação do pedido e use-o na URL abaixo.

```bash
# Substitua {ORDER_ID} pelo ID do seu pedido
curl -X GET http://localhost:8080/orders/{ORDER_ID}
```

* **Inicialmente,** o status será `PENDENTE`.
* **Após alguns segundos,** ao consultar novamente, o status mudará para `PROCESSADO`.

---

## Como Rodar os Testes

O projeto possui uma suíte completa de testes de unidade e de integração. Para executá-los, use o Maven Wrapper:

```bash
./mvnw test
```

---

## Parando a Aplicação

Para parar todos os contêineres, pressione `Ctrl + C` no terminal onde o `docker-compose` está rodando e execute:
```bash
docker-compose down
```

---

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.