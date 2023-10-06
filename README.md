<h1 align="center">
  Microfinanciamento Coletivo
</h1>

<p align="center">
 <img src="https://img.shields.io/static/v1?label=LinkedIn&message=@lucas-frsabatini&color=0e76a8&labelColor=000000" alt="@lucas-fsabatini"/>
</p>

O principal objetivo dos desenvolvedores com este projeto é o estudo e a prática de conteúdos e ferramentas referentes ao desenvolvimento de projetos em Java, utilizando de forma mais **corporativa** o **Git** e o **GitHub** com diferentes **branches**, a fim de manter o sistema em funcionamento enquanto outras funcionalidades são implementadas. Outro ponto forte de estudo e prática com este projeto, até o momento, é a implementação dos **Testes Automatizados com JUnit 5**, testando diferentes casos de sucesso e de falha para os principais métodos da API, testando assim, se as **regras de negócio** estão de acordo com o proposto no planejamento pré-estabelecido através da **modelagem do banco de dados** e da organização pré-implementação, conforme os screenshots abaixo.

O projeto é uma API RESTful desenvolvida com o objetivo de fornecer uma interface para que os usuários cadastrados possam criar seus projetos a fim de participarem de um **microfinanciamento coletivo** para arrecadar fundos para suas causas.

## Tecnologias
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Mysql](https://dev.mysql.com/downloads/)
- [JUnit 5](https://junit.org/)

## Práticas adotadas

- API REST
- Consultas com Spring Data JPA
- Testes automatizados com JUnit 5
- Injeção de Dependências
- Tratamento de respostas de erro

## Como Executar

- Clonar repositório git
- Construir o projeto:
```
$ ./mvnw clean package
```
- Executar a aplicação:
```
$ java -jar target/microfinanciamento_coletivo-0.0.1-SNAPSHOT.jar
```

- Você também pode apenas clonar o repositório e executar diretamente através do comando:

```
$ mvn spring-boot:run
```

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [postman](https://www.postman.com/):

# (ATENÇÃO: colocar prints do postman com os métodos abaixo)
- Criar Tarefa
```
POST
```

- Listar Tarefas
```
GET
```

- Atualizar Tarefa
```
UPDATE
```

- Remover Tarefa
```
DELETE
```
