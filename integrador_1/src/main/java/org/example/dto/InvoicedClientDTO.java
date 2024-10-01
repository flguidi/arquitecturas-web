package org.example.dto;

public class InvoicedClientDTO {
    private int id;
    private String name;
    private float totalInvoicedAmount;

    public InvoicedClientDTO(int id, String name, float totalInvoicedAmount) {
        this.id = id;
        this.name = name;
        this.totalInvoicedAmount = totalInvoicedAmount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getTotalInvoicedAmount() {
        return totalInvoicedAmount;
    }

    @Override
    public String toString() {
        return "(ID: " + id + " | Nombre: " + name + " | Total: " + totalInvoicedAmount + ")";
    }
}
