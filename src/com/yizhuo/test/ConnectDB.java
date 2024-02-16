package com.yizhuo.test;

import com.yizhuo.utility.Database;

import java.util.Date;

public class ConnectDB {
    public static void main(String[] args) {
        Database.createTableIfNotExists();

        connected();
//        Database.searchTOP5();
//        Database.updateRecord();
        int rows = Database.getRows();
        System.out.println(rows);
        Date now = new Date();
        Database.updateRecord(100,now);
    }

    private static void connected() {
        String username = "zhangsan";
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

