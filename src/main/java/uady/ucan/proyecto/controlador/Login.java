package uady.ucan.proyecto.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import uady.ucan.proyecto.modelo.EstadoAutenticacion;
import uady.ucan.proyecto.modelo.Usuario;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;
import uady.ucan.proyecto.repositorios.RepositorioUsuarios;
import uady.ucan.proyecto.servicios.Autenticador;

import java.security.NoSuchAlgorithmException;

import static uady.ucan.proyecto.controlador.Alerta.mostrarAlerta;
import static uady.ucan.proyecto.modelo.EstadoAutenticacion.EXITO;

public class Login {
    @FXML private TextField usuarioEntrada;
    @FXML private PasswordField contraseñaEntrada;
    @FXML private Button botonLogin;
    //ID-19 replace inheritence with delegation
    private ControladorVentanas gestorVentanas;

    // Ya no se hace el "new", se declara vacío para recibirlo por constructor
    private RepositorioUsuarios repositorioUsuarios;
    private Autenticador autenticador = new Autenticador();

    // ID-15: Add Parameter
    public Login(RepositorioAlumnos repositorioAlumnos, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
        // El delegado ahora recibe ambos repositorios intactos para pasarlos al resto de ventanas
        this.gestorVentanas = new ControladorVentanas(repositorioAlumnos, repositorioUsuarios);
    }

    @FXML
    public void inicioSesion() throws NoSuchAlgorithmException { // 0: Entrar a la app, 1: Salir
        repositorioUsuarios.leerUsuarios();

            Usuario usuario = repositorioUsuarios.buscarUsuario(usuarioEntrada.getText());
            EstadoAutenticacion estadoLogin = autenticador.revisarCredenciales(usuario, contraseñaEntrada);

            switch (estadoLogin) {
                case EXITO:
                    break;

                case USUARIO_INCORRECTO:
                    mostrarAlerta(Alert.AlertType.WARNING, "Usuario incorrecto.");
                    break;

                case CONTRASENA_INCORRECTA:
                    mostrarAlerta(Alert.AlertType.WARNING, "Contraseña incorrecta.");
                    break;
            }

            if (estadoLogin == EXITO) {
                //ID-19: se delega la acción
                gestorVentanas.menu();
                botonLogin.getScene().getWindow().hide();
            }
        }
    }