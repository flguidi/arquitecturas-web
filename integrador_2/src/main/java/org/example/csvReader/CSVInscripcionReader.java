package org.example.csvReader;

import org.example.entity.*;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.LinkedList;

public class CSVInscripcionReader extends CSVReader {
    public CSVInscripcionReader(String path) {
        super(path);
    }

    public LinkedList<Inscripcion> getInscripciones() throws IOException, ParseException {
        Iterable<CSVRecord> records = this.read();
        LinkedList<Inscripcion> inscripciones = new LinkedList<>();
        for (CSVRecord record : records) {
            Carrera carrera = new Carrera(Integer.parseInt(record.get(1)));
            Alumno alumno = new Alumno(Integer.parseInt(record.get(0)));
            LocalDate fechaInscripcion = LocalDate.parse(record.get(3));

            // A las fechas vacías se les asigna null
            LocalDate fechaEgreso = null;
            String fechaEgresoStr = record.get(4);
            if (!fechaEgresoStr.isEmpty()) {
                fechaEgreso = LocalDate.parse(record.get(4));
            }

            int antiguedad = Integer.parseInt(record.get(5));
            boolean graduado = Boolean.parseBoolean(record.get(6));

            // Se crea un objeto Inscripcion por cada registro
            Inscripcion inscripcion = new Inscripcion(carrera,
                    alumno,
                    fechaInscripcion,
                    fechaEgreso,
                    antiguedad,
                    graduado);
            inscripciones.add(inscripcion);
        }
        return inscripciones;
    }
}
