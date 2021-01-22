package com.company.models;

import org.bson.conversions.Bson;

import java.util.ArrayList;

import static com.mongodb.client.model.Aggregates.skip;
import static com.mongodb.client.model.Aggregates.limit;

public class Pagination {
    private Integer start;
    private Integer total;

    public Pagination(Integer start, Integer total) {
        this.start = start;
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public static ArrayList<Bson> paginate(ArrayList<Bson> aggregation, Pagination pagination) {
        if (pagination == null) {
            return aggregation;
        }
        if (pagination.start != null) {
            aggregation.add(skip(pagination.start));
        }
        if (pagination.total != null) {
            aggregation.add(limit(pagination.total));
        }
        return aggregation;
    }
}
