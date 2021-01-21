package com.company;

import java.rmi.*;
import java.rmi.registry.*;

public class RegistryHandler {
    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(1044);
        reg.rebind("server", new Server());
    }
}