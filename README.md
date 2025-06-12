# Sistema de Cadastro de Usuários em Java Swing

Este é um projeto de exemplo que demonstra a construção de uma aplicação desktop para Cadastro de Usuários (CRUD - Criar, Ler, Atualizar, Deletar) utilizando Java Swing e Maven.

O principal objetivo do projeto não é apenas a funcionalidade, mas também a aplicação de boas práticas de desenvolvimento, como a separação de responsabilidades em camadas (Model-View-DAO), tratamento robusto de exceções com feedback ao usuário, e a implementação de um sistema de logging profissional com SLF4J e Logback.

---

## ✨ Features

* **CRUD completo** de usuários (Criar, Ler, Atualizar e Deletar).
* Interface gráfica intuitiva construída com **Java Swing**.
* Comunicação com banco de dados **MySQL** via JDBC.
* **Gerenciamento de dependências padronizado** com **Maven**.
* **Gerenciamento de logs profissional** com SLF4J e Logback, que direciona os logs técnicos para um arquivo (`app.log`) em vez de poluir o console.
* **Tratamento de exceções robusto**, utilizando exceções personalizadas (`DataAccessException`) para fornecer feedback claro ao usuário sem expor detalhes de falhas internas.
* Código estruturado com **separação de responsabilidades** (View, Model, DAO).

---

## 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Java Swing** (para a interface gráfica)
* **Maven** (para gerenciamento de dependências e build do projeto)
* **MySQL Connector/J 9.3.0** (Driver de conexão MySQL)
* **SLF4J API 2.0.17** (Fachada de Logging)
* **Logback Classic 1.5.18** (Implementação do Logging)

---

## 🚀 Como Configurar e Executar

Siga os passos abaixo para executar o projeto em sua máquina local.

### 1. Pré-requisitos

* **JDK 21** ou versão superior instalada.
* **Apache Maven** instalado e configurado.
* Um servidor de banco de dados **MySQL** em execução.

### 2. Clone o Repositório

```bash
git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
cd seu-repositorio
```

### 3. Configure o Banco de Dados

Execute o script SQL abaixo em seu cliente MySQL para criar o banco de dados e a tabela necessários.

```sql
-- Crie um novo banco de dados (se ainda não existir)
CREATE DATABASE IF NOT EXISTS cadastro_usuarios_db;

-- Use o banco de dados recém-criado
USE cadastro_usuarios_db;

-- Crie a tabela de usuários
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT email_unique UNIQUE (email)
);
```

### 4. Configure a Conexão com o Banco

Abra o arquivo `src/main/java/br/com/seuprojeto/util/ConexaoMySQL.java` e altere as credenciais de conexão para corresponderem à sua configuração local do MySQL (URL, usuário e senha).

### 5. Execute a Aplicação

Como este é um projeto Maven, a IDE cuidará de baixar as dependências listadas no `pom.xml`.

1.  **Importe o projeto na sua IDE** (IntelliJ, Eclipse, etc.) como um "Existing Maven Project".
2.  Aguarde a IDE baixar todas as dependências (processo de "Resolving Dependencies").
3.  Encontre a classe principal que contém o método `main` (provavelmente `br.com.seuprojeto.Main` ou `br.com.seuprojeto.view.TelaUsuarios`).
4.  Clique com o botão direito sobre ela e selecione **`Run`**.

---

## 📂 Estrutura do Projeto

O projeto segue a estrutura padrão do Maven, que organiza o código de forma clara e padronizada.

```
SEU_PROJETO/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/seuprojeto/
│   │   │       ├── dao/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       ├── view/
│   │   │       ├── util/
│   │   │       └── Main.java
│   │   └── resources/
│   │       └── logback.xml
│   └── test/
├── pom.xml                   <-- O coração do projeto Maven
└── README.md                 <-- Você está aqui
```

---

## 📜 Licença

Este projeto está licenciado sob a Licença MIT.