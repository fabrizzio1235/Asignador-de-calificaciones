package uady.ucan.proyecto;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

import java.security.NoSuchAlgorithmException;

import static uady.ucan.proyecto.Alerta.setAlert;

public class Autenticador {
    private Encriptador encriptador = new Encriptador();

    public void revisarCredenciales(Usuario usuario, PasswordField contraseña) throws NoSuchAlgorithmException {
        if (usuario == null) {
            setAlert(Alert.AlertType.WARNING, "Usuario incorrecto.");
            return;
        }
        boolean contraseñaCorrecta = usuario.getPassword().equals(
                encriptador.encriptarContraseña(contraseña.getText()));
        if (!contraseñaCorrecta) {
            setAlert(Alert.AlertType.WARNING, "Contraseña incorrecta.");
        }
    }
}
