package models;

import java.util.ArrayList;
import java.util.List;

public class Materia {

    private long id;
    private String nome;
    private Professor professor;
    Curso curso;

    public Materia() {
    }

    public Materia(long id, String nome, Professor professor, Curso curso) {
        this.id = id;
        this.nome = nome;
        this.professor = professor;
        this.curso = curso;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}