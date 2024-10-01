package org.example.factory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.repository.AlumnoRepositoryImp;
import org.example.repository.CarreraRepositoryImp;
import org.example.repository.InscripcionRepositoryImp;

public class MySQLFactory extends FactoryEntityManager {
    private static MySQLFactory instance;
    private EntityManagerFactory emf;

    private MySQLFactory() {
        this.emf = Persistence.createEntityManagerFactory("MYSQL");
    }

    public static MySQLFactory getInstance() {
        if(instance == null){
            instance = new MySQLFactory();
        }
        return instance;
    }

    @Override
    public CarreraRepositoryImp getCarreraRepository() {
        return CarreraRepositoryImp.getInstance(emf);
    }
    @Override
    public AlumnoRepositoryImp getAlumnoRepository() {
        return AlumnoRepositoryImp.getInstance(emf);
    }
    @Override
    public InscripcionRepositoryImp getInscripcionRepository() {
        return InscripcionRepositoryImp.getInstance(emf);
    }

    public void closeEntityManagerFactory() {
        emf.close();
    }
}