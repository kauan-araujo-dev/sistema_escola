package models;

public abstract class Pessoa {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    public int getID() {return this.id;}
    public void setID(int valor) { this.id = valor; }

    public String getNome() { return this.nome; }
    public void setNome(String valor) { this.nome = valor; }

    public String getCpf() { return this.cpf; }
    public void setCpf(String valor) { this.cpf = valor; }

    public String getEmail() { return this.email; }
    public void setEmail(String valor) { this.email = valor; }

    public String getTelefone() { return this.telefone; }
    public void setTelefone(String valor) { this.telefone = valor; }
}

