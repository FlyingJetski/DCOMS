package com.company.common;

import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.common.exceptions.NotMatchException;
import com.company.models.Category;
import com.company.models.Item;
import com.company.models.User;
import org.bson.types.ObjectId;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
    // User
    public User findUserById(ObjectId id) throws RemoteException, NotFoundException;

    public ObjectId insertUser(User user) throws RemoteException, DuplicateException, MandatoryException;

    public boolean updateUser(ObjectId id, User user) throws RemoteException, NotFoundException;

    public boolean updateUserPassword(ObjectId id, String oldPassword, String newPassword)
            throws RemoteException, NotFoundException, NotMatchException;

    public boolean deleteUser(ObjectId id) throws RemoteException, NotFoundException;

    public boolean logIn(String username, String password) throws RemoteException, NotFoundException;

    // Category
    public Category findCategoryById(ObjectId id) throws NotFoundException;

    public ObjectId insertCategory(Category category) throws DuplicateException, MandatoryException;

    public boolean updateCategory(ObjectId id, Category category) throws NotFoundException;

    public boolean deleteCategory(ObjectId id) throws NotFoundException;

    // Item
    public Item findItemById(ObjectId id) throws NotFoundException;

    public ObjectId insertItem(Item item) throws DuplicateException, MandatoryException;

    public boolean updateItem(ObjectId id, Item item) throws NotFoundException;

    public boolean deleteItem(ObjectId id) throws NotFoundException;
}