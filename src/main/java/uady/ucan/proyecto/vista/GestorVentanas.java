package uady.ucan.proyecto.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import uady.ucan.proyecto.archivos.LectorCSV;
import uady.ucan.proyecto.controlador.ControladorArchivos;
import uady.ucan.proyecto.controlador.ControladorLogin;
import uady.ucan.proyecto.controlador.ControladorMenu;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;
import uady.ucan.proyecto.repositorios.RepositorioUsuarios;
import uady.ucan.proyecto.servicios.ServicioCalificaciones;

import java.io.IOException;

import static uady.ucan.proyecto.controlador.ControladorAlerta.mostrarAlerta;

//ID-04: Extract Class (y Move Field)
//ControladorVentanas mezclaba el menú principal con los métodos utilitarios para abrir ventanas.
//Ahora GestorVentanas se encarga de abrir y cerrar ventanas, y de crear los controladores que reciben parámetros;
//cualquier controlador puede usarlo sin depender del menú.
public class GestorVentanas {
    public static final String VIEWS_DIRECTORY = "/uady/ucan/proyecto/Views/";
    public static final String LOGIN_VIEW_FXML = VIEWS_DIRECTORY + "Login.fxml";
    public static final String MENU_VIEW_FXML = VIEWS_DIRECTORY + "Menu.fxml";
    public static final String GENERAR_VIEW_FXML = VIEWS_DIRECTORY + "Generar.fxml";
    public static final String CALIFICACIONES_VIEW_FXML = VIEWS_DIRECTORY + "Calificaciones.fxml";
    public static final String CSV_USUARIO_VIEW_FXML = VIEWS_DIRECTORY + "CSVUsuario.fxml";

    private final RepositorioAlumnos repositorioAlumnos;
    private final RepositorioUsuarios repositorioUsuarios;

    // ID-15: Add Parameter (recibe los repositorios por constructor)
    public GestorVentanas(RepositorioAlumnos repositorioAlumnos, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioAlumnos = repositorioAlumnos;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public void abrirVentana(String fxmlFileName, String title) {
        try {
            Stage st = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(GestorVentanas.class.getResource(fxmlFileName));
            //Evita que el fxml genere un controlador sin parametros
            fxmlLoader.setControllerFactory(this::crearClasesConParametros);

            Scene scene = new Scene(fxmlLoader.load());

            st.setTitle(title);
            st.setScene(scene);
            st.sizeToScene();
            st.centerOnScreen();
            st.show();

        } catch (IOException | NullPointerException | IllegalStateException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error al cargar la vista " + e.getMessage());
        }
    }

    // Metódo que se encarga de crear clases con parámetros (pŕincipalmente controladores)
    // que fxml sin ninguna intervencion generaria sin sus parametros
    private Object crearClasesConParametros (Class<?> tipo) {
        if (tipo == ControladorMenu.class) {
            return new ControladorMenu(repositorioAlumnos, repositorioUsuarios);
        }
        if (tipo == LectorCSV.class) {
            return new LectorCSV(repositorioAlumnos);
        }
        if (tipo == ServicioCalificaciones.class) {
            return new ServicioCalificaciones(repositorioAlumnos);
        }
        if (tipo == ControladorLogin.class) {
            return new ControladorLogin(repositorioAlumnos, repositorioUsuarios);
        }
        if (tipo == ControladorArchivos.class) {
            return new ControladorArchivos(repositorioAlumnos);
        }
        try {
            //Hace el comportamiento default, generar la clase sin parametros
            return tipo.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //ID-08 Introduce Foreign Method (cerrarVentanaDe(Node control))
    //  Dos clases usaban la misma cadena de llamadas para cerrar la ventana, así que
    // se propone una operación que javaFx no ofrece directamente mediante el metodo
    public void cerrarVentanaDe(Node control){
        control.getScene().getWindow().hide();

    }
}
