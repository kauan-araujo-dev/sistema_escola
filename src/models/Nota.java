package models;

public class Nota {

    private long id;
    private double valor;
    private Aluno aluno;
    private Materia materia;

    public Nota() {
    }

    public Nota(long id, double valor, Aluno aluno, Materia materia) {
        this.id = id;
        this.valor = valor;
        this.aluno = aluno;
        this.materia = materia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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