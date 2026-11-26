
package views;

import controllers.NotaController;
import models.Aluno;
import models.Materia;
import models.Nota;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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

public class NotaView extends View {

    private NotaController control = new NotaController();
    private TableView<Nota> table = new TableView<>();

    @Override
    public void abrir(Stage stage) {
fecharEvento(stage);

        BorderPane panPrincipal = new BorderPane();
        GridPane p = new GridPane();

        Scene scn = new Scene(panPrincipal, 600, 400);
        stage.setScene(scn);

        p.add(new Label("Id: "), 0, 0);
        p.add(new Label("Valor: "), 0, 1);
        p.add(new Label("Aluno: "), 0, 2);
        p.add(new Label("Materia: "), 0, 3);

        TextField txtId = new TextField();
        txtId.setEditable(false);

        TextField txtValor = new TextField();
        ComboBox<Aluno> cbAluno = new ComboBox<>();
        ComboBox<Materia> cbMateria = new ComboBox<>();

        cbAluno.setItems(control.getAlunos());
        cbMateria.setItems(control.getMaterias());

        p.add(txtId, 1, 0);
        p.add(txtValor, 1, 1);
        p.add(cbAluno, 1, 2);
        p.add(cbMateria, 1, 3);

        TableColumn<Nota, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(
                item -> new ReadOnlyLongWrapper(item.getValue().getId()).asObject());

        TableColumn<Nota, Double> colValor = new TableColumn<>("Valor");
        colValor.setCellValueFactory(
                item -> new ReadOnlyDoubleWrapper(item.getValue().getValor()).asObject());

        TableColumn<Nota, Aluno> colAluno = new TableColumn<>("Aluno");
        colAluno.setCellValueFactory(
                item -> new ReadOnlyObjectWrapper<Aluno>(item.getValue().getAluno()));

        TableColumn<Nota, Materia> colMateria = new TableColumn<>("Matéria");
        colMateria.setCellValueFactory(
                item -> new ReadOnlyObjectWrapper<Materia>(item.getValue().getMateria()));

        TableColumn<Nota, Void> colAcoes = new TableColumn<>("Ações");
        Callback<TableColumn<Nota, Void>, TableCell<Nota, Void>> callback = new Callback<>() {
            public TableCell<Nota, Void> call(TableColumn<Nota, Void> tc) {
                TableCell<Nota, Void> tableCell = new TableCell<>() {

                    private final Button btnExcluir = new Button("Excluir");
                    private final Button btnAtualizar = new Button("Atualizar");
                    private final HBox painelBotoes = new HBox();

                    {
                        painelBotoes.getChildren().addAll(btnAtualizar, btnExcluir);

                        btnExcluir.setOnAction(e -> {
                            System.out.printf("Nota %s excluido\n", getTableRow().getItem());
                            control.excluir(getTableRow().getItem());
                        });

                        btnAtualizar.setOnAction(e -> {
                            System.out.printf("Nota %s atualizado\n", getTableRow().getItem());
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
                colId, colValor, colAluno, colMateria, colAcoes);

        table.setItems(control.getLista());

        table.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, antigo, novo) -> {
                            control.popularNota(novo);
                        });

        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtValor.textProperty(), control.valorProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(cbAluno.valueProperty(), control.alunoProperty());
        Bindings.bindBidirectional(cbMateria.valueProperty(), control.materiaProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnPesquisar = new Button("Pesquisar");

        p.add(btnSalvar, 0, 6);
        p.add(btnPesquisar, 1, 6);

        btnSalvar.setOnAction(e -> {
            if (control.salvar()) {
                new Alert(AlertType.INFORMATION,
                        "Nota cadastrada com sucesso", ButtonType.OK)
                        .showAndWait();

                System.out.printf("Nota cadastrada com sucesso!\n");
            }
        });

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);

        btnPesquisar.setOnAction(e -> {
            if (control.alunoProperty().get() != null) {
                Nota n = new Nota();
                n.setAluno(control.alunoProperty().get());
                control.pesquisar(n);
            }
        });

        stage.show();
    }
}
