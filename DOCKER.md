# Rodando o Gaspo API com Docker

O ambiente Docker usa apenas um banco PostgreSQL: `gaspo_db`.

## Subir tudo

```bash
docker compose up -d --build
```

Serviços:

```text
API: http://localhost:18082
PostgreSQL: localhost:15444 / database gaspo_db / user postgres / senha gaspo123
```

Dentro da rede Docker, a API conecta no banco por:

```text
jdbc:postgresql://postgres_gaspo:5432/gaspo_db
```

## Resetar banco

Para recriar o banco do zero:

```bash
docker compose down -v
docker compose up -d --build
```

Esse comando remove volumes do projeto Docker Compose atual.
