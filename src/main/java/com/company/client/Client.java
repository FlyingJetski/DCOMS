package com.company.client;

import com.company.common.*;
import java.net.*;
import java.rmi.*;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        RemoteInterface server = (RemoteInterface)Naming.lookup("rmi://localhost:1044/server");
        User user = new User()
                .setUsername("asd")
                .setPassword("asd")
                .setFirstName("asd")
                .setIcPassport("asd");
//        server.insertUser(user);
    }
}