package com.example.verifyShop.models;

public class Transaction {
    private String id;
    private Integer stock;
    private String name;

    public String getId() {
        return this.id;
    }

    public Integer getStock() {
        return this.stock;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String transactionId) {
        this.id = transactionId;
    }

    public void setStock(Integer type) {
        this.stock = type;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + " Name: " + this.name + " Amount: " + this.stock;
    }

}
