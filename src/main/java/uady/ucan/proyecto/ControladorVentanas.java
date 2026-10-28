package uady.ucan.proyecto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static uady.ucan.proyecto.Alerta.setAlert;

public class ControladorVentanas {
    public static final String VIEWS_DIRECTORY = "/uady/ucan/proyecto/Views/";
    public static final String LOGIN_VIEW_FXML = VIEWS_DIRECTORY + "Login.fxml";
    public static final String MENU_VIEW_FXML = VIEWS_DIRECTORY + "Menu.fxml";
    public static final String GENERAR_VIEW_FXML = VIEWS_DIRECTORY + "Generar.fxml";
    public static final String CALIFICACIONES_VIEW_FXML = VIEWS_DIRECTORY + "Calificaciones.fxml";
    public static final String CSV_USUARIO_VIEW_FXML = VIEWS_DIRECTORY + "CSVUsuario.fxml";

    protected RepositorioAlumnos repositorioAlumnos = new RepositorioAlumnos();

    @FXML
    private TextField csvNombre;
    @FXML
    private TextField pdfNombre;
    @FXML
    private Button botonSalir;


    public void abrirVentana(String fxmlFileName, String title) {
        try {
            Stage st = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(ControladorVentanas.class.getResource(fxmlFileName));
            Scene scene = new Scene(fxmlLoader.load());

            st.setTitle(title);
            st.setScene(scene);

            st.sizeToScene();
            st.centerOnScreen();

            st.show();

        } catch (IOException | NullPointerException | IllegalStateException e) {
            setAlert(Alert.AlertType.WARNING, "Error al cargar la vista " + e.getMessage());
        }
    }




    @FXML
    public void asignarCalificacion () {
        abrirVentana(CALIFICACIONES_VIEW_FXML, "Gestion de Calificaciones");
    }

    @FXML
    public void recibirCSV () {
        abrirVentana(CSV_USUARIO_VIEW_FXML, "Busqueda de CSV");
    }

    @FXML
    public void generar () {
        if(repositorioAlumnos.getAlumnos() == null){
            setAlert(Alert.AlertType.WARNING, "Asegurarse de escoger el archivo CSV");
            return;
        }
        abrirVentana(GENERAR_VIEW_FXML, "Generar CSV/PDF");

    }
    @FXML
    public void menu (){
        abrirVentana(MENU_VIEW_FXML, "Menu Principal");
    }

    @FXML
    public void login() {
        abrirVentana(LOGIN_VIEW_FXML, "Inicio de Sesión");
    }

    public void cerrarSesion () {
        botonSalir.getScene().getWindow().hide();
        login();
    }

    @FXML
    public void salir () {
        Platform.exit();
    }

    @FXML
    public void generarCSV () {
        GeneradorDeCSV csv = new GeneradorDeCSV();
        csv.generarCsv(csvNombre.getText());
    }
    @FXML
    public void generarPDF() {
        GeneradorReportePDF pdf = new GeneradorReportePDF();
        pdf.generarPdf(pdfNombre.getText());
    }
}
