package com.example.shoppingcart.Model;

public class ProductData {
    private Integer stock;
    private String productName;
    private String id;

    public String getId() {
        return this.id;
    }

    public Integer getStock() {
        return this.stock;
    }

    public String getName() {
        return this.productName;
    }

    public void setId(String transactionId) {
        this.id = transactionId;
    }

    public void setStock(Integer type) {
        this.stock = type;
    }
    public void setName(String name) {
        this.productName = name;
    }


}
