# Projeto - Serviço de Cadastro

<p align="center">
<img src="https://img.shields.io/badge/Backend%20Language-Java%2017-orange?style=for-the-badge&logo=java" alt="Java 17">
<img src="https://img.shields.io/badge/Backend%20Framework-SpringBoot%203.5.3-green?style=for-the-badge&logo=spring" alt="Spring Boot 3.5.3">
<img src="https://img.shields.io/badge/Gerenciador%20de%20Dependências-Maven-purple?style=for-the-badge&logo=apache-maven" alt="Maven">
<img src="https://img.shields.io/badge/Banco%20de%20Dados-PostgreSQL-blue?style=for-the-badge&logo=postgresql" alt="PostgreSQL">
<img src="https://img.shields.io/badge/Mensageria-Apache%20Kafka-red?style=for-the-badge&logo=apache-kafka" alt="Apache Kafka">
<img src="https://img.shields.io/badge/Containerização-Docker%20e%20Docker%20Compose-blueviolet?style=for-the-badge&logo=docker" alt="Docker">
<img src="https://img.shields.io/badge/Testes-JUnit5%20e%20Mockito-red?style=for-the-badge&logo=junit5" alt="JUnit5 e Mockito">
<img src="https://img.shields.io/badge/Qualidade%20de%20Código-SonarCloud-yellowgreen?style=for-the-badge&logo=sonarcloud" alt="SonarCloud">
</p>

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=Sobral-Dev_projeto-WeFit)](https://sonarcloud.io/summary/new_code?id=Sobral-Dev_projeto-WeFit)

Este projeto é uma API de cadastro de usuários (Pessoa Física e Pessoa Jurídica), desenvolvido com Spring Boot, focado em boas práticas de Clean Code, princípios SOLID e arquitetura modular multicamadas. Ele foi projetado para ser expansível e se integrar futuramente a um ecossistema de microsserviços via mensageria Kafka.

## Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Framework:** Spring Boot 3.5.3
- **Gerenciador de Dependências:** Apache Maven
- **Banco de Dados:** PostgreSQL
- **Mensageria:** Apache Kafka
- **Containerização:** Docker e Docker Compose
- **Mapeamento de Objetos:** MapStruct (junto ao JPA + Hibernate)
- **Migrações de Banco de Dados:** Flyway
- **Testes:** JUnit 5, Mockito, Spring Test (MockMvc), H2 Database
- **Qualidade de Código:** SonarCloud
- **Automação de Release:** Semantic Release (via GitHub Actions)

## Arquitetura

O projeto segue uma arquitetura em camadas (Controller, Service, Repository, Entity), promovendo a separação de responsabilidades e a manutenibilidade.

- **Injeção de Dependências por Construtor:** Todas as dependências obrigatórias são injetadas via construtor, garantindo imutabilidade, clareza no contrato das classes e facilitando a testabilidade.

- **Princípios SOLID:**

  - **SRP (Single Responsibility Principle):** As classes de serviço foram granularizadas (ex: PessoaFisicaCreationService, UsuarioQueryService) e os controladores divididos, garantindo que cada componente tenha uma única razão para mudar.
  - **DIP (Dependency Inversion Principle):** A dependência de abstrações (interfaces como KafkaEventPublisher) em vez de implementações concretas promove um alto grau de desacoplamento.

## Validações Customizadas

Para garantir a integridade dos dados, foram implementados validadores personalizados utilizando o Bean Validation do Jakarta:

- **CPF:** Validação do formato e dígitos verificadores.
- **CNPJ:** Validação do formato e dígitos verificadores.
- **CEP:** Validação do formato (8 dígitos numéricos).

As exceções de validação e de negócio são tratadas globalmente por um GlobalExceptionHandler, que retorna respostas HTTP padronizadas e amigáveis ao cliente.

## Testes

- **Testes Unitários:** Focam em unidades isoladas de código (métodos de validadores, serviços, publicadores Kafka), utilizando Mockito para simular dependências.
- **Testes de Integração (e2e):** Validam a interação entre múltiplas camadas da aplicação (controladores, serviços, repositórios) e a persistência no banco de dados. Utilizam o Spring Test (MockMvc) e um banco de dados H2 em memória para agilidade e isolamento.

## Microsserviços e Infraestrutura

Este serviço de cadastro foi projetado como parte de um ecossistema de microsserviços.

- **Mensageria Kafka:** A comunicação assíncrona entre microsserviços é facilitada pelo Apache Kafka. O `cadastro-service` publica eventos de usuario.cadastrado para que outros serviços (como um futuro `notificacao-service`) possam consumi-los e reagir.
- **Docker e Docker Compose:** A infraestrutura de banco de dados (PostgreSQL) e mensageria (Kafka e ZooKeeper) é orquestrada via Docker Compose, garantindo um ambiente de desenvolvimento consistente e isolado.

## Como Executar a API

### Pré-requisitos:

- Java 17 JDK
- Apache Maven
- Docker e Docker Compose

Navegue até a pasta `backend` no terminal.

Inicie os serviços de infraestrutura com Docker Compose:

```bash
    docker-compose up -d
```
Isso iniciará o PostgreSQL, Kafka e ZooKeeper.

Limpe e construa o projeto Maven:

```bash
mvn clean install -U
```
O Flyway fará as migrações do banco de dados automaticamente na primeira execução.

Execute a aplicação Spring Boot:
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## Documentação dos Endpoints

[Acesse Aqui](#)
