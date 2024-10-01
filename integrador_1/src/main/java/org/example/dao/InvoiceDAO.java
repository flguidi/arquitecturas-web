package org.example.dao;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.Invoice;
import org.example.factories.MySQLDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InvoiceDAO implements DAO<Invoice> {
    @Override
    public void createTable() throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "CREATE TABLE IF NOT EXISTS factura (" +
                "id_factura INT PRIMARY KEY, " +
                "id_cliente INT, " +
                "FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente))";

        conn.prepareStatement(sql).execute();
        conn.commit();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void dropTable() throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "DROP TABLE IF EXISTS factura";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void loadCSVData(CSVParser data) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "INSERT INTO factura (id_factura, id_cliente) VALUES (?, ?)";

        for (CSVRecord line : data) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(line.get("id_factura")));
            ps.setString(2, line.get("id_cliente"));
            ps.executeUpdate();
            conn.commit();
            ps.close();
        }

        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public Invoice get(int id) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();
        Invoice invoiceById = null;

        String sql = "SELECT * FROM factura f WHERE f.id_cliente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id); // Id del cliente buscado

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int clientId = rs.getInt("id_cliente"); // Se obtiene el ID del cliente
            invoiceById = new Invoice(id, clientId); // Se crea un objeto Invoice
        }

        conn.commit();
        rs.close();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
        return invoiceById;
    }

    @Override
    public void update(Invoice invoice, String[] params) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "UPDATE factura SET id_cliente = ? WHERE id_factura = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, params[0]); // Nuevo ID de cliente
        ps.setInt(2, invoice.getId()); // Id de la factura a modificar
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void delete(Invoice invoice) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "DELETE FROM factura WHERE id_cliente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, invoice.getId()); // Id de la factura a eliminar
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }
}
