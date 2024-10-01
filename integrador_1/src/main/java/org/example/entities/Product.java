package org.example.entities;

public class Product {
    private int id;
    private String name;
    private Float value;

    public Product(int id, String name, Float value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "(ID: " + id + " | Nombre: " + name + " | Valor: " + value + ")";
    }
}
