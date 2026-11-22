package controllers;

import java.util.List;

import DAO.CursoDAO;
import models.Curso;

public class CursoController {
    CursoDAO dao;

    public CursoController() {
        dao = new CursoDAO();
    }

    public void add(String nome, String descricao, String turno, int cargaHoraria, String modalidade) {
        dao.insert(new Curso(null, nome, descricao, turno, cargaHoraria, modalidade));
    }

    public void edit(int id, String nome, String descricao, String turno, int cargaHoraria, String modalidade) {
        dao.update(new Curso(id, nome, descricao, turno, cargaHoraria, modalidade));
    }

    public Curso list(int id) {
        return dao.find(id);
    }

    public List<Curso> listAll() {
        return dao.findAll();
    }
}