package org.example.dto;

public class DTOInforme {
	private String nombreCarrera;
	private Integer fechaEgreso;
	private Integer cantInscriptos;
	private Integer cantGraduados;

	public DTOInforme(String nombreCarrera, Integer fechaEgreso, Integer cantInscriptos, Integer cantGraduados) {
		this.nombreCarrera = nombreCarrera;
		this.fechaEgreso = fechaEgreso;
		this.cantInscriptos = cantInscriptos;
		this.cantGraduados = cantGraduados;
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}

	public Integer getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(Integer anio) {
		this.fechaEgreso = fechaEgreso;
	}

	public Integer getCantInscriptos() {
		return cantInscriptos;
	}

	public void setCantInscriptos(Integer cantInscriptos) {
		this.cantInscriptos = cantInscriptos;
	}

	public Integer getCantGraduados() {
		return cantGraduados;
	}

	public void setCantGraduados(Integer cantGraduados) {
		this.cantGraduados = cantGraduados;
	}

	@Override
	public String toString() {
		return "Carrera: " + nombreCarrera + ", Año: " + fechaEgreso + ", Cantidad Inscriptos: " + cantInscriptos
				+ ", Cantidad Egresos: " + cantGraduados;
	}
}
