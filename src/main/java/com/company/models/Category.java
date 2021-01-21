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

    public static Category findCategoryById(ObjectId id) throws NotFoundException {
        Bson filter = eq("_id", id);
        Category category = Database.categories.find(filter).first();
        if (category == null) {
            throw new NotFoundException("Category ID");
        }
        return category;
    }

    private static Category findCategoryByName(String categoryname) {
        Bson filter = eq("name", categoryname);
        return Database.categories.find(filter).first();
    }

    private static boolean checkRequiredFieldsCategory(Category category) {
        if (category.name == null || category.name.equals("")) {
            return false;
        }
        return true;
    }

    public static ObjectId insertCategory(Category category) throws DuplicateException, MandatoryException {
        if (findCategoryByName(category.name) != null) {
            throw new DuplicateException("The category name");
        }
        if (!checkRequiredFieldsCategory(category)) {
            throw new MandatoryException("category name must be filled");
        }
        InsertOneResult result = Database.categories.insertOne(category);
        return result.getInsertedId().asObjectId().getValue();
    }

    private static ArrayList<Bson> generateUpdates(ArrayList<Bson> updates, Category category) {
        if (category.name != null && !category.name.equals("")) {
            updates.add(set("name", category.name));
        }
        if (category.description != null) {
            updates.add(set("description", category.description));
        }
        return updates;
    }

    public static boolean updateCategory(ObjectId id, Category category) throws NotFoundException {
        Bson filter = eq("_id", id);
        ArrayList<Bson> updates = new ArrayList<Bson>();
        updates.add(set("updated_at", new Date()));
        updates = generateUpdates(updates, category);
        if (Database.categories.updateOne(filter, updates).getModifiedCount() >= 1 ? true : false) {
            return true;
        }
        throw new NotFoundException("Category ID");
    }

    public static boolean deleteCategory(ObjectId id) throws NotFoundException {
        Bson filter = eq("_id", id);
        if (Database.categories.deleteOne(filter).getDeletedCount() >= 1 ? true : false) {
            return true;
        }
        throw new NotFoundException("Category ID");
    }
}
