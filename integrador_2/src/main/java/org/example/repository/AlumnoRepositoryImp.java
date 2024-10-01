package org.example.repository;

import org.example.dto.DTOAlumno;
import org.example.entity.Alumno;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class AlumnoRepositoryImp implements AlumnoRepository {
    private static AlumnoRepositoryImp instance;
    private EntityManagerFactory emf;
    private EntityManager em;

    private AlumnoRepositoryImp(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static AlumnoRepositoryImp getInstance(EntityManagerFactory emf){
        if(instance==null){
            return new AlumnoRepositoryImp(emf);
        }
        return instance;
    }

    @Override
    public void save(Alumno a) {
        em = emf.createEntityManager();
        if (!em.contains(a)) {
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
        } else {
            em.merge(a);
        }
        em.close();

        // Verificación posterior al guardado
        em = emf.createEntityManager();
        Alumno savedAlumno = em.find(Alumno.class, a.getIdAlumno());  // Recupera el objeto por ID
        if (savedAlumno != null) {
            System.out.println("El alumno se guardó correctamente: " +
                    "Nombre: "+savedAlumno.getNombre()+
                    ", Apellido: "+savedAlumno.getApellido());
        } else {
            System.out.println("Error al guardar el alumno.");
        }
        em.close();
    }

    @Override
    public List<DTOAlumno> getAll() {
        em = emf.createEntityManager();
        Query query = em.createQuery("SELECT a FROM Alumno a ORDER BY a.apellido");
        List<Alumno> alumnos = query.getResultList();
        List<DTOAlumno> alumnosDTOList = this.crearDTOAlumnos(alumnos);
        em.close();
        return alumnosDTOList;
    }

    @Override
    public DTOAlumno getById(long id) {
        em = emf.createEntityManager();
        Alumno a = em.find(Alumno.class, id);
        if (a == null) {
            System.out.println("No se encontró un alumno con el id: " + id);
            return null;
        }
        return this.crearDTOAlumno(a);
    }

    @Override
    public DTOAlumno getByNroLibreta(int nroLibreta) {
        em = emf.createEntityManager();
        Alumno alumno = this.em.createQuery("SELECT a FROM Alumno a WHERE a.nroLibreta = :nroLibreta", Alumno.class)
                .setParameter("nroLibreta", nroLibreta)
                .getSingleResult();
        em.close();
        return crearDTOAlumno(alumno);
    }

    @Override
    public List<DTOAlumno> getByGenero(String genero) {
        em = emf.createEntityManager();
        Query q = em.createQuery("SELECT a FROM Alumno a WHERE a.genero = :genero");
        q.setParameter("genero", genero);
        List<Alumno> alumnos = q.getResultList();
        List<DTOAlumno> alumnosByGenero = this.crearDTOAlumnos(alumnos);
        em.close();
        return alumnosByGenero;
    }

    @Override
    public List<DTOAlumno> getByCarreraCiudad(Long idCarrera, String ciudad) {
        em = emf.createEntityManager();
        Query q = em.createQuery("SELECT a FROM Alumno a, IN(a.inscripciones) i WHERE i.carrera.id = :idCarrera AND a.ciudad = :ciudadOrigen");
        q.setParameter("idCarrera", idCarrera);
        q.setParameter("ciudadOrigen", ciudad);
        List<Alumno> alumnos = q.getResultList();
        List<DTOAlumno> alumnosByCarreraCiudad = this.crearDTOAlumnos(alumnos);
        em.close();
        return alumnosByCarreraCiudad;
    }
    
    private List<DTOAlumno> crearDTOAlumnos (List<Alumno> alumnoList) {
    	List<DTOAlumno> DTOAlumnoList = new ArrayList<>();
    	for (Alumno alumno : alumnoList) {
    		DTOAlumnoList.add(crearDTOAlumno(alumno));
        }
    	return DTOAlumnoList;
    }

    //String nombre, String apellido, int edad, String genero, int dni, String ciudad, int nroLibreta
    private DTOAlumno crearDTOAlumno (Alumno alumno) {
        return new DTOAlumno(
    			alumno.getNombre(),
                alumno.getApellido(),
                alumno.getEdad(),
                alumno.getGenero(),
                alumno.getDni(),
    			alumno.getCiudad(),
                alumno.getNroLibreta());
    }
}