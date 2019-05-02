/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesalandalus.programacion.reservasaulas.vista;

import java.time.LocalDate;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.modelo.ModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Tramo;

/**
 *
 * @author Youness
 */
public class IUTextual {

    private static final String ERROR = "ERROR";
    private static final String NOMBRE_VALIDO = "Youness";
    private static final String CORREO_VALIDO = "yl@hotmail.com";
    protected ModeloReservasAulas modelo;

    public IUTextual() {
        modelo = new ModeloReservasAulas();
        Opcion.setVista(this);
    }

    public void comenzar() throws OperationNotSupportedException {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Opcion.getOpcionSegunOrdinal(Consola.elegirOpcion());
            opcion.ejecutar();
        } while (opcion != Opcion.SALIR);
    }

    public void salir() {
        System.out.println("Has salido del programa");
    }

    public void insertarAula() {
        Consola.mostrarCabecera("INSERTAR AULA");
        try {
            Aula aula = Consola.leerAula();
            modelo.insertarAula(aula);
        } catch (OperationNotSupportedException e) {
            System.out.println(ERROR + e.getMessage());
        }
        System.out.println("Aula insertada correctamente.");
    }

    public void borrarAula() {
        Consola.mostrarCabecera("BORRAR AULA");
        try {
            Aula aula = Consola.leerAula();
            modelo.borrarAula(aula);
            System.out.println("Aula eliminada correctamente.");
        } catch (OperationNotSupportedException | IllegalArgumentException e) {
            System.out.println(ERROR + e.getMessage());
        }
    }

    public void buscarAula() throws OperationNotSupportedException {
        Consola.mostrarCabecera("BUSCAR AULA");
        String nombre = Consola.leerNombreAula();
        Aula aula = new Aula(nombre);
        Aula aulaABuscar = modelo.buscarAula(aula);
        if (aulaABuscar == null) {
            System.out.println(ERROR + "El aula a buscar no existe.");
        } else {
            System.out.println("Esta es la aula encontrada: " + aulaABuscar);
        }
    }

    public void listarAulas() {
        Consola.mostrarCabecera("LISTAR AULAS");
        List<String> aulasRepresentadas = modelo.representarAulas();
        if (aulasRepresentadas.isEmpty()) {
            System.out.println(ERROR + "No hay aulas.");
        }
        for (String copia: aulasRepresentadas) {
            System.out.println(copia);
        }
    }

    public void insertarProfesor() {
        Consola.mostrarCabecera("INSERTAR PROFESOR");
        try {
            Profesor profesor = Consola.leerProfesor();
            modelo.insertarProfesor(profesor);
            System.out.println("Profesor insertado correctamente.");
        } catch (OperationNotSupportedException e) {
            System.out.println(ERROR + e.getMessage());
        }
    }

    public void borrarProfesor() {
        Consola.mostrarCabecera("BORRAR PROFESOR");
        String nombre = Consola.leerNombreProfesor();
        Profesor borrar = new Profesor(nombre, CORREO_VALIDO);
        try {
            modelo.borrarProfesor(borrar);
            System.out.println("Profesor borrado correctamente.");
        } catch (OperationNotSupportedException e) {
            System.out.println(ERROR + e.getMessage());
        }
    }

    public void buscarProfesor() throws OperationNotSupportedException {
        Consola.mostrarCabecera("BUSCAR PROFESOR");
        String nombre = Consola.leerNombreProfesor();
        Profesor profesor = new Profesor(nombre, CORREO_VALIDO);
        Profesor profesorABuscar = modelo.buscarProfesor(profesor);
        if (profesorABuscar == null) {
            System.out.println(ERROR + "El profesor a buscar no existe.");
        } else {
            System.out.println("Este es el profesor buscado: " + profesor);
        }
    }

    public void listarProfesores() {
        Consola.mostrarCabecera("LISTAR PROFESORES");
        List<String> profesoresRepresentados = modelo.representarProfesores();
        if (profesoresRepresentados.isEmpty()) {
            System.out.println(ERROR + "No hay prefesores.");
        }
        for (String copia: profesoresRepresentados) {
            System.out.println(copia);
        }
    }

    public void realizarReserva() throws OperationNotSupportedException {
        Consola.mostrarCabecera("REALIZAR RESERVA");
        String nombre = Consola.leerNombreProfesor();
        Profesor profesor = new Profesor(nombre, CORREO_VALIDO);
        boolean existeProfesor = true;
        if (modelo.buscarProfesor(profesor) == null) {
            System.out.println(ERROR + "El profesor insertado no existe.");
            existeProfesor = false;
        }
        Reserva reserva = null;
        if (existeProfesor) {
            reserva = leerReserva(profesor);
            if (reserva == null) {
                System.out.println(ERROR + "El aula insertada no existe.");
            }
        }
        if (reserva == null) {
            System.out.println(ERROR + "La reserva no se ha podido realizar.");
        } else {
            try {
                modelo.realizarReserva(reserva);
                System.out.println("La reserva se ha realizado correctamente.");
            } catch (OperationNotSupportedException e) {
                System.out.println(ERROR + e.getMessage());
            }
        }
    }

    private Reserva leerReserva(Profesor profesor) throws OperationNotSupportedException {
        Profesor profesorABuscar = modelo.buscarProfesor(profesor);
        if (profesorABuscar == null) {
            return null;
        }
        String nombreAula = Consola.leerNombreAula();
        Aula aulaABuscar = modelo.buscarAula(new Aula(nombreAula));
        if (aulaABuscar == null) {
            return null;
        }
        LocalDate dia = Consola.leerDia();
        Tramo tramo = Consola.leerTramo();
        Permanencia permanencia = new Permanencia(dia, tramo);
        return new Reserva(profesorABuscar, aulaABuscar, permanencia);
    }

    public void anularReserva() throws OperationNotSupportedException {
        Consola.mostrarCabecera("ANULAR RESERVA");
        String nombre = Consola.leerNombreProfesor();
        Profesor profesor = new Profesor(nombre, CORREO_VALIDO);
        boolean existeProfesor = true;
        if (modelo.buscarProfesor(profesor) == null) {
            System.out.println(ERROR + "El profesor insertado no existe.");
            existeProfesor = false;
        }
        Reserva reserva = null;
        if (existeProfesor) {
            reserva = leerReserva(profesor);
            if (reserva == null) {
                System.out.println("El aula insertada no existe.");
            }
        }
        if (reserva == null) {
            System.out.println("La reserva no se ha podido anular.");
        } else {
            try {
                modelo.anularReserva(reserva);
            } catch (OperationNotSupportedException e) {
                System.out.println(ERROR + e.getMessage());
            }
            System.out.println("Reserva anulación de reserva se ha realizado correctamente.");
        }
    }

    public void listarReservas() {
        Consola.mostrarCabecera("LISTAR RESERVAS");
        List<String> reservasRepresentadas = modelo.representarReserva();
        if (reservasRepresentadas.isEmpty()) {
            System.out.println("No hay reservas.");
        }
        for (String copia: reservasRepresentadas) {
            System.out.println(copia);
        }
    }

    public void listarReservasAula() throws OperationNotSupportedException {
        Consola.mostrarCabecera("LISTAR RESERVAS AULA");
        String nombre = Consola.leerNombreAula();
        Aula aula = new Aula(nombre);
        boolean existeAula = true;
        if (modelo.buscarAula(aula) == null) {
            System.out.println(ERROR + "El aula insertada no existe.");
            existeAula = false;
        }
        List<Reserva> reservasRepresentadas = modelo.getReservasAula(aula);
        if (existeAula && reservasRepresentadas.isEmpty()) {
            System.out.println("No hay aulas reservadas.");
        }
        if (existeAula) {
            for (Reserva copia: reservasRepresentadas) {
            System.out.println(copia);
        }
        }
    }

    public void listarReservasProfesor() throws OperationNotSupportedException {
        Consola.mostrarCabecera("LISTAR RESERVAS PROFESOR");
        String nombre = Consola.leerNombreProfesor();
        Profesor profesor = new Profesor(nombre, CORREO_VALIDO);
        boolean existeProfesor = true;
        if (modelo.buscarProfesor(profesor) == null) {
            System.out.println(ERROR + "El profesor insertado no existe.");
            existeProfesor = false;
        }
        List<Reserva> reservasRepresentadas = modelo.getReservasProfesor(profesor);
        if (existeProfesor && reservasRepresentadas.isEmpty()) {
            System.out.println("No hay aulas reservas con el profesor insertado.");
        }
        if (existeProfesor) {
            for (Reserva copia: reservasRepresentadas) {
            System.out.println(copia);
            }
        }
    }

    public void listarReservasPermanencia() {
        Consola.mostrarCabecera("LISTAR RESERVAS PERMANENCIA");
        LocalDate dia = Consola.leerDia();
        Tramo tramo = Consola.leerTramo();
        Permanencia permanencia = new Permanencia(dia, tramo);
        List<Reserva> reservasRepresentadas = modelo.getReservasPermanencia(permanencia);
        if (reservasRepresentadas.isEmpty()) {
            System.out.println("No hay ninguna aula reservada con el tramo y día insertados.");
        }
        for (Reserva copia: reservasRepresentadas) {
            System.out.println(copia);
        }
    }

    public void consultarDisponibilidad() throws OperationNotSupportedException {
        Consola.mostrarCabecera("CONSULTAR DISPONIBILIDAD");
        String nombre = Consola.leerNombreAula();
        Aula aula = new Aula(nombre);
        boolean existeAula = true;
        if (modelo.buscarAula(aula) == null) {
            System.out.println(ERROR + "El aula insertada no existe.");
            existeAula = false;
        }
        if (existeAula) {
            LocalDate dia = Consola.leerDia();
            Tramo tramo = Consola.leerTramo();
            Permanencia permanencia = new Permanencia(dia, tramo);
            boolean disponibleAula = modelo.consultarDisponibilidad(aula, permanencia);
            if (disponibleAula) {
                System.out.println("El aula esta libre para el día y tramo insertado.");
            } else {
                System.out.println("El aula no esta libre para el día y tramo insertado.");
            }
        }
    }

}
