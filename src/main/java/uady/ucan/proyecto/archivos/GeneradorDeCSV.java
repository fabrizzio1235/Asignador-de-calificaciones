package uady.ucan.proyecto.archivos;
import javafx.scene.control.Alert;
import uady.ucan.proyecto.modelo.Alumno;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import static uady.ucan.proyecto.controlador.Alerta.mostrarAlerta;

public class GeneradorDeCSV extends GeneradorDeArchivo {

    @Override protected String nombrePorDefecto() { return "Calificaciones"; }
    @Override protected String extension() { return ".csv"; }
    @Override protected String mensajeExito() { return "Archivo CSV creado con exito en: "; }

    // ID-15: Add Parameter
    public GeneradorDeCSV(RepositorioAlumnos repositorioAlumnos) {
        super(repositorioAlumnos);
    }

    public void generarCsv(String csvNombre) {
        generar(csvNombre);

    }

    //restriccion contemplada para generar el .csv
    @Override protected boolean puedeGenerar() { return repositorioAlumnos.todosCalificados(); }
    @Override protected String mensajeNoPuedeGenerar() { return "Quedan alumnos por calificar"; }

    @Override
    protected void escribir(String ruta) throws IOException {
        ArrayList<Alumno> alumnos = repositorioAlumnos.getAlumnos();
        alumnos.sort(Comparator.comparingInt(Alumno::getCalificacion));
        System.out.println("Escriba el nombre que desea para el archivo de salida:");

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(ruta))) {
            wr.write("Matricula,Primer Apellido,Segundo Apellido,Nombres,Calificacion");
            wr.newLine();
            //voy a cambiar finalmente el nombre de alumnillo...
            for (Alumno alumno : alumnos) {
                wr.write(alumno.toString());
                wr.newLine();
            }
        }
    }
    @Override
    protected void manejarError(Exception e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error: " + e.getMessage());
    }

}