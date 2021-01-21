package com.company;

import com.company.common.*;
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
        return User.findUserById(id);
    }

    @Override
    public ObjectId insertUser(User user) throws RemoteException, DuplicateException, MandatoryException {
        return User.insertUser(user);
    }

    @Override
    public boolean updateUser(ObjectId id, User user) throws RemoteException, NotFoundException {
        return User.updateUser(id, user);
    }

    @Override
    public boolean updateUserPassword(ObjectId id, String oldPassword, String newPassword) throws RemoteException, NotFoundException, NotMatchException {
        return User.updateUserPassword(id, oldPassword, newPassword);
    }

    @Override
    public boolean deleteUser(ObjectId id) throws RemoteException, NotFoundException {
        return User.deleteUser(id);
    }

    @Override
    public boolean logIn(String username, String password) throws RemoteException, NotFoundException {
        return User.logIn(username, password);
    }

    // Category
    @Override
    public Category findCategoryById(ObjectId id) throws NotFoundException {
        return Category.findCategoryById(id);
    }

    @Override
    public ObjectId insertCategory(Category category) throws DuplicateException, MandatoryException {
        return Category.insertCategory(category);
    }

    @Override
    public boolean updateCategory(ObjectId id, Category category) throws NotFoundException {
        return Category.updateCategory(id, category);
    }

    @Override
    public boolean deleteCategory(ObjectId id) throws NotFoundException {
        return Category.deleteCategory(id);
    }

    // Item
    @Override
    public Item findItemById(ObjectId id) throws NotFoundException {
        return Item.findItemById(id);
    }

    @Override
    public ObjectId insertItem(Item item) throws DuplicateException, MandatoryException {
        return Item.insertItem(item);
    }

    @Override
    public boolean updateItem(ObjectId id, Item item) throws NotFoundException {
        return Item.updateItem(id, item);
    }

    @Override
    public boolean deleteItem(ObjectId id) throws NotFoundException {
        return Item.deleteItem(id);
    }
}