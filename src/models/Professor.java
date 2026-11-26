package models;

import java.time.LocalDate;

public class Professor extends Pessoa{
    private String departamento;

    public String getDepartamento() { return this.departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String toString() {
        return super.getNome();
    }

}
