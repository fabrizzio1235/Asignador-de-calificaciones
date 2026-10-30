package uady.ucan.proyecto.servicios;

import javafx.scene.control.PasswordField;
import uady.ucan.proyecto.modelo.EstadoAutenticacion;
import uady.ucan.proyecto.modelo.Usuario;

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
