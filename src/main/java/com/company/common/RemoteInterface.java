package com.company.common;

import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.common.exceptions.NotMatchException;
import com.company.models.*;
import org.bson.types.ObjectId;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteInterface extends Remote {
    // Miscellaneous
    public void resetDatabase() throws RemoteException, DuplicateException, MandatoryException;

    // User
    public ArrayList<User> getUsers(boolean isAdmin, String searchString, Sort sort, Pagination pagination) throws RemoteException ;

    public User findUserById(ObjectId id) throws RemoteException, NotFoundException;

    public ObjectId insertUser(User user) throws RemoteException, DuplicateException, MandatoryException;

    public boolean updateUser(ObjectId id, User user) throws RemoteException, NotFoundException;

    public boolean updateUserPassword(ObjectId id, String oldPassword, String newPassword)
            throws RemoteException, NotFoundException, NotMatchException;

    public boolean deleteUser(ObjectId id) throws RemoteException, NotFoundException;

    public boolean logIn(String username, String password) throws RemoteException, NotFoundException;

    // Category
    public ArrayList<Category> getCategories(String searchString, Sort sort, Pagination pagination) throws RemoteException ;

    public Category findCategoryById(ObjectId id) throws RemoteException, NotFoundException;

    public ObjectId insertCategory(Category category) throws RemoteException, DuplicateException, MandatoryException;

    public boolean updateCategory(ObjectId id, Category category) throws RemoteException, NotFoundException;

    public boolean deleteCategory(ObjectId id) throws RemoteException, NotFoundException;

    // Item
    public ArrayList<Item> getItems(String searchString, Sort sort, Pagination pagination) throws RemoteException ;

    public Item findItemById(ObjectId id) throws RemoteException, NotFoundException;

    public ObjectId insertItem(Item item) throws RemoteException, DuplicateException, MandatoryException;

    public boolean updateItem(ObjectId id, Item item) throws RemoteException, NotFoundException;

    public boolean deleteItem(ObjectId id) throws RemoteException, NotFoundException;
}