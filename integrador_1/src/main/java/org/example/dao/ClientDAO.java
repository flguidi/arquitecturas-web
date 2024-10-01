package org.example.dao;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dto.InvoicedClientDTO;
import org.example.entities.Client;
import org.example.factories.MySQLDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements DAO<Client> {
    @Override
    public void createTable() throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "CREATE TABLE IF NOT EXISTS cliente ( " +
                "id_cliente INT PRIMARY KEY, " +
                "nombre VARCHAR(500), " +
                "email VARCHAR(150)) ";

        conn.prepareStatement(sql).execute();
        conn.commit();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void dropTable() throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "DROP TABLE IF EXISTS cliente";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void loadCSVData(CSVParser data) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "INSERT INTO cliente (id_cliente, nombre, email) VALUES (?, ?, ?)";

        for (CSVRecord line : data) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(line.get("id_cliente")));
            ps.setString(2, line.get("nombre"));
            ps.setString(3, line.get("email"));
            ps.executeUpdate();
            conn.commit();
            ps.close();
        }

        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public Client get(int id) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();
        Client clienteById = null;

        String sql = "SELECT c.nombre, c.email FROM cliente c WHERE c.id_cliente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id); // Id del cliente buscado

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String nombre = rs.getString("nombre"); // Se obtiene el nombre
            String email = rs.getString("email"); // Se obtiene el email
            clienteById = new Client(id, nombre, email); // Se crea un objeto Client con los datos obtenidos
        }

        conn.commit();
        rs.close();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
        return clienteById;
    }

    @Override
    public void update(Client client, String[] params) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "UPDATE cliente SET nombre = ?, email = ? WHERE id_cliente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, params[0]); // Nuevo nombre
        ps.setString(2, params[1]); // Nuevo email
        ps.setInt(3, client.getId()); // Id del cliente a modificar
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }

    @Override
    public void delete(Client client) throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, client.getId()); // Id del cliente a eliminar
        ps.executeUpdate();

        conn.commit();
        ps.close();
        MySQLDAOFactory.getInstance().disconnect();
    }

    public List<InvoicedClientDTO> mostValuableClients() throws Exception {
        Connection conn = MySQLDAOFactory.getInstance().connect();

        String sql = "SELECT c.id_cliente, c.nombre, SUM(p.valor * fp.cantidad) AS facturado " +
                "FROM cliente c " +
                "JOIN factura f ON c.id_cliente = f.id_cliente " +
                "JOIN factura_producto fp ON f.id_factura = fp.id_factura " +
                "JOIN producto p ON p.id_producto = fp.id_producto " +
                "GROUP BY c.id_cliente " +
                "ORDER BY facturado DESC";

        List<InvoicedClientDTO> clients = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt(1); // ID
            String name = rs.getString(2); // Nombre
            float total = rs.getFloat(3); // Total facturado
            InvoicedClientDTO client = new InvoicedClientDTO(id, name, total);
            clients.add(client);
        }

        conn.commit();
        ps.close();
        rs.close();
        MySQLDAOFactory.getInstance().disconnect();
        return clients;
    }
}
