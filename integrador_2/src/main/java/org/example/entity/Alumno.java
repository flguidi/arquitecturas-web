package org.example.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Alumno implements Serializable {
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAlumno;

	@Column
	private String nombre;

	@Column
	private String apellido;

	@Column
	private int edad;

	@Column
	private String genero;

	@Column
	private int dni;

	@Column
	private String ciudad;

	@Column
	private int nroLibreta;

	// Relación
	@OneToMany(mappedBy="alumno", fetch=FetchType.LAZY)
	private List<Inscripcion> inscripciones;

	// Constructores
	public Alumno() {}

	public Alumno(long idAlumno) {
		this.idAlumno = idAlumno;
		this.inscripciones = new ArrayList<>();
	}

	public Alumno(String nombre, String apellido, int edad, String genero, int dni, String ciudad, int nroLibreta) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.genero = genero;
		this.dni = dni;
		this.ciudad = ciudad;
		this.nroLibreta = nroLibreta;
		this.inscripciones = new ArrayList<>();
	}

	public Alumno(long idAlumno, String nombre, String apellido, int edad, String genero, int dni, String ciudad,
				  int nroLibreta) {
		this(nombre, apellido, edad, genero, dni, ciudad, nroLibreta);
		this.idAlumno = idAlumno;
	}

	// Getters y Setters
	public long getIdAlumno() {
		return idAlumno;
	}

	public int getDni() {
		return dni;
	}

	public int getNroLibreta() {
		return nroLibreta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
}
