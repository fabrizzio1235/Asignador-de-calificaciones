package uady.ucan.proyecto;

import java.util.ArrayList;

// AHora existe una clase que se encarga de almacenar a la lista de alumnos que se está modificando en la aplicación.

public class RepositorioAlumnos {
    private static ArrayList<Alumno> listaAlumnos;

    public ArrayList<Alumno> getAlumnos() {
        return listaAlumnos;
    }

    public void setAlumnos(ArrayList<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }
}