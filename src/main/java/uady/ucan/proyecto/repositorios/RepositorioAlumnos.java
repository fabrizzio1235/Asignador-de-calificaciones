package uady.ucan.proyecto.repositorios;

import uady.ucan.proyecto.modelo.Alumno;

import java.util.ArrayList;

// ID-2 Ahora existe una clase que se encarga de almacenar a la lista de alumnos que se está modificando en la aplicación.

public class RepositorioAlumnos {
    // ID-02: Move Field e ID-12: Change Value to Reference (instancia única, no estático)
    private ArrayList<Alumno> listaAlumnos;

    public ArrayList<Alumno> getAlumnos() {
        return listaAlumnos;
    }

    public void setAlumnos(ArrayList<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    public boolean todosCalificados() {
        if(getAlumnos() == null) {return false;}
        for (Alumno a : getAlumnos()) {
            if (a.getCalificacion() == -1) {
                return false;
            }
        }
        return true;
    }
}