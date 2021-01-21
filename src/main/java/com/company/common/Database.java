package com.company.common;

import com.company.models.Category;
import com.company.models.Item;
import com.company.models.User;
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
    public static MongoCollection<User> users;
    public static MongoCollection<Category> categories;
    public static MongoCollection<Item> items;

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
        users = db.getCollection("users", User.class);
        categories = db.getCollection("categories", Category.class);
        items = db.getCollection("items", Item.class);
    }
}