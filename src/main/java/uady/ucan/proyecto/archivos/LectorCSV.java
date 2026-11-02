package uady.ucan.proyecto.archivos;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import uady.ucan.proyecto.modelo.Alumno;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static uady.ucan.proyecto.controlador.ControladorAlerta.mostrarAlerta;

public class LectorCSV {
    //Replace Magic Number with Symbolic Constant - Nuevo
    private static final int COLUMNAS_POR_ALUMNO = 4;
    private static final int COLUMNA_MATRICULA = 0;
    private static final int COLUMNA_APELLIDO1 = 1;
    private static final int COLUMNA_APELLIDO2 = 2;
    private static final int COLUMNA_NOMBRES = 3;

    @FXML
    private TextField csvUsuario;
    @FXML
    private Label mostrarCsvUsuario;
    @FXML
    private Label rutaUsuario;

    private ArrayList<Alumno> listaAlumnos;

    private RepositorioAlumnos repositorioAlumnos;


    // ID-15: Add Parameter
    public LectorCSV(RepositorioAlumnos repositorioAlumnos) {
        this.repositorioAlumnos = repositorioAlumnos;
    }

    @FXML
    public void buscarCsvUsuario() {
        String archivoCalificaciones = "src/main/resources/" + csvUsuario.getText() + ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCalificaciones))) {
            ArrayList<Alumno> nuevaListaAlumnos = new ArrayList<>();
            String linea;
            br.readLine();
            linea = br.readLine();

            while (linea != null) {
                String[] renglon = linea.split(",");
                if (renglon.length == COLUMNAS_POR_ALUMNO) {
                    //Inline Method - Nuevo
                    nuevaListaAlumnos.add(new Alumno(renglon[COLUMNA_MATRICULA], renglon[COLUMNA_APELLIDO1], renglon[COLUMNA_APELLIDO2], renglon[COLUMNA_NOMBRES]));
                }
                linea = br.readLine();
            }
            repositorioAlumnos.setAlumnos(nuevaListaAlumnos);

        } catch (IOException e) {
            mostrarCsvUsuario.setText("No seleccionado");
            mostrarCsvUsuario.setTextFill(Color.RED);
            mostrarAlerta(Alert.AlertType.WARNING, "Error al buscar archivo: " + e.getMessage());
            return;
        }

        mostrarCsvUsuario.setText(csvUsuario.getText());
        mostrarCsvUsuario.setTextFill(Color.GREEN);
        rutaUsuario.setText(new File(archivoCalificaciones).getAbsolutePath());
    }
}