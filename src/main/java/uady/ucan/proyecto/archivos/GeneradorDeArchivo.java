package uady.ucan.proyecto.archivos;

import javafx.scene.control.Alert;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;

import java.io.File;

import static uady.ucan.proyecto.controlador.Alerta.mostrarAlerta;

//ID-20 : Extract Superclass
//GeneradorDeCSV y GeneradorReportePDF seguian pasos para generar en una misma secuencia.
//Una superclase disminuye la repeticion de codigo y expone el comportamiento en comun

public abstract class GeneradorDeArchivo {

    protected final RepositorioAlumnos repositorioAlumnos;
    protected GeneradorDeArchivo(RepositorioAlumnos repositorioAlumnos) {
        this.repositorioAlumnos = repositorioAlumnos;
    }

    // ID-20: Form Template Method
    public final void generar(String nombre) {
        if (!puedeGenerar()) {
            mostrarAlerta(Alert.AlertType.WARNING, mensajeNoPuedeGenerar());
            return;
        }
        String nombreFinal = (nombre == null || nombre.trim().isEmpty()) ? nombrePorDefecto() : nombre;
        String ruta = "output/" + nombreFinal + extension();
        try {
            escribir(ruta);
            mostrarAlerta(Alert.AlertType.CONFIRMATION, mensajeExito() + new File(ruta).getAbsolutePath());
        } catch (Exception e) {
            manejarError(e);
        }
    }

    // comportamiento variable
    protected abstract String nombrePorDefecto();
    protected abstract String extension();   //para pdf o csv
    protected abstract String mensajeExito();
    protected abstract void escribir(String ruta) throws Exception;

    // comportamientos en comun
    protected boolean puedeGenerar() { return true; } //definido en true porque solo GeneradorCSV define una restriccion
    protected String mensajeNoPuedeGenerar() { return ""; } //mensaje que puede ser sobreescrito
    //atrapa los errores al generar en las subclases
    protected void manejarError(Exception e) { e.printStackTrace(); }
}


