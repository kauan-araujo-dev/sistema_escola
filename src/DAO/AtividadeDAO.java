
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import core.Database;
import models.Atividade;

public class AtividadeDAO implements DAO<Atividade> {
    public void insert(Atividade atividade) {
        String sql = "INSERT INTO atividades (titulo, descricao, tipo, datacriacao, dataentrega) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, atividade.getTitulo());
            stmt.setString(2, atividade.getDescricao());
            stmt.setString(3, atividade.getTipo());
            stmt.setDate(4, Date.valueOf(atividade.getDataCriacao()));
            stmt.setDate(5, Date.valueOf(atividade.getDataEntrega()));

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao inserir atividade: " + e.getMessage());
        }
    }

    @Override
    public void update(Atividade atividade) {
        String sql = "UPDATE atividades SET titulo = ?, descricao = ?, tipo = ?, datacriacao = ?, dataentrega = ? WHERE id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, atividade.getTitulo());
            stmt.setString(2, atividade.getDescricao());
            stmt.setString(3, atividade.getTipo());
            stmt.setDate(4, Date.valueOf(atividade.getDataCriacao()));
            stmt.setDate(5, Date.valueOf(atividade.getDataEntrega()));
            stmt.setInt(6, atividade.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao atualizar atividade");
        }
    }

    @Override
    public Atividade find(int id) {
        String sql = "SELECT * FROM atividades WHERE id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Atividade(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("tipo"),
                    rs.getDate("datacriacao").toLocalDate(),
                    rs.getDate("dataentrega").toLocalDate()
                );
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar atividade");
        }

        return null;
    }

    @Override
    public List<Atividade> findAll() {
        String sql = "SELECT * FROM atividades";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            List<Atividade> listAtividades = new ArrayList<>();

            while (rs.next()) {
                listAtividades.add(new Atividade(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("tipo"),
                    rs.getDate("datacriacao").toLocalDate(),
                    rs.getDate("dataentrega").toLocalDate()
                ));
            }

            return listAtividades;

        } catch (Exception e) {
            System.out.println("Erro ao buscar atividades");
        }

        return null;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM atividades WHERE id = ?";

        try {
            Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao deletar atividade");
        }
    }
}
