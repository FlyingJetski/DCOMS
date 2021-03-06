package com.company.common;

import com.company.models.*;
import org.bson.types.ObjectId;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteInterface extends Remote {
    // Miscellaneous
    public void resetDatabase() throws RemoteException;

    // User
    public ArrayList<User> getUsers(boolean isAdmin, String searchString, Sort sort, Pagination pagination) throws RemoteException ;

    public User findUserById(ObjectId id) throws RemoteException;

    public ObjectId insertUser(User user) throws RemoteException;

    public boolean updateUser(ObjectId id, User user) throws RemoteException;

    public boolean updateUserPassword(ObjectId id, String oldPassword, String newPassword) throws RemoteException;

    public boolean deleteUser(ObjectId id) throws RemoteException;

    public boolean logIn(String username, String password) throws RemoteException;

    // Category
    public ArrayList<Category> getCategories(String searchString, Sort sort, Pagination pagination) throws RemoteException ;

    public Category findCategoryById(ObjectId id) throws RemoteException;

    public ObjectId insertCategory(Category category) throws RemoteException;

    public boolean updateCategory(ObjectId id, Category category) throws RemoteException;

    public boolean deleteCategory(ObjectId id) throws RemoteException;

    // Item
    public ArrayList<Item> getItems(String searchString, Sort sort, Pagination pagination) throws RemoteException ;

    public Item findItemById(ObjectId id) throws RemoteException;

    public ObjectId insertItem(Item item) throws RemoteException;

    public boolean updateItem(ObjectId id, Item item) throws RemoteException;

    public boolean deleteItem(ObjectId id) throws RemoteException;
}