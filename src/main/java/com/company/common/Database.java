package com.company.common;

import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.models.*;
import com.company.utility.UserUtility;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Database {
    private static MongoDatabase db;
    private static boolean initialized;
    public static MongoCollection<Activity> activities;
    public static MongoCollection<User> users;
    public static MongoCollection<Category> categories;
    public static MongoCollection<Item> items;
    public static MongoCollection<Sale> sales;

    public static void initialize() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .codecRegistry(codecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(clientSettings);
        MongoDatabase db = mongoClient.getDatabase("dcoms");
        Database.db = db;
        activities = db.getCollection("activities", Activity.class);
        users = db.getCollection("users", User.class);
        categories = db.getCollection("categories", Category.class);
        items = db.getCollection("items", Item.class);
        sales = db.getCollection("sales", Sale.class);
        Database.initialized = true;
    }

    public static void resetDatabase() throws DuplicateException, MandatoryException {
        if (!Database.initialized) {
            Database.initialize();
        }

        users.drop();
        db.createCollection("users");

        User admin = new User()
                .setAdmin(true)
                .setUsername("admin")
                .setPassword("password")
                .setFirstName("Admin")
                .setIcPassport("ADMIN000");
        UserUtility.insertUser(admin);

        User user = new User()
                .setUsername("sales")
                .setPassword("password")
                .setFirstName("Sales")
                .setIcPassport("SALES00");
        UserUtility.insertUser(user);

        activities.drop();
        db.createCollection("activities");
        categories.drop();
        db.createCollection("categories");
        items.drop();
        db.createCollection("items");
        sales.drop();
        db.createCollection("sales");
    }
}