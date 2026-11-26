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
        createTables();
    }

    private static void createTables() {

        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS professores (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            nome VARCHAR(100) NOT NULL,
                            cpf VARCHAR(14) UNIQUE NOT NULL,
                            email VARCHAR(100),
                            telefone VARCHAR(20),
                            departamento VARCHAR(100)
                        )
                    """);

            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS alunos (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            nome VARCHAR(100) NOT NULL,
                            cpf VARCHAR(14) UNIQUE NOT NULL,
                            email VARCHAR(100),
                            telefone VARCHAR(20),
                            matricula VARCHAR(30) UNIQUE NOT NULL
                        )
                    """);

            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS cursos (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            nome VARCHAR(100) NOT NULL,
                            descricao TEXT,
                            turno VARCHAR(50),
                            cargahoraria INT,
                            modalidade VARCHAR(50)
                        )
                    """);

            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS materia (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            nome VARCHAR(100) NOT NULL,
                            professor_id INTEGER NOT NULL,
                            curso_id INTEGER NOT NULL,

                            FOREIGN KEY (professor_id) REFERENCES professores(id),
                            FOREIGN KEY (curso_id) REFERENCES cursos(id)
                        )
                    """);

            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS nota (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            valor REAL NOT NULL,
                            aluno_id INTEGER NOT NULL,
                            materia_id INTEGER NOT NULL,

                            FOREIGN KEY (aluno_id) REFERENCES alunos(id),
                            FOREIGN KEY (materia_id) REFERENCES materia(id)
                        )
                    """);

            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS aluno_materia (
                            aluno_id INTEGER NOT NULL,
                            materia_id INTEGER NOT NULL,

                            PRIMARY KEY (aluno_id, materia_id),

                            FOREIGN KEY (aluno_id) REFERENCES alunos(id),
                            FOREIGN KEY (materia_id) REFERENCES materia(id)
                        )
                    """);

            stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS atividades (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            titulo VARCHAR(100) NOT NULL,
                            descricao TEXT,
                            tipo VARCHAR(50),
                            datacriacao DATE,
                            dataentrega DATE,
                            idprofessor INTEGER NOT NULL,
                            idmateria INTEGER NOT NULL,

                            FOREIGN KEY (idprofessor) REFERENCES professores(id),
                            FOREIGN KEY (idmateria) REFERENCES materia(id)
                        )
                    """);

            System.out.println("Tabelas inicializadas com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao iniciar tabelas");
            e.printStackTrace();
        }
    }
}
