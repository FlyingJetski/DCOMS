package com.company;

import com.company.common.Database;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;

import javax.xml.crypto.Data;
import java.rmi.*;
import java.rmi.registry.*;

public class RegistryHandler {
    public static void main(String[] args) throws RemoteException, DuplicateException, MandatoryException {
        final int port = 1044;
        Database.initialize();
        Registry reg = LocateRegistry.createRegistry(port);
        reg.rebind("server", new Server());
        System.out.println("Listening on port " + port);
        Database.resetDatabase();
    }
}