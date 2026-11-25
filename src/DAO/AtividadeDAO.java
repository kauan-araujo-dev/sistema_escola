package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Atividade;

public class AtividadeDAO implements DAO<Atividade> {

    private Connection con;

    public AtividadeDAO() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Classe carregada com sucesso");

            con = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/Trabalho_POO",
                    "root",
                    "");

            System.out.println("Conectado no banco de dados");

        } catch (ClassNotFoundException e) {
            System.out.println("Classe MariaDB nao encontrada");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar no banco de dados");
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Atividade atividade) {
        try {
            String sql = "INSERT INTO atividades (titulo, descricao, tipo, datacriacao, dataentrega, idprofessor, idmateria) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, atividade.getTitulo());
            stmt.setString(2, atividade.getDescricao());
            stmt.setString(3, atividade.getTipo());
            stmt.setDate(4, Date.valueOf(atividade.getDataCriacao()));
            stmt.setDate(5, Date.valueOf(atividade.getDataEntrega()));
            stmt.setInt(6, atividade.getIdProfessor());
            stmt.setInt(7, atividade.getIdMateria());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir atividade");
            e.printStackTrace();
        }
    }

    @Override
    public Atividade find(int id) {
        try {
            String sql = "SELECT * FROM atividades WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Atividade a = new Atividade();

                a.setId(rs.getInt("id"));
                a.setTitulo(rs.getString("titulo"));
                a.setDescricao(rs.getString("descricao"));
                a.setTipo(rs.getString("tipo"));
                a.setDataCriacao(rs.getDate("datacriacao").toLocalDate());
                a.setDataEntrega(rs.getDate("dataentrega").toLocalDate());
                a.setIdProfessor(rs.getInt("idprofessor"));
                a.setIdMateria(rs.getInt("idmateria"));

                return a;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar atividade");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Atividade> findAll() {
        List<Atividade> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM atividades";

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Atividade a = new Atividade();

                a.setId(rs.getInt("id"));
                a.setTitulo(rs.getString("titulo"));
                a.setDescricao(rs.getString("descricao"));
                a.setTipo(rs.getString("tipo"));
                a.setDataCriacao(rs.getDate("datacriacao").toLocalDate());
                a.setDataEntrega(rs.getDate("dataentrega").toLocalDate());
                a.setIdProfessor(rs.getInt("idprofessor"));
                a.setIdMateria(rs.getInt("idmateria"));

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar atividades");
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void update(Atividade atividade) {
        try {
            String sql = "UPDATE atividades SET titulo = ?, descricao = ?, tipo = ?, datacriacao = ?, dataentrega = ?, idprofessor = ?, idmateria = ? WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, atividade.getTitulo());
            stmt.setString(2, atividade.getDescricao());
            stmt.setString(3, atividade.getTipo());
            stmt.setDate(4, Date.valueOf(atividade.getDataCriacao()));
            stmt.setDate(5, Date.valueOf(atividade.getDataEntrega()));
            stmt.setInt(6, atividade.getIdProfessor());
            stmt.setInt(7, atividade.getIdMateria());
            stmt.setInt(8, atividade.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar atividade");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM atividades WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar atividade");
            e.printStackTrace();
        }
    }
}