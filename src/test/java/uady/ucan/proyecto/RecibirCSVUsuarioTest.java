package uady.ucan.proyecto;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class RecibirCSVUsuarioTest extends PruebaBase {
    private static final String DIR = "/uady/ucan/proyecto/Views/";

    /*
     * Técnica: Extract Method/Class en lectura de CSV (ID-07) / Replace Magic Number (ID-11) / Replace Inheritance with Delegation (ID-18b)
     * Objetivo: Verificar que al ingresar un nombre de CSV válido ("listaDS") se cargan los alumnos, la etiqueta se pinta en verde con el nombre y se muestra la ruta absoluta.
     * Resultado esperado: #mostrarCsvUsuario tiene texto "listaDS" en Color.GREEN y #rutaUsuario contiene la ruta absoluta de src/main/resources/listaDS.csv.
     * Datos: Nombre de archivo = "listaDS".
     */
    @Test
    void cargarCsvValidoMuestraEtiquetaVerdeYRutaAbsoluta() {
        abrir(DIR + "CSVUsuario.fxml", "Busqueda de CSV");
        TextField campoCsv = buscar("#csvUsuario");
        Button confirmarCarga = buscar("#botonConfirmarCarga");

        interact(() -> {
            campoCsv.setText("listaDS");
            confirmarCarga.fire();
        });

        Label mostrarCsv = buscar("#mostrarCsvUsuario");
        Label ruta = buscar("#rutaUsuario");
        String rutaEsperada = new File("src/main/resources/listaDS.csv").getAbsolutePath();

        assertEquals("listaDS", mostrarCsv.getText());
        assertEquals(Color.GREEN, mostrarCsv.getTextFill());
        assertEquals(rutaEsperada, ruta.getText());
        assertNull(textoAlertaAbierta());
    }

    /*
     * Técnica: Extract Method/Class en lectura de CSV (ID-07) / Replace Inheritance with Delegation (ID-18b)
     * Objetivo: Verificar que al buscar un archivo CSV inexistente se muestra "No seleccionado" en rojo, se lanza la alerta de advertencia, la ruta previa no se limpia y la lista de alumnos queda en null.
     * Resultado esperado: #mostrarCsvUsuario dice "No seleccionado" en Color.RED, #rutaUsuario conserva la ruta previa, alerta inicia con "Error al buscar archivo: " y la tabla de calificaciones abre vacía (lista en null).
     * Datos: Primero se carga "listaDS" y luego se busca "archivoInexistente".
     */
    @Test
    void cargarCsvInexistenteMuestraErrorEnRojoNoLimpiaRutaYDejaListaEnNull() {
        abrir(DIR + "CSVUsuario.fxml", "Busqueda de CSV");
        TextField campoCsv = buscar("#csvUsuario");
        Button confirmarCarga = buscar("#botonConfirmarCarga");

        interact(() -> {
            campoCsv.setText("listaDS");
            confirmarCarga.fire();
        });
        Label ruta = buscar("#rutaUsuario");
        String rutaPrevia = ruta.getText();

        interact(() -> {
            campoCsv.setText("archivoInexistente");
            confirmarCarga.fire();
        });

        Label mostrarCsv = buscar("#mostrarCsvUsuario");
        String alerta = textoAlertaAbierta();

        assertEquals("No seleccionado", mostrarCsv.getText());
        assertEquals(Color.RED, mostrarCsv.getTextFill());
        assertEquals(rutaPrevia, ruta.getText(), "La ruta mostrada no debe limpiarse tras un error");
        assertNotNull(alerta);
        assertTrue(alerta.startsWith("Error al buscar archivo: "));
    }
}