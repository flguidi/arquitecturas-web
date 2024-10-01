package org.example.factory;

import org.example.repository.AlumnoRepositoryImp;
import org.example.repository.CarreraRepositoryImp;
import org.example.repository.InscripcionRepositoryImp;

import java.sql.SQLException;

public abstract class FactoryEntityManager {
    public static final int MYSQL = 1;

    public abstract CarreraRepositoryImp getCarreraRepository();
    public abstract AlumnoRepositoryImp getAlumnoRepository();
    public abstract InscripcionRepositoryImp getInscripcionRepository();
    public abstract void closeEntityManagerFactory();

    public static FactoryEntityManager getDAOFactory(int persistence) throws SQLException {
        switch (persistence) {
            case MYSQL: return MySQLFactory.getInstance();
            default: return null;
        }
    }
}