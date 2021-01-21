package com.company;

import com.company.common.*;
import com.company.utility.*;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.common.exceptions.NotMatchException;
import com.company.models.Category;
import com.company.models.Item;
import com.company.models.User;
import org.bson.types.ObjectId;

import java.rmi.*;
import java.rmi.server.*;

@SuppressWarnings("serial")
public class Server extends UnicastRemoteObject implements RemoteInterface {
    public Server() throws RemoteException {
        super();
    }

    // User
    @Override
    public User findUserById(ObjectId id) throws RemoteException, NotFoundException {
        return UserUtility.findUserById(id);
    }

    @Override
    public ObjectId insertUser(User user) throws RemoteException, DuplicateException, MandatoryException {
        return UserUtility.insertUser(user);
    }

    @Override
    public boolean updateUser(ObjectId id, User user) throws RemoteException, NotFoundException {
        return UserUtility.updateUser(id, user);
    }

    @Override
    public boolean updateUserPassword(ObjectId id, String oldPassword, String newPassword) throws RemoteException, NotFoundException, NotMatchException {
        return UserUtility.updateUserPassword(id, oldPassword, newPassword);
    }

    @Override
    public boolean deleteUser(ObjectId id) throws RemoteException, NotFoundException {
        return UserUtility.deleteUser(id);
    }

    @Override
    public boolean logIn(String username, String password) throws RemoteException, NotFoundException {
        return UserUtility.logIn(username, password);
    }

    // Category
    @Override
    public Category findCategoryById(ObjectId id) throws RemoteException, NotFoundException {
        return CategoryUtility.findCategoryById(id);
    }

    @Override
    public ObjectId insertCategory(Category category) throws RemoteException, DuplicateException, MandatoryException {
        return CategoryUtility.insertCategory(category);
    }

    @Override
    public boolean updateCategory(ObjectId id, Category category) throws RemoteException, NotFoundException {
        return CategoryUtility.updateCategory(id, category);
    }

    @Override
    public boolean deleteCategory(ObjectId id) throws RemoteException, NotFoundException {
        return CategoryUtility.deleteCategory(id);
    }

    // Item
    @Override
    public Item findItemById(ObjectId id) throws RemoteException, NotFoundException {
        return ItemUtility.findItemById(id);
    }

    @Override
    public ObjectId insertItem(Item item) throws RemoteException, DuplicateException, MandatoryException {
        return ItemUtility.insertItem(item);
    }

    @Override
    public boolean updateItem(ObjectId id, Item item) throws RemoteException, NotFoundException {
        return ItemUtility.updateItem(id, item);
    }

    @Override
    public boolean deleteItem(ObjectId id) throws RemoteException, NotFoundException {
        return ItemUtility.deleteItem(id);
    }
}