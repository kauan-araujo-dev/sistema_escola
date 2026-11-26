
package views;

import controllers.AlunoMateriaController;
import models.AlunoMateria;
import models.Aluno;
import models.Materia;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AlunoMateriaView extends View {

    private AlunoMateriaController control = new AlunoMateriaController();
    private TableView<AlunoMateria> table = new TableView<>();

    @Override
    public void abrir(Stage stage) {
        fecharEvento(stage);

        BorderPane panPrincipal = new BorderPane();
        GridPane p = new GridPane();

        Scene scn = new Scene(panPrincipal, 600, 400);
        stage.setScene(scn);

        p.add(new Label("Aluno: "), 0, 0);
        p.add(new Label("Materia: "), 0, 1);

        ComboBox<Aluno> cbAluno = new ComboBox<>();
        ComboBox<Materia> cbMateria = new ComboBox<>();

        cbAluno.setItems(control.getAlunos());
        cbMateria.setItems(control.getMaterias());

        p.add(cbAluno, 1, 0);
        p.add(cbMateria, 1, 1);

        TableColumn<AlunoMateria, Aluno> colAluno = new TableColumn<>("Aluno");
        colAluno.setCellValueFactory(
                item -> new ReadOnlyObjectWrapper<Aluno>(item.getValue().getAluno()));

        TableColumn<AlunoMateria, Materia> colMateria = new TableColumn<>("Materia");
        colMateria.setCellValueFactory(
                item -> new ReadOnlyObjectWrapper<Materia>(item.getValue().getMateria()));

        TableColumn<AlunoMateria, Void> colAcoes = new TableColumn<>("Ações");

        Callback<TableColumn<AlunoMateria, Void>, TableCell<AlunoMateria, Void>> callback = new Callback<>() {
            public TableCell<AlunoMateria, Void> call(TableColumn<AlunoMateria, Void> tc) {
                TableCell<AlunoMateria, Void> tableCell = new TableCell<>() {

                    private final Button btnExcluir = new Button("Excluir");
                    private final HBox painelBotoes = new HBox();

                    {
                        painelBotoes.getChildren().add(btnExcluir);

                        btnExcluir.setOnAction(e -> {
                            AlunoMateria am = getTableRow().getItem();

                            if (am != null) {
                                System.out.printf("Associacao %s - %s excluida\n",
                                        am.getAluno(), am.getMateria());

                                control.excluir(am);
                            }
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
                colAluno, colMateria, colAcoes);

        table.setItems(control.getLista());

        table.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, antigo, novo) -> {
                            if (novo != null) {
                                control.alunoProperty().set(novo.getAluno());
                                control.materiaProperty().set(novo.getMateria());
                            }
                        });

        Bindings.bindBidirectional(cbAluno.valueProperty(), control.alunoProperty());
        Bindings.bindBidirectional(cbMateria.valueProperty(), control.materiaProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnPesquisar = new Button("Pesquisar");
        Button btnLimpar = new Button("Limpar");

        p.add(btnSalvar, 0, 3);
        p.add(btnPesquisar, 1, 3);
        p.add(btnLimpar, 2, 3);

        btnSalvar.setOnAction(e -> {
            if (control.salvar()) {
                new Alert(AlertType.INFORMATION,
                        "Associacao cadastrada com sucesso", ButtonType.OK)
                        .showAndWait();

                System.out.printf("Associacao cadastrada com sucesso!\n");
            } else {
                new Alert(AlertType.WARNING,
                        "Selecione um aluno e uma materia", ButtonType.OK)
                        .showAndWait();
            }
        });

        btnLimpar.setOnAction(e -> {
            control.limparCampos();
            control.pesquisarTodos();
        });

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);

        btnPesquisar.setOnAction(e -> {
            if (control.alunoProperty().get() != null) {

                AlunoMateria am = new AlunoMateria(
                        control.alunoProperty().get(),
                        control.materiaProperty().get());

                control.pesquisar(am);
            }
        });

        stage.show();
    }

}
