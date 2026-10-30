module uady.ucan.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.librepdf.openpdf;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;

    // Abrimos y exportamos el paquete donde están los archivos .java
    opens uady.ucan.proyecto to javafx.fxml;
    exports uady.ucan.proyecto;
    exports uady.ucan.proyecto.modelo;
    opens uady.ucan.proyecto.modelo to javafx.fxml;
    exports uady.ucan.proyecto.repositorios;
    opens uady.ucan.proyecto.repositorios to javafx.fxml;
    exports uady.ucan.proyecto.servicios;
    opens uady.ucan.proyecto.servicios to javafx.fxml;
    exports uady.ucan.proyecto.controlador;
    opens uady.ucan.proyecto.controlador to javafx.fxml;
    exports uady.ucan.proyecto.archivos;
    opens uady.ucan.proyecto.archivos to javafx.fxml;
}