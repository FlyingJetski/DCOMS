package com.company.common;

import com.lambdaworks.crypto.SCryptUtil;

public class Authentication {
    public static String hash(String password) {
        return SCryptUtil.scrypt(password, 16, 16, 16);
    }

    public static boolean match(String password, String hashedPassword) {
        return SCryptUtil.check(password, hashedPassword);
    }
}