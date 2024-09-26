# Etapa 1: Construção da aplicação
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copia o arquivo pom.xml e instala as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante dos arquivos da aplicação
COPY src ./src

# Compila a aplicação
RUN mvn clean package -DskipTests

# Etapa 2: Execução da aplicação
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o JAR gerado para o container
COPY --from=build /app/target/*.jar app.jar

# Porta que será exposta pelo Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
