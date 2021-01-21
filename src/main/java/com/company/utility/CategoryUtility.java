package com.company.utility;

import com.company.common.Database;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.models.Category;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class CategoryUtility {
    private CategoryUtility() {
        throw new UnsupportedOperationException();
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
        if (category.getName() == null || category.getName().equals("")) {
            return false;
        }
        return true;
    }

    public static ObjectId insertCategory(Category category) throws DuplicateException, MandatoryException {
        if (findCategoryByName(category.getName()) != null) {
            throw new DuplicateException("The category name");
        }
        if (!checkRequiredFieldsCategory(category)) {
            throw new MandatoryException("category name must be filled");
        }
        InsertOneResult result = Database.categories.insertOne(category);
        return result.getInsertedId().asObjectId().getValue();
    }

    private static ArrayList<Bson> generateUpdates(ArrayList<Bson> updates, Category category) {
        if (category.getName() != null && !category.getName().equals("")) {
            updates.add(set("name", category.getName()));
        }
        if (category.getDescription() != null) {
            updates.add(set("description", category.getDescription()));
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