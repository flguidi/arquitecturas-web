package org.example.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Inscripcion implements Serializable {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idInscripcion;

    @ManyToOne
    private Carrera carrera;

    @ManyToOne
    private Alumno alumno;

    @Column
    private LocalDate fechaInscripcion;

    @Column(nullable = true)
    private LocalDate fechaEgreso;

    @Column
    private int antiguedad;

    @Column
    private boolean graduado;

    // Constructores
    public Inscripcion() {}

    public Inscripcion(Carrera carrera, Alumno alumno, LocalDate fechaInscripcion, LocalDate fechaEgreso,
                       int antiguedad, boolean graduado) {
        this.carrera = carrera;
        this.alumno = alumno;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaEgreso = fechaEgreso;
        this.antiguedad = antiguedad;
        this.graduado = graduado;
    }

    public Inscripcion(long idInscripcion, Carrera carrera, Alumno alumno, LocalDate fechaInscripcion,
                       LocalDate fechaEgreso, int antiguedad, boolean graduado) {
        this(carrera, alumno, fechaInscripcion, fechaEgreso, antiguedad, graduado);
        this.idInscripcion = idInscripcion;
    }

    // Getters and setters
    public long getIdInscripcion() {
        return idInscripcion;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(LocalDate fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public boolean isGraduado() {
        return graduado;
    }

    public void setGraduado(boolean graduado) {
        this.graduado = graduado;
    }
}