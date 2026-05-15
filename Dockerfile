FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x ./mvnw
RUN ./mvnw -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -DskipTests package

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN apk add --no-cache postgresql-client

COPY --from=build /app/target/*.jar app.jar

ENV SERVER_PORT=8081
ENV WAIT_FOR_DB_HOSTS=postgres_gaspo:5432

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "\
  for target in $(echo \"$WAIT_FOR_DB_HOSTS\" | tr ',' ' '); do \
    host=${target%:*}; \
    port=${target#*:}; \
    echo \"Waiting for $host:$port...\"; \
    until pg_isready -h \"$host\" -p \"$port\" -q; do \
      sleep 2; \
    done; \
  done; \
  exec java ${JAVA_OPTS:-} -jar /app/app.jar \
"]
