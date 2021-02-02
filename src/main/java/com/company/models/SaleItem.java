package com.company.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;

public class SaleItem {
    private ObjectId id;
    @BsonProperty(value = "name")
    private String name;
    @BsonProperty(value = "category_id")
    private ObjectId categoryId;
    @BsonProperty(value = "category")
    private Category category;
    @BsonProperty(value = "amount")
    private Integer amount;
    @BsonProperty(value = "price")
    private Double price;

    public SaleItem() {}

    public ObjectId getId() {
        return id;
    }

    public SaleItem setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SaleItem setName(String name) {
        this.name = name;
        return this;
    }

    public ObjectId getCategoryId() {
        return categoryId;
    }

    public SaleItem setCategoryId(ObjectId categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public SaleItem setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public SaleItem setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public SaleItem setPrice(Double price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "SaleItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", category=" + category +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
