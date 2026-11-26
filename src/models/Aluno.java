package models;

public class Aluno extends Pessoa {
    private String matricula;

    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String valor) {
        this.matricula = valor;
    }

    @Override
    public String toString() {
        return super.getNome();
    }
}
