
# Projeto Rocketseat Projeto Java 1

Este é um projeto de API fictícia para uma empresa de cursos de programação. A API permite gerenciar informações sobre cursos, incluindo operações de criação, atualização, remoção e consulta.

### Team

Time de desenvolvedores formado pelo Mercado Livre e a Rocketseat para a criação e apresentação de projeto sobre o treinamento Java (Wave 2 | 2024).

| <p align="center">Nome</p>  | <p align="center">LinkedIn</p>  | <p align="center">Slack</p> | <p align="center">BU</p> |
|-----------------------------|------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| <p align="center">Daniela Beleze Karasawa</p>         | <p align="center">[<img src = "src/assets/img/logo_linkedin.svg" alt="linkend" width="10%"/>](https://www.linkedin.com/in/danielakarasawa)</p> | <p align="center">[<img src = "src/assets/img/logo_slack.svg" alt="slack" width="10%"/>](https://meli.enterprise.slack.com/team/UTKTT9ZQQ)</p>   | <p align="center">[<img src = "src/assets/img/m_envios.png" alt="mercado envios" width="100%"/>](http://www.mercadolivre.com.br)</p>     |
| <p align="center">Felipe dos Santos Geroldi</p>      | <p align="center">[<img src = "src/assets/img/logo_linkedin.svg" alt="linkend" width="10%"/>](https://www.linkedin.com/in/felipe-geroldi/)</p> | <p align="center">[<img src = "src/assets/img/logo_slack.svg" alt="slack" width="10%"/>](https://meli.enterprise.slack.com/team/U06Q43CQ5KN)</p> | <p align="center">[<img src = "src/assets/img/m_marketplace.png" alt="mercado livre" width="100%"/>](http://www.mercadolivre.com.br)</p> |
| <p align="center">Sophia Fiama da Silva</p>         | <p align="center">[<img src = "src/assets/img/logo_linkedin.svg" alt="linkend" width="10%"/>](https://www.linkedin.com/in/sophiafiama/)</p>    | <p align="center">[<img src = "src/assets/img/logo_slack.svg" alt="slack" width="10%"/>](https://meli.enterprise.slack.com/team/U03PXBS6T98)</p> | <p align="center">[<img src = "src/assets/img/m_pago.png" alt="mercado pago" width="100%"/>](http://www.mercadopago.com.br)</p>          |
| <p align="center">Tayna Alves Rodrigues</p>        | <p align="center">[<img src = "src/assets/img/logo_linkedin.svg" alt="linkend" width="10%"/>](https://www.linkedin.com/in/oituty/)</p>         | <p align="center">[<img src = "src/assets/img/logo_slack.svg" alt="slack" width="10%"/>](https://meli.enterprise.slack.com/team/U06PV0E0T0W)</p> | <p align="center">[<img src = "src/assets/img/m_marketplace.png" alt="mercado livre" width="100%"/>](http://www.mercadolivre.com.br)</p> |
| <p align="center">Vinicius Ventura de Andrade</p>  | <p align="center">[<img src = "src/assets/img/logo_linkedin.svg" alt="linkend" width="10%"/>](https://www.linkedin.com/in/vini-ventura29/)</p> | <p align="center">[<img src = "src/assets/img/logo_slack.svg" alt="slack" width="10%"/>](https://meli.enterprise.slack.com/team/U06PV004UDC)</p> | <p align="center">[<img src = "src/assets/img/m_marketplace.png" alt="mercado livre" width="100%"/>](http://www.mercadolivre.com.br)</p> |

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

```

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


### Documentação da API
A documentação da API está disponível em http://localhost:8080/swagger-ui.html.
