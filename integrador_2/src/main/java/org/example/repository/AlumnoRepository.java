package org.example.repository;

import org.example.dto.DTOAlumno;
import org.example.entity.Alumno;

import java.util.List;

public interface AlumnoRepository {
    public void save(Alumno s);
    public List<DTOAlumno> getAll();
    public DTOAlumno getById(long id);
    public DTOAlumno getByNroLibreta(int nlibreta);
    public List<DTOAlumno> getByGenero(String genero);
    public List<DTOAlumno> getByCarreraCiudad(Long idCarrera, String ciudad);
}
