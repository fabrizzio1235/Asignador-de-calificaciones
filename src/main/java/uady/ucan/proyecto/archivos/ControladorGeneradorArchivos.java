package uady.ucan.proyecto.archivos;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;

public class ControladorGeneradorArchivos {

    @FXML private TextField csvNombre;
    @FXML private TextField pdfNombre;

    private RepositorioAlumnos repositorioAlumnos;

    // ID-15 aplicado de una vez para esta nueva clase
    public ControladorGeneradorArchivos(RepositorioAlumnos repositorioAlumnos) {
        this.repositorioAlumnos = repositorioAlumnos;
    }

    @FXML
    public void generarCSV() {
        GeneradorDeCSV csv = new GeneradorDeCSV(repositorioAlumnos);
        csv.generarCsv(csvNombre.getText());
    }

    @FXML
    public void generarPDF() {
        GeneradorReportePDF pdf = new GeneradorReportePDF(repositorioAlumnos);
        pdf.generarPdf(pdfNombre.getText());
    }
}