package com.company.models;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Date;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Sample {
    public static void main(String[] args) {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .codecRegistry(codecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(clientSettings);
        MongoDatabase db = mongoClient.getDatabase("dcoms");
        MongoCollection<Admin> adminsCollection = db.getCollection("admins", Admin.class);

        Admin admin = new Admin(new Date());
        adminsCollection.insertOne(admin);

        Admin findAdmin = adminsCollection.find(eq("_id", new ObjectId("6007ff6f183d971f09c616da"))).first();
        System.out.println(findAdmin);

//            Document admin = new Document("_id", new ObjectId());
//            admin.append("first_name", "Joshua");
//            admin.append("username", "joshua");
//            admin.append("password", "password");
//            adminsCollection.insertOne(admin);
//
//            Document insertedAdmin = adminsCollection.find(new Document("first_name", "Joshua")).first();
//            System.out.println(insertedAdmin.toJson());
//
//            Bson filter = eq("first_name", "Joshua");
//            Bson update1 = set("username", "notjoshua");
////            Bson update2 = set("first_name", "Not");
//            Bson update3 = rename("last_name", "new_first_name");
//            UpdateResult upres = adminsCollection.updateOne(filter, combine(update1, update3));
//            System.out.println(upres);
//
//            DeleteResult delres = adminsCollection.deleteMany(filter);
//            System.out.println(delres);
    }
}