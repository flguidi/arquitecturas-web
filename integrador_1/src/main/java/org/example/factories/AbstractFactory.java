package org.example.factories;

import org.example.dao.ClientDAO;
import org.example.dao.InvoiceDAO;
import org.example.dao.InvoiceProductDAO;
import org.example.dao.ProductDAO;

import java.sql.Connection;

public abstract class AbstractFactory {
    public static final int MYSQL = 1;

    public static AbstractFactory getDAOFactory(int factory){
        switch (factory) {
            case MYSQL: return MySQLDAOFactory.getInstance();
            default: return null;
        }
    }

    public abstract Connection connect() throws Exception;
    public abstract void disconnect() throws Exception;

    public abstract ClientDAO getClientDAO();
    public abstract InvoiceDAO getInvoiceDAO();
    public abstract InvoiceProductDAO getInvoiceProductDAO();
    public abstract ProductDAO getProductDAO();
}
