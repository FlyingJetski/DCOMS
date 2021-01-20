package com.company.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

public class Admin {
    private ObjectId id;
    @BsonProperty(value = "created_at")
    private Date created_at;

    public Admin() {}

    public Admin(Date created_at) {
        this.created_at = created_at;
    }

    public ObjectId getId() {
        return id;
    }

    public Admin setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Admin setCreated_at(Date created_at) {
        this.created_at = created_at;
        return this;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", created_at=" + created_at +
                '}';
    }
}
