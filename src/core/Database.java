package core;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:src/data/database.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            setConnection();
        }
        return connection;
    }

    private static void setConnection() throws SQLException {
        new File("src/data").mkdirs(); 
        connection = DriverManager.getConnection(URL);
        criarTabelas(connection);
    }

    private static void criarTabelas(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS cursos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    descricao TEXT,
                    turno TEXT,
                    cargahoraria INTEGER,
                    modalidade TEXT
                )""");

            st.execute("""
                CREATE TABLE IF NOT EXISTS atividades (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo TEXT NOT NULL,
                    descricao TEXT,
                    tipo TEXT,
                    datacriacao TEXT,
                    dataentrega TEXT,
                    idprofessor INTEGER,
                    idmateria INTEGER
                )""");
        }
    }
}