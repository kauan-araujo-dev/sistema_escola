package views;

import controllers.AtividadeController;
import models.Atividade;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

public class AtividadeView extends View {

    private AtividadeController control = new AtividadeController();
    private TableView<Atividade> table = new TableView<>();

    @Override
    public void abrir(Stage stage) {
        fecharEvento(stage);

        BorderPane panPrincipal = new BorderPane();
        GridPane p = new GridPane();

        Scene scn = new Scene(panPrincipal, 600, 400);
        stage.setScene(scn);

        p.add(new Label("Id: "), 0, 0);
        p.add(new Label("Titulo Atividade: "), 0, 1);
        p.add(new Label("Descricao: "), 0, 2);
        p.add(new Label("Tipo: "), 0, 3);
        p.add(new Label("Data Criacao: "), 0, 4);
        p.add(new Label("Data Entrega: "), 0, 5);
        p.add(new Label("Código do Professor: "), 0, 6);
        p.add(new Label("Código da Materia: "), 0, 7);

        TextField txtId = new TextField();
        txtId.setEditable(false);

        TextField txtTitulo = new TextField();
        TextField txtDescricao = new TextField();
        TextField txtTipo = new TextField();
        TextField txtDataCriacao = new TextField();
        TextField txtDataEntrega = new TextField();
        TextField txtIdProfessor = new TextField();
        TextField txtIdMateria = new TextField();

        p.add(txtId, 1, 0);
        p.add(txtTitulo, 1, 1);
        p.add(txtDescricao, 1, 2);
        p.add(txtTipo, 1, 3);
        p.add(txtDataCriacao, 1, 4);
        p.add(txtDataEntrega, 1, 5);
        p.add(txtIdProfessor, 1, 6);
        p.add(txtIdMateria, 1, 7);

        TableColumn<Atividade, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(
                item -> new ReadOnlyLongWrapper(item.getValue().getId()).asObject());

        TableColumn<Atividade, String> colTitulo = new TableColumn<>("Titulo");
        colTitulo.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getTitulo()));

        TableColumn<Atividade, String> colDescricao = new TableColumn<>("Descricao");
        colDescricao.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getDescricao()));

        TableColumn<Atividade, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getTipo()));

        TableColumn<Atividade, String> colDataCriacao = new TableColumn<>("Data Criacao");
        colDataCriacao.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getDataCriacao().toString()));

        TableColumn<Atividade, String> colDataEntrega = new TableColumn<>("Data Entrega");
        colDataEntrega.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getDataEntrega().toString()));

        TableColumn<Atividade, Integer> colIdProfessor = new TableColumn<>("Código do Professor");
        colIdProfessor.setCellValueFactory(
                item -> new ReadOnlyObjectWrapper<>(item.getValue().getIdProfessor()));

        TableColumn<Atividade, Integer> colIdMateria = new TableColumn<>("Código da Materia");
        colIdMateria.setCellValueFactory(
                item -> new ReadOnlyObjectWrapper<>(item.getValue().getIdMateria()));

        TableColumn<Atividade, Void> colAcoes = new TableColumn<>("Ações");
        Callback<TableColumn<Atividade, Void>, TableCell<Atividade, Void>> callback = new Callback<>() {
            public TableCell<Atividade, Void> call(TableColumn<Atividade, Void> tc) {
                TableCell<Atividade, Void> tableCell = new TableCell<>() {

                    private final Button btnExcluir = new Button("Excluir");
                    private final Button btnAtualizar = new Button("Atualizar");
                    private final HBox painelBotoes = new HBox();

                    {
                        painelBotoes.getChildren().addAll(btnAtualizar, btnExcluir);

                        btnExcluir.setOnAction(e -> {
                            System.out.printf("Atividade %s excluida\n", getTableRow().getItem());
                            control.excluir(getTableRow().getItem());
                        });

                        btnAtualizar.setOnAction(e -> {
                            System.out.printf("Atividade %s atualizada\n", getTableRow().getItem());
                            control.atualizar(getTableRow().getItem());
                        });
                    }

                    public void updateItem(Void valor, boolean empty) {
                        super.updateItem(valor, empty);
                        if (!empty) {
                            setGraphic(painelBotoes);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
                return tableCell;
            }
        };

        colAcoes.setCellFactory(callback);

        table.getColumns().addAll(
                colId, colTitulo, colDescricao, colTipo,
                colDataCriacao, colDataEntrega, colIdProfessor,
                colIdMateria, colAcoes);

        table.setItems(control.getLista());

        table.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, antigo, novo) -> {
                            control.popularAtividade(novo);
                        });

        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtTitulo.textProperty(), control.tituloProperty());
        Bindings.bindBidirectional(txtDescricao.textProperty(), control.descricaoProperty());
        Bindings.bindBidirectional(txtTipo.textProperty(), control.tipoProperty());
        Bindings.bindBidirectional(txtDataCriacao.textProperty(), control.dataCriacaoProperty());
        Bindings.bindBidirectional(txtDataEntrega.textProperty(), control.dataEntregaProperty());
        Bindings.bindBidirectional(txtIdProfessor.textProperty(), control.idProfessorProperty(),
                new NumberStringConverter());
        Bindings.bindBidirectional(txtIdMateria.textProperty(), control.idMateriaProperty(),
                new NumberStringConverter());

        Button btnSalvar = new Button("Salvar");
        Button btnPesquisar = new Button("Pesquisar");

        p.add(btnSalvar, 0, 8);
        p.add(btnPesquisar, 1, 8);

        btnSalvar.setOnAction(e -> {
            if (control.salvar()) {
                new Alert(AlertType.INFORMATION,
                        "Atividade cadastrada com sucesso", ButtonType.OK)
                        .showAndWait();

                System.out.printf("Atividade cadastrada com sucesso!\n");
            }
        });

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);

        btnPesquisar.setOnAction(e -> {
            Atividade a = new Atividade();
            a.setTitulo(control.tituloProperty().get());
            control.pesquisar(a);
        });

        stage.show();
    }

}