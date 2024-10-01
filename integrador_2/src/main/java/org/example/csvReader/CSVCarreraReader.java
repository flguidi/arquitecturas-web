package org.example.csvReader;

import org.example.entity.Carrera;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.LinkedList;

public class CSVCarreraReader extends CSVReader{
    public CSVCarreraReader(String path) {
        super(path);
    }

    public LinkedList<Carrera> getCarreras() throws IOException {
        Iterable<CSVRecord> records =  this.read();
        LinkedList<Carrera> carreras = new LinkedList<>();
        for (CSVRecord record : records) {
            String nombreCarrera = record.get(1);
            carreras.add(new Carrera(nombreCarrera));
        }
        return carreras;
    }
}
