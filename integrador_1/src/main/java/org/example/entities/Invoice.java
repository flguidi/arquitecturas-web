package org.example.entities;

public class Invoice {
    private int id;
    private int clienteId;

    public Invoice(int id, int clienteId) {
        this.id = id;
        this.clienteId = clienteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public String toString() {
        return "(ID: " + id + " | ID-Cliente: " + clienteId + ")";
    }
}
