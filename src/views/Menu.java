package views;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class Menu extends Application{



    @Override
    public void start(Stage stage) {
        abrir(stage);
    }

    public void abrir(Stage stage){
         VBox painel = new VBox(15);
        painel.setAlignment(Pos.CENTER);

        Button btnAluno = new Button("Alunos");
        Button btnProfessor = new Button("Professores");
        Button btnCurso = new Button("Cursos");
        Button btnMateria = new Button("Materias");
        Button btnNota = new Button("Notas");
        Button btnAtividade = new Button("Atividades");
        Button btnAlunoMateria = new Button("Alunos x Materias");

        painel.getChildren().addAll(
                btnAluno,
                btnProfessor,
                btnCurso,
                btnMateria,
                btnNota,
                btnAtividade,
                btnAlunoMateria
        );

        AlunoView alunoView = new AlunoView();
        ProfessorView professorView = new ProfessorView();
        CursoView cursoView = new CursoView();
        MateriaView materiaView = new MateriaView();
        NotaView notaView = new NotaView();
        AtividadeView atividadeView = new AtividadeView();
        AlunoMateriaView alunoMateriaView = new AlunoMateriaView();

        btnAluno.setOnAction(e -> alunoView.abrir(stage));
        btnProfessor.setOnAction(e -> professorView.abrir(stage));
        btnCurso.setOnAction(e -> cursoView.abrir(stage));
        btnMateria.setOnAction(e -> materiaView.abrir(stage));
        btnNota.setOnAction(e -> notaView.abrir(stage));
        btnAtividade.setOnAction(e -> atividadeView.abrir(stage));
        btnAlunoMateria.setOnAction(e -> alunoMateriaView.abrir(stage));

        stage.setScene(new Scene(painel, 300, 400));
        stage.setTitle("Menu");
        stage.show();
    }
}
