package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Aluno;


// DAO = classe que fala com o banco. Aqui fica todo o SQL do aluno, o resto do sistema nao mexe em SQL
// implements DAO<Aluno> = promete implementar os metodos da interface (insert, find, findAll, update, delete)
public class AlunoDAO implements DAO<Aluno> {

    private Connection con; // a conexao com o banco, se ficar null é pq a conexao falhou


    // o construtor roda quando o controller faz new AlunoDAO(), é aqui que abre a conexao
    public AlunoDAO() {
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // carrega o driver do mariadb (o .jar da pasta libs), se nao achar cai no catch
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
            e.printStackTrace(); // mostra o motivo real do erro
        }
    }


    // CREATE
    @Override
    public void insert(Aluno a) {
        try {
            // cada ? é um valor que entra depois. Tem que ter o mesmo numero de ? e de colunas (5 e 5)
            // id nao entra pq o banco gera sozinho (AUTO_INCREMENT)
            String sql = "INSERT INTO alunos (nome, cpf, email, telefone, matricula) VALUES (?, ?, ?, ?, ?)";

            // PreparedStatement monta o comando com os ? e evita SQL Injection (nunca concatenar texto no sql)
            PreparedStatement stmt = con.prepareStatement(sql);

            // o numero é a posicao do ? (começa no 1), na ordem das colunas
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getCpf());
            stmt.setString(3, a.getEmail());
            stmt.setString(4, a.getTelefone());
            stmt.setString(5, a.getMatricula());
            stmt.executeUpdate(); // executeUpdate é pra comandos que mudam o banco (insert, update, delete)

        } catch (SQLException e) {
            System.out.println("Erro ao inserir no banco de dados.");
            e.printStackTrace(); // mostra o motivo (ex: cpf duplicado)
        }
    }

    // READ (um so) - busca pelo id
    @Override
    public Aluno find(int id) {
        try {
            String sql = "SELECT * FROM alunos WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery(); // executeQuery é pra SELECT, devolve o ResultSet (as linhas do resultado)

            // rs.next() anda pra proxima linha e devolve false se nao tiver mais. Aqui é um so, entao if
            if (rs.next()) {
                Aluno a = new Aluno();

                // transforma a linha do banco em um objeto Aluno, o nome entre aspas é o da coluna
                a.setID(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setCpf(rs.getString("cpf"));
                a.setEmail(rs.getString("email"));
                a.setTelefone(rs.getString("telefone"));
                a.setMatricula(rs.getString("matricula"));

                return a;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar aluno");
            e.printStackTrace();
        }

        return null; // nao achou ninguem (ou deu erro)
    }

    // READ (todos) - é o que enche a tabela da tela
    @Override
    public List<Aluno> findAll() {
        List<Aluno> lista = new ArrayList<>(); // lista vazia que vai receber os alunos

        try {
            String sql = "SELECT * FROM alunos"; // sem WHERE, entao traz tudo

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // while pq tem varias linhas, repete ate acabar. Cada volta cria um Aluno
            while (rs.next()){
                Aluno a = new Aluno();

                a.setID(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setCpf(rs.getString("cpf"));
                a.setEmail(rs.getString("email"));
                a.setTelefone(rs.getString("telefone"));
                a.setMatricula(rs.getString("matricula"));

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar alunos");
            e.printStackTrace();
        }

        return lista; // se deu erro devolve vazia, a tela nao quebra
    }

    // UPDATE - o controller chama quando o id ja existe (id diferente de 0)
    @Override
    public void update(Aluno a) {
        try {
            // o WHERE id = ? é essencial, sem ele atualizava TODOS os alunos
            String sql = "UPDATE alunos SET nome = ?, cpf = ?, email = ?, telefone = ?, matricula = ? WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getCpf());
            stmt.setString(3, a.getEmail());
            stmt.setString(4, a.getTelefone());
            stmt.setString(5, a.getMatricula());
            stmt.setLong(6, a.getID()); // o 6 é o ? do WHERE, por isso o id vai por ultimo

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aluno");
            e.printStackTrace();
        }
    }

    // DELETE - apaga pelo id
    public void delete(int id) {
        try {
            // tambem precisa do WHERE, senao apagava a tabela inteira
            String sql = "DELETE FROM alunos WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar aluno");
            e.printStackTrace();
        }
    }
}