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

    public static Item findItemById(ObjectId id) throws NotFoundException {
        Bson filter = eq("_id", id);
        Item item = Database.items.find(filter).first();
        if (item == null) {
            throw new NotFoundException("Item ID");
        }
        return item;
    }

    private static Item findItemByName(String itemname) {
        Bson filter = eq("name", itemname);
        return Database.items.find(filter).first();
    }

    private static boolean checkRequiredFieldsItem(Item item) {
        if (item.name == null || item.name.equals("")) {
            return false;
        }
        if (item.category == null) {
            return false;
        }
        if (item.stock == null) {
            item.stock = 0;
        }
        if (item.price == null) {
            item.price = 0.0;
        }
        return true;
    }

    public static ObjectId insertItem(Item item) throws DuplicateException, MandatoryException {
        if (findItemByName(item.name) != null) {
            throw new DuplicateException("The item name");
        }
        if (!checkRequiredFieldsItem(item)) {
            throw new MandatoryException("item name and category must be filled");
        }
        InsertOneResult result = Database.items.insertOne(item);
        return result.getInsertedId().asObjectId().getValue();
    }

    private static ArrayList<Bson> generateUpdates(ArrayList<Bson> updates, Item item) {
        if (item.name != null && !item.name.equals("")) {
            updates.add(set("name", item.name));
        }
        if (item.category != null) {
            updates.add(set("category", item.category));
        }
        if (item.name != null && !item.name.equals("")) {
            updates.add(set("name", item.name));
        }
        if (item.stock != null) {
            updates.add(set("stock", item.stock));
        }
        if (item.price != null) {
            updates.add(set("price", item.price));
        }
        if (item.description != null) {
            updates.add(set("description", item.description));
        }
        return updates;
    }

    public static boolean updateItem(ObjectId id, Item item) throws NotFoundException {
        Bson filter = eq("_id", id);
        ArrayList<Bson> updates = new ArrayList<Bson>();
        updates.add(set("updated_at", new Date()));
        updates = generateUpdates(updates, item);
        if (Database.items.updateOne(filter, updates).getModifiedCount() >= 1 ? true : false) {
            return true;
        }
        throw new NotFoundException("Item ID");
    }

    public static boolean deleteItem(ObjectId id) throws NotFoundException {
        Bson filter = eq("_id", id);
        if (Database.items.deleteOne(filter).getDeletedCount() >= 1 ? true : false) {
            return true;
        }
        throw new NotFoundException("Item ID");
    }
}
