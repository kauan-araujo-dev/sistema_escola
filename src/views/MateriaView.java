
package views;

import controllers.MateriaController;
import models.Materia;
import models.Professor;
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

public class MateriaView extends View {

    private MateriaController control = new MateriaController();
    private TableView<Materia> table = new TableView<>();

    @Override
    public void abrir(Stage stage) {
        fecharEvento(stage);

        BorderPane panPrincipal = new BorderPane();
        GridPane p = new GridPane();

        Scene scn = new Scene(panPrincipal, 600, 400);
        stage.setScene(scn);

        p.add(new Label("Id: "), 0, 0);
        p.add(new Label("Nome: "), 0, 1);
        p.add(new Label("Professor: "), 0, 2);
        p.add(new Label("Curso: "), 0, 3);

        TextField txtId = new TextField();
        txtId.setEditable(false);

        TextField txtNome = new TextField();

        ComboBox<Professor> cbProfessor = new ComboBox<>();
        ComboBox<Curso> cbCurso = new ComboBox<>();

        cbProfessor.setItems(control.getProfessores());
        cbCurso.setItems(control.getCursos());

        p.add(txtId, 1, 0);
        p.add(txtNome, 1, 1);
        p.add(cbProfessor, 1, 2);
        p.add(cbCurso, 1, 3);

        TableColumn<Materia, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(
                item -> new ReadOnlyLongWrapper(item.getValue().getId()).asObject());

        TableColumn<Materia, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getNome()));

        TableColumn<Materia, Professor> colProfessor = new TableColumn<>("Professor");
        colProfessor.setCellValueFactory(
                item -> new ReadOnlyObjectWrapper<Professor>(item.getValue().getProfessor()));

        TableColumn<Materia, Curso> colCurso = new TableColumn<>("Curso");
        colCurso.setCellValueFactory(
                item -> new ReadOnlyObjectWrapper<Curso>(item.getValue().getCurso()));

        TableColumn<Materia, Void> colAcoes = new TableColumn<>("Ações");

        Callback<TableColumn<Materia, Void>, TableCell<Materia, Void>> callback = new Callback<>() {
            public TableCell<Materia, Void> call(TableColumn<Materia, Void> tc) {
                TableCell<Materia, Void> tableCell = new TableCell<>() {

                    private final Button btnExcluir = new Button("Excluir");
                    private final Button btnAtualizar = new Button("Atualizar");
                    private final HBox painelBotoes = new HBox();

                    {
                        painelBotoes.getChildren().addAll(btnAtualizar, btnExcluir);

                        btnExcluir.setOnAction(e -> {
                            System.out.printf("Materia %s excluida\n", getTableRow().getItem());
                            control.excluir(getTableRow().getItem());
                        });

                        btnAtualizar.setOnAction(e -> {
                            System.out.printf("Materia %s atualizada\n", getTableRow().getItem());
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
                colId, colNome, colProfessor, colCurso, colAcoes);

        table.setItems(control.getLista());

        table.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, antigo, novo) -> {
                            control.popularMateria(novo);
                        });

        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(cbProfessor.valueProperty(), control.professorProperty());
        Bindings.bindBidirectional(cbCurso.valueProperty(), control.cursoProperty());

        Button btnSalvar = new Button("Salvar");
        Button btnPesquisar = new Button("Pesquisar");

        p.add(btnSalvar, 0, 6);
        p.add(btnPesquisar, 1, 6);

        btnSalvar.setOnAction(e -> {
            if (control.salvar()) {
                new Alert(AlertType.INFORMATION,
                        "Materia cadastrada com sucesso", ButtonType.OK)
                        .showAndWait();

                System.out.printf("Materia cadastrada com sucesso!\n");
            } else {
                new Alert(AlertType.WARNING,
                        "Preencha todos os campos", ButtonType.OK)
                        .showAndWait();
            }
        });

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);

        btnPesquisar.setOnAction(e -> {
            Materia m = new Materia();
            m.setNome(control.nomeProperty().get());
            control.pesquisar(m);
        });

        stage.show();
    }

}
