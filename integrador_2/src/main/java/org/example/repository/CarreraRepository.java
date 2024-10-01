package org.example.repository;

import org.example.dto.DTOInscriptosCarrera;
import org.example.entity.Carrera;

import java.util.List;

public interface CarreraRepository {
    public void save(Carrera c);
    public Carrera getById(long id);
    public List<DTOInscriptosCarrera> getCarreraOrderByCantEstudiantes();
}
