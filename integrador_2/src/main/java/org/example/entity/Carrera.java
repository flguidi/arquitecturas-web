package org.example.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrera implements Serializable{
	//Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idCarrera;

	@Column
	private String nombreCarrera;

	//Relacion
	@OneToMany(mappedBy="carrera", fetch=FetchType.LAZY)
	private List<Inscripcion> alumnos;

	// Constructores
	public Carrera() {
		this.alumnos = new ArrayList<>();
	}

	public Carrera(long idCarrera) {
		this();
		this.idCarrera = idCarrera;
	}

	public Carrera(String nombreCarrera) {
		this();
		this.nombreCarrera = nombreCarrera;
	}

	public Carrera(long idCarrera, String nombreCarrera) {
		this(idCarrera);
		this.nombreCarrera = nombreCarrera;
	}

	// Getters y Setters
	public long getIdCarrera() {
		return idCarrera;
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}
}
