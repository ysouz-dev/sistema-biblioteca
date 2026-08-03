CREATE DATABASE IF NOT EXISTS biblioteca
DEFAULT CHARSET utf8bm4
DEFAULT COLLATE utf8mb4_general_ci;

USE biblioteca;

CREATE TABLE IF NOT EXISTS usuarios (
cpf varchar(11) not null,
nome varchar(50) not null,
sexo enum('F', 'M', 'I') not null,
PRIMARY KEY(cpf)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS enderecos (
cpf_usuario varchar(11) not null,
rua varchar(20) not null,
bairro varchar(20) not null,
numero varchar(10) not null,
cep varchar(8) not null,
FOREIGN KEY(cpf_usuario) REFERENCES usuarios(cpf)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS livros (
titulo varchar(40) not null,
autor varchar(40) not null,
isbn varchar(13) not null,
ano_lancamento smallint not null,
disponivel boolean not null,
PRIMARY KEY(isbn)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS emprestimos (
id int auto_increment not null,
cpf_usuario varchar(11) not null,
isbn_livro varchar(13) not null,
data date not null,
situacao enum('PENDENTE', 'DEVOLVIDO') default 'PENDENTE',
PRIMARY KEY(id),
FOREIGN KEY(cpf_usuario) REFERENCES usuarios(cpf),
FOREIGN KEY(isbn_livro) REFERENCES livros(isbn)
) DEFAULT CHARSET = utf8mb4;

