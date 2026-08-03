# 📚 Sistema de Biblioteca

---

Sistema de gerenciamento de biblioteca desenvolvido em Java com arquitetura em camadas, permitindo o cadastro de usuários e livros, além do controle de empréstimos e devoluções.

# ✨ Funcionalidades
- Cadastro de usuários
- Cadastro de livros
- Atualização de dados
- Exclusão de registros
- Consulta de usuários e livros
- Registro de empréstimos
- Registro de devoluções
- Validações de regras de negócio
- Tratamento de exceções personalizadas
- Persistência dos dados em banco MySQL

---

# 🛠 Tecnologias
- Java 21
- Maven
- JDBC
- MySQL
- Programação Orientada a Objetos
- Arquitetura em Camadas

###  📂 Estrutura
  - 📂 src
    - ├── 📂controller
    - ├── 📂service
    - ├── 📂repository
    - ├── 📂model
    - ├── 📂dto
    - ├── 📂validation
    - ├── 📂exception
    - ├── 📂enums
    - └── 📂connection
    
---

# 🏗 Arquitetura

O projeto foi desenvolvido seguindo o padrão de separação de responsabilidades:

- Controller: interação com o usuário.
- Service: regras de negócio.
- Repository: acesso ao banco de dados.
- Model: entidades do sistema.
- DTO: transferência de dados entre camadas.
- Validation: validação dos dados de entrada.
- Exception: exceções personalizadas.

---

# 🚀 Como executar

### Pré-requisitos
- Java 21
- Maven
- MySQL

**1. Execute o script de criação das tabelas:**
```
mysql -u root -p < database/schema.sql
```

**2. Configure as credencias de conexão**

Crie o arquivo `src/main/resources/database.properties` baseado no exemplo:
```
Properties

database.url = jdbc:mysql://localhost:3306/biblioteca
database.user = seu_usuario
database.pass = sua_senha
```

> O arquivo `database.properties` está no `gitignore` e não é versionado por conter dados sensíveis.
> Mas disponibilizei um exemplo com `databaseExample.properties`

# 🧑‍💻 Como Rodar

**1. Clone o repositório**
``` 
git clone https://github.com/ysouz-dev/gerenciador-barbearia
```

**2. Configure o banco de dados conforme as instruções acima**

**3. Abra o projeto no IntelliJ IDEA como projeto Maven**

**4. Execute a classe `Main.java`**


---

# 📖 Regras de negócio
- Não permite cadastrar usuários duplicados.
- Não permite cadastrar livros duplicados.
- Um livro não pode ser emprestado enquanto estiver indisponível.
- O empréstimo só pode ser finalizado através da devolução.
- Diversas validações impedem dados inconsistentes.