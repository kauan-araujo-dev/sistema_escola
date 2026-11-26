package views;

import controllers.AlunoController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import models.Aluno;

// extends Application é o que transforma a classe em um app JavaFX
public class AlunoView extends View {

    // o controller é criado aqui (e não no abrir) pq o launch instancia a classe antes de chamar o abrir
    // só pode ter um controller, senao tabela e campos usam controllers diferentes
    private AlunoController control = new AlunoController();
    private TableView<Aluno> table = new TableView<>(); //Tabela que cada linha tem um obj Aluno

    // o abrir é chamado sozinho pelo javafx, é onde monta a tela. Stage = a janela
    @Override
    public void abrir(Stage stage) {
fecharEvento(stage);
        BorderPane panPrincipal = new BorderPane(); //painel que decide onde cada componente fica
        GridPane p = new GridPane(); // painel em grade (linha e coluna), usado no formulario

        Scene scn = new Scene(panPrincipal, 600,400); //cria a cena (tipo a moldura)
        stage.setScene(scn);//define a cena que vai ser usada
        stage.setTitle("Aluno"); //titulo da moldura


        //Criando o titulo dos campos do formulário.
        // p.add(componente, coluna, linha) -> coluna primeiro, depois a linha
        p.add(new Label("Id: "), 0, 0);
        p.add(new Label("Nome: "), 0, 1);
        p.add(new Label("Cpf: "), 0, 2);
        p.add(new Label("Email: "), 0, 3);
        p.add(new Label("Telefone: "), 0, 4);
        p.add(new Label("Matricula:"), 0, 5);

        //criando os campos de texto do formulário
        TextField txtId = new TextField();
        txtId.setEditable(false); //id não é editavel, controla pelo banco
        TextField txtNome = new TextField();
        TextField txtCpf = new TextField();
        TextField txtEmail = new TextField();
        TextField txtTelefone = new TextField();
        TextField txtMatricula = new TextField();

        //adiciona os campos no gridpane (coluna 1, na mesma linha do label)
        p.add(txtId, 1, 0);
        p.add(txtNome, 1, 1);
        p.add(txtCpf, 1, 2);
        p.add(txtEmail, 1, 3);
        p.add(txtTelefone, 1, 4);
        p.add(txtMatricula, 1, 5);



        panPrincipal.setTop(p); // poe o gridpane na parte de cima do painel principal

        TableColumn<Aluno, String> colNome = new TableColumn<>("Nome"); // Criando uma coluna, que as linhas são do tipo Aluno e mostram Strings
        colNome.setCellValueFactory( //Seta o valor da celula da coluna
                item -> new ReadOnlyStringWrapper(item.getValue().getNome()) // ReadOnlyStringWrapper embrulha o texto pq a celula nao aceita String puro, só mostra, não edita
        );

        TableColumn<Aluno, String> colCpf = new TableColumn<>("CPF"); // Criando uma coluna, que as linhas são do tipo Aluno e mostram Strings
        colCpf.setCellValueFactory( //Seta o valor da celula da coluna
                item -> new ReadOnlyStringWrapper(item.getValue().getCpf()) //item representa a linha atual, aqui ele fala pro item adquirir o valor de getCPF
        );

        TableColumn<Aluno, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getEmail())
        );

        TableColumn<Aluno, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getTelefone()) // corrigido, antes tava getEmail
        );

        TableColumn<Aluno, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getMatricula())
        );

        //Criando os botões

        TableColumn<Aluno, Void> colAcoes = new TableColumn<>("Ações"); //Void pois a coluna não exibe nenhum valor do ALuno

        // Callback é a "fabrica" de celulas: recebe a coluna e devolve uma celula nova pra cada linha
        // aqui usa setCellFactory (como a celula é desenhada) e não setCellValueFactory (qual valor vai nela)
        Callback<TableColumn<Aluno, Void>, TableCell<Aluno, Void>> callback = new Callback<>(){
            public TableCell<Aluno, Void> call (TableColumn<Aluno, Void> tc) {
                // classe anonima: uma subclasse de TableCell sem nome, criada na hora
                TableCell<Aluno, Void> tableCell = new TableCell<>() {

                    private final Button btnExcluir = new Button("Excluir");
                    private final Button btnAtualizar = new Button("Atualizar");
                    private final HBox painelBotoes = new HBox(); // HBox coloca os botoes lado a lado (VBox seria um embaixo do outro)

                    // esse bloco solto roda quando a celula é criada, funciona tipo um construtor
                    {
                        painelBotoes.getChildren().addAll(btnAtualizar, btnExcluir);

                        btnExcluir.setOnAction (e -> {
                            // getTableRow().getItem() pega o Aluno da linha onde o botao foi clicado
                            control.excluir(getTableRow().getItem());
                        });

                        btnAtualizar.setOnAction(e -> {
                            // só joga os dados da linha nos campos, quem salva mesmo é o botao Salvar
                            control.atualizar(getTableRow().getItem());
                        });
                    }

                    // o javafx chama isso toda vez que a celula é criada ou reaproveitada
                    // empty = true quando a linha é vazia (a tabela desenha linhas extras no final)
                    @Override
                    public void updateItem(Void valor, boolean empty) {
                        super.updateItem(valor, empty); // deixa a classe pai fazer o trabalho dela
                        if (!empty) {

                            setGraphic(painelBotoes); // linha com aluno: mostra os botoes
                        } else {
                            setGraphic(null); // linha vazia: sem botoes
                        }
                    }
                };
                return tableCell;
            }
        };

        colAcoes.setCellFactory(callback);

        table.getColumns().addAll(colNome, colCpf, colEmail, colTelefone, colMatricula, colAcoes); //adiciona as colunas na tabela (na ordem que aparecem)
        table.setItems(control.getLista()); //Liga a tabela a lista do controller (é ObservableList, entao a tabela atualiza sozinha)

        panPrincipal.setCenter(table); // tabela no centro, ocupa o espaço que sobra

        // listener: roda toda vez que seleciona outra linha da tabela
        // "novo" é o Aluno clicado, e o controller joga os dados dele nos campos
        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    control.popularAlunos(novo);
                });

        // bindBidirectional liga o campo e a property do controller nos dois sentidos
        // digitou no campo -> muda no controller / controller mudou -> aparece no campo
        // NumberStringConverter só no id pq o campo é String e o id é numero
        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtCpf.textProperty(), control.cpfProperty());
        Bindings.bindBidirectional(txtEmail.textProperty(), control.emailProperty());
        Bindings.bindBidirectional(txtTelefone.textProperty(), control.telefoneProperty());
        Bindings.bindBidirectional(txtMatricula.textProperty(), control.matriculaProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnPesquisar = new Button("Pesquisar");

        p.add(btnSalvar, 0, 6);
        p.add(btnPesquisar, 1, 6);

        // setOnAction define o que acontece no clique (lambda)
        btnSalvar.setOnAction(e -> {
            // salvar() no controller decide: id 0 = insert, id preenchido = update
            if (control.salvar()) {
                // Alert é a janelinha de aviso, showAndWait espera fechar antes de seguir
                new Alert(Alert.AlertType.INFORMATION,
                        "Aluno cadastrado com sucesso", ButtonType.OK)
                        .showAndWait();
            }
        });

        btnPesquisar.setOnAction(e -> {
            // cria um Aluno só com o nome digitado e o controller filtra a lista por ele
            Aluno a = new Aluno();
            a.setNome(control.nomeProperty().get());
            control.pesquisar(a);
        });

        stage.show(); // sem isso a janela não aparece

    }
}