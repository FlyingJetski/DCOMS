package com.company.models;

import com.company.common.Database;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.mongodb.client.result.InsertOneResult;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class Item {
    //features: record grocery items, sales, stock, list available items, registration
    private ObjectId id;
    @BsonProperty(value = "created_at")
    private Date createdAt;
    @BsonProperty(value = "updated_at")
    private Date updatedAt;
    @BsonProperty(value = "name")
    private String name;
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

    public Item setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Item setPrice(double price) {
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
