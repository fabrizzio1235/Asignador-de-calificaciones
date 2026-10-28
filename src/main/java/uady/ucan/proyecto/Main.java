package uady.ucan.proyecto;

import javafx.application.Application;
import javafx.stage.Stage;

import static uady.ucan.proyecto.ControladorVentanas.LOGIN_VIEW_FXML;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        RepositorioAlumnos repositorioAlumnos = new RepositorioAlumnos();
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
        ControladorVentanas cv = new ControladorVentanas(repositorioAlumnos, repositorioUsuarios);
        cv.abrirVentana(LOGIN_VIEW_FXML, "Iniciar Sesión");
    }

    public static void main(String[] args) {
        launch(args);
    }
}