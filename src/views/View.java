package views;

import javafx.stage.Stage;

public abstract class View {
    public abstract void abrir(Stage stage);

    public void fecharEvento(Stage stage) {
        stage.setOnCloseRequest(e -> {
            e.consume();
            new Menu().abrir(stage);
        });
    }
}