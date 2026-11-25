package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Aluno;



public class AlunoDAO implements DAO<Aluno> {

    private Connection con;


    @Override
    public void insert(Aluno a) {
        try {
            String sql = "INSERT INTO alunos (nome, cpf, email, telefone, matricula) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getCpf());
            stmt.setString(3, a.getEmail());
            stmt.setString(4, a.getTelefone());
            stmt.setString(5, a.getMatricula());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                a.setID(rs.getLong(1));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir no banco de dados.");
        }
    }

    @Override
    public Aluno find(int id) {
        try {
            String sql = "SELECT * FROM alunos WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Aluno a = new Aluno();

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

        return null;
    }

    @Override
    public List<Aluno> findAll() {
        List<Aluno> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM alunos";

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Aluno a = new Aluno();

                a.setID(rs.getLong("id"));
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

        return lista;
    }

    @Override
    public void update(Aluno a) {
        try {
            String sql = "UPDATE alunos SET nome = ?, cpf = ?, email = ?, telefone = ?, matricula = ? WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getCpf());
            stmt.setString(3, a.getEmail());
            stmt.setString(4, a.getTelefone());
            stmt.setString(5, a.getMatricula());
            stmt.setLong(6, a.getID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aluno");
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
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