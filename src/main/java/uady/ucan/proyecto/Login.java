package uady.ucan.proyecto;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.security.NoSuchAlgorithmException;

import static uady.ucan.proyecto.Alerta.setAlert;

public class Login extends ControladorVentanas {
    @FXML private TextField usuarioEntrada;
    @FXML private PasswordField contraseñaEntrada;
    @FXML private Button botonLogin;

    // Ya no se hace el "new", se declara vacío para recibirlo por constructor
    private RepositorioUsuarios repositorioUsuarios;
    private Encriptador encriptador = new Encriptador();

    // ID-15: Add Parameter
    public Login(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    @FXML
    public void inicioSesion() throws NoSuchAlgorithmException { // 0: Entrar a la app, 1: Salir
        repositorioUsuarios.leerUsuarios();
        Usuario usuario = repositorioUsuarios.buscarUsuario(usuarioEntrada.getText());

        if (usuario == null) {
            setAlert(Alert.AlertType.WARNING, "Usuario incorrecto.");
            return;
        }

        boolean contraseñaCorrecta = usuario.getPassword().equals(
                encriptador.encriptarContraseña(contraseñaEntrada.getText()));
        if (!contraseñaCorrecta) {
            setAlert(Alert.AlertType.WARNING, "Contraseña incorrecta.");
            return;
        }

        menu();
        botonLogin.getScene().getWindow().hide();
    }
}