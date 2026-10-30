package uady.ucan.proyecto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static uady.ucan.proyecto.Alerta.mostrarAlerta;

public class ControladorVentanas {
    public static final String VIEWS_DIRECTORY = "/uady/ucan/proyecto/Views/";
    public static final String LOGIN_VIEW_FXML = VIEWS_DIRECTORY + "Login.fxml";
    public static final String MENU_VIEW_FXML = VIEWS_DIRECTORY + "Menu.fxml";
    public static final String GENERAR_VIEW_FXML = VIEWS_DIRECTORY + "Generar.fxml";
    public static final String CALIFICACIONES_VIEW_FXML = VIEWS_DIRECTORY + "Calificaciones.fxml";
    public static final String CSV_USUARIO_VIEW_FXML = VIEWS_DIRECTORY + "CSVUsuario.fxml";

    private  RepositorioAlumnos repositorioAlumnos;
    private  RepositorioUsuarios repositorioUsuarios;

    @FXML
    private Button botonSalir;

    // ID-15: Add Parameter (recibe los repositorios por constructor)
    public ControladorVentanas(RepositorioAlumnos repositorioAlumnos, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioAlumnos = repositorioAlumnos;
        this.repositorioUsuarios = repositorioUsuarios;
    }
    //ID-15 Por como funciona JAVA, hay que declarar un cosntructor vacio para que los constructores hijos llamen a este
    //TODO Al aplicarse el ID-18(quitar los extends) se puede eliminar este constructor vacio

    public ControladorVentanas() {}

    public void abrirVentana(String fxmlFileName, String title) {
        try {
            Stage st = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(ControladorVentanas.class.getResource(fxmlFileName));
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
        if (tipo == ControladorVentanas.class) {
            return new ControladorVentanas(repositorioAlumnos, repositorioUsuarios);
        }
        if (tipo == RecibirCSVUsuario.class) {
            return new RecibirCSVUsuario(repositorioAlumnos);
        }
        if (tipo == AsignadorDeCalificaciones.class) {
            return new AsignadorDeCalificaciones(repositorioAlumnos);
        }
        if (tipo == Login.class) {
            return new Login(repositorioAlumnos, repositorioUsuarios);
        }
        if (tipo == ControladorGeneradorArchivos.class) {
            return new ControladorGeneradorArchivos(repositorioAlumnos);
        }
        try {
            //Hace el comportamiento default, generar la clase sin parametros
            return tipo.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            mostrarAlerta(Alert.AlertType.WARNING, "Asegurarse de escoger el archivo CSV");
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
        cerrarVentanaDe(botonSalir);
        login();
    }

    @FXML
    public void salir () {
        Platform.exit();
    }
    //ID-08 Introduce Foreign Method (cerrarVentanaDe(Node control) en ControladorVentanas)
    //  Dos clases usaban la misma cadena de llamadas para cerrar la ventana, así que
    // se propone una operación que javaFx no ofrece directamente mediante el metodo
    public void cerrarVentanaDe(Node control){
        control.getScene().getWindow().hide();

    }

}
