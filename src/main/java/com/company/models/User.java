package com.company.models;

import com.company.common.Authentication;
import com.company.common.Database;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.common.exceptions.NotMatchException;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class User {
    private ObjectId id;
    @BsonProperty(value = "created_at")
    private Date createdAt;
    @BsonProperty(value = "updated_at")
    private Date updatedAt;
    @BsonProperty(value = "admin")
    private boolean admin;
    @BsonProperty(value = "username")
    private String username;
    @BsonProperty(value = "password")
    private String password;
    @BsonProperty(value = "first_name")
    private String firstName;
    @BsonProperty(value = "last_name")
    private String lastName;
    @BsonProperty(value = "ic_passport")
    private String icPassport;
    @BsonProperty(value = "address")
    private String address;
    @BsonProperty(value = "phone_number")
    private String phoneNumber;

    public User() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public ObjectId getId() {
        return id;
    }

    public User setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public User setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public User setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public boolean isAdmin() {
        return admin;
    }

    public User setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        password = Authentication.hash(password);
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getIcPassport() {
        return icPassport;
    }

    public User setIcPassport(String icPassport) {
        this.icPassport = icPassport;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", admin=" + admin +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", icPassport='" + icPassport + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public static User findUserById(ObjectId id) throws NotFoundException {
        Bson filter = eq("_id", id);
        User user = Database.users.find(filter).first();
        if (user == null) {
            throw new NotFoundException("User ID");
        }
        return user;
    }

    private static User findUserByUsername(String username) {
        Bson filter = eq("username", username);
        return Database.users.find(filter).first();
    }

    private static boolean checkRequiredFieldsUser(User user) {
        if (user.username == null || user.username.equals("")) {
            return false;
        }
        if (user.password == null || user.password.equals("")) {
            return false;
        }
        if (user.firstName == null || user.firstName.equals("")) {
            return false;
        }
        if (user.icPassport == null || user.icPassport.equals("")) {
            return false;
        }
        return true;
    }

    public static ObjectId insertUser(User user) throws DuplicateException, MandatoryException {
        if (findUserByUsername(user.username) != null) {
            throw new DuplicateException("The username");
        }
        if (!checkRequiredFieldsUser(user)) {
            throw new MandatoryException("username, password, first name, and IC/passport must be filled");
        }
        InsertOneResult result = Database.users.insertOne(user);
        return result.getInsertedId().asObjectId().getValue();
    }

    private static ArrayList<Bson> generateUpdates(ArrayList<Bson> updates, User user) {
        if (user.username != null && !user.username.equals("")) {
            updates.add(set("username", user.username));
        }
        if (user.password != null && !user.password.equals("")) {
            updates.add(set("password", user.password));
        }
        if (user.firstName != null && !user.firstName.equals("")) {
            updates.add(set("first_name", user.firstName));
        }
        if (user.lastName != null) {
            updates.add(set("last_name", user.lastName));
        }
        if (user.icPassport != null && !user.icPassport.equals("")) {
            updates.add(set("ic_passport", user.icPassport));
        }
        if (user.address != null) {
            updates.add(set("address", user.address));
        }
        if (user.phoneNumber != null) {
            updates.add(set("phone_number", user.phoneNumber));
        }
        return updates;
    }

    public static boolean updateUser(ObjectId id, User user) throws NotFoundException {
        Bson filter = eq("_id", id);
        ArrayList<Bson> updates = new ArrayList<Bson>();
        updates.add(set("updated_at", new Date()));
        updates.add(set("admin", user.admin));
        updates = generateUpdates(updates, user);
        if (Database.users.updateOne(filter, updates).getModifiedCount() >= 1 ? true : false) {
            return true;
        }
        throw new NotFoundException("User ID");
    }

    public static boolean updateUserPassword(ObjectId id, String oldPassword, String newPassword) throws NotFoundException, NotMatchException {
        Bson filter = eq("_id", id);
        User user = findUserById(id);
        if (!Authentication.match(oldPassword, user.password)) {
            throw new NotMatchException("Old password", "saved password");
        }
        Bson update = set("password", Authentication.hash(newPassword));
        return Database.users.updateOne(filter, update).getModifiedCount() >= 1 ? true : false;
    }

    public static boolean deleteUser(ObjectId id) throws NotFoundException {
        Bson filter = eq("_id", id);
        if (Database.users.deleteOne(filter).getDeletedCount() >= 1 ? true : false) {
            return true;
        }
        throw new NotFoundException("User ID");
    }

    public static boolean logIn(String username, String password) throws NotFoundException {
        User user = findUserByUsername(username);
        if (!Authentication.match(password, user.password)) {
            throw new NotFoundException("User with specified username and password");
        }
        return true;
    }
}