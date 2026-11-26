
package controllers;

import DAO.AlunoDAO;
import DAO.MateriaDAO;
import DAO.AlunoMateriaDAO;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import models.Aluno;
import models.Materia;
import models.AlunoMateria;

public class AlunoMateriaController {

    private ObservableList<AlunoMateria> lista = FXCollections.observableArrayList();
    private ObservableList<Aluno> alunos = FXCollections.observableArrayList();
    private ObservableList<Materia> materias = FXCollections.observableArrayList();

    private ObjectProperty<Aluno> aluno = new SimpleObjectProperty<>();
    private ObjectProperty<Materia> materia = new SimpleObjectProperty<>();

    private AlunoMateriaDAO dao = new AlunoMateriaDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();
    private MateriaDAO materiaDAO = new MateriaDAO();

    public AlunoMateriaController() {
        pesquisarTodos();
        alunos.addAll(alunoDAO.findAll());
        materias.addAll(materiaDAO.findAll());
    }

    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.findAll());
    }

    public boolean salvar() {

        if (aluno.get() == null || materia.get() == null) {
            return false;
        }

        AlunoMateria am = new AlunoMateria(
                aluno.get(),
                materia.get());

        dao.insert(am);

        pesquisarTodos();
        limparCampos();

        return true;
    }

    public void limparCampos() {
        aluno.set(null);
        materia.set(null);
    }

    public void excluir(AlunoMateria am) {
        dao.delete(am.getAluno().getID());
        pesquisarTodos();
    }

    public void pesquisar(AlunoMateria am) {
        lista.clear();
        lista.addAll(dao.findAll());

        lista.removeIf(item -> item.getAluno().getID() != am.getAluno().getID()
                || item.getMateria().getId() != am.getMateria().getId());
    }

    public ObjectProperty<Aluno> alunoProperty() {
        return aluno;
    }

    public ObjectProperty<Materia> materiaProperty() {
        return materia;
    }

    public ObservableList<AlunoMateria> getLista() {
        return lista;
    }

    public ObservableList<Aluno> getAlunos() {
        return alunos;
    }

    public ObservableList<Materia> getMaterias() {
        return materias;
    }
}
