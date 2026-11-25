package controllers;

import DAO.AlunoDAO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Aluno;
import models.Curso;

public class AlunoController {

    private ObservableList<Aluno> lista = FXCollections.observableArrayList();
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty cpf = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty matricula = new SimpleStringProperty("");
    private long contadorId = 1;

    private AlunoDAO dao = new AlunoDAO();

    public AlunoController() { pesquisarTodos(); }

    public void popularAlunos(Aluno a) {
        if (a != null) {
            nome.set(a.getNome());
            cpf.set(a.getCpf());
            email.set(a.getEmail());
            telefone.set(a.getTelefone());
            matricula.set(a.getMatricula());
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.findAll());
    }

    public boolean salvar(){

        Aluno a = new Aluno();

        a.setNome(nome.get());
        a.setCpf(cpf.get());
        a.setEmail(email.get());
        a.setTelefone(telefone.get());
        a.setMatricula(matricula.get());
        if (id.get() == 0) {
            dao.insert(a);
        } else {
            a.setID(id.get());
            dao.update(a);
        }


        pesquisarTodos();
        limparCampos();

        return true;
    }

    public void limparCampos(){
        id.set(0);
        nome.set("");
        cpf.set("");
        email.set("");
        telefone.set("");
        matricula.set("");
    }

    public void excluir(Aluno a){
        dao.delete(a.getID());
        pesquisarTodos();
    }

    public void atualizar(Aluno a){
        if (a != null) {
            nome.set(a.getNome());
            cpf.set(a.getCpf());
            email.set(a.getEmail());
            telefone.set(a.getTelefone());
            matricula.set(a.getMatricula());
        }
    }

    public Aluno pesquisar(int id){ return dao.find(id); }

    public void pesquisar(Aluno a) {
        lista.clear();
        lista.addAll(dao.findAll());

        lista.removeIf(aluno ->
                !aluno.getNome().toLowerCase()
                        .contains(a.getNome().toLowerCase())
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

    public StringProperty matricularoperty() {
        return matricula;
    }

    public ObservableList<Aluno> getLista() {
        return lista;
    }
}
