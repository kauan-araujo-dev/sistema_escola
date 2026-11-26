package models;

public class AlunoMateria {

    private Aluno aluno;
    private Materia materia;

    public AlunoMateria() {
    }

    public AlunoMateria(Aluno aluno, Materia materia) {
        this.aluno = aluno;
        this.materia = materia;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}