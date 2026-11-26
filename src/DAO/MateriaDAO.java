package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import core.Database;
import models.Materia;
import models.Professor;
import models.Curso;

public class MateriaDAO implements DAO<Materia> {

    @Override
    public void insert(Materia materia) {
        String sql = "INSERT INTO materia (nome, professor_id, curso_id) VALUES (?, ?, ?)";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, materia.getNome());
            stmt.setInt(2, (int) materia.getProfessor().getID());
            stmt.setInt(3, (int) materia.getCurso().getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao inserir matéria");
        }
    }

    @Override
    public void update(Materia materia) {
        String sql = "UPDATE materia SET nome = ?, professor_id = ?, curso_id = ? WHERE id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, materia.getNome());
            stmt.setInt(2, (int) materia.getProfessor().getID());
            stmt.setInt(3, (int) materia.getCurso().getId());
            stmt.setInt(4, (int) materia.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao atualizar matéria");
        }
    }

    @Override
    public Materia find(int id) {
        String sql = "SELECT * FROM materia WHERE id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ProfessorDAO professorDao = new ProfessorDAO();
                Professor professor = professorDao.find(rs.getInt("professor_id"));

                CursoDAO cursoDao = new CursoDAO();
                Curso curso = cursoDao.find(rs.getInt("curso_id"));

                return new Materia(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    professor,
                    curso
                );
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar matéria");
        }

        return null;
    }

    @Override
    public List<Materia> findAll() {
        String sql = "SELECT * FROM materia";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<Materia> listaMaterias = new ArrayList<Materia>();

            while (rs.next()) {
                ProfessorDAO professorDao = new ProfessorDAO();
                Professor professor = professorDao.find(rs.getInt("professor_id"));

                CursoDAO cursoDao = new CursoDAO();
                Curso curso = cursoDao.find(rs.getInt("curso_id"));

                listaMaterias.add(
                    new Materia(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        professor,
                        curso
                    )
                );
            }

            return listaMaterias;

        } catch (Exception e) {
            System.out.println("Erro ao buscar matérias");
        }

        return null;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM materia WHERE id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao deletar matéria");
        }
    }
}