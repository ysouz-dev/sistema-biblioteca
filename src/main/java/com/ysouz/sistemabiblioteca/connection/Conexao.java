package com.ysouz.sistemabiblioteca.connection;

import com.ysouz.sistemabiblioteca.exception.DatabaseException;

import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;

public final class Conexao {
    private static final Properties props = new Properties();

    static {
        try {
            props.load(Conexao.class.getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            throw new DatabaseException("Erro ao carregar configurações do banco de dados", e);
        }
    }

    public static Connection getConexao() throws SQLException {
        String url = props.getProperty("database.url");
        String user = props.getProperty("database.user");
        String pass = props.getProperty("database.pass");

        return DriverManager.getConnection(url, user, pass);
    }
}
