package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import core.Database;
import models.Curso;

public class CursoDAO implements DAO<Curso> {
    public void insert(Curso curso) {
        String sql = "INSERT INTO curso (nome, descricao, turno, cargahoraria, modalidade) VALUES ?, ?, ?, ?, ?";

        try {
            Connection connection = Database.getConnection();

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setString(3, curso.getTurno());
            stmt.setInt(4, curso.getCargaHoraria());
            stmt.setString(5, curso.getModalidade());

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao inserir curso");
        }
    }

    @Override
    public void update(Curso curso) {
        String sql = "UPDATE curso SET nome = ?, descricao = ?, turno = ?, cargahoraria = ?, modalidade = ? WHERE id = ?";

        try {
            Connection connection = Database.getConnection();

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setString(3, curso.getTurno());
            stmt.setInt(4, curso.getCargaHoraria());
            stmt.setString(5, curso.getModalidade());

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar curso");
        }
    }

    @Override
    public Curso fetch(int id) {
        String sql = "SELECT * FROM cursos WHERE id = ?";
        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Curso(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"),
                        rs.getString("turno"), rs.getInt("cargahoraria"), rs.getString("modalidade"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar curso");
        }
    }

    @Override
    public List<Curso> fetchAll() {
        String sql = "SELECT * FROM cursos";
        try {

            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<Curso> listCursos = new ArrayList<Curso>();
            while (rs.next()) {
                listCursos.add(new Curso(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"),
                        rs.getString("turno"), rs.getInt("cargahoraria"), rs.getString("modalidade")));
            }
            return listCursos;
        } catch (Exception e) {
            System.out.println("Erro ao buscar curso");
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

        } catch (Exception e) {
            System.out.println("Erro ao deletar curso");
        }
    }
}
