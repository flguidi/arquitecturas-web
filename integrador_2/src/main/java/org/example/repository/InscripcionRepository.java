package org.example.repository;

import org.example.dto.DTOInforme;
import org.example.entity.Inscripcion;

import java.util.List;

public interface InscripcionRepository{
    void save(Inscripcion i);
    List<DTOInforme> crearInforme();
}
