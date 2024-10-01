package org.example.csvReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CSVReader {

    private String path;

    public CSVReader(String path){
        this.path = path;
    }

    public Iterable<CSVRecord> read() throws IOException {
        Reader data = new FileReader(this.path);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(data);
        return records;
    }
}

