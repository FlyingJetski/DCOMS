package com.company.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;

public class Activity {
    private ObjectId id;
    @BsonProperty(value = "created_at")
    private Date createdAt;
    @BsonProperty(value = "actor_id")
    private ObjectId actorId;
    @BsonProperty(value = "actor")
    private User actor;
    @BsonProperty(value = "description")
    private String description;

    public ObjectId getId() {
        return id;
    }

    public Activity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Activity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ObjectId getActorId() {
        return actorId;
    }

    public Activity setActorId(ObjectId actorId) {
        this.actorId = actorId;
        return this;
    }

    public User getActor() {
        return actor;
    }

    public Activity setActor(User actor) {
        this.actor = actor;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Activity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", actor=" + actor +
                ", description='" + description + '\'' +
                '}';
    }
}
