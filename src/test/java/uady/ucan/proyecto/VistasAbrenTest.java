package uady.ucan.proyecto;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VistasAbrenTest extends PruebaBase {
    private static final String DIR = "/uady/ucan/proyecto/Views/";

    /*
     * Técnica: Collapse Hierarchy (ID-17) / Add Parameter (ID-15)
     * Objetivo: Verificar que la vista Login.fxml se carga y muestra su ventana con sus controles de entrada.
     * Resultado esperado: Ventana visible con título "Inicio de Sesión" y controles #usuarioEntrada, #contraseñaEntrada y #botonLogin.
     * Datos: Ruta "/uady/ucan/proyecto/Views/Login.fxml", título "Inicio de Sesión".
     */
    @Test
    void loginSeAbreConSusCampos() {
        verificar("Login.fxml", "Inicio de Sesión", "#usuarioEntrada", "#contraseñaEntrada", "#botonLogin");
    }

    /*
     * Técnica: Collapse Hierarchy (ID-17) / Add Parameter (ID-15)
     * Objetivo: Verificar que la vista Menu.fxml se carga y muestra sus botones principales.
     * Resultado esperado: Ventana visible con título "Menu Principal" y botones #botonIrGenerar, #botonCerrarSesion, #botonSalir, #botonIrCsv y #botonIrCalificaciones.
     * Datos: Ruta "/uady/ucan/proyecto/Views/Menu.fxml", título "Menu Principal".
     */
    @Test
    void menuSeAbreConSusBotones() {
        verificar("Menu.fxml", "Menu Principal", "#botonIrGenerar", "#botonCerrarSesion", "#botonSalir", "#botonIrCsv", "#botonIrCalificaciones");
    }

    /*
     * Técnica: Collapse Hierarchy (ID-17) / Add Parameter (ID-15) / Extract Class (ID-09)
     * Objetivo: Verificar que la vista Generar.fxml se carga con sus campos de nombre y botones de confirmación.
     * Resultado esperado: Ventana visible con título "Generar CSV/PDF" y controles #csvNombre, #pdfNombre, #botonConfirmarCsv y #botonConfirmarPdf.
     * Datos: Ruta "/uady/ucan/proyecto/Views/Generar.fxml", título "Generar CSV/PDF".
     */
    @Test
    void generarSeAbreConSusCamposYBotones() {
        verificar("Generar.fxml", "Generar CSV/PDF", "#csvNombre", "#pdfNombre", "#botonConfirmarCsv", "#botonConfirmarPdf");
    }

    /*
     * Técnica: Collapse Hierarchy (ID-17) / Add Parameter (ID-15)
     * Objetivo: Verificar que la vista Calificaciones.fxml se carga y contiene la tabla de alumnos.
     * Resultado esperado: Ventana visible con título "Gestion de Calificaciones" y control #tablaAlumnos.
     * Datos: Ruta "/uady/ucan/proyecto/Views/Calificaciones.fxml", título "Gestion de Calificaciones".
     */
    @Test
    void calificacionesSeAbreConSuTabla() {
        verificar("Calificaciones.fxml", "Gestion de Calificaciones", "#tablaAlumnos");
    }

    /*
     * Técnica: Collapse Hierarchy (ID-17) / Add Parameter (ID-15)
     * Objetivo: Verificar que la vista CSVUsuario.fxml se carga con su campo de texto, botón y etiquetas.
     * Resultado esperado: Ventana visible con título "Busqueda de CSV" y controles #csvUsuario, #botonConfirmarCarga, #mostrarCsvUsuario y #rutaUsuario.
     * Datos: Ruta "/uady/ucan/proyecto/Views/CSVUsuario.fxml", título "Busqueda de CSV".
     */
    @Test
    void csvUsuarioSeAbreConSusCamposYEtiquetas() {
        verificar("CSVUsuario.fxml", "Busqueda de CSV", "#csvUsuario", "#botonConfirmarCarga", "#mostrarCsvUsuario", "#rutaUsuario");
    }

    /*
     * Técnica: Add Parameter y setControllerFactory (ID-15)
     * Objetivo: Verificar que abrir dos ventanas de CSVUsuario.fxml crea instancias independientes de controlador que no comparten sus campos de texto.
     * Resultado esperado: Escribir en el campo #csvUsuario de la primera ventana no altera el campo #csvUsuario de la segunda ventana.
     * Datos: Dos aperturas de "/uady/ucan/proyecto/Views/CSVUsuario.fxml", texto "listaDS" en la primera.
     */
    @Test
    void dosVentanasDeCsvNoCompartenCampos() {
        abrir(DIR + "CSVUsuario.fxml", "CSV 1");
        TextField campoVentana1 = buscar("#csvUsuario");
        interact(() -> campoVentana1.setText("listaDS"));

        abrir(DIR + "CSVUsuario.fxml", "CSV 2");
        var campos = lookup("#csvUsuario").queryAllAs(TextField.class).stream().toList();

        assertEquals(2, campos.size());
        assertNotEquals(campos.get(0).getText(), campos.get(1).getText());
    }

    /*
     * Técnica: Rename Method en handlers de navegación (ID-16b) / Collapse Hierarchy (ID-17)
     * Objetivo: Verificar que los botones de navegación del menú principal abren las ventanas de búsqueda de CSV y gestión de calificaciones con sus títulos esperados.
     * Resultado esperado: Al pulsar #botonIrCsv se abre "Busqueda de CSV" y al pulsar #botonIrCalificaciones se abre "Gestion de Calificaciones".
     * Datos: Vista Menu.fxml, clics en #botonIrCsv y #botonIrCalificaciones.
     */
    @Test
    void botonesDeNavegacionDelMenuAbrenSusVentanas() {
        abrir(DIR + "Menu.fxml", "Menu Principal");
        Button botonIrCsv = buscar("#botonIrCsv");
        Button botonIrCalificaciones = buscar("#botonIrCalificaciones");

        interact(() -> {
            botonIrCsv.fire();
            botonIrCalificaciones.fire();
        });

        assertTrue(hayVentana("Busqueda de CSV"));
        assertTrue(hayVentana("Gestion de Calificaciones"));
    }

    /*
     * Técnica: Collapse Hierarchy (ID-17)
     * Objetivo: Verificar que al intentar abrir una vista inexistente se muestra una alerta de advertencia.
     * Resultado esperado: Se muestra una alerta cuyo mensaje comienza con "Error al cargar la vista ".
     * Datos: Ruta "/uady/ucan/proyecto/Views/NoExiste.fxml".
     */
    @Test
    void abrirVistaInexistenteMuestraAlertaDeError() {
        abrir(DIR + "NoExiste.fxml", "Fallo");
        String alerta = textoAlertaAbierta();
        assertNotNull(alerta);
        assertTrue(alerta.startsWith("Error al cargar la vista "));
    }

    private void verificar(String fxml, String titulo, String... controles) {
        abrir(DIR + fxml, titulo);
        assertTrue(hayVentana(titulo), "La ventana '" + titulo + "' debe estar visible");
        for (String s : controles) {
            assertTrue(hayControl(s), "La vista " + fxml + " debe tener " + s);
        }
    }
}