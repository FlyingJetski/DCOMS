package com.company;

import com.company.common.*;
import java.rmi.*;
import java.rmi.server.*;

@SuppressWarnings("serial")
public class Server extends UnicastRemoteObject implements RemoteInterface {
    public Server() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello Inventory";
    }
}