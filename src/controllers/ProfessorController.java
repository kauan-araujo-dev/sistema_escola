package controllers;

import DAO.ProfessorDAO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Professor;

public class ProfessorController {

    private ObservableList<Professor> lista = FXCollections.observableArrayList();
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty cpf = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty departamento = new SimpleStringProperty("");

    private ProfessorDAO dao = new ProfessorDAO();

    public ProfessorController() { pesquisarTodos(); }

    public void popularProfessores(Professor p) {
        if (p != null) {
            id.set(p.getID());
            nome.set(p.getNome());
            cpf.set(p.getCpf());
            email.set(p.getEmail());
            telefone.set(p.getTelefone());
            departamento.set(p.getDepartamento());
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.findAll());
    }

    public boolean salvar() {
        Professor p = new Professor();

        p.setNome(nome.get());
        p.setCpf(cpf.get());
        p.setEmail(email.get());
        p.setTelefone(telefone.get());
        p.setDepartamento(departamento.get());

        if (id.get() == 0) {
            dao.insert(p);
        } else {
            p.setID(id.get());
            dao.update(p);
        }

        pesquisarTodos();
        limparCampos();

        return true;
    }

    public void limparCampos() {
        id.set(0);
        nome.set("");
        cpf.set("");
        email.set("");
        telefone.set("");
        departamento.set("");
    }

    public void excluir(Professor p) {
        dao.delete(p.getID());
        pesquisarTodos();
    }

    public void atualizar(Professor p) {
        popularProfessores(p);
    }

    public Professor pesquisar(int id) {
        return dao.find(id);
    }

    public void pesquisar(Professor p) {
        lista.clear();
        lista.addAll(dao.findAll());

        lista.removeIf(professor ->
                !professor.getNome().toLowerCase()
                        .contains(p.getNome().toLowerCase())
        );
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty cpfProperty() {
        return cpf;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public StringProperty departamentoProperty() {
        return departamento;
    }

    public ObservableList<Professor> getLista() {
        return lista;
    }
}