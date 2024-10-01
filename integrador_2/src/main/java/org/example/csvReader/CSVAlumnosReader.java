package org.example.csvReader;

import org.apache.commons.csv.CSVRecord;
import org.example.entity.Alumno;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;

public class CSVAlumnosReader extends CSVReader {
    public CSVAlumnosReader(String path) {
        super(path);
    }

    public LinkedList<Alumno> getAlumnos() throws IOException, ParseException {
        Iterable<CSVRecord> records =  this.read();
        LinkedList<Alumno> alumnos = new LinkedList<>();
        for (CSVRecord record : records) {
            // idAlumno(autogenerado),nombre,apellido,edad,genero,dni,ciudad,nroLibreta
            String nombre = (record.get(1));
            String apellido = (record.get(2));
            int edad = Integer.parseInt(record.get(3));
            String genero = (record.get(4));
            int dni = Integer.parseInt(record.get(5));
            String ciudad = (record.get(6));
            int nroLibreta = Integer.parseInt(record.get(7));

            alumnos.add(new Alumno(nombre, apellido, edad, genero, dni, ciudad, nroLibreta));
        }
        return alumnos;
    }
}

