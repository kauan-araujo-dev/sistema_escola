
package controllers;

import DAO.CursoDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Curso;

public class CursoController {

    private ObservableList<Curso> lista = FXCollections.observableArrayList();

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty descricao = new SimpleStringProperty("");
    private StringProperty turno = new SimpleStringProperty("");
    private IntegerProperty cargaHoraria = new SimpleIntegerProperty(0);
    private StringProperty modalidade = new SimpleStringProperty("");

    private CursoDAO dao = new CursoDAO();

    public CursoController() {
        pesquisarTodos();
    }

    public void popularCurso(Curso c) {
        if (c != null) {
            nome.set(c.getNome());
            descricao.set(c.getDescricao());
            turno.set(c.getTurno());
            cargaHoraria.set(c.getCargaHoraria());
            modalidade.set(c.getModalidade());
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.findAll());
    }

    public boolean salvar() {

        Curso c = new Curso();
        c.setNome(nome.get());
        c.setDescricao(descricao.get());
        c.setTurno(turno.get());
        c.setCargaHoraria(cargaHoraria.get());
        c.setModalidade(modalidade.get());

        if (id.get() == 0) {
            dao.insert(c);
        } else {
            c.setId(id.get());
            dao.update(c);
        }

        pesquisarTodos();
        limparCampos();

        return true;
    }

    public void limparCampos() {
        id.set(0);
        nome.set("");
        descricao.set("");
        turno.set("");
        cargaHoraria.set(0);
        modalidade.set("");
    }

    public void excluir(Curso c) {
        dao.delete(c.getId());
        pesquisarTodos();
    }

    public void atualizar(Curso c) {
        if (c != null) {
            id.set(c.getId());
            nome.set(c.getNome());
            descricao.set(c.getDescricao());
            turno.set(c.getTurno());
            cargaHoraria.set(c.getCargaHoraria());
            modalidade.set(c.getModalidade());
        }
    }

    public Curso pesquisar(int id) {
        return dao.find(id);
    }

    public void pesquisar(Curso c) {
        lista.clear();
        lista.addAll(dao.findAll());

        lista.removeIf(curso ->
            !curso.getNome().toLowerCase()
                .contains(c.getNome().toLowerCase())
        );
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public StringProperty turnoProperty() {
        return turno;
    }

    public IntegerProperty cargaHorariaProperty() {
        return cargaHoraria;
    }

    public StringProperty modalidadeProperty() {
        return modalidade;
    }

    public ObservableList<Curso> getLista() {
        return lista;
    }
}
