package com.company.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;

public class Item {
    private ObjectId id;
    @BsonProperty(value = "created_at")
    private Date createdAt;
    @BsonProperty(value = "updated_at")
    private Date updatedAt;
    @BsonProperty(value = "name")
    private String name;
    @BsonProperty(value = "category_id")
    private ObjectId categoryId;
    @BsonProperty(value = "category")
    private Category category;
    @BsonProperty(value = "stock")
    private Integer stock;
    @BsonProperty(value = "price")
    private Double price;
    @BsonProperty(value = "description")
    private String description;

    public Item() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public ObjectId getId() {
        return id;
    }

    public Item setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Item setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Item setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public ObjectId getCategoryId() {
        return categoryId;
    }

    public Item setCategoryId(ObjectId categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public Item setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Integer getStock() {
        return stock;
    }

    public Item setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Item setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", stock=" + stock +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
