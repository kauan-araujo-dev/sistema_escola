package controllers;

import DAO.AlunoDAO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Aluno;

// o controller fica no meio: a view (tela) chama ele e ele chama o DAO (banco)
// assim a tela nao mexe em SQL e o DAO nao mexe em tela
public class AlunoController {

    private ObservableList<Aluno> lista = FXCollections.observableArrayList(); //lista observavel, atualiza sozinha a tela quando for mexida.

    // Property = valor "observavel", avisa quando muda. É o que permite o bindBidirectional da view
    // cada campo do formulario tem uma property aqui
    private IntegerProperty id = new SimpleIntegerProperty(0); // 0 = aluno novo (ainda nao existe no banco)
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty cpf = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty matricula = new SimpleStringProperty("");

    private AlunoDAO dao = new AlunoDAO(); // new AlunoDAO() abre a conexao com o banco

    // quando o controller é criado ja carrega os alunos do banco na lista
    public AlunoController() { pesquisarTodos(); }

    // chamado pelo listener da tabela (quando clica numa linha), joga os dados do aluno nos campos
    public void popularAlunos(Aluno a) {
        if (a != null) { // quando nao tem linha selecionada o "novo" vem null, entao testa antes
            id.set(a.getID()); // sem essa linha o id fica 0 e o salvar faz insert em vez de update
            nome.set(a.getNome());
            cpf.set(a.getCpf());
            email.set(a.getEmail());
            telefone.set(a.getTelefone());
            matricula.set(a.getMatricula());
        }
    }

    // busca tudo no banco e refaz a lista, como a lista é observavel a tabela atualiza sozinha
    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.findAll());
    }

    // chamado pelo botao Salvar
    public boolean salvar(){

        // monta um Aluno com o que ta nas properties (que ja tem o que foi digitado, por causa do binding)
        Aluno a = new Aluno();

        a.setNome(nome.get());
        a.setCpf(cpf.get());
        a.setEmail(email.get());
        a.setTelefone(telefone.get());
        a.setMatricula(matricula.get());

        // aqui decide se cria ou edita, pelo id
        if (id.get() == 0) {
            dao.insert(a); // id 0 = aluno novo, faz insert
        } else {
            a.setID(id.get()); // id preenchido = aluno que ja existe, precisa do id pro WHERE do update
            dao.update(a);
        }


        pesquisarTodos(); // recarrega a tabela pra mostrar a mudanca
        limparCampos(); // limpa o formulario (e volta o id pra 0)

        return true; // sempre true, mesmo se o banco deu erro (o DAO so imprime o erro no console)
    }

    // como os campos estao ligados por binding, mudar a property limpa o campo na tela sozinho
    public void limparCampos(){
        id.set(0);
        nome.set("");
        cpf.set("");
        email.set("");
        telefone.set("");
        matricula.set("");
    }

    // chamado pelo botao Excluir da linha
    public void excluir(Aluno a){
        dao.delete(a.getID()); // apaga no banco pelo id
        pesquisarTodos(); // recarrega a tabela
    }

    // chamado pelo botao Atualizar da linha, so joga os dados nos campos
    // quem grava de verdade é o salvar(), que vai cair no update pq o id ta preenchido
    public void atualizar(Aluno a){
        if (a != null) {
            id.set(a.getID());
            nome.set(a.getNome());
            cpf.set(a.getCpf());
            email.set(a.getEmail());
            telefone.set(a.getTelefone());
            matricula.set(a.getMatricula());
        }
    }

    // pesquisa um aluno so, pelo id
    public Aluno pesquisar(int id){ return dao.find(id); }

    // chamado pelo botao Pesquisar, filtra a tabela pelo nome
    public void pesquisar(Aluno a) {
        lista.clear();
        lista.addAll(dao.findAll()); // traz todos do banco...

        // ...e remove da lista quem NAO tem o texto digitado no nome
        // toLowerCase nos dois lados pra nao diferenciar maiuscula de minuscula
        lista.removeIf(aluno ->
                !aluno.getNome().toLowerCase()
                        .contains(a.getNome().toLowerCase())
        );
    }

    // os getters das properties sao o que a view usa no bindBidirectional
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty cpfProperty() {
        return cpf;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public StringProperty matriculaProperty() {
        return matricula;
    }

    // a view usa isso no table.setItems
    public ObservableList<Aluno> getLista() {
        return lista;
    }
}