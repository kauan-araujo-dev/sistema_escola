package models;

import java.time.LocalDate;

public class Atividade {
    private Integer id;
    private String titulo;
    private String descricao;
    private String tipo;
    private LocalDate dataCriacao;
    private LocalDate dataEntrega;
    private Integer idProfessor;
    private Integer idMateria;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public int getIdProfessor(){
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor){
        this.idProfessor = idProfessor;
    }

    public int getIdMateria(){
        return idMateria;
    }

    public void setIdMateria(int idMateria){
        this.idMateria = idMateria;
    }

}
