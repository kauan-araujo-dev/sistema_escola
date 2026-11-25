package controllers;

import DAO.AtividadeDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Atividade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AtividadeController {

    private ObservableList<Atividade> lista = FXCollections.observableArrayList();

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty titulo = new SimpleStringProperty("");
    private StringProperty descricao = new SimpleStringProperty("");
    private StringProperty tipo = new SimpleStringProperty("");
    private StringProperty dataCriacao = new SimpleStringProperty("");
    private StringProperty dataEntrega = new SimpleStringProperty("");
    private IntegerProperty idProfessor = new SimpleIntegerProperty(0);
    private IntegerProperty idMateria = new SimpleIntegerProperty(0);

    private AtividadeDAO dao = new AtividadeDAO();

    public AtividadeController() {
        pesquisarTodos();
    }

    public void popularAtividade(Atividade a) {
        if (a != null) {
            titulo.set(a.getTitulo());
            descricao.set(a.getDescricao());
            tipo.set(a.getTipo());
            dataCriacao.set(a.getDataCriacao().toString());
            dataEntrega.set(a.getDataEntrega().toString());
            idProfessor.set(a.getIdProfessor());
            idMateria.set(a.getIdMateria());
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.findAll());
    }

    public boolean salvar() {

        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Atividade a = new Atividade();
            a.setTitulo(titulo.get());
            a.setDescricao(descricao.get());
            a.setTipo(tipo.get());

            a.setDataCriacao(LocalDate.parse(dataCriacao.get(), formato));
            a.setDataEntrega(LocalDate.parse(dataEntrega.get(), formato));

            a.setIdProfessor(idProfessor.get());
            a.setIdMateria(idMateria.get());

            if (id.get() == 0) {
                dao.insert(a);
            } else {
                a.setId(id.get());
                dao.update(a);
            }

            pesquisarTodos();
            limparCampos();

            return true;

        } catch (DateTimeParseException e) {
            System.out.println("Erro: Digite as datas no formato dd/MM/yyyy.");
            return false;
        }
    }

    public void limparCampos() {
        id.set(0);
        titulo.set("");
        descricao.set("");
        tipo.set("");
        dataCriacao.set("");
        dataEntrega.set("");
        idProfessor.set(0);
        idMateria.set(0);
    }

    public void excluir(Atividade a) {
        dao.delete(a.getId());
        pesquisarTodos();
    }

    public void atualizar(Atividade a) {
        if (a != null) {

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            id.set(a.getId());
            titulo.set(a.getTitulo());
            descricao.set(a.getDescricao());
            tipo.set(a.getTipo());

            dataCriacao.set(a.getDataCriacao().format(formato));
            dataEntrega.set(a.getDataEntrega().format(formato));

            idProfessor.set(a.getIdProfessor());
            idMateria.set(a.getIdMateria());
        }
    }

    public Atividade pesquisar(int id) {
        return dao.find(id);
    }

    public void pesquisar(Atividade a) {
        lista.clear();
        lista.addAll(dao.findAll());

        lista.removeIf(atividade -> !atividade.getTitulo().toLowerCase()
                .contains(a.getTitulo().toLowerCase()));
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public StringProperty dataCriacaoProperty() {
        return dataCriacao;
    }

    public StringProperty dataEntregaProperty() {
        return dataEntrega;
    }

    public IntegerProperty idProfessorProperty() {
        return idProfessor;
    }

    public IntegerProperty idMateriaProperty() {
        return idMateria;
    }

    public ObservableList<Atividade> getLista() {
        return lista;
    }
}