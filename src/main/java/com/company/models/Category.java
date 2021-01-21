package com.company.models;

import com.company.common.Authentication;
import com.company.common.Database;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.common.exceptions.NotMatchException;
import com.mongodb.client.result.InsertOneResult;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class Category {
    //category: fruits, vegetables, dairy products, cereals
    private ObjectId id;
    @BsonProperty(value = "created_at")
    private Date createdAt;
    @BsonProperty(value = "updated_at")
    private Date updatedAt;
    @BsonProperty(value = "name")
    private String name;
    @BsonProperty(value = "description")
    private String description;

    public Category() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public ObjectId getId() {
        return id;
    }

    public Category setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Category setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Category setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
