# Sistema de Processamento de Pedidos Assíncrono - Challenge BTG Pactual

<p align="center">
    <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-%2304D361">
    <img alt="Language: Java" src="https://img.shields.io/badge/language-java-green">
    <img alt="Version: 1.0" src="https://img.shields.io/badge/version-1.0-yellowgreen">
</p>

## Visão Geral

Este é um sistema backend que simula o processamento assíncrono de pedidos, desenvolvido como parte
do desafio técnico do BTG Pactual. A aplicação expõe uma API REST para criação e consulta de
pedidos, utilizando RabbitMQ para gerenciar o fluxo de processamento de forma desacoplada e
resiliente.

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

O ambiente é 100% containerizado. Com o Docker em execução, basta um único comando para iniciar toda
a aplicação.

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/luizeeduardo-dev/btgpactual-orders-challenge.git
   cd btgpactual-orders-challenge
   ```

2. **Inicie os contêineres:**
   ```bash
   docker-compose up --build
   ```
   A flag `--build` garante que a imagem da sua aplicação seja construída a partir do `Dockerfile`
   multi-stage, que compila e empacota o projeto automaticamente.

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

Você pode interagir com a API usando os comandos `curl` fornecidos abaixo ou importando a coleção do Postman que acompanha o projeto para uma experiência de teste mais fácil.

### Usando o Postman (Recomendado)

Uma coleção do Postman com as requisições prontas está incluída neste repositório para facilitar os testes.

1.  Abra o aplicativo Postman.
2.  Clique no botão **Import** no canto superior esquerdo.
3.  Selecione a aba **File** e clique em **Upload Files**.
4.  Navegue até a raiz deste projeto e selecione o arquivo: [btgPactual challenger.postman_collection.json](postman/btgPactual challenger.postman_collection.json).
5.  Clique em **Import**.

Uma nova coleção chamada "BTG Orders API" aparecerá na sua barra lateral.

* **Para criar um pedido:** Execute a requisição `POST Criar Pedido`.
* **Para consultar um pedido:** Copie o valor do campo `id` da resposta da requisição de criação e cole-o na variável `orderId` da requisição `GET Consultar Pedido` antes de executá-la.

### Usando cURL

#### 1. Criar um Novo Pedido

Execute o comando abaixo no seu terminal.

```bash
curl -X POST http://localhost:8080/orders \
-H "Content-Type: application/json" \
-d '{
    "clientId": "cliente-abc-123",
    "items": ["produto-A", "produto-B", "produto-C"]
}'
```

#### 2. Consultar o Status de um Pedido

Copie o `id` retornado no corpo da resposta da criação do pedido e use-o na URL abaixo.

```bash
# Substitua {ORDER_ID} pelo ID do seu pedido
curl -X GET http://localhost:8080/orders/{ORDER_ID}
```

* **Inicialmente,** o status será `PENDENTE`.
* **Após alguns segundos,** ao consultar novamente, o status mudará para `PROCESSADO`.

---

## Como Rodar os Testes

O projeto possui uma suíte completa de testes de unidade e de integração. Para executá-los, use o
Maven Wrapper:

```bash
./mvnw test
```

---

## Parando a Aplicação

Para parar todos os contêineres, pressione `Ctrl + C` no terminal onde o `docker-compose` está
rodando e execute:

```bash
docker-compose down
```

---

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
