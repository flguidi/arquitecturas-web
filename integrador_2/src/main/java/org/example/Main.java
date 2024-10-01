package org.example;

import org.example.csvReader.*;
import org.example.dto.*;
import org.example.entity.*;
import org.example.factory.*;
import org.example.repository.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static CarreraRepositoryImp carreraRepo;
    private static AlumnoRepositoryImp alumnoRepo;
    private static InscripcionRepositoryImp inscripcionRepo;

    private static void csvUpload(AlumnoRepositoryImp alumnoRepo, CarreraRepositoryImp carreraRepo,
                                  InscripcionRepositoryImp inscripcionRepo) throws IOException, ParseException {
        String filePath = new File("").getAbsolutePath();
        LinkedList<Alumno> alumnos = new CSVAlumnosReader(filePath +
                "/src/main/resources/csv/alumnos.csv").getAlumnos();
        for (Alumno a : alumnos) {
            alumnoRepo.save(a);
        }

        LinkedList<Carrera> careers = new CSVCarreraReader(filePath +
                "/src/main/resources/csv/carreras.csv").getCarreras();
        for (Carrera c : careers) {
            carreraRepo.save(c);
        }

        LinkedList<Inscripcion> inscripciones = new CSVInscripcionReader(filePath +
                "/src/main/resources/csv/inscripciones.csv").getInscripciones();
        for (Inscripcion i : inscripciones) {
            inscripcionRepo.save(i);
        }
    }

    public static void main(String[] args) throws ParseException, IOException, SQLException {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE); // Sólo se imprimen errores severos

        FactoryEntityManager mysqlFactory = FactoryEntityManager.getDAOFactory(FactoryEntityManager.MYSQL);

        carreraRepo = mysqlFactory.getCarreraRepository();
        alumnoRepo = mysqlFactory.getAlumnoRepository();
        inscripcionRepo = mysqlFactory.getInscripcionRepository();

        System.out.println("--- CARGA DE ARCHIVOS .CSV ---\n");
        csvUpload(alumnoRepo, carreraRepo, inscripcionRepo); // Se cargan los datos de los CSV a las tablas

        System.out.println("\n--- EJERCICIO 2 ---");

        // 2A) Dar de alta un estudiante.
        System.out.println("\n2A) Dar de alta un estudiante.");

        Alumno a1 = new Alumno("Juan", "Perez",35,"masculino",34123456,"Tandil",123456);
        Alumno a2 = new Alumno("Maria", "Garcia",31,"femenino",37123456,"Ayacucho",234567);
        Alumno a3 = new Alumno("Armando", "Castillo",40,"masculimo",30123456,"Mar del Plata",321456);
        alumnoRepo.save(a1);
        alumnoRepo.save(a2);
        alumnoRepo.save(a3);

        Carrera c1 = new Carrera("Licenciatura en Tecnología Ambiental");
        carreraRepo.save(c1);

        Carrera c2 = new Carrera("Licenciatura en Educacion Matemática");
        carreraRepo.save(c2);

        // 2B) Matricular un estudiante en una carrera.
        System.out.println("\n2B) Matricular un estudiante en una carrera.");
        Inscripcion i1 = new Inscripcion(c1,a1, LocalDate.parse("2020-03-04"),LocalDate.parse("2023-07-04"),3,true);
        inscripcionRepo.save(i1);

        // 2C) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
        System.out.println("\n2C) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.");
        System.out.println("Ordenado por apellido ASC:");
        for (DTOAlumno s : alumnoRepo.getAll()){
            System.out.println(s);
        }

        // 2D) Recuperar un estudiante, en base a su número de libreta universitaria.
        System.out.println("\n2D) Recuperar un estudiante, en base a su número de libreta universitaria.");
        System.out.println("Estudiante con numero de libreta = 223344:");
        System.out.println(alumnoRepo.getByNroLibreta(223344));

        // 2E) Recuperar todos los estudiantes, en base a su género.
        System.out.println("\n2E) Recuperar todos los estudiantes, en base a su género.");
        System.out.println("Estudiantes cuyo genero es masculino:");
        for (DTOAlumno s : alumnoRepo.getByGenero("masculino")){
            System.out.println(s);
        }

        // 2F) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
        System.out.println("\n2F) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.");
        for (DTOInscriptosCarrera c : carreraRepo.getCarreraOrderByCantEstudiantes()){
            System.out.println(c);
        }

        // 2G) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
        System.out.println("\n2G) Estudiantes de la carrera de Ingenieria en Sistemas que viven en Buenos Aires.");
        Long idCarrera = 1L;
        for (DTOAlumno s : alumnoRepo.getByCarreraCiudad(idCarrera, "Buenos Aires")){
            System.out.println(s);
        }


        // 3) Generar un reporte de las carreras, que para cada carrera incluya información de los inscriptos
        // y egresados por año. Se deben ordenar las carreras alfabeticamente, y presentar los años de manera
        // cronológica.
        System.out.println("\n--- EJERCICIO 3 ---");

        System.out.println("\n3) Reporte de las carreras ordenadas alfabeticamente, con cantidad de inscriptos y egresados por año.");
        for (DTOInforme i : inscripcionRepo.crearInforme()){
            System.out.println(i);
        }
    }
}
