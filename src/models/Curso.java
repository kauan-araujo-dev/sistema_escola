package models;

public class Curso {
    private Integer id;
    private String nome;
    private String descricao;
    private String turno;
    private int cargaHoraria;
    private String modalidade;

    public Curso(Integer id, String nome, String descricao, String turno, int cargaHoraria, String modalidade){
        super();
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.turno = turno;
        this.cargaHoraria = cargaHoraria;
        this.modalidade = modalidade;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

}
