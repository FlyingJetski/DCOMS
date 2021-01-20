package com.company.common;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public static void main(String[] args) {
//        String connectionString = System.getProperty("mongodb.uri");
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            List<Object> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db));
//            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
//            databases.forEach(db -> System.out.println(db.toJson()));
        }
    }
}