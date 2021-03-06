package com.company;

import com.company.common.*;
import com.company.models.*;
import com.company.utility.*;
import org.bson.types.ObjectId;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Server extends UnicastRemoteObject implements RemoteInterface {
    public Server() throws RemoteException {
        super();
    }

    // Miscellaneous
    @Override
    public void resetDatabase() throws RemoteException {
        Database.resetDatabase();
    }

    // User
    @Override
    public ArrayList<User> getUsers(boolean isAdmin, String searchString, Sort sort, Pagination pagination) throws RemoteException {
        return UserUtility.getUsers(isAdmin, searchString, sort, pagination);
    }

    @Override
    public User findUserById(ObjectId id) throws RemoteException {
        return UserUtility.findUserById(id);
    }

    @Override
    public ObjectId insertUser(User user) throws RemoteException {
        return UserUtility.insertUser(user);
    }

    @Override
    public boolean updateUser(ObjectId id, User user) throws RemoteException {
        return UserUtility.updateUser(id, user);
    }

    @Override
    public boolean updateUserPassword(ObjectId id, String oldPassword, String newPassword) throws RemoteException {
        return UserUtility.updateUserPassword(id, oldPassword, newPassword);
    }

    @Override
    public boolean deleteUser(ObjectId id) throws RemoteException {
        return UserUtility.deleteUser(id);
    }

    @Override
    public boolean logIn(String username, String password) throws RemoteException {
        return UserUtility.logIn(username, password);
    }

    // Category
    @Override
    public ArrayList<Category> getCategories(String searchString, Sort sort, Pagination pagination) throws RemoteException {
        return CategoryUtility.getCategories(searchString, sort, pagination);
    }

    @Override
    public Category findCategoryById(ObjectId id) throws RemoteException {
        return CategoryUtility.findCategoryById(id);
    }

    @Override
    public ObjectId insertCategory(Category category) throws RemoteException {
        return CategoryUtility.insertCategory(category);
    }

    @Override
    public boolean updateCategory(ObjectId id, Category category) throws RemoteException {
        return CategoryUtility.updateCategory(id, category);
    }

    @Override
    public boolean deleteCategory(ObjectId id) throws RemoteException {
        return CategoryUtility.deleteCategory(id);
    }

    // Item
    @Override
    public ArrayList<Item> getItems(String searchString, Sort sort, Pagination pagination) throws RemoteException {
        return ItemUtility.getItems(searchString, sort, pagination);
    }

    @Override
    public Item findItemById(ObjectId id) throws RemoteException {
        return ItemUtility.findItemById(id);
    }

    @Override
    public ObjectId insertItem(Item item) throws RemoteException {
        return ItemUtility.insertItem(item);
    }

    @Override
    public boolean updateItem(ObjectId id, Item item) throws RemoteException {
        return ItemUtility.updateItem(id, item);
    }

    @Override
    public boolean deleteItem(ObjectId id) throws RemoteException {
        return ItemUtility.deleteItem(id);
    }
}