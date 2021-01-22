package com.company.utility;

import com.company.common.Authentication;
import com.company.common.Database;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.common.exceptions.NotMatchException;
import com.company.models.Pagination;
import com.company.models.Sort;
import com.company.models.User;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;

public class UserUtility {
    private UserUtility() {
        throw new UnsupportedOperationException();
    }

    public static ArrayList<User> getUsers(Boolean isAdmin, String searchString, Sort sort, Pagination pagination) {
        ArrayList<Bson> aggregation = new ArrayList<Bson>();
        aggregation.add(match(eq("is_admin", isAdmin)));
        if (searchString != null) {
            aggregation.add(or(
                    regex("first_name", searchString),
                    regex("last_name", searchString),
                    regex("address", searchString),
                    regex("phone_number", searchString)
            ));
        }
        if (sort != null) {
            if (sort.isAsc()) {
                aggregation.add(sort(Sorts.ascending("first_name")));
            } else {
                aggregation.add(sort(Sorts.descending("first_name")));
            }
        }
        aggregation = Pagination.paginate(aggregation, pagination);
        return Database.users.aggregate(aggregation).into(new ArrayList<User>());
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
        if (user.getUsername() == null || user.getUsername().equals("")) {
            return false;
        }
        if (user.getPassword() == null || user.getPassword().equals("")) {
            return false;
        }
        if (user.getFirstName() == null || user.getFirstName().equals("")) {
            return false;
        }
        if (user.getIcPassport() == null || user.getIcPassport().equals("")) {
            return false;
        }
        return true;
    }

    public static ObjectId insertUser(User user) throws DuplicateException, MandatoryException {
        if (findUserByUsername(user.getUsername()) != null) {
            throw new DuplicateException("The username");
        }
        if (!checkRequiredFieldsUser(user)) {
            throw new MandatoryException("username, password, first name, and IC/passport must be filled");
        }

        user.setPassword(Authentication.hash(user.getPassword()));
        InsertOneResult result = Database.users.insertOne(user);
        return result.getInsertedId().asObjectId().getValue();
    }

    private static ArrayList<Bson> generateUpdates(ArrayList<Bson> updates, User user) {
        if (user.getUsername() != null && !user.getUsername().equals("")) {
            updates.add(set("username", user.getUsername()));
        }
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            updates.add(set("password", user.getPassword()));
        }
        if (user.getFirstName() != null && !user.getFirstName().equals("")) {
            updates.add(set("first_name", user.getFirstName()));
        }
        if (user.getLastName() != null) {
            updates.add(set("last_name", user.getLastName()));
        }
        if (user.getIcPassport() != null && !user.getIcPassport().equals("")) {
            updates.add(set("ic_passport", user.getIcPassport()));
        }
        if (user.getAddress() != null) {
            updates.add(set("address", user.getAddress()));
        }
        if (user.getPhoneNumber() != null) {
            updates.add(set("phone_number", user.getPhoneNumber()));
        }
        return updates;
    }

    public static boolean updateUser(ObjectId id, User user) throws NotFoundException {
        Bson filter = eq("_id", id);
        ArrayList<Bson> updates = new ArrayList<Bson>();
        updates.add(set("updated_at", new Date()));
        updates.add(set("admin", user.isAdmin()));
        updates = generateUpdates(updates, user);
        if (Database.users.updateOne(filter, updates).getModifiedCount() >= 1 ? true : false) {
            return true;
        }
        throw new NotFoundException("User ID");
    }

    public static boolean updateUserPassword(ObjectId id, String oldPassword, String newPassword) throws NotFoundException, NotMatchException {
        Bson filter = eq("_id", id);
        User user = findUserById(id);
        if (!Authentication.match(oldPassword, user.getPassword())) {
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
        if (!Authentication.match(password, user.getPassword())) {
            throw new NotFoundException("User with specified username and password");
        }
        return true;
    }
}
