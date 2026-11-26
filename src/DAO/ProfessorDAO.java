package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Professor;

// DAO = classe que fala com o banco. Aqui fica todo o SQL do professor, o resto do sistema nao mexe em SQL
// implements DAO<Professor> = promete implementar os metodos da interface (insert, find, findAll, update, delete)
public class ProfessorDAO implements DAO<Professor> {

    private Connection con; // a conexao com o banco, se ficar null é pq a conexao falhou (ou nunca foi criada)

    // o construtor roda quando o controller faz new ProfessorDAO(), é aqui que abre a conexao
    // sem ele o con ficava null e dava NullPointerException em tudo
    public ProfessorDAO() {
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // carrega o driver do mariadb (o .jar da pasta libs)
            con = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3307/escola", // tipo do banco, servidor, porta e nome do banco
                    "root", // usuario
                    "123456"); // senha
            System.out.println("Conectado no banco de dados");

        } catch (ClassNotFoundException e) {
            System.out.println("Classe Maria DB não encontrada"); // driver nao ta no projeto
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar no banco de dados"); // senha, porta ou banco errado
            e.printStackTrace();
        }
    }

    // CREATE
    @Override
    public void insert(Professor p) {
        try {
            // cada ? é um valor que entra depois. Tem que ter o mesmo numero de ? e de colunas (5 e 5)
            // id nao entra pq o banco gera sozinho (AUTO_INCREMENT)
            String sql = "INSERT INTO professores (nome, cpf, email, telefone, departamento) VALUES (?, ?, ?, ?, ?)";

            // PreparedStatement monta o comando com os ? e evita SQL Injection (nunca concatenar texto no sql)
            PreparedStatement stmt = con.prepareStatement(sql);

            // o numero é a posicao do ? (começa no 1), na ordem das colunas
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getDepartamento());
            stmt.executeUpdate(); // executeUpdate é pra comandos que mudam o banco (insert, update, delete)

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir no banco de dados.");
            ex.printStackTrace(); // mostra o motivo (ex: cpf duplicado)
        }
    }

    // READ (um so) - busca pelo id
    @Override
    public Professor find(int id) {
        try {
            String sql = "SELECT * FROM professores WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery(); // executeQuery é pra SELECT, devolve o ResultSet (as linhas do resultado)

            // rs.next() anda pra proxima linha e devolve false se nao tiver mais. Aqui é um so, entao if
            if (rs.next()) {
                Professor p = new Professor();

                // transforma a linha do banco em um objeto Professor, o nome entre aspas é o da coluna
                p.setID(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getString("telefone"));
                p.setDepartamento(rs.getString("departamento"));

                return p;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar professor");
            e.printStackTrace();
        }

        return null; // nao achou ninguem (ou deu erro)
    }

    // READ (todos) - é o que enche a tabela da tela
    @Override
    public List<Professor> findAll() {
        List<Professor> lista = new ArrayList<>(); // lista vazia que vai receber os professores

        try {
            String sql = "SELECT * FROM professores"; // sem WHERE, entao traz tudo

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // while pq tem varias linhas, repete ate acabar. Cada volta cria um Professor
            while (rs.next()) {
                Professor p = new Professor();

                p.setID(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getString("telefone"));
                p.setDepartamento(rs.getString("departamento"));

                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar professores");
            e.printStackTrace();
        }

        return lista; // se deu erro devolve vazia, a tela nao quebra
    }

    // UPDATE - o controller chama quando o id ja existe (id diferente de 0)
    @Override
    public void update(Professor p) {
        try {
            // o WHERE id = ? é essencial, sem ele atualizava TODOS os professores
            String sql = "UPDATE professores SET nome = ?, cpf = ?, email = ?, telefone = ?, departamento = ? WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getDepartamento());
            stmt.setLong(6, p.getID()); // o 6 é o ? do WHERE, por isso o id vai por ultimo

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar professor");
            e.printStackTrace();
        }
    }

    // DELETE - apaga pelo id
    @Override
    public void delete(int id) {
        try {
            // tambem precisa do WHERE, senao apagava a tabela inteira
            String sql = "DELETE FROM professores WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar professor");
            e.printStackTrace();
        }
    }
}