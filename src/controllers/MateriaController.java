
package controllers;

import DAO.MateriaDAO;
import DAO.ProfessorDAO;
import DAO.CursoDAO;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import models.Materia;
import models.Professor;
import models.Curso;

public class MateriaController {

    private ObservableList<Materia> lista = FXCollections.observableArrayList();
    private ObservableList<Professor> professores = FXCollections.observableArrayList();
    private ObservableList<Curso> cursos = FXCollections.observableArrayList();

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty nome = new SimpleStringProperty("");

    private ObjectProperty<Professor> professor = new SimpleObjectProperty<>();
    private ObjectProperty<Curso> curso = new SimpleObjectProperty<>();

    private MateriaDAO dao = new MateriaDAO();
    private ProfessorDAO professorDAO = new ProfessorDAO();
    private CursoDAO cursoDAO = new CursoDAO();

    public MateriaController() {
        pesquisarTodos();
        professores.addAll(professorDAO.findAll());
        cursos.addAll(cursoDAO.findAll());
    }

    public void popularMateria(Materia m) {
        if (m != null) {
            nome.set(m.getNome());
            professor.set(m.getProfessor());
            curso.set(m.getCurso());
        }
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.findAll());
    }

    public boolean salvar() {

        if (nome.get().isBlank() || professor.get() == null || curso.get() == null) {
            return false;
        }

        Materia m = new Materia();
        m.setNome(nome.get());
        m.setProfessor(professor.get());
        m.setCurso(curso.get());

        if (id.get() == 0) {
            dao.insert(m);
        } else {
            m.setId(id.get());
            dao.update(m);
        }

        pesquisarTodos();
        limparCampos();

        return true;
    }

    public void limparCampos() {
        id.set(0);
        nome.set("");
        professor.set(null);
        curso.set(null);
    }

    public void excluir(Materia m) {
        dao.delete((int) m.getId());
        pesquisarTodos();
    }

    public void atualizar(Materia m) {
        if (m != null) {
            id.set((int) m.getId());
            nome.set(m.getNome());
            professor.set(m.getProfessor());
            curso.set(m.getCurso());
        }
    }

    public Materia pesquisar(int id) {
        return dao.find(id);
    }

    public void pesquisar(Materia m) {
        lista.clear();
        lista.addAll(dao.findAll());

        lista.removeIf(materia ->
            materia.getNome().toLowerCase()
                .contains(m.getNome().toLowerCase()) == false
        );
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public ObjectProperty<Professor> professorProperty() {
        return professor;
    }

    public ObjectProperty<Curso> cursoProperty() {
        return curso;
    }

    public ObservableList<Materia> getLista() {
        return lista;
    }

    public ObservableList<Professor> getProfessores() {
        return professores;
    }

    public ObservableList<Curso> getCursos() {
        return cursos;
    }
}
