package uady.ucan.proyecto;

import org.junit.jupiter.api.Test;
import uady.ucan.proyecto.modelo.Alumno;
import uady.ucan.proyecto.repositorios.RepositorioAlumnos;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioAlumnosTest {

    private ArrayList<Alumno> listaConDosAlumnos() {
        ArrayList<Alumno> lista = new ArrayList<>();
        lista.add(new Alumno("A1", "PAGEAU", "SANTOS", "FRANCOIS"));
        lista.add(new Alumno("A2", "CORZO", "NIIDEA", "OMAR"));
        return lista;
    }

    /*
     * Técnica: Decompose Conditional hayAlumnosCargados() (ID-14)
     * Objetivo: Verificar que sin lista cargada el repositorio responde que no hay alumnos cargados y que no todos están calificados, y que getAlumnos() sigue devolviendo null.
     * Resultado esperado: hayAlumnosCargados() == false, todosCalificados() == false y getAlumnos() == null.
     * Datos: RepositorioAlumnos recién creado.
     */
    @Test
    void sinListaCargadaNoHayAlumnosYNoEstanCalificados() {
        RepositorioAlumnos repositorio = new RepositorioAlumnos();

        assertFalse(repositorio.hayAlumnosCargados());
        assertFalse(repositorio.todosCalificados());
        assertNull(repositorio.getAlumnos());
    }

    /*
     * Técnica: Decompose Conditional hayAlumnosCargados() (ID-14)
     * Objetivo: Verificar que una lista cargada (aunque esté vacía) cuenta como "cargada" y que setAlumnos(null) vuelve al estado sin cargar.
     * Resultado esperado: true con lista vacía y con lista de alumnos; false después de setAlumnos(null).
     * Datos: lista vacía, lista de dos alumnos y null.
     */
    @Test
    void unaListaCargadaCuentaComoCargadaAunqueEsteVaciaYNullLaDescarga() {
        RepositorioAlumnos repositorio = new RepositorioAlumnos();

        repositorio.setAlumnos(new ArrayList<>());
        assertTrue(repositorio.hayAlumnosCargados());

        repositorio.setAlumnos(listaConDosAlumnos());
        assertTrue(repositorio.hayAlumnosCargados());

        repositorio.setAlumnos(null);
        assertFalse(repositorio.hayAlumnosCargados());
    }

    /*
     * Técnica: Decompose Conditional hayAlumnosCargados() (ID-14) / Move Method todosCalificados() (ID-03)
     * Objetivo: Verificar que todosCalificados() es false mientras algún alumno conserve -1 y true cuando todos tienen calificación (incluido 0).
     * Resultado esperado: false con dos alumnos sin calificar y con uno calificado; true con ambos calificados (0 y 100); true con lista vacía.
     * Datos: lista de dos alumnos con calificaciones 0 y 100, y una lista vacía.
     */
    @Test
    void todosCalificadosSoloEsTrueSiNingunAlumnoConservaMenosUno() {
        RepositorioAlumnos repositorio = new RepositorioAlumnos();
        ArrayList<Alumno> lista = listaConDosAlumnos();
        repositorio.setAlumnos(lista);
        assertFalse(repositorio.todosCalificados());

        lista.get(0).setCalificacion(0);
        assertFalse(repositorio.todosCalificados());

        lista.get(1).setCalificacion(100);
        assertTrue(repositorio.todosCalificados());

        repositorio.setAlumnos(new ArrayList<>());
        assertTrue(repositorio.todosCalificados());
    }
}
