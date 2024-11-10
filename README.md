# Projeto Rocketseat Projeto Java 1

Este é um projeto de API fictícia para uma empresa de cursos de programação. A API permite gerenciar informações sobre cursos, incluindo operações de criação, atualização, remoção e consulta.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.4
- Spring Data JPA
- H2 Database
- Lombok
- SpringDoc OpenAPI

## Configuração do Ambiente

### Pré-requisitos

- Java 17
- Maven

### Configuração do Banco de Dados

A aplicação está configurada para usar um banco de dados H2 em memória para o perfil de desenvolvimento. As configurações podem ser encontradas no arquivo `src/main/resources/application-dev.properties`:

```properties
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:devdb
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect

```



### Executando a Aplicação
Para executar a aplicação, utilize o Maven:

```
./mvnw spring-boot:run
```

A aplicação estará disponível em http://localhost:8080.

### Endpoints da API
Criar Curso
- URL: `/cursos`
- Método: POST
- Body:
```
{
  "name": "Nome do Curso",
  "category": "Categoria do Curso",
  "active": true
}
````

Obter Todos os Cursos
- URL: `/cursos`
- Método: GET

Obter Curso por ID
- URL: `/cursos/{id}`
- Método: GET

Atualizar Status do Curso
- URL: `/cursos/{id}/active`
- Método: PATCH

Remover Curso
- URL: `/cursos/{id}`
- Método: DELETE


Documentação da API
A documentação da API está disponível em http://localhost:8080/swagger-ui.html.