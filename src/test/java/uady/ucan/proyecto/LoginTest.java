package uady.ucan.proyecto;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest extends PruebaBase {
    private static final String DIR = "/uady/ucan/proyecto/Views/";

    /*
     * Técnica: Extract Class en Login (ID-05) / Replace Inheritance with Delegation (ID-19) / Self Encapsulate Field (ID-10)
     * Objetivo: Verificar que al ingresar un usuario que no existe en users.csv se muestra la alerta "Usuario incorrecto." y no se abre el menú principal.
     * Resultado esperado: Alerta con mensaje "Usuario incorrecto.", ventana "Menu Principal" no visible y ventana "Inicio de Sesión" permanece visible.
     * Datos: usuario = "usuarioInexistente", contraseña = "cualquierPassword".
     */
    @Test
    void loginConUsuarioIncorrectoMuestraAlertaYNoAbreMenu() {
        abrir(DIR + "Login.fxml", "Inicio de Sesión");
        TextField usuario = buscar("#usuarioEntrada");
        PasswordField password = buscar("#contraseñaEntrada");
        Button botonLogin = buscar("#botonLogin");

        interact(() -> {
            usuario.setText("usuarioInexistente");
            password.setText("cualquierPassword");
            botonLogin.fire();
        });

        assertEquals("Usuario incorrecto.", textoAlertaAbierta());
        assertFalse(hayVentana("Menu Principal"));
        assertTrue(hayVentana("Inicio de Sesión"));
    }

    /*
     * Técnica: Extract Class en Login (ID-05) / Replace Inheritance with Delegation (ID-19) / Replace Magic Number (ID-11)
     * Objetivo: Verificar que al ingresar un usuario válido pero con contraseña errónea se valida el hash SHA-256, se muestra "Contraseña incorrecta." y no se abre el menú.
     * Resultado esperado: Alerta con mensaje "Contraseña incorrecta.", ventana "Menu Principal" no visible y ventana "Inicio de Sesión" permanece visible.
     * Datos: usuario = "fransua" (existe en users.csv), contraseña = "passwordEquivocada".
     */
    @Test
    void loginConContrasenaIncorrectaMuestraAlertaYNoAbreMenu() {
        abrir(DIR + "Login.fxml", "Inicio de Sesión");
        TextField usuario = buscar("#usuarioEntrada");
        PasswordField password = buscar("#contraseñaEntrada");
        Button botonLogin = buscar("#botonLogin");

        interact(() -> {
            usuario.setText("fransua");
            password.setText("passwordEquivocada");
            botonLogin.fire();
        });

        assertEquals("Contraseña incorrecta.", textoAlertaAbierta());
        assertFalse(hayVentana("Menu Principal"));
        assertTrue(hayVentana("Inicio de Sesión"));
    }

    /*
     * Técnica: Extract Class (ID-05) / Introduce Foreign Method cerrarVentanaDe (ID-08) / Collapse Hierarchy (ID-17) / Replace Inheritance with Delegation (ID-19)
     * Objetivo: Verificar que con credenciales válidas se abre el menú principal y se oculta la ventana de login, y que al pulsar "Cerrar sesión" se oculta el menú y vuelve a abrirse el login.
     * Resultado esperado: Tras login válido: "Menu Principal" visible e "Inicio de Sesión" oculta. Tras "Cerrar sesión": "Menu Principal" oculta e "Inicio de Sesión" visible.
     * Datos: usuario = "fransua", contraseña = "oty".
     */
    @Test
    void loginCorrectoAbreMenuOcultaLoginYCerrarSesionRegresaALogin() {
        abrir(DIR + "Login.fxml", "Inicio de Sesión");
        TextField usuario = buscar("#usuarioEntrada");
        PasswordField password = buscar("#contraseñaEntrada");
        Button botonLogin = buscar("#botonLogin");

        interact(() -> {
            usuario.setText("fransua");
            password.setText("oty");
            botonLogin.fire();
        });

        assertNull(textoAlertaAbierta());
        assertTrue(hayVentana("Menu Principal"));
        assertFalse(hayVentana("Inicio de Sesión"));

        Button botonCerrarSesion = buscar("#botonCerrarSesion");
        interact(botonCerrarSesion::fire);

        assertFalse(hayVentana("Menu Principal"));
        assertTrue(hayVentana("Inicio de Sesión"));
    }
}