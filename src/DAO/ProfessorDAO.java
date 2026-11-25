package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Aluno;
import models.Professor;

public class ProfessorDAO implements DAO<Professor> {

    private Connection con;

    @Override
    public void insert(Professor p) {
        try {
            String sql = "INSERT INTO professores (nome, cpf, email, telefone, departamento) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getDepartamento());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                p.setID(rs.getInt(1));
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir no banco de dados.");
        }
    }

    @Override
    public Professor find(int id) {
        try {
            String sql = "SELECT * FROM professores WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Professor p = new Professor();

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

        return null;
    }

    @Override
    public List<Professor> findAll() {
        List<Professor> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM professores";

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

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

        return lista;
    }

    @Override
    public void update(Professor p) {
        try {
            String sql = "UPDATE professores SET nome = ?, cpf = ?, email = ?, telefone = ?, departamento = ? WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getDepartamento());
            stmt.setLong(6, p.getID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar professor");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM professores WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar professor");
            e.printStackTrace();
        }
    }



}
