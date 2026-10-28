package uady.ucan.proyecto;

import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.util.ArrayList;

abstract class PruebaBase extends ApplicationTest {
    protected ControladorVentanas controlador;
    protected RepositorioAlumnos repositorioAlumnos;

    @Override
    public void start(Stage stage) {
        new File("output").mkdirs();
    }

    @BeforeEach
    void prepararEstado() {
        // HOY (Antes de refactorizar):
        controlador = new ControladorVentanas();
        repositorioAlumnos.setAlumnos(null);
        // PRÓXIMA SEMANA (Después de ID-12 e ID-15):
        // controlador = new ControladorVentanas(new RepositorioAlumnos(), new RepositorioUsuarios());
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