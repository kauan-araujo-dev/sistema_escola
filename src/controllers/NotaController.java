package controllers;

import DAO.NotaDAO;
import DAO.AlunoDAO;
import DAO.MateriaDAO;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import models.Nota;
import models.Aluno;
import models.Materia;

public class NotaController {

    private ObservableList<Nota> lista = FXCollections.observableArrayList();
    private ObservableList<Aluno> alunos = FXCollections.observableArrayList();
    private ObservableList<Materia> materias = FXCollections.observableArrayList();

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private DoubleProperty valor = new SimpleDoubleProperty(0);

    private ObjectProperty<Aluno> aluno = new SimpleObjectProperty<>();
    private ObjectProperty<Materia> materia = new SimpleObjectProperty<>();

    private NotaDAO dao = new NotaDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();
    private MateriaDAO materiaDAO = new MateriaDAO();

    public NotaController() {
        pesquisarTodos();
        alunos.addAll(alunoDAO.findAll());
        materias.addAll(materiaDAO.findAll());
    }

    public void popularNota(Nota n) {
        if (n != null) {
            valor.set(n.getValor());
            aluno.set(n.getAluno());
            materia.set(n.getMateria());
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.findAll());
    }

    public boolean salvar() {

        if (aluno.get() == null || materia.get() == null) {
            return false;
        }
        
        Nota n = new Nota();
        n.setValor(valor.get());
        n.setAluno(aluno.get());
        n.setMateria(materia.get());

        if (id.get() == 0) {
            dao.insert(n);
        } else {
            n.setId(id.get());
            dao.update(n);
        }

        pesquisarTodos();
        limparCampos();

        return true;
    }

    public void limparCampos() {
        id.set(0);
        valor.set(0);
        aluno.set(null);
        materia.set(null);
    }

    public void excluir(Nota n) {
        dao.delete((int) n.getId());
        pesquisarTodos();
    }

    public void atualizar(Nota n) {
        if (n != null) {
            id.set((int) n.getId());
            valor.set(n.getValor());
            aluno.set(n.getAluno());
            materia.set(n.getMateria());
        }
    }

    public Nota pesquisar(int id) {
        return dao.find(id);
    }

    public void pesquisar(Nota n) {
        lista.clear();
        lista.addAll(dao.findAll());

        lista.removeIf(nota -> nota.getAluno().getNome().toLowerCase()
                .contains(n.getAluno().getNome().toLowerCase()) == false);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public DoubleProperty valorProperty() {
        return valor;
    }

    public ObjectProperty<Aluno> alunoProperty() {
        return aluno;
    }

    public ObjectProperty<Materia> materiaProperty() {
        return materia;
    }

    public ObservableList<Nota> getLista() {
        return lista;
    }

    public ObservableList<Aluno> getAlunos() {
        return alunos;
    }

    public ObservableList<Materia> getMaterias() {
        return materias;
    }
}