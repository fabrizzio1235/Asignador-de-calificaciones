package uady.ucan.proyecto;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import uady.ucan.proyecto.modelo.Alumno;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioCompartidoTest extends PruebaBase {
    private static final String DIR = "/uady/ucan/proyecto/Views/";

    /*
     * Técnica: Move Field a RepositorioAlumnos (ID-02) / Change Value to Reference (ID-12) / Decompose Conditional hayAlumnosCargados() (ID-14) / Collapse Hierarchy (ID-17)
     * Objetivo: Verificar que pulsar "Generar" en el menú sin haber cargado un archivo CSV muestra la alerta de advertencia y no abre la ventana de generación.
     * Resultado esperado: Alerta con texto "Asegurarse de escoger el archivo CSV" y la ventana "Generar CSV/PDF" no se muestra.
     * Datos: Lista de alumnos sin cargar (null), vista Menu.fxml, clic en #botonIrGenerar.
     */
    @Test
    void sinCsvCargadoGenerarMuestraAlertaYNoAbreVentana() {
        abrir(DIR + "Menu.fxml", "Menu Principal");
        Button botonGenerar = buscar("#botonIrGenerar");

        interact(botonGenerar::fire);

        assertEquals("Asegurarse de escoger el archivo CSV", textoAlertaAbierta());
        assertFalse(hayVentana("Generar CSV/PDF"));
    }

    /*
     * Técnica: Move Field a RepositorioAlumnos (ID-02) / Change Value to Reference (ID-12) / Add Parameter (ID-15)
     * Objetivo: Verificar que al cargar el archivo CSV desde la vista CSVUsuario.fxml, la misma lista de alumnos es visible en la tabla de Calificaciones.fxml y permite abrir Generar.fxml desde el menú.
     * Resultado esperado: Etiqueta #mostrarCsvUsuario con "listaDS", #tablaAlumnos con 6 alumnos cargados y ventana "Generar CSV/PDF" abierta al pulsar #botonIrGenerar.
     * Datos: Nombre de archivo "listaDS" (ubicado en src/main/resources/listaDS.csv con 6 alumnos).
     */
    @Test
    void alumnosCargadosEnCsvSeVenEnCalificacionesYPermitenAbrirGenerar() {
        abrir(DIR + "CSVUsuario.fxml", "Busqueda de CSV");
        TextField campoCsv = buscar("#csvUsuario");
        Button confirmarCarga = buscar("#botonConfirmarCarga");

        interact(() -> {
            campoCsv.setText("listaDS");
            confirmarCarga.fire();
        });

        Label etiquetaMostrar = buscar("#mostrarCsvUsuario");
        assertEquals("listaDS", etiquetaMostrar.getText());

        abrir(DIR + "Calificaciones.fxml", "Gestion de Calificaciones");
        TableView<Alumno> tabla = buscar("#tablaAlumnos");
        assertEquals(6, tabla.getItems().size());

        abrir(DIR + "Menu.fxml", "Menu Principal");
        Button botonGenerar = buscar("#botonIrGenerar");
        interact(botonGenerar::fire);

        assertNull(textoAlertaAbierta());
        assertTrue(hayVentana("Generar CSV/PDF"));
    }
}