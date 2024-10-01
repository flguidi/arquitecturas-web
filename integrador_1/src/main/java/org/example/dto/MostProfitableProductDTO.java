package org.example.dto;

public class MostProfitableProductDTO {
    private int productId;
    private String productName;
    private float profit;

    public MostProfitableProductDTO(int productId, String productName, float profit) {
        this.productId = productId;
        this.productName = productName;
        this.profit = profit;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public float getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return "(ID: " + productId + " | Nombre: " + productName + " | Recaudación: " + profit + ")";
    }
}
