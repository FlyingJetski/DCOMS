package com.company.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

public class Sale {
    private ObjectId id;
    @BsonProperty(value = "created_at")
    private Date createdAt;
    @BsonProperty(value = "updated_at")
    private Date updatedAt;
    @BsonProperty(value = "sale_items")
    private ArrayList<SaleItem> saleItems;
    @BsonProperty(value = "total")
    private Double total;

    public Sale() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public ObjectId getId() {
        return id;
    }

    public Sale setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Sale setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Sale setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public ArrayList<SaleItem> getSaleItems() {
        return saleItems;
    }

    public Sale setSaleItems(ArrayList<SaleItem> saleItems) {
        this.saleItems = saleItems;
        return this;
    }

    public Double getTotal() {
        return total;
    }

    public Sale setTotal(Double total) {
        this.total = total;
        return this;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", saleItems=" + saleItems +
                ", total=" + total +
                '}';
    }
}
