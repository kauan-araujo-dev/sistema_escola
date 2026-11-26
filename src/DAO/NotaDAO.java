package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import core.Database;
import models.Aluno;
import models.Materia;
import models.Nota;

public class NotaDAO implements DAO<Nota> {

    @Override
    public void insert(Nota nota) {
        String sql = "INSERT INTO nota (valor, aluno_id, materia_id) VALUES (?, ?, ?)";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setDouble(1, nota.getValor());
            stmt.setInt(2, (int) nota.getAluno().getID());
            stmt.setInt(3, (int) nota.getMateria().getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao inserir nota");
        }
    }

    @Override
    public void update(Nota nota) {
        String sql = "UPDATE nota SET valor = ?, aluno_id = ?, materia_id = ? WHERE id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setDouble(1, nota.getValor());
            stmt.setInt(2, (int) nota.getAluno().getID());
            stmt.setInt(3, (int) nota.getMateria().getId());
            stmt.setInt(4, (int) nota.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao atualizar nota");
        }
    }

    @Override
    public Nota find(int id) {
        String sql = "SELECT * FROM nota WHERE id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                AlunoDAO alunoDao = new AlunoDAO();
                Aluno aluno = alunoDao.find(rs.getInt("aluno_id"));
            

                MateriaDAO materiaDao = new MateriaDAO();
                Materia materia = materiaDao.find(rs.getInt("materia_id"));

                return new Nota(
                    rs.getInt("id"),
                    rs.getDouble("valor"),
                    aluno,
                    materia
                );
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar nota");
        }

        return null;
    }

    @Override
    public List<Nota> findAll() {
        String sql = "SELECT * FROM nota";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<Nota> listaNotas = new ArrayList<Nota>();

            while (rs.next()) {
                AlunoDAO alunoDao = new AlunoDAO();
                Aluno aluno = alunoDao.find(rs.getInt("aluno_id"));
            

                MateriaDAO materiaDao = new MateriaDAO();
                Materia materia = materiaDao.find(rs.getInt("materia_id"));

                listaNotas.add(
                    new Nota(
                        rs.getInt("id"),
                        rs.getDouble("valor"),
                        aluno,
                        materia
                    )
                );
            }

            return listaNotas;

        } catch (Exception e) {
            System.out.println("Erro ao buscar notas");
        }

        return null;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM nota WHERE id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao deletar nota");
        }
    }
}