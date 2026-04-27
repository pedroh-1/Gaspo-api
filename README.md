# Gaspo-api

Sistema de integração e gestão de dados de saúde, desenvolvido para conectar o banco de dados oficial do **e-SUS APS** ao sistema interno **GASPO**, permitindo uma operação unificada e eficiente.

## 🛠 Tecnologias Utilizadas
* **Linguagem:** Java 21 (OpenJDK 23)
* **Framework:** Spring Boot 3.4.0
* **Persistência:** Spring Data JPA (Hibernate 6.6.2)
* **Banco de Dados:** PostgreSQL 16
* **Gerenciador de Dependências:** Maven
* **Documentação:** Springdoc OpenAPI (Swagger UI)
* **Utilitários:** Lombok

## 📂 Descrição da Estrutura
O projeto utiliza uma arquitetura de múltiplos *datasources* para separar os dados legados (e-SUS) dos dados operacionais do sistema (GASPO).

```text
src/main/java/com/gaspo/api/
├── config/             # Configurações de conexão (EsusConfig, GaspoConfig)
├── controller/         # Endpoints da API (REST)
├── model/              # Entidades do Banco de Dados
│   ├── esus/           # Tabelas herdadas/integradas (Consulta, Paciente, Profissional)
│   └── gaspo/          # Tabelas próprias do sistema (Avaliacao, Notificacao)
├── repository/         # Interfaces de acesso a dados
│   ├── esus/           # Repositórios do banco e-SUS
│   └── gaspo/          # Repositórios do banco GASPO
└── service/            # Regras de negócio e orquestração
    └── enums/          # Classes de Status e tipos fixos

````

## ⚙️ Configurações Necessárias
Como o projeto utiliza dois bancos de dados, você deve configurar o arquivo src/main/resources/application.properties com as credenciais corretas para ambos:

Properties
## Configuração Banco e-SUS (Porta 5433)
esus.datasource.url=jdbc:postgresql://localhost:5433/esus
esus.datasource.username=postgres
esus.datasource.password=SUA_SENHA_AQUI
esus.datasource.driver-class-name=org.postgresql.Driver

## Configuração Banco GASPO (Porta 5432)
gaspo.datasource.url=jdbc:postgresql://localhost:5432/gaspo_db
gaspo.datasource.username=postgres
gaspo.datasource.password=SUA_SENHA_AQUI
gaspo.datasource.driver-class-name=org.postgresql.Driver

## JPA e Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Nota: Certifique-se de substituir SUA_SENHA_AQUI pela senha real do seu PostgreSQL.

## 🚀 Instruções de Execução
1. Pré-requisitos
Ter o PostgreSQL instalado e rodando com as bases criadas (esus e gaspo_db).

Ter o JDK 21+ configurado na sua IDE.

Maven configurado.

2. Passo a Passo
Clone o repositório:

Bash
git clone <link-do-seu-projeto>
Atualize as dependências:
No IntelliJ, clique com o botão direito no arquivo pom.xml > Maven > Reload Project.

Configure o Banco:
Verifique no seu DataGrip/DBeaver se a senha configurada no application.properties consegue conectar em ambos os bancos.

Executar:

No IntelliJ, clique no ícone de "Play" na classe GaspoApplication.java.

Ou, via terminal, dentro da pasta do projeto:

Bash
mvn spring-boot:run
Acesso:

API: http://localhost:8081

Documentação (Swagger): http://localhost:8081/swagger-ui.html

## ⚠️ Dicas de Manutenção
Ao criar novos Models: Sempre verifique em qual pacote (esus ou gaspo) a classe deve ficar e se o pacote correspondente está escaneado no arquivo Config (ex: EsusConfig.java).

Ao criar novos Repositories: Certifique-se de que o nome da interface é único no projeto (ex: PacienteEsusRepository e PacienteGaspoRepository) para evitar conflitos de Bean no Spring.

Desenvolvido como projeto acadêmico - IF Goiano.
