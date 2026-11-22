package views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controllers.UserController;

public class ViewAddUser {
    Stage stage;
    UserController userController;

    public ViewAddUser(Stage stage) {
        this.stage = stage;
        userController = new UserController();
    }

    public void show() {
        Label lblNome = new Label("Nome:");
        TextField txtNome = new TextField();

        Label lblIdade = new Label("Idade:");
        TextField txtIdade = new TextField();

        Button btnSalvar = new Button("Salvar");

        VBox layout = new VBox(10);

        layout.getChildren().addAll(
                lblNome,
                txtNome,
                lblIdade,
                txtIdade,
                btnSalvar);

        btnSalvar.setOnAction(e -> {
            Label labelErro = new Label();

            String nomeText = txtNome.getText().trim();
            String idadeText = txtIdade.getText().trim();

            try {
                if (!nomeText.equals("") || !idadeText.equals("")) {
                    userController.add(nomeText, Integer.parseInt(idadeText));
                } else {
                    labelErro.setText("Preencha todos os campos");
                    layout.getChildren().addAll(labelErro);
                }
            } catch (Exception error) {
                labelErro.setText("Erro ao inserir usuário");
                layout.getChildren().addAll(labelErro);
            }
        });

        Scene scene = new Scene(layout);
        stage.setTitle("Cadastra Usuário");
        stage.setScene(scene);
        stage.show();
    }
}
