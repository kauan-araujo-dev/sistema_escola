package DAO;

import core.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Curso;

public class CursoDAO implements DAO<Curso> {

    private Connection con;

    public CursoDAO() {
        try {
            con = Database.getConnection();

            System.out.println("Conectado no banco de dados");

        } catch (SQLException e) {
            System.out.println("Erro ao conectar no banco de dados");
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Curso curso) {
        try {
            String sql = "INSERT INTO cursos (nome, descricao, turno, cargahoraria, modalidade) VALUES (?, ?, ?, ?, ?)";

            // Prepara o comando SQL para execução.
            PreparedStatement stmt = con.prepareStatement(sql);
            // Define os valores dos parâmetros na mesma ordem dos ? do SQL.
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setString(3, curso.getTurno());
            stmt.setInt(4, curso.getCargaHoraria());
            stmt.setString(5, curso.getModalidade());
            // Executa o insert
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir curso");
            e.printStackTrace();
        }
    }

    @Override
    public Curso find(int id) {
        try {
            String sql = "SELECT * FROM cursos WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            // Executa consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();

            // Verifica se encontrou registro
            if (rs.next()) {
                Curso c = new Curso();
                // Transfere dados do select para objeto curso
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                c.setTurno(rs.getString("turno"));
                c.setCargaHoraria(rs.getInt("cargahoraria"));
                c.setModalidade(rs.getString("modalidade"));

                return c;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar curso");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Curso> findAll() {
        // Cria lista vazia para armazenar os cursos encontrados
        List<Curso> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM cursos";

            PreparedStatement stmt = con.prepareStatement(sql);
            // Executa a consulta e obtém os registros.
            ResultSet rs = stmt.executeQuery();
            // Percorre todos os registros retornados da consulta
            while (rs.next()) {
                Curso c = new Curso();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                c.setTurno(rs.getString("turno"));
                c.setCargaHoraria(rs.getInt("cargahoraria"));
                c.setModalidade(rs.getString("modalidade"));

                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cursos");
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void update(Curso curso) {
        try {
            String sql = "UPDATE cursos SET nome = ?, descricao = ?, turno = ?, cargahoraria = ?, modalidade = ? WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setString(3, curso.getTurno());
            stmt.setInt(4, curso.getCargaHoraria());
            stmt.setString(5, curso.getModalidade());
            stmt.setInt(6, curso.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar curso");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM cursos WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar curso");
            e.printStackTrace();
        }
    }
}
