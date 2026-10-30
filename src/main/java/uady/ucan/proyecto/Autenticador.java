package uady.ucan.proyecto;

import javafx.scene.control.PasswordField;
import java.security.NoSuchAlgorithmException;

public class Autenticador {
    private Encriptador encriptador = new Encriptador();

    public EstadoAutenticacion revisarCredenciales(Usuario usuario, PasswordField contraseña) throws NoSuchAlgorithmException {
        if (usuario == null) {
            return EstadoAutenticacion.USUARIO_INCORRECTO;
        }
        boolean contraseñaCorrecta = usuario.getPassword().equals(
                encriptador.encriptarContraseña(contraseña.getText()));

        if (!contraseñaCorrecta) {
            return EstadoAutenticacion.CONTRASENA_INCORRECTA;
        }

        return EstadoAutenticacion.EXITO;
    }
}
