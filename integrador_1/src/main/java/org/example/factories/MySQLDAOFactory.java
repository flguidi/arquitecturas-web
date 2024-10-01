package org.example.factories;

import org.example.dao.ClientDAO;
import org.example.dao.InvoiceDAO;
import org.example.dao.InvoiceProductDAO;
import org.example.dao.ProductDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDAOFactory extends AbstractFactory {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URI = "jdbc:mysql://localhost:3306/mysql_integrador1";
    private static final String USER = "root";
    private static final String PASS = "pw";

    private static MySQLDAOFactory instance = new MySQLDAOFactory();
    private static Connection connection;

    private MySQLDAOFactory() {}

    public static synchronized MySQLDAOFactory getInstance() {
        if (instance == null) {
            instance = new MySQLDAOFactory();
        }
        return instance;
    }

    public Connection connect() throws Exception {
        if (connection == null) {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(URI, USER, PASS);
            connection.setAutoCommit(false);
        }
        return connection;
    }

    public void disconnect() throws Exception {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    @Override
    public ClientDAO getClientDAO() {
        return new ClientDAO();
    }

    @Override
    public InvoiceDAO getInvoiceDAO() {
        return new InvoiceDAO();
    }

    @Override
    public InvoiceProductDAO getInvoiceProductDAO() {
        return new InvoiceProductDAO();
    }

    @Override
    public ProductDAO getProductDAO() {
        return new ProductDAO();
    }
}
