**Gaspo-api**

Sistema de integração e gestão de dados de saúde, projetado para atuar como ponte entre a base de dados do e-SUS APS e o sistema interno GASPO, otimizando a consulta de dados e o gerenciamento de registros de pacientes, profissionais e agendamentos.

🛠 Tecnologias Utilizadas
Este projeto foi desenvolvido utilizando as seguintes tecnologias e frameworks:

Linguagem: Java 

Framework: Spring Boot 3.4.0

Persistência: Spring Data JPA (Hibernate 6.6.2)

Banco de Dados: PostgreSQL 16

Documentação de API: Springdoc OpenAPI (Swagger UI 2.8.5)

Gerenciador de Dependências: Maven

📂 Descrição da Estrutura
O projeto foi estruturado para suportar o acesso a múltiplos bancos de dados simultaneamente (e-SUS e GASPO), utilizando pacotes para segregar as responsabilidades:

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

🚀 Instruções de Execução
Pré-requisitos
JDK 21 ou superior instalado na máquina.

PostgreSQL instalado e rodando.

Maven instalado (ou uso do wrapper do projeto).

IDE de sua preferência (IntelliJ IDEA recomendado).

Configuração do Banco de Dados
Certifique-se de que os dois bancos de dados existam no seu servidor PostgreSQL local:

esus (para dados legados).

gaspo_db (para dados do seu sistema).

Configure as credenciais (usuário e senha) nos arquivos de configuração (application.properties ou diretamente nos seus arquivos Config.java).

Como rodar o projeto
Clone o repositório:

Bash
git clone <link-do-seu-repositorio>
cd Gaspo-api
Compile o projeto:

Bash
mvn clean install
Execute a aplicação:

Via Terminal: mvn spring-boot:run

Via IntelliJ: Clique no ícone de "Play" na classe GaspoApplication.java.

Acesse a API:

Com o servidor rodando, acesse http://localhost:8081 (verifique a porta definida no seu application.properties).

A documentação da API (Swagger) estará disponível em: http://localhost:8081/swagger-ui.html.


📝 Notas de Desenvolvimento
Integração: Este sistema utiliza arquitetura de multi-datasource. Certifique-se de que as entidades estejam no pacote correto (model/esus ou model/gaspo) para que o Hibernate consiga gerenciar as tabelas corretamente nos seus respectivos bancos.

Enums: Classes de enumeração (StatusConsulta, StatusProfissional) estão localizadas em com.gaspo.api.model.enums para acesso global.

Desenvolvido como projeto acadêmico - IF Goiano.







Datas:

15/05 Projeto Interface

29/05 

12/06 Entrega do Backend

26/06 Correção e Ajuste

![Uploading image.png…]()

