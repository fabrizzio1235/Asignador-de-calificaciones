package uady.ucan.proyecto;

import javafx.application.Application;
import javafx.stage.Stage;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;
import uady.ucan.proyecto.repositorios.RepositorioUsuarios;
import uady.ucan.proyecto.vista.GestorVentanas;

import static uady.ucan.proyecto.vista.GestorVentanas.LOGIN_VIEW_FXML;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        RepositorioAlumnos repositorioAlumnos = new RepositorioAlumnos();
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
        GestorVentanas gestorVentanas = new GestorVentanas(repositorioAlumnos, repositorioUsuarios);
        gestorVentanas.abrirVentana(LOGIN_VIEW_FXML, "Iniciar Sesión");
    }

    public static void main(String[] args) {
        launch(args);
    }
}