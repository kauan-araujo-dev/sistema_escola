package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import core.Database;
import models.Aluno;
import models.Materia;
import models.AlunoMateria;

public class AlunoMateriaDAO implements DAO<AlunoMateria> {

    @Override
    public void insert(AlunoMateria alunoMateria) {
        String sql = "INSERT INTO aluno_materia (aluno_id, materia_id) VALUES (?, ?)";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, (int) alunoMateria.getAluno().getID());
            stmt.setInt(2, (int) alunoMateria.getMateria().getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao inserir vínculo entre aluno e matéria");
        }
    }

    @Override
    public void update(AlunoMateria alunoMateria) {
        String sql = "UPDATE aluno_materia SET materia_id = ? WHERE aluno_id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, (int) alunoMateria.getMateria().getId());
            stmt.setInt(2, (int) alunoMateria.getAluno().getID());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao atualizar vínculo entre aluno e matéria");
        }
    }

    @Override
    public AlunoMateria find(int alunoId) {
        String sql = "SELECT * FROM aluno_materia WHERE aluno_id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, alunoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                AlunoDAO alunoDao = new AlunoDAO();
                Aluno aluno = alunoDao.find(rs.getInt("aluno_id"));

                MateriaDAO materiaDao = new MateriaDAO();
                Materia materia = materiaDao.find(rs.getInt("materia_id"));

                return new AlunoMateria(aluno, materia);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar vínculo entre aluno e matéria");
        }

        return null;
    }

    @Override
    public List<AlunoMateria> findAll() {
        String sql = "SELECT * FROM aluno_materia";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<AlunoMateria> listaAlunoMateria = new ArrayList<AlunoMateria>();

            while (rs.next()) {
                AlunoDAO alunoDao = new AlunoDAO();
                Aluno aluno = alunoDao.find(rs.getInt("aluno_id"));

                MateriaDAO materiaDao = new MateriaDAO();
                Materia materia = materiaDao.find(rs.getInt("materia_id"));

                listaAlunoMateria.add(
                    new AlunoMateria(aluno, materia)
                );
            }

            return listaAlunoMateria;

        } catch (Exception e) {
            System.out.println("Erro ao buscar vínculos entre alunos e matérias");
        }

        return null;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM aluno_materia WHERE aluno_id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao deletar vínculo entre aluno e matéria");
        }
    }
}