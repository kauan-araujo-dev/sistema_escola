package controllers;

import DAO.ProfessorDAO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Professor;

// o controller fica no meio: a view (tela) chama ele e ele chama o DAO (banco)
// assim a tela nao mexe em SQL e o DAO nao mexe em tela
public class ProfessorController {

    private ObservableList<Professor> lista = FXCollections.observableArrayList(); //lista observavel, atualiza sozinha a tela quando for mexida.

    // Property = valor "observavel", avisa quando muda. É o que permite o bindBidirectional da view
    // cada campo do formulario tem uma property aqui
    private IntegerProperty id = new SimpleIntegerProperty(0); // 0 = professor novo (ainda nao existe no banco)
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty cpf = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty departamento = new SimpleStringProperty(""); // no aluno era matricula, aqui é departamento

    private ProfessorDAO dao = new ProfessorDAO(); // new ProfessorDAO() abre a conexao com o banco

    // quando o controller é criado ja carrega os professores do banco na lista
    public ProfessorController() { pesquisarTodos(); }

    // chamado pelo listener da tabela (quando clica numa linha), joga os dados do professor nos campos
    public void popularProfessores(Professor p) {
        if (p != null) { // quando nao tem linha selecionada o "novo" vem null, entao testa antes
            id.set(p.getID()); // sem essa linha o id fica 0 e o salvar faz insert em vez de update
            nome.set(p.getNome());
            cpf.set(p.getCpf());
            email.set(p.getEmail());
            telefone.set(p.getTelefone());
            departamento.set(p.getDepartamento());
        }
    }

    // busca tudo no banco e refaz a lista, como a lista é observavel a tabela atualiza sozinha
    public void pesquisarTodos() {
        lista.clear();
        lista.addAll(dao.findAll());
    }

    // chamado pelo botao Salvar
    public boolean salvar() {
        // monta um Professor com o que ta nas properties (que ja tem o que foi digitado, por causa do binding)
        Professor p = new Professor();

        p.setNome(nome.get());
        p.setCpf(cpf.get());
        p.setEmail(email.get());
        p.setTelefone(telefone.get());
        p.setDepartamento(departamento.get());

        // aqui decide se cria ou edita, pelo id
        if (id.get() == 0) {
            dao.insert(p); // id 0 = professor novo, faz insert
        } else {
            p.setID(id.get()); // id preenchido = professor que ja existe, precisa do id pro WHERE do update
            dao.update(p);
        }

        pesquisarTodos(); // recarrega a tabela pra mostrar a mudanca
        limparCampos(); // limpa o formulario (e volta o id pra 0)

        return true; // sempre true, mesmo se o banco deu erro (o DAO so imprime o erro no console)
    }

    // como os campos estao ligados por binding, mudar a property limpa o campo na tela sozinho
    // o id.set(0) é o mais importante, volta o formulario pro modo "professor novo"
    public void limparCampos() {
        id.set(0);
        nome.set("");
        cpf.set("");
        email.set("");
        telefone.set("");
        departamento.set("");
    }

    // chamado pelo botao Excluir da linha
    public void excluir(Professor p) {
        dao.delete(p.getID()); // apaga no banco pelo id
        pesquisarTodos(); // recarrega a tabela
    }

    // chamado pelo botao Atualizar da linha, so joga os dados nos campos
    // reaproveita o popularProfessores pq os dois fazem a mesma coisa
    // quem grava de verdade é o salvar(), que vai cair no update pq o id ta preenchido
    public void atualizar(Professor p) {
        popularProfessores(p);
    }

    // pesquisa um professor so, pelo id (sobrecarga: mesmo nome, parametro diferente)
    public Professor pesquisar(int id) {
        return dao.find(id);
    }

    // chamado pelo botao Pesquisar, filtra a tabela pelo nome
    public void pesquisar(Professor p) {
        lista.clear();
        lista.addAll(dao.findAll()); // traz todos do banco...

        // ...e remove da lista quem NAO tem o texto digitado no nome
        // toLowerCase nos dois lados pra nao diferenciar maiuscula de minuscula
        lista.removeIf(professor ->
                !professor.getNome().toLowerCase()
                        .contains(p.getNome().toLowerCase())
        );
    }

    // os getters das properties sao o que a view usa no bindBidirectional
    // o nome tem que ser igual ao que a view chama (ex: departamentoProperty)
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

    public StringProperty departamentoProperty() {
        return departamento;
    }

    // a view usa isso no table.setItems(...)
    public ObservableList<Professor> getLista() {
        return lista;
    }
}