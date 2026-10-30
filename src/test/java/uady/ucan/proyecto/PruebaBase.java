package uady.ucan.proyecto;

import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;
import uady.ucan.proyecto.controlador.ControladorVentanas;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;
import uady.ucan.proyecto.repositorios.RepositorioUsuarios;

import java.io.File;
import java.util.ArrayList;

abstract class PruebaBase extends ApplicationTest {
    protected ControladorVentanas controlador;

    @Override
    public void start(Stage stage) {
        new File("output").mkdirs();
    }

    @BeforeEach
    void prepararEstado() {
            // ID-15 y ID-02
        RepositorioAlumnos repositorioAlumnos = new RepositorioAlumnos();
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
        controlador = new ControladorVentanas(repositorioAlumnos, repositorioUsuarios);
    }

    @AfterEach
    void cerrarVentanas() {
        interact(() -> new ArrayList<>(Window.getWindows()).forEach(Window::hide));
    }

    protected void abrir(String rutaFxml, String titulo) {
        interact(() -> controlador.abrirVentana(rutaFxml, titulo));
    }

    protected boolean hayVentana(String titulo) {
        return listWindows().stream()
                .anyMatch(w -> w instanceof Stage s && s.isShowing() && titulo.equals(s.getTitle()));
    }

    protected boolean hayControl(String selector) {
        return !lookup(selector).queryAll().isEmpty();
    }

    protected <T extends Node> T buscar(String selector) {
        return lookup(selector).query();
    }

    protected DialogPane dialogoAlertaAbierta() {
        return lookup(".dialog-pane").queryAllAs(DialogPane.class).stream()
                .findFirst().orElse(null);
    }

    protected Stage stageAlertaAbierta() {
        return listWindows().stream()
                .filter(w -> w instanceof Stage s && s.isShowing()
                        && s.getScene() != null && s.getScene().getRoot() instanceof DialogPane)
                .map(w -> (Stage) w)
                .findFirst().orElse(null);
    }

    protected String textoAlertaAbierta() {
        DialogPane dp = dialogoAlertaAbierta();
        return dp != null ? dp.getContentText() : null;
    }
}