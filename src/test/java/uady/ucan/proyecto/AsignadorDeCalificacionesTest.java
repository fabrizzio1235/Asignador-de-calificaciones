package uady.ucan.proyecto;

import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import uady.ucan.proyecto.modelo.Alumno;

import static org.junit.jupiter.api.Assertions.*;

class AsignadorDeCalificacionesTest extends PruebaBase {
    private static final String DIR = "/uady/ucan/proyecto/Views/";

    /*
     * Técnica: Extract Method en initialize() (ID-01a) / Decompose Conditional hayAlumnosCargados() (ID-14)
     * Objetivo: Verificar que al abrir la pantalla de calificaciones sin haber cargado un archivo CSV, la tabla se muestra vacía.
     * Resultado esperado: #tablaAlumnos tiene 0 elementos.
     * Datos: Lista de alumnos sin cargar (null), vista Calificaciones.fxml.
     */
    @Test
    void sinCsvCargadoLaTablaAbreVacia() {
        abrir(DIR + "Calificaciones.fxml", "Gestion de Calificaciones");
        TableView<Alumno> tabla = buscar("#tablaAlumnos");

        assertTrue(tabla.getItems().isEmpty());
    }

    /*
     * Técnica: Extract Method en initialize() (ID-01a) / Self Encapsulate Field getNombreCompleto() (ID-10) / Replace Inheritance with Delegation (ID-18b)
     * Objetivo: Verificar que con alumnos cargados la tabla es editable y sus tres columnas muestran matrícula, nombre completo ("apellido1 apellido2 nombres") y calificación inicial ("-1").
     * Resultado esperado: 6 alumnos en la tabla, tabla editable y celdas de la fila 0 coinciden con matrícula, getNombreCompleto() y "-1".
     * Datos: Archivo "listaDS" cargado previamente (6 alumnos).
     */
    @Test
    void tablaMuestraColumnasMatriculaNombreCompletoYCalificacionInicial() {
        cargarListaDS();
        abrir(DIR + "Calificaciones.fxml", "Gestion de Calificaciones");
        TableView<Alumno> tabla = buscar("#tablaAlumnos");

        assertEquals(6, tabla.getItems().size());
        assertTrue(tabla.isEditable());

        Alumno primero = tabla.getItems().get(0);
        String nombreEsperado = primero.getAppellido1() + " " + primero.getAppellido2() + " " + primero.getNombres();

        assertEquals(nombreEsperado, primero.getNombreCompleto());
        assertEquals(primero.getMatricula(), tabla.getColumns().get(0).getCellObservableValue(0).getValue());
        assertEquals(nombreEsperado, tabla.getColumns().get(1).getCellObservableValue(0).getValue());
        assertEquals("-1", tabla.getColumns().get(2).getCellObservableValue(0).getValue());
    }

    /*
     * Técnica: Move Method validación a Alumno (ID-04) / Replace Magic Number límites 0 y 100 (ID-11) / Extract Method (ID-01a)
     * Objetivo: Verificar que editar la columna de calificación con valores enteros dentro del rango permitido (incluyendo límites 0 y 100) actualiza al alumno sin mostrar alerta.
     * Resultado esperado: La calificación del alumno cambia a 0 y luego a 100, sin abrir alertas.
     * Datos: Ediciones en fila 0 con valores "0" y "100".
     */
    @Test
    void edicionDeCalificacionEnLimitesValidosActualizaAlAlumno() {
        cargarListaDS();
        abrir(DIR + "Calificaciones.fxml", "Gestion de Calificaciones");
        TableView<Alumno> tabla = buscar("#tablaAlumnos");
        Alumno alumno = tabla.getItems().get(0);

        editarCeldaCalificacion(tabla, 0, "0");
        assertEquals(0, alumno.getCalificacion());
        assertNull(textoAlertaAbierta());

        editarCeldaCalificacion(tabla, 0, "100");
        assertEquals(100, alumno.getCalificacion());
        assertNull(textoAlertaAbierta());
    }

    /*
     * Técnica: Move Method validación a Alumno (ID-04) / Replace Magic Number límites 0 y 100 (ID-11) / Extract Method (ID-01a)
     * Objetivo: Verificar que ingresar una calificación entera fuera del rango 0..100 (tanto negativa -5 como mayor a 100) muestra la alerta de advertencia y conserva la calificación anterior.
     * Resultado esperado: Se abre la alerta "Calificación invalida --> 0<= calificacion <= 100" en ambos casos y la calificación del alumno permanece en -1.
     * Datos: Ediciones en fila 0 con valores "-5" y "101".
     */
    @Test
    void edicionDeCalificacionFueraDeRangoMuestraAlertaYNoModificaAlumno() {
        cargarListaDS();
        abrir(DIR + "Calificaciones.fxml", "Gestion de Calificaciones");
        TableView<Alumno> tabla = buscar("#tablaAlumnos");
        Alumno alumno = tabla.getItems().get(0);

        editarCeldaCalificacion(tabla, 0, "-5");
        assertEquals("Calificación invalida --> 0<= calificacion <= 100", textoAlertaAbierta());
        assertEquals(-1, alumno.getCalificacion());

        editarCeldaCalificacion(tabla, 0, "101");
        assertEquals("Calificación invalida --> 0<= calificacion <= 100", textoAlertaAbierta());
        assertEquals(-1, alumno.getCalificacion());
    }

    /*
     * Técnica: Move Method validación a Alumno (ID-04) / Extract Method (ID-01a)
     * Objetivo: Verificar que ingresar un valor no entero (decimales o caracteres) en la columna de calificación muestra la alerta correspondiente y no modifica al alumno.
     * Resultado esperado: Se abre la alerta "Calificación invalida solo acepta enteros --> 0<= calificacion <= 100" y la calificación permanece en -1.
     * Datos: Edición en fila 0 con valor "95.5".
     */
    @Test
    void edicionDeCalificacionNoEnteraMuestraAlertaYNoModificaAlumno() {
        cargarListaDS();
        abrir(DIR + "Calificaciones.fxml", "Gestion de Calificaciones");
        TableView<Alumno> tabla = buscar("#tablaAlumnos");
        Alumno alumno = tabla.getItems().get(0);

        editarCeldaCalificacion(tabla, 0, "95.5");

        assertEquals("Calificación invalida solo acepta enteros --> 0<= calificacion <= 100", textoAlertaAbierta());
        assertEquals(-1, alumno.getCalificacion());
    }

    private void cargarListaDS() {
        abrir(DIR + "CSVUsuario.fxml", "Busqueda de CSV");
        TextField campoCsv = buscar("#csvUsuario");
        Button confirmarCarga = buscar("#botonConfirmarCarga");
        interact(() -> {
            campoCsv.setText("listaDS");
            confirmarCarga.fire();
        });
        cerrarVentanas();
    }

    @SuppressWarnings("unchecked")
    private void editarCeldaCalificacion(TableView<Alumno> tabla, int fila, String nuevoValor) {
        TableColumn<Alumno, String> colCalificacion = (TableColumn<Alumno, String>) tabla.getColumns().get(2);
        interact(() -> {
            TablePosition<Alumno, String> pos = new TablePosition<>(tabla, fila, colCalificacion);
            TableColumn.CellEditEvent<Alumno, String> evento = new TableColumn.CellEditEvent<>(
                    tabla, pos, TableColumn.editCommitEvent(), nuevoValor
            );
            Event.fireEvent(colCalificacion, evento);
        });
    }
}