package com.company;

import com.company.common.*;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.common.exceptions.NotMatchException;
import com.company.models.User;
import org.bson.types.ObjectId;

import java.rmi.*;
import java.rmi.server.*;

@SuppressWarnings("serial")
public class Server extends UnicastRemoteObject implements RemoteInterface {
    public Server() throws RemoteException {
        super();
    }

//    @Override
    public User findUserById(ObjectId id) throws RemoteException, NotFoundException {
        return User.findUserById(id);
    }

//    @Override
    public ObjectId insertUser(User user) throws RemoteException, DuplicateException, MandatoryException {
        return User.insertUser(user);
    }

//    @Override
    public boolean updateUser(ObjectId id, User user) throws RemoteException, NotFoundException {
        return User.updateUser(id, user);
    }

//    @Override
    public boolean updateUserPassword(ObjectId id, String oldPassword, String newPassword) throws RemoteException, NotFoundException, NotMatchException {
        return User.updateUserPassword(id, oldPassword, newPassword);
    }

//    @Override
    public boolean deleteUser(ObjectId id) throws RemoteException, NotFoundException {
        return User.deleteUser(id);
    }

//    @Override
    public static boolean logIn(String username, String password) throws RemoteException, NotFoundException {
        return User.logIn(username, password);
    }
}