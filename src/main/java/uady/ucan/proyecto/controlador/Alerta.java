package uady.ucan.proyecto.controlador;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

// Ahora existe una clase que se encarga de la función que lanza un pop-up en la interfaz gráfica.

public class Alerta {
    static Alert defaultAlert;
    static ButtonType acceptButton = new ButtonType("Aceptar");
    //ID-16: Rename Method (mostrarAlerta(tipo, mensaje)).
    static public void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        defaultAlert = new Alert(tipo);
        defaultAlert.setTitle("Información");
        defaultAlert.setHeaderText(null);
        defaultAlert.getButtonTypes().setAll(acceptButton);
        defaultAlert.setContentText(mensaje);
        defaultAlert.show();
    }
}
