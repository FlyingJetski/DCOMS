package com.company.models;

import com.company.common.Authentication;
import com.company.common.Database;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
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
    public static void main(String[] args) throws DuplicateException, MandatoryException {
        Database.initialize();

//        Item item = new Item()
//                .setName("asd")
//                .setCategory(new Category());
//        System.out.println(item.getStock());
//
//        Item.checkRequiredFieldsItem(item);
//        System.out.println(item.getStock());

//        User user = new User()
//                .setUsername("joshua")
//                .setPassword("password")
//                .setFirstName("Joshua")
//                .setIcPassport("B9554665");
//        ObjectId insertedId = User.insertUser(user);
//
//        try {
//            User.updateUserPassword(new ObjectId(), "passwor", "pass");
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//        Admin admin = new Admin().setPassword("password");
////        Database.admins.insertOne(admin);
//        System.out.println(Authentication.match("password", "$s0$41010$kBCURPk05PcIgnIhPmKq8A==$ejSSdMYzmTcxA8gzif8dG+OdZgwU8fhVcqN/yq57Tms="));

//        Bson filter = eq("_id", new ObjectId("6007ffaed3c75001ef69d548"));
//        DeleteResult result = Database.admins.deleteOne(filter);
//        System.out.println(result.getDeletedCount() >= 1 ? true : false);


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