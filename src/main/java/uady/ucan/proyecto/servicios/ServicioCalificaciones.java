package uady.ucan.proyecto.servicios;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import uady.ucan.proyecto.modelo.Alumno;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;

import java.net.URL;
import java.util.ResourceBundle;

import static uady.ucan.proyecto.controlador.ControladorAlerta.mostrarAlerta;

public class ServicioCalificaciones implements Initializable {
    @FXML private TableView<Alumno> tablaAlumnos;
    @FXML private TableColumn<Alumno, String> matricula;
    @FXML private TableColumn<Alumno, String> alumno;
    @FXML private TableColumn<Alumno, String> calificacion;


    private final RepositorioAlumnos repositorioAlumnos;
    private ObservableList<Alumno> alumnos = null;

    // ID-15: Add Parameter
    public ServicioCalificaciones(RepositorioAlumnos repositorioAlumnos) {
        this.repositorioAlumnos = repositorioAlumnos;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarAlumnosEnTabla();
        configurarColumnas();
    }

    //Extract Method - Nuevo
    //Llena la tabla con los alumnos del CSV; si no hay lista cargada la tabla queda sin items
    private void cargarAlumnosEnTabla() {
        if (repositorioAlumnos.hayAlumnosCargados()) {
            alumnos = FXCollections.observableArrayList(repositorioAlumnos.getAlumnos());
            tablaAlumnos.setItems(alumnos);
        }
    }

    //Extract Method - Nuevo
    //Define qué muestra cada columna y deja editable la de calificación
    private void configurarColumnas() {
        matricula.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMatricula()));
        alumno.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombreCompleto()));
        calificacion.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCalificacion())));

        tablaAlumnos.setEditable(true);
        calificacion.setCellFactory(TextFieldTableCell.forTableColumn());

        calificacion.setOnEditCommit(this::manejarEdicionCalificacion);
    }

    //Extract Method - Nuevo
    //Qué ocurre al confirmar la edición de una calificación en la tabla
    private void manejarEdicionCalificacion(TableColumn.CellEditEvent<Alumno, String> event) {
        Alumno alumnoEditado = event.getRowValue();

        try {
            int nuevaCal = Integer.parseInt(event.getNewValue()); //no recibe decimales
            if (nuevaCal >= Alumno.CALIFICACION_MINIMA && nuevaCal <= Alumno.CALIFICACION_MAXIMA) {
                alumnoEditado.setCalificacion(nuevaCal);
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Calificación invalida --> 0<= calificacion <= 100");
                tablaAlumnos.refresh();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Calificación invalida solo acepta enteros --> 0<= calificacion <= 100");
            tablaAlumnos.refresh();
        }
    }

}