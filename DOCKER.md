# Rodando o Gaspo API com Docker

## Estrutura esperada

Coloque o dump ou scripts SQL do banco legado e-SUS dentro desta pasta:

```text
docker/
  esus/
    init/
      01-schema.sql
      02-dados.sql
```

O PostgreSQL executa automaticamente arquivos `.sql`, `.sql.gz` e scripts `.sh` presentes em `/docker-entrypoint-initdb.d` somente na primeira criação do volume do banco.

## Subir tudo

Na raiz do projeto:

```bash
docker compose up --build
```

A API ficará disponível em:

```text
http://localhost:18081
```

## Bancos expostos para ferramentas locais

Dentro do Docker, a API usa os nomes dos serviços:

```text
postgres_esus:5432
postgres_gaspo:5432
```

Na máquina host, para acessar via DBeaver/psql:

```text
e-SUS: localhost:15433 / database esus_db / user postgres / senha 12345678
Gaspo: localhost:15434 / database gaspo_db / user postgres / senha gaspo123
```

## Resetar os bancos

Se precisar executar novamente os scripts de inicialização do e-SUS, remova os volumes:

```bash
docker compose down -v
docker compose up --build
```

## Por que não usar localhost dentro da API

Cada serviço do Compose roda em seu próprio contêiner. Dentro do contêiner da API, `localhost` aponta para o próprio contêiner da API, não para o PostgreSQL.

O Docker Compose cria uma rede interna e registra cada serviço por nome. Por isso a API conecta em:

```text
jdbc:postgresql://postgres_esus:5432/esus_db
jdbc:postgresql://postgres_gaspo:5432/gaspo_db
```

Esses nomes funcionam como DNS interno da rede Docker. Assim todos da equipe usam a mesma configuração, sem depender de PostgreSQL instalado localmente.
