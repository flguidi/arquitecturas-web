package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.example.dao.ClientDAO;
import org.example.dao.InvoiceDAO;
import org.example.dao.InvoiceProductDAO;
import org.example.dao.ProductDAO;
import org.example.dto.InvoicedClientDTO;
import org.example.dto.MostProfitableProductDTO;
import org.example.entities.Client;
import org.example.entities.InvoiceProduct;
import org.example.factories.AbstractFactory;

import java.io.FileReader;
import java.util.List;

public class Main {
    private static AbstractFactory factory = AbstractFactory.getDAOFactory(AbstractFactory.MYSQL);
    private static ClientDAO clientDAO;
    private static InvoiceDAO invoiceDAO;
    private static InvoiceProductDAO invoiceProductDAO;
    private static ProductDAO productDAO;

    public static void main(String[] args) throws Exception {
        instanciateDAOs();

        // 1. Creación de esquema de la base de datos
        createTables();

        // 2. Carga de tablas a partir de archivos CSV
        populateTables();

        // 3. Producto con mayor recaudación
        MostProfitableProductDTO mpp = productDAO.mostProfitableProduct();
        System.out.println("Producto con mayor recaudación: " + mpp);

        // 4. Lista de clientes de mayor a menor facturación
        System.out.println("\nLista de clientes de mayor a menor facturación:");
        List<InvoicedClientDTO> clients = clientDAO.mostValuableClients();
        for (InvoicedClientDTO client : clients) {
            System.out.println("Cliente: " + client);
        }

        // Pruebas de operaciones CRUD
        System.out.println("\nOperaciones CRUD");

        // SELECT
        int clientId = 5;
        Client client = clientDAO.get(clientId);
        System.out.println("Cliente con ID=" + clientId + ": " +  client);

        // UPDATE
        String[] params = {"Juan Pérez", "juanperez@mail.com"};
        clientDAO.update(client, params);
        System.out.println("Cliente con ID=" + clientId + ": " +  clientDAO.get(clientId));

        // DELETE
        int invProdId = 1;
        InvoiceProduct ip = invoiceProductDAO.get(invProdId);
        invoiceProductDAO.delete(ip);
        System.out.println("Registro de Factura-Producto eliminado: " + ip);

        // Eliminación de tablas
        dropTables();
    }

    public static void instanciateDAOs() throws Exception {
        clientDAO = factory.getClientDAO();
        invoiceDAO = factory.getInvoiceDAO();
        invoiceProductDAO = factory.getInvoiceProductDAO();
        productDAO = factory.getProductDAO();
    }

    public static void createTables() throws Exception {
        clientDAO.createTable();
        invoiceDAO.createTable();
        productDAO.createTable();
        invoiceProductDAO.createTable();
    }

    public static void populateTables() throws Exception {
        CSVParser clientCSV = CSVFormat.DEFAULT
                .withHeader()
                .parse(new FileReader("src/main/resources/csv/clientes.csv"));
        CSVParser invoiceCSV = CSVFormat.DEFAULT
                .withHeader()
                .parse(new FileReader("src/main/resources/csv/facturas.csv"));
        CSVParser productDataCSV = CSVFormat.DEFAULT
                .withHeader()
                .parse(new FileReader("src/main/resources/csv/productos.csv"));
        CSVParser invoiceProductCSV = CSVFormat.DEFAULT
                .withHeader()
                .parse(new FileReader("src/main/resources/csv/facturas-productos.csv"));

        clientDAO.loadCSVData(clientCSV);
        invoiceDAO.loadCSVData(invoiceCSV);
        productDAO.loadCSVData(productDataCSV);
        invoiceProductDAO.loadCSVData(invoiceProductCSV);
    }

    public static void dropTables() throws Exception {
        invoiceProductDAO.dropTable();
        invoiceDAO.dropTable();
        clientDAO.dropTable();
        productDAO.dropTable();
    }
}