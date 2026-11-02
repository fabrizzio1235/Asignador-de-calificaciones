package uady.ucan.proyecto.controlador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;
import uady.ucan.proyecto.repositorios.RepositorioUsuarios;
import uady.ucan.proyecto.vista.GestorVentanas;

import static uady.ucan.proyecto.controlador.ControladorAlerta.mostrarAlerta;
import static uady.ucan.proyecto.vista.GestorVentanas.CALIFICACIONES_VIEW_FXML;
import static uady.ucan.proyecto.vista.GestorVentanas.CSV_USUARIO_VIEW_FXML;
import static uady.ucan.proyecto.vista.GestorVentanas.GENERAR_VIEW_FXML;
import static uady.ucan.proyecto.vista.GestorVentanas.LOGIN_VIEW_FXML;
import static uady.ucan.proyecto.vista.GestorVentanas.MENU_VIEW_FXML;

//ID-04: Rename Class (antes ControladorVentanas)
//Ahora solo atiende los botones del menú principal; abrir y cerrar ventanas lo hace GestorVentanas
public class ControladorMenu {
    private RepositorioAlumnos repositorioAlumnos;
    private GestorVentanas gestorVentanas;

    @FXML
    private Button botonSalir;

    // ID-15: Add Parameter (recibe los repositorios por constructor)
    public ControladorMenu(RepositorioAlumnos repositorioAlumnos, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioAlumnos = repositorioAlumnos;
        this.gestorVentanas = new GestorVentanas(repositorioAlumnos, repositorioUsuarios);
    }
    //ID-15 Por como funciona JAVA, hay que declarar un cosntructor vacio para que los constructores hijos llamen a este
    //TODO Al aplicarse el ID-18(quitar los extends) se puede eliminar este constructor vacio

    public ControladorMenu() {}

    @FXML
    public void asignarCalificacion () {
        gestorVentanas.abrirVentana(CALIFICACIONES_VIEW_FXML, "Gestion de Calificaciones");
    }

    @FXML
    public void recibirCSV () {
        gestorVentanas.abrirVentana(CSV_USUARIO_VIEW_FXML, "Busqueda de CSV");
    }

    @FXML
    public void generar () {
        if(!repositorioAlumnos.hayAlumnosCargados()){
            mostrarAlerta(Alert.AlertType.WARNING, "Asegurarse de escoger el archivo CSV");
            return;
        }
        gestorVentanas.abrirVentana(GENERAR_VIEW_FXML, "Generar CSV/PDF");

    }
    @FXML
    public void menu (){
        gestorVentanas.abrirVentana(MENU_VIEW_FXML, "Menu Principal");
    }

    @FXML
    public void login() {
        gestorVentanas.abrirVentana(LOGIN_VIEW_FXML, "Inicio de Sesión");
    }

    public void cerrarSesion () {
        gestorVentanas.cerrarVentanaDe(botonSalir);
        login();
    }

    @FXML
    public void salir () {
        Platform.exit();
    }

}
