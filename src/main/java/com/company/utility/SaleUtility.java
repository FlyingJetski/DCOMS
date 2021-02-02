package com.company.utility;

import com.company.common.Database;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.models.Sale;
import com.company.models.Pagination;
import com.company.models.SaleItem;
import com.company.models.Sort;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Aggregates.lookup;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class SaleUtility {
    private SaleUtility() {
        throw new UnsupportedOperationException();
    }

    public static ArrayList<Sale> getSales(String searchString, Sort sort, Pagination pagination) {
        ArrayList<Bson> aggregation = new ArrayList<Bson>();
        aggregation.add(lookup("categories", "sale_sales.category_id", "_id", "sale_sales.category"));
        if (searchString != null) {
            aggregation.add(or(
                    regex("sale_sales.name", searchString),
                    regex("sale_sales.category.name", searchString)
            ));
        }
        if (sort != null) {
            switch (sort.getOption()) {
                case BY_DATE:
                    if (sort.isAsc()) {
                        aggregation.add(sort(Sorts.ascending("created_at")));
                    } else {
                        aggregation.add(sort(Sorts.descending("created_at")));
                    }
                    break;
                case BY_PRICE:
                    if (sort.isAsc()) {
                        aggregation.add(sort(Sorts.ascending("total")));
                    } else {
                        aggregation.add(sort(Sorts.descending("total")));
                    }
                    break;
            }
        }
        aggregation = Pagination.paginate(aggregation, pagination);
        return Database.sales.aggregate(aggregation).into(new ArrayList<Sale>());
    }

    public static Sale findSaleById(ObjectId id) throws NotFoundException {
        ArrayList<Bson> aggregation = new ArrayList<Bson>();
        aggregation.add(match(eq("_id", id)));
        aggregation.add(lookup("categories", "category_id", "_id", "category"));
        Sale sale = Database.sales.aggregate(aggregation).into(new ArrayList<Sale>()).get(0);
        if (sale == null) {
            throw new NotFoundException("Sale ID");
        }
        return sale;
    }

    private static boolean checkRequiredFieldsSale(Sale sale) {
        if (sale.getSaleItems() == null || sale.getSaleItems().size() == 0) {
            return false;
        }
        return true;
    }

    private static double generateTotalPrice(ArrayList<SaleItem> saleItems) {
        double totalPrice = 0.0;
        for (SaleItem saleItem : saleItems) {
            totalPrice += saleItem.getAmount() * saleItem.getPrice();
        }
        return totalPrice;
    }

    public static ObjectId insertSale(Sale sale) throws DuplicateException, MandatoryException {
        if (!checkRequiredFieldsSale(sale)) {
            throw new MandatoryException("sale items must be filled");
        }
        sale.setTotal(generateTotalPrice(sale.getSaleItems()));
        InsertOneResult result = Database.sales.insertOne(sale);
        return result.getInsertedId().asObjectId().getValue();
    }

    private static ArrayList<Bson> generateUpdates(ArrayList<Bson> updates, Sale sale) {
        if (sale.getSaleItems() == null || sale.getSaleItems().size() == 0) {
            updates.add(set("sale_items", sale.getSaleItems()));
            updates.add(set("total", generateTotalPrice(sale.getSaleItems())));
        }
        return updates;
    }

    public static boolean updateSale(ObjectId id, Sale sale) throws NotFoundException {
        Bson filter = eq("_id", id);
        ArrayList<Bson> updates = new ArrayList<Bson>();
        updates.add(set("updated_at", new Date()));
        updates = generateUpdates(updates, sale);
        if (Database.sales.updateOne(filter, updates).getModifiedCount() >= 1 ? true : false) {
            return true;
        }
        throw new NotFoundException("Sale ID");
    }

    public static boolean deleteSale(ObjectId id) throws NotFoundException {
        Bson filter = eq("_id", id);
        if (Database.sales.deleteOne(filter).getDeletedCount() >= 1 ? true : false) {
            return true;
        }
        throw new NotFoundException("Sale ID");
    }
}
