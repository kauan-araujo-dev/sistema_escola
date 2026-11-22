
package views;

import controllers.CursoController;
import models.Curso;

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

public class CursoView extends Application {

    private CursoController control = new CursoController();
    private TableView<Curso> table = new TableView<>();

    @Override
    public void start(Stage stage) {

        BorderPane panPrincipal = new BorderPane();
        GridPane p = new GridPane();

        Scene scn = new Scene(panPrincipal, 600, 400);
        stage.setScene(scn);

        p.add(new Label("Id: "), 0, 0);
        p.add(new Label("Nome: "), 0, 1);
        p.add(new Label("Descricao: "), 0, 2);
        p.add(new Label("Turno: "), 0, 3);
        p.add(new Label("Carga Horária: "), 0, 4);
        p.add(new Label("Modalidade: "), 0, 5);

        TextField txtId = new TextField();
        txtId.setEditable(false);

        TextField txtNome = new TextField();
        TextField txtDescricao = new TextField();
        TextField txtTurno = new TextField();
        TextField txtCargaHoraria = new TextField();
        TextField txtModalidade = new TextField();

        p.add(txtId, 1, 0);
        p.add(txtNome, 1, 1);
        p.add(txtDescricao, 1, 2);
        p.add(txtTurno, 1, 3);
        p.add(txtCargaHoraria, 1, 4);
        p.add(txtModalidade, 1, 5);

        TableColumn<Curso, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(
                item -> new ReadOnlyLongWrapper(item.getValue().getId()).asObject());

        TableColumn<Curso, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getNome()));

        TableColumn<Curso, String> colDescricao = new TableColumn<>("Descricao");
        colDescricao.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getDescricao()));

        TableColumn<Curso, String> colTurno = new TableColumn<>("Turno");
        colTurno.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getTurno()));

        TableColumn<Curso, Integer> colCargaHoraria = new TableColumn<>("Carga Horária");
        colCargaHoraria.setCellValueFactory(
                item -> new ReadOnlyObjectWrapper<>(item.getValue().getCargaHoraria()));

        TableColumn<Curso, String> colModalidade = new TableColumn<>("Modalidade");
        colModalidade.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getModalidade()));

        TableColumn<Curso, Void> colAcoes = new TableColumn<>("Ações");
        Callback<TableColumn<Curso, Void>, TableCell<Curso, Void>> callback = new Callback<>() {
            public TableCell<Curso, Void> call(TableColumn<Curso, Void> tc) {
                TableCell<Curso, Void> tableCell = new TableCell<>() {

                    private final Button btnExcluir = new Button("Excluir");
                    private final Button btnAtualizar = new Button("Atualizar");
                    private final HBox painelBotoes = new HBox();

                    {
                        painelBotoes.getChildren().addAll(btnAtualizar, btnExcluir);

                        btnExcluir.setOnAction(e -> {
                            System.out.printf("Curso %s excluido\n", getTableRow().getItem());
                            control.excluir(getTableRow().getItem());
                        });

                        btnAtualizar.setOnAction(e -> {
                            System.out.printf("Curso %s atualizado\n", getTableRow().getItem());
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
                colId, colNome, colDescricao, colTurno,
                colCargaHoraria, colModalidade, colAcoes);

        table.setItems(control.getLista());

        table.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, antigo, novo) -> {
                            control.popularCurso(novo);
                        });

        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtDescricao.textProperty(), control.descricaoProperty());
        Bindings.bindBidirectional(txtTurno.textProperty(), control.turnoProperty());
        Bindings.bindBidirectional(
                txtCargaHoraria.textProperty(),
                control.cargaHorariaProperty(),
                new NumberStringConverter());
        Bindings.bindBidirectional(txtModalidade.textProperty(), control.modalidadeProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnPesquisar = new Button("Pesquisar");

        p.add(btnSalvar, 0, 6);
        p.add(btnPesquisar, 1, 6);

        btnSalvar.setOnAction(e -> {
            if (control.salvar()) {
                new Alert(AlertType.INFORMATION,
                        "Curso cadastrado com sucesso", ButtonType.OK)
                        .showAndWait();

                System.out.printf("Curso cadastrado com sucesso!\n");
            }
        });

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);

        btnPesquisar.setOnAction(e -> {
            Curso c = new Curso();
            c.setNome(control.nomeProperty().get());
            control.pesquisar(c);
        });

        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(CursoView.class, args);
    }
}
