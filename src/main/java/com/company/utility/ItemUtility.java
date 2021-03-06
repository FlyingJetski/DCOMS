package com.company.utility;

import com.company.common.Database;
import com.company.models.Item;
import com.company.models.Pagination;
import com.company.models.Sort;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Updates.set;

public class ItemUtility {
    private ItemUtility() {
        throw new UnsupportedOperationException();
    }

    public static ArrayList<Item> getItems(String searchString, Sort sort, Pagination pagination) {
        ArrayList<Bson> aggregation = new ArrayList<Bson>();
        aggregation.add(lookup("categories", "category_id", "_id", "category"));
        if (searchString != null) {
            aggregation.add(or(
                    regex("name", searchString),
                    regex("category.name", searchString),
                    regex("stock", searchString),
                    regex("price", searchString),
                    regex("description", searchString)
            ));
        }
        if (sort != null) {
            switch (sort.getOption()) {
                case BY_NAME:
                    if (sort.isAsc()) {
                        aggregation.add(sort(Sorts.ascending("name")));
                    } else {
                        aggregation.add(sort(Sorts.descending("name")));
                    }
                    break;
                case BY_CATEGORY:
                    if (sort.isAsc()) {
                        aggregation.add(sort(Sorts.ascending("category.name")));
                    } else {
                        aggregation.add(sort(Sorts.descending("category.name")));
                    }
                    break;
                case BY_STOCK:
                    if (sort.isAsc()) {
                        aggregation.add(sort(Sorts.ascending("stock")));
                    } else {
                        aggregation.add(sort(Sorts.descending("stock")));
                    }
                    break;
                case BY_PRICE:
                    if (sort.isAsc()) {
                        aggregation.add(sort(Sorts.ascending("price")));
                    } else {
                        aggregation.add(sort(Sorts.descending("price")));
                    }
                    break;
                case BY_DESC:
                    if (sort.isAsc()) {
                        aggregation.add(sort(Sorts.ascending("description")));
                    } else {
                        aggregation.add(sort(Sorts.descending("description")));
                    }
                    break;
            }
        }
        aggregation = Pagination.paginate(aggregation, pagination);
        return Database.items.aggregate(aggregation).into(new ArrayList<Item>());
    }

    public static Item findItemById(ObjectId id) {
        ArrayList<Bson> aggregation = new ArrayList<Bson>();
        aggregation.add(match(eq("_id", id)));
        aggregation.add(lookup("categories", "category_id", "_id", "category"));
        Item item = Database.items.aggregate(aggregation).into(new ArrayList<Item>()).get(0);
        return item;
    }

    private static Item findItemByName(String name) {
        Bson filter = eq("name", name);
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

    public static ObjectId insertItem(Item item) {
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

    public static boolean updateItem(ObjectId id, Item item) {
        Bson filter = eq("_id", id);
        ArrayList<Bson> updates = new ArrayList<Bson>();
        updates.add(set("updated_at", new Date()));
        updates = generateUpdates(updates, item);
        if (Database.items.updateOne(filter, updates).getModifiedCount() >= 1 ? true : false) {
            return true;
        }
        return false;
    }

    public static boolean deleteItem(ObjectId id) {
        Bson filter = eq("_id", id);
        if (Database.items.deleteOne(filter).getDeletedCount() >= 1 ? true : false) {
            return true;
        }
        return false;
    }
}
