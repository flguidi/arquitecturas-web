package org.example.dao;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.InvoiceProduct;
import org.example.factories.MySQLDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InvoiceProductDAO implements DAO<InvoiceProduct> {
    @Override
    public void createTable() throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "CREATE TABLE IF NOT EXISTS factura_producto (" +
                "id_factura INT, " +
                "id_producto INT, " +
                "cantidad INT, " +
                "PRIMARY KEY (id_factura, id_producto), " +
                "FOREIGN KEY (id_factura) REFERENCES factura (id_factura), " +
                "FOREIGN KEY (id_producto) REFERENCES producto (id_producto))";

        conn.prepareStatement(sql).execute();
        conn.commit();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void dropTable() throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "DROP TABLE IF EXISTS factura_producto";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void loadCSVData(CSVParser data) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "INSERT INTO factura_producto (id_factura, id_producto, cantidad) VALUES (?, ?, ?)";

        for (CSVRecord line : data) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(line.get("id_factura")));
            ps.setInt(2, Integer.parseInt(line.get("id_producto")));
            ps.setInt(3, Integer.parseInt(line.get("cantidad")));
            ps.executeUpdate();
            conn.commit();
            ps.close();
        }

        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public InvoiceProduct get(int id) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();
        InvoiceProduct invoiceProductById = null;

        String sql = "SELECT * FROM factura_producto WHERE id_factura = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int invoiceId = rs.getInt("id_factura"); // ID de la factura
            int productId = rs.getInt("id_producto"); // ID del producto
            int quantity = rs.getInt("cantidad"); // Cantidad
            invoiceProductById = new InvoiceProduct(invoiceId, productId, quantity);
        }

        conn.commit();
        rs.close();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
        return invoiceProductById;
    }

    @Override
    public void update(InvoiceProduct invoiceProduct, String[] params) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "UPDATE factura_producto SET cantidad = ? WHERE id_factura = ? AND id_producto = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, invoiceProduct.getQuantity());  // Nueva cantidad
        ps.setInt(2, invoiceProduct.getInvoiceId()); // ID de factura a modificar
        ps.setInt(3, invoiceProduct.getProductId()); // ID de producto a modificar
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void delete(InvoiceProduct invoiceProduct) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "DELETE FROM factura_producto WHERE id_factura = ? AND id_producto = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, invoiceProduct.getInvoiceId()); // Id de la factura a eliminar
        ps.setInt(2, invoiceProduct.getProductId()); // Id del producto a eliminar
        ps.executeUpdate();

        ps.close();
        conn.commit();
        MySQLDAOFactory.getInstance().disconnect();
    }
}
