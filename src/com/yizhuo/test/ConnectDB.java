package com.yizhuo.test;

import com.yizhuo.utility.Database;

public class ConnectDB {
    public static void main(String[] args) {
        Database.createTableIfNotExists();

        String username = "zhangsa";
        String password = "123456";

        System.out.println(Database.checkUsernameExists(username));
        boolean usernameExists = Database.checkUsernameExists(username);
        if (usernameExists) {
            boolean passwordCorrect = Database.checkPasswordCorrect(username, password);
            if (passwordCorrect) {
                System.out.println("Username and password are correct.");
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("Username does not exist.");
        }
    }
}

