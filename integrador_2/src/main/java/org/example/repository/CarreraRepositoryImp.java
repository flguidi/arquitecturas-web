package org.example.repository;

import org.example.dto.DTOInscriptosCarrera;
import org.example.entity.Carrera;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CarreraRepositoryImp implements CarreraRepository {
    private EntityManagerFactory emf;
    private EntityManager em;
    private static CarreraRepositoryImp instance;

    private CarreraRepositoryImp(EntityManagerFactory emf){
        this.emf = emf;
    }

    public static CarreraRepositoryImp getInstance(EntityManagerFactory emf){
        if(instance==null){
            return new CarreraRepositoryImp(emf);
        }
        return instance;
    }

    @Override
    public void save(Carrera c) {
        em = emf.createEntityManager();
        if(!em.contains(c)){
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        }
        else{
            em.merge(c);
        }
        em.close();
        // Verificación posterior al guardado
        em = emf.createEntityManager();
        Carrera savedCarrera = em.find(Carrera.class, c.getIdCarrera());
        if (savedCarrera != null) {
            System.out.println("La carrera se guardó correctamente: " + savedCarrera.getNombreCarrera());
        } else {
            System.out.println("Error al guardar la carrera.");
        }
        em.close();
    }

    @Override
    public Carrera getById(long id) {
        em = emf.createEntityManager();
        Carrera c = em.find(Carrera.class, id);
        em.close();
        return c;
    }

    @Override
    public List<DTOInscriptosCarrera> getCarreraOrderByCantEstudiantes() {
        em = emf.createEntityManager();
        Query q = em.createQuery(
                "SELECT c.nombreCarrera, COUNT(a) " +
                        "FROM Carrera c " +
                        "LEFT JOIN c.alumnos a " +
                        "GROUP BY c.nombreCarrera " +
                        "ORDER BY COUNT(a) DESC"
        );

        List<Object[]> resultados = q.getResultList();
        List<DTOInscriptosCarrera> inscriptosPorCarrera = new ArrayList<>();

        for (Object[] resultado : resultados) {
            String nombreCarrera = (String) resultado[0];
            Long cantidadEstudiantes = (Long) resultado[1];
            DTOInscriptosCarrera dto = new DTOInscriptosCarrera(nombreCarrera, cantidadEstudiantes);
            inscriptosPorCarrera.add(dto);
        }

        em.close();
        return inscriptosPorCarrera;
    }
}
