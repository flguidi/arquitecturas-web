package org.example.dao;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dto.MostProfitableProductDTO;
import org.example.entities.Product;
import org.example.factories.MySQLDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO implements DAO<Product> {
    @Override
    public void createTable() throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "CREATE TABLE IF NOT EXISTS producto (" +
                "id_producto INT PRIMARY KEY, " +
                "nombre VARCHAR(255), " +
                "valor DECIMAL(10, 2))";

        conn.prepareStatement(sql).execute();
        conn.commit();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void dropTable() throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "DROP TABLE IF EXISTS producto";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void loadCSVData(CSVParser data) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "INSERT INTO producto (id_producto, nombre, valor) VALUES (?, ?, ?)";

        for (CSVRecord line : data) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(line.get("id_producto")));
            ps.setString(2, line.get("nombre"));
            ps.setFloat(3, Float.parseFloat(line.get("valor")));
            ps.executeUpdate();
            conn.commit();
            ps.close();
        }

        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public Product get(int id) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();
        Product productById = null;

        String sql = "SELECT p.nombre, p.valor FROM producto p WHERE p.id_producto = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id); // Id del cliente buscado

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String name = rs.getString("nombre"); // Se obtiene el nombre del producto
            float value = rs.getFloat("valor"); // Se obtiene el valor del producto
            productById = new Product(id, name, value); // Se crea un objeto Product
        }

        conn.commit();
        rs.close();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
        return productById;
    }

    @Override
    public void update(Product product, String[] params) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "UPDATE producto SET nombre = ?, valor = ? WHERE id_producto = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, params[0]); // Nuevo nombre del producto
        ps.setFloat(2, Float.parseFloat(params[1])); // Nuevo valor del producto
        ps.setInt(2, product.getId()); // ID del producto a actualizar
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void delete(Product product) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "DELETE FROM producto WHERE id_producto = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, product.getId()); // Id del producto a eliminar
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }

    public MostProfitableProductDTO mostProfitableProduct() throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "SELECT p.id_producto, p.nombre, SUM(p.valor * fp.cantidad) AS recaudacion " +
                "FROM producto p " +
                "JOIN factura_producto fp ON (p.id_producto = fp.id_producto) " +
                "GROUP BY p.id_producto " +
                "ORDER BY recaudacion DESC " +
                "LIMIT 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        MostProfitableProductDTO mpp = null;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1); // ID
            String name = rs.getString(2); // Nombre
            float profit = rs.getFloat(3); // Recaudación
            mpp = new MostProfitableProductDTO(id, name, profit); // Se crea el objeto que más recaudó
        }

        ps.close();
        rs.close();
        conn.commit();
        MySQLDAOFactory.getInstance().disconnect();
        return mpp;
    }
}
