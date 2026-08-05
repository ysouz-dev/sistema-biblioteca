CREATE TYPE genero AS ENUM (
    'F',
    'M',
    'I'
);

CREATE TABLE IF NOT EXISTS usuarios (
nome varchar(50) not null,
cpf varchar(11) not null,
sexo genero not null,
PRIMARY KEY(cpf)
);

CREATE TABLE IF NOT EXISTS enderecos (
cpf_usuario varchar(11) not null,
rua varchar(40) not null,
bairro varchar(40) not null,
numero varchar(10) not null,
cep varchar(8) not null,
PRIMARY KEY(cpf_usuario),
FOREIGN KEY(cpf_usuario) REFERENCES usuarios(cpf)
);

CREATE TABLE IF NOT EXISTS livros (
titulo varchar(40) not null,
autor varchar(40) not null,
isbn varchar(13) not null,
ano_lancamento smallint not null,
disponivel boolean not null,
PRIMARY KEY(isbn)
);

CREATE TYPE status_emprestimo as ENUM (
    'PENDENTE',
    'DEVOLVIDO'
);

CREATE TABLE IF NOT EXISTS emprestimos (
id int GENERATED ALWAYS AS IDENTITY not null,
cpf_usuario varchar(11) not null,
isbn_livro varchar(13) not null,
data date not null,
situacao status_emprestimo default 'PENDENTE' not null,
PRIMARY KEY(id),
FOREIGN KEY(cpf_usuario) REFERENCES usuarios(cpf),
FOREIGN KEY(isbn_livro) REFERENCES livros(isbn)
);

