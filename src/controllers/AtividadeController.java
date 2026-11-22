package controllers;

import java.util.List;

import DAO.AtividadeDAO;
import models.Atividade;

public class AtividadeController {
    AtividadeDAO dao;

    public AtividadeController() {
        dao = new AtividadeDAO();
    }

    public void add(String titulo, String descricao, String tipo, java.time.LocalDate dataCriacao, java.time.LocalDate dataEntrega, int idProfessor, int idMateria) {
        dao.insert(new Atividade(null, titulo, descricao, tipo, dataCriacao, dataEntrega, idProfessor, idMateria));
    }

    public void edit(int id, String titulo, String descricao, String tipo, java.time.LocalDate dataCriacao, java.time.LocalDate dataEntrega, int idProfessor, int idMateria) {
        dao.update(new Atividade(id, titulo, descricao, tipo, dataCriacao, dataEntrega, idProfessor, idMateria));
    }

    public Atividade list(int id) {
        return dao.find(id);
    }

    public List<Atividade> listAll() {
        return dao.findAll();
    }

}