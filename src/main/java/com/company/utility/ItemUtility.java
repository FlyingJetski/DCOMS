package com.company.utility;

import com.company.common.Database;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.models.Item;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class ItemUtility {
    private ItemUtility() {
        throw new UnsupportedOperationException();
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
        if (item.getName() == null || item.getName().equals("")) {
            return false;
        }
        if (item.getCategory() == null) {
            return false;
        }
        if (item.getStock() == null) {
            item.setStock(0);
        }
        if (item.getPrice() == null) {
            item.setPrice(0.0);
        }
        return true;
    }

    public static ObjectId insertItem(Item item) throws DuplicateException, MandatoryException {
        if (findItemByName(item.getName()) != null) {
            throw new DuplicateException("The item name");
        }
        if (!checkRequiredFieldsItem(item)) {
            throw new MandatoryException("item name and category must be filled");
        }
        InsertOneResult result = Database.items.insertOne(item);
        return result.getInsertedId().asObjectId().getValue();
    }

    private static ArrayList<Bson> generateUpdates(ArrayList<Bson> updates, Item item) {
        if (item.getName() != null && !item.getName().equals("")) {
            updates.add(set("name", item.getName()));
        }
        if (item.getCategory() != null) {
            updates.add(set("category", item.getCategory()));
        }
        if (item.getStock() != null) {
            updates.add(set("stock", item.getStock()));
        }
        if (item.getPrice() != null) {
            updates.add(set("price", item.getPrice()));
        }
        if (item.getDescription() != null) {
            updates.add(set("description", item.getDescription()));
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
