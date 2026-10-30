package uady.ucan.proyecto;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uady.ucan.proyecto.modelo.Alumno;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenerarArchivosTest extends PruebaBase {
    private static final String DIR = "/uady/ucan/proyecto/Views/";

    @BeforeEach
    void cargarAlumnosCalificadosDesdeVistas() {
        abrir(DIR + "CSVUsuario.fxml", "Busqueda de CSV");
        TextField campoCsv = buscar("#csvUsuario");
        Button confirmarCarga = buscar("#botonConfirmarCarga");
        interact(() -> {
            campoCsv.setText("listaDS");
            confirmarCarga.fire();
        });

        abrir(DIR + "Calificaciones.fxml", "Gestion de Calificaciones");
        TableView<Alumno> tabla = buscar("#tablaAlumnos");
        interact(() -> {
            int calificacion = 100;
            for (Alumno a : tabla.getItems()) {
                a.setCalificacion(calificacion);
                calificacion -= 10;
            }
        });

        cerrarVentanas();
    }

    /*
     * Técnica: Extract Class ControladorGenerar (ID-09) / Replace Inheritance with Delegation (ID-18a) / Form Template Method (ID-20)
     * Objetivo: Verificar que al escribir nombres personalizados en #csvNombre y #pdfNombre en Generar.fxml y pulsar sus botones de confirmación, se crean los archivos correspondientes en output/.
     * Resultado esperado: Se crean output/MiReportePrueba.csv y output/MiReportePrueba.pdf.
     * Datos: csvNombre = "MiReportePrueba", pdfNombre = "MiReportePrueba", 6 alumnos de listaDS calificados.
     */
    @Test
    void generarCsvYPdfConNombrePersonalizadoCreaArchivos() {
        File archivoCsv = new File("output/MiReportePrueba.csv");
        File archivoPdf = new File("output/MiReportePrueba.pdf");
        archivoCsv.delete();
        archivoPdf.delete();

        abrir(DIR + "Generar.fxml", "Generar CSV/PDF");
        TextField csvNombre = buscar("#csvNombre");
        TextField pdfNombre = buscar("#pdfNombre");
        Button confirmarCsv = buscar("#botonConfirmarCsv");
        Button confirmarPdf = buscar("#botonConfirmarPdf");

        interact(() -> {
            csvNombre.setText("MiReportePrueba");
            pdfNombre.setText("MiReportePrueba");
            confirmarCsv.fire();
            confirmarPdf.fire();
        });

        assertTrue(archivoCsv.exists(), "Debe haberse creado output/MiReportePrueba.csv");
        assertTrue(archivoPdf.exists(), "Debe haberse creado output/MiReportePrueba.pdf");

        archivoCsv.delete();
        archivoPdf.delete();
    }

    /*
     * Técnica: Extract Class ControladorGenerar (ID-09) / Replace Inheritance with Delegation (ID-18a) / Form Template Method (ID-20)
     * Objetivo: Verificar que al dejar vacíos los campos #csvNombre y #pdfNombre en Generar.fxml se utilizan los nombres por defecto "Calificaciones" y "Reporte_Calificaciones".
     * Resultado esperado: Se crean output/Calificaciones.csv y output/Reporte_Calificaciones.pdf.
     * Datos: csvNombre = "", pdfNombre = "", 6 alumnos de listaDS calificados.
     */
    @Test
    void generarCsvYPdfConNombreVacioUsaNombresPorDefecto() {
        File csvDefecto = new File("output/Calificaciones.csv");
        File pdfDefecto = new File("output/Reporte_Calificaciones.pdf");
        csvDefecto.delete();
        pdfDefecto.delete();

        abrir(DIR + "Generar.fxml", "Generar CSV/PDF");
        Button confirmarCsv = buscar("#botonConfirmarCsv");
        Button confirmarPdf = buscar("#botonConfirmarPdf");

        interact(() -> {
            confirmarCsv.fire();
            confirmarPdf.fire();
        });

        assertTrue(csvDefecto.exists(), "Con campo vacío debe crearse output/Calificaciones.csv");
        assertTrue(pdfDefecto.exists(), "Con campo vacío debe crearse output/Reporte_Calificaciones.pdf");

        csvDefecto.delete();
        pdfDefecto.delete();
    }

    /*
     * Técnica: Extract Method en generarCsv() (ID-01b) / Replace Nested Conditional with Guard Clauses (ID-13) / Replace Inheritance with Delegation (ID-18a)
     * Objetivo: Verificar que el archivo CSV generado incluye el encabezado esperado y ordena a los alumnos de menor a mayor calificación.
     * Resultado esperado: Primera línea es "Matricula,Primer Apellido,Segundo Apellido,Nombres,Calificacion", el primer alumno listado tiene calificación 50 y el último 100.
     * Datos: 6 alumnos con calificaciones asignadas en orden descendente (100, 90, 80, 70, 60, 50).
     */
    @Test
    void csvGeneradoContieneEncabezadoYOrdenaDeMenorAMayorCalificacion() throws IOException {
        File archivoCsv = new File("output/Ordenados.csv");
        archivoCsv.delete();

        abrir(DIR + "Generar.fxml", "Generar CSV/PDF");
        TextField csvNombre = buscar("#csvNombre");
        Button confirmarCsv = buscar("#botonConfirmarCsv");

        interact(() -> {
            csvNombre.setText("Ordenados");
            confirmarCsv.fire();
        });

        assertTrue(archivoCsv.exists());
        List<String> lineas = Files.readAllLines(archivoCsv.toPath(), java.nio.charset.Charset.defaultCharset());
        assertEquals(7, lineas.size(), "Debe tener 1 encabezado y 6 líneas de alumnos");
        assertEquals("Matricula,Primer Apellido,Segundo Apellido,Nombres,Calificacion", lineas.get(0));
        assertTrue(lineas.get(1).endsWith(",50"), "El primer alumno del archivo debe ser el de menor calificación (50)");
        assertTrue(lineas.get(6).endsWith(",100"), "El último alumno del archivo debe ser el de mayor calificación (100)");

        archivoCsv.delete();
    }

    /*
     * Técnica: Move Method todosCalificados() (ID-03) / Replace Nested Conditional with Guard Clauses (ID-13) / Replace Magic Number en PDF (ID-11) / Form Template Method (ID-20)
     * Objetivo: Verificar que cuando quedan alumnos sin calificar (-1), la generación de CSV se rechaza con la alerta "Quedan alumnos por calificar", mientras que el PDF sí se genera con sus encabezados y "S/C".
     * Resultado esperado: No se crea el archivo CSV, se muestra "Quedan alumnos por calificar", sí se crea el PDF y su texto contiene "Reporte de Calificaciones", "Matrícula", "Calificación" y "S/C".
     * Datos: Lista "listaDS" recién cargada (con calificaciones en -1 sin modificar).
     */
    @Test
    void conAlumnosSinCalificarRechazaCsvPeroSiGeneraPdfConSC() throws IOException {
        File csvNoCreado = new File("output/SinCalificar.csv");
        File pdfCreado = new File("output/SinCalificar.pdf");
        csvNoCreado.delete();
        pdfCreado.delete();

        abrir(DIR + "CSVUsuario.fxml", "Busqueda de CSV");
        TextField campoCsv = buscar("#csvUsuario");
        Button confirmarCarga = buscar("#botonConfirmarCarga");
        interact(() -> {
            campoCsv.setText("listaDS");
            confirmarCarga.fire();
        });
        cerrarVentanas();

        abrir(DIR + "Generar.fxml", "Generar CSV/PDF");
        TextField csvNombre = buscar("#csvNombre");
        TextField pdfNombre = buscar("#pdfNombre");
        Button confirmarCsv = buscar("#botonConfirmarCsv");
        Button confirmarPdf = buscar("#botonConfirmarPdf");

        interact(() -> {
            csvNombre.setText("SinCalificar");
            confirmarCsv.fire();
        });

        assertEquals("Quedan alumnos por calificar", textoAlertaAbierta());
        assertFalse(csvNoCreado.exists(), "No debe crearse el CSV si faltan alumnos por calificar");

        interact(() -> {
            pdfNombre.setText("SinCalificar");
            confirmarPdf.fire();
        });

        assertTrue(pdfCreado.exists(), "El PDF sí debe generarse aunque existan alumnos con -1 (S/C)");
        try (PdfReader reader = new PdfReader(pdfCreado.getAbsolutePath())) {
            String textoPdf = new PdfTextExtractor(reader).getTextFromPage(1);
            assertTrue(textoPdf.contains("Reporte de Calificaciones"));
            assertTrue(textoPdf.contains("Diseño de Software"));
            assertTrue(textoPdf.contains("Matrícula"));
            assertTrue(textoPdf.contains("S/C"));
        }
        pdfCreado.delete();
    }

    /*
     * Técnica: Extract Class Alerta (ID-06) / Rename Method mostrarAlerta (ID-16a)
     * Objetivo: Verificar que el cuadro de diálogo de alerta conserva su título "Información", sin encabezado, con un único botón "Aceptar" y el mensaje recibido.
     * Resultado esperado: Stage de alerta con título "Información", headerText == null, botón "Aceptar" y mensaje con la ruta absoluta del archivo creado.
     * Datos: Generación exitosa de output/PruebaAlerta.csv.
     */
    @Test
    void formatoDeAlertaCumpleContratoDeTituloSinEncabezadoYBotonAceptar() {
        File archivoCsv = new File("output/PruebaAlerta.csv");
        archivoCsv.delete();

        abrir(DIR + "Generar.fxml", "Generar CSV/PDF");
        TextField csvNombre = buscar("#csvNombre");
        Button confirmarCsv = buscar("#botonConfirmarCsv");

        interact(() -> {
            csvNombre.setText("PruebaAlerta");
            confirmarCsv.fire();
        });

        Stage stageAlerta = stageAlertaAbierta();
        DialogPane paneAlerta = dialogoAlertaAbierta();

        assertNotNull(stageAlerta);
        assertNotNull(paneAlerta);
        assertEquals("Información", stageAlerta.getTitle());
        assertNull(paneAlerta.getHeaderText());
        assertEquals(1, paneAlerta.getButtonTypes().size());
        assertEquals("Aceptar", paneAlerta.getButtonTypes().get(0).getText());
        assertEquals("Archivo CSV creado con exito en: " + archivoCsv.getAbsolutePath(), paneAlerta.getContentText());

        archivoCsv.delete();
    }

    /*
     * Técnica: Extract Method en generarCsv() (ID-01b) / Form Template Method (ID-20)
     * Objetivo: Verificar que cuando ocurre una IOException al escribir el archivo CSV se muestra una alerta de error con el prefijo "Error: ".
     * Resultado esperado: Alerta abierta cuyo texto inicia con "Error: ".
     * Datos: csvNombre con subdirectorio inexistente "subcarpeta_inexistente/fallo".
     */
    @Test
    void errorDeEscrituraEnCsvMuestraAlertaDeError() {
        abrir(DIR + "Generar.fxml", "Generar CSV/PDF");
        TextField csvNombre = buscar("#csvNombre");
        Button confirmarCsv = buscar("#botonConfirmarCsv");

        interact(() -> {
            csvNombre.setText("subcarpeta_inexistente/fallo");
            confirmarCsv.fire();
        });

        String alerta = textoAlertaAbierta();
        assertNotNull(alerta);
        assertTrue(alerta.startsWith("Error: "));
    }
}