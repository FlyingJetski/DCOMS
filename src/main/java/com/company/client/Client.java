package com.company.client;

import com.company.common.*;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.models.User;

import java.net.*;
import java.rmi.*;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, DuplicateException, MandatoryException {
        RemoteInterface server = (RemoteInterface)Naming.lookup("rmi://localhost:1044/server");
        server.resetDatabase();
    }
}