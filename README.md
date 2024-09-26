# Mini Sistema de Arquivos Virtuais - Backend

Este repositório contém a implementação do backend para um mini sistema de arquivos virtuais. O backend foi desenvolvido usando Spring Boot e está configurado para funcionar com um banco de dados MySQL.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Spring Data JPA**: Para interação com o banco de dados.
- **Flyway**: Para migração e versionamento do banco de dados.
- **MySQL**: Banco de dados relacional.

## Requisitos

- Java 11 ou superior
- Maven
- MySQL

## Configuração do Projeto

1. **Clone o repositório:**
    ```bash
    git clone https://github.com/danicoln/arq-virtuais.git
    cd arq-virtuais
    ```

2. **Configurar o banco de dados MySQL:**

    Crie um banco de dados no MySQL e configure as credenciais no arquivo `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco
    spring.datasource.username=usuario
    spring.datasource.password=senha
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL57Dialect
    ```

3. **Instale as dependências e compile o projeto:**
    ```bash
    mvn clean install
    ```

4. **Execute a aplicação:**
    ```bash
    mvn spring-boot:run
    ```

    A aplicação será iniciada na porta padrão `8080`. 

5. **Scripts de Migração:**

    O projeto utiliza o Flyway para migração de banco de dados. As migrações estão localizadas em `src/main/resources/db/migration`.

## Testes

Para executar os testes, use o seguinte comando:

```bash
mvn test
