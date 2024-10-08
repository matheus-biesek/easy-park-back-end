# Sistema de Estacionamento - Backend (Java Spring)

Este projeto representa o **backend** do sistema de estacionamento, desenvolvido com **Java Spring** e seguindo uma arquitetura **monolítica**. O objetivo principal deste backend é fornecer serviços de API REST para gerenciar vagas de estacionamento, controlar a cancela, login de usuarios e exibir mensagens administrativas. O sistema é integrado com o hardware (ESP32 e Arduino) e interage com um banco de dados **PostgreSQL** para persistência.

## Tecnologias Utilizadas

- **Spring Boot**: Framework utilizado para construir a API REST e serviços da aplicação.
- **Spring Data JPA**: Utilizado para abstração do banco de dados, permitindo interação com o PostgreSQL via ORM.
- **Spring Security**: Implementa autenticação e autorização, protegendo as requisições da API.
- **JWT (JSON Web Token)**: Utilizado para gerar tokens que autenticam os usuários no sistema.
- **Lombok**: Facilita a criação de getters, setters e construtores automáticos nas classes, reduzindo o boilerplate.
- **Logback**: Gerencia os logs da aplicação, ajudando na depuração e monitoramento.
- **Hibernate Validator (Valid)**: Utilizado nos DTOs para validar as requisições recebidas pela API.
- **PostgreSQL**: Sistema de gerenciamento de banco de dados utilizado pela aplicação.
- **Docker**: A aplicação está containerizada para facilitar o deploy e a execução local.

## Organização do Projeto

O projeto está organizado em diferentes **Packages**, conforme segue:

- **Controller**: Responsável por gerenciar as requisições recebidas. Valida os parâmetros via DTOs e chama os serviços correspondentes.
- **DTO (Data Transfer Object)**: Classes que padronizam os dados de request e response para as APIs.
- **Model**: Contém as entidades que representam as tabelas do banco de dados.
- **Enum**: Define as enumerações utilizadas em várias partes do projeto.
- **Infra**: Arquivos responsáveis pela configuração do **Spring Security**, autenticação e controle de acessos.
- **Repository**: Interface responsável pela comunicação com o banco de dados, utilizando **Spring Data JPA** para definir métodos de busca e manipulação das entidades.
- **Service**: Implementa a lógica de negócio, sendo responsável por buscar e atualizar dados no banco de dados através dos repositórios.
- **Utils**: Contém classes auxiliares para funcionalidades genéricas.

## Configurações do Banco de Dados

A aplicação está conectada a um banco de dados **PostgreSQL**, com as configurações divididas entre dois arquivos:

- **application.properties**: Configurado para uso em ambiente de desenvolvimento local.
- **application-prod.properties**: Contém as variáveis de ambiente e configurações de produção.

## Docker e Docker Compose

A aplicação possui um **Dockerfile** que utiliza a imagem **Java Alpine**, configurada para rodar na porta 8080 por padrão.

Para executar a aplicação localmente de forma containerizada, siga os seguintes passos:

1. Gere o arquivo JAR da aplicação:
   ```
   mvn clean package
   ```
   O arquivo deve ser nomeado como `solutions-back-0.0.1-SNAPSHOT.jar`.

2. Crie a imagem Docker:
   ```
   docker build -t park-back-dev-local:1.0 .
   ```

3. Utilize o **docker-compose.yml** para rodar a aplicação e o banco de dados **PostgreSQL**:
   ```
   docker compose up
   ```

O `docker-compose.yml` define dois serviços:
- **park-back**: Para rodar o container do backend.
- **postgres**: Para rodar o banco de dados PostgreSQL.

Os containers estão configurados para se comunicarem entre si através de uma rede definida no arquivo de composição.

## Segurança e Autenticação

A aplicação utiliza **Spring Security** para controlar o acesso às APIs. Para ambientes de desenvolvimento e produção, é possível configurar o controle de acesso da seguinte maneira:

- **CORS Configuração**: No arquivo `CorsConfig`, altere o método `.allowedOrigins` para definir o domínio autorizado a realizar requisições ao backend.

- **Configurações de Permissão**: No arquivo `SecurityConfig`, ajuste as permissões de rotas conforme necessário:
    - Para proteger uma rota, remova o método `.permitAll()` e utilize `.hasAnyRole("ADMIN")` para definir as permissões corretas.

**JWT** é utilizado para autenticar os usuários através de um token com duração de 2 horas, gerado no login e anexado aos headers das requisições subsequentes.

## Boas Práticas e Manutenção

1. **Arquitetura Orientada a Objetos**: O projeto segue princípios de **Clean Code** e é estruturado de forma modular, facilitando a manutenção e a adição de novas funcionalidades no futuro.

2. **Uso do Lombok**: Facilita a criação de métodos padrão, como getters e setters, mantendo o código mais enxuto.

3. **Logs e Monitoramento**: Através do **Logback**, a aplicação gera logs detalhados que ajudam no monitoramento e depuração do sistema em tempo real.

4. **Validação de Requisições**: Os DTOs da aplicação utilizam anotações de validação, garantindo que as requisições atendam aos requisitos esperados antes de serem processadas.

## Deploy em Produção

Para realizar o deploy da aplicação em um ambiente de produção, algumas etapas adicionais são necessárias:

1. **Configuração de CORS**: Ajuste os domínios permitidos na configuração de CORS para o domínio correto da aplicação frontend.

2. **Ajuste de Permissões de Rotas**: Configure corretamente as permissões no arquivo `SecurityConfig`, garantindo que apenas usuários autenticados possam acessar as APIs necessárias.

## Como Rodar Localmente

1. Gere o JAR do projeto com o comando:
   ```
   mvn clean package
   ```

2. Construa a imagem Docker:
   ```
   docker build -t park-back-dev-local:1.0 .
   ```

3. Suba os containers utilizando **Docker Compose**:
   ```
   docker compose up
   ```