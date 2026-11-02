package uady.ucan.proyecto.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import uady.ucan.proyecto.archivos.GeneradorCSV;
import uady.ucan.proyecto.archivos.GeneradorPDF;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;

public class ControladorArchivos {

    @FXML private TextField csvNombre;
    @FXML private TextField pdfNombre;

    private RepositorioAlumnos repositorioAlumnos;

    // ID-15 aplicado de una vez para esta nueva clase
    public ControladorArchivos(RepositorioAlumnos repositorioAlumnos) {
        this.repositorioAlumnos = repositorioAlumnos;
    }

    @FXML
    public void generarCSV() {
        GeneradorCSV csv = new GeneradorCSV(repositorioAlumnos);
        csv.generarCsv(csvNombre.getText());
    }

    @FXML
    public void generarPDF() {
        GeneradorPDF pdf = new GeneradorPDF(repositorioAlumnos);
        pdf.generarPdf(pdfNombre.getText());
    }
}