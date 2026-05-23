# Gaspo-api

API do GASPO, um sistema de apoio a unidades de saúde para cadastro de usuários,
profissionais, unidades, avisos, agendas, consultas, notificações e avaliações.

O projeto usa somente o banco PostgreSQL do GASPO. Não há integração com bancos
externos de prontuário.

## Tecnologias

* Java 21
* Spring Boot 3.4.0
* Spring Data JPA / Hibernate
* PostgreSQL 16
* Maven
* Thymeleaf
* Springdoc OpenAPI

## Estrutura

```text
src/main/java/com/gaspo/api/
├── config/             # Configuração JPA do banco GASPO
├── controller/         # Endpoints REST e páginas web
├── dto/                # Objetos de request/response
├── mapper/             # Conversão entre entidades e DTOs
├── model/gaspo/        # Entidades persistidas no banco GASPO
├── repository/gaspo/   # Repositórios JPA do banco GASPO
└── service/            # Regras de negócio
```

## Banco

Configuração padrão para execução local com o Postgres do `docker-compose.yml`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:15444/gaspo_db
spring.datasource.username=postgres
spring.datasource.password=gaspo123
spring.jpa.hibernate.ddl-auto=update
```

As tabelas são criadas/atualizadas pelo Hibernate, incluindo:

* `tb_usuario`
* `tb_funcionario`
* `tb_unidade_saude`
* `tb_profissional`
* `tb_agenda`
* `tb_consulta`
* `tb_aviso`
* `tb_notificacao`
* `tb_avaliacao`

## Execução

Subir com Docker:

```bash
docker compose up -d --build
```

Acesso:

```text
Aplicação: http://localhost:18082
Swagger: http://localhost:18082/swagger-ui/index.html
PostgreSQL: localhost:15444 / gaspo_db / postgres / gaspo123
```

Execução local pela IDE ou Maven:

```bash
./mvnw spring-boot:run
```

Nesse caso, mantenha o Postgres do compose ativo com:

```bash
docker compose up -d postgres_gaspo
```
