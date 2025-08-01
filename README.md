# Sistema de Cadastro de Livros (Java + MySQL)

Este é um projeto simples de console feito em Java, com conexão ao banco de dados MySQL, que permite **cadastrar, visualizar, editar e excluir livros** diretamente pelo terminal.

---

## Funcionalidades

-  Cadastro de livros com título, autor, ano de publicação e preço
-  Edição de informações de livros cadastrados
-  Listagem de todos os livros cadastrados
-  Remoção de livros do sistema
-  Armazenamento dos dados no banco de dados MySQL

---

## Tecnologias Utilizadas

- **Java** (com JDBC)
- **MySQL**
- **IDE**: Eclipse
- **JDK**: 17+ (ou a versão que você usou)

---

## Estrutura da Tabela no MySQL

```sql
CREATE DATABASE biblioteca;

USE biblioteca;

CREATE TABLE livro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    ano_publicacao INT,
    preco DECIMAL(10,2)
);
```
## Como Executar o Projeto
### 1. Clone o repositório

```bash
git clone https://github.com/aquiles-sa/sistema-cadastro-livros.git
cd sistema-cadastro-livros
```

### 2. Crie o banco de dados no MySQL
Abra seu terminal do MySQL ou outro SGBD e execute:
```sql
CREATE DATABASE biblioteca;

USE biblioteca;

CREATE TABLE livro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    ano_publicacao INT,
    preco DECIMAL(10,2)
);
```

### 3. Configure a conexão com o banco
Em uma classe separada para configurar a conexão, atualize os dados:
```java
String url = "jdbc:mysql://localhost:3306/biblioteca";
String usuario = "root";
String senha = "SUA_SENHA_AQUI";
```

### 4. Compile e execute o projeto
```bash
javac Main.java
java Main
```

Desenvolvido por Aquiles Araújo.
