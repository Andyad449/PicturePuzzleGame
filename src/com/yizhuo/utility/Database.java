package com.yizhuo.utility;

import com.yizhuo.ui.LoginJFrame;

import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Database {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/PicturePuzzleGame?serverTimezone=Asia/Shanghai";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public static void createTableIfNotExists() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement user = conn.createStatement();
             Statement score = conn.createStatement()) {
            String createUserQuery = "CREATE TABLE IF NOT EXISTS user (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(20) NOT NULL UNIQUE," +
                    "password VARCHAR(20) NOT NULL" +
                    ")";
            String createScoreQuery = "CREATE TABLE IF NOT EXISTS score(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(20) NOT NULL UNIQUE," +
                    "step INT NOT NULL," +
                    "recordTime DATETIME NOT NULL" +
                    ")";
            user.executeUpdate(createUserQuery);
            score.executeUpdate(createScoreQuery);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkUsernameExists(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkPasswordCorrect(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return password.equals(storedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void insertRegisterInfo(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> searchTOP5() {
        ArrayList<String> ranking = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt;
        int recordNum = 0;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.prepareStatement("select username,step,recordTime from score order by step,recordTime");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                recordNum++;
                String username = resultSet.getString("username");
                String step = resultSet.getString("step");
                String recordTime = resultSet.getString("recordTime");
                ranking.add(Integer.toString(recordNum));
                ranking.add(username);
                ranking.add(step);
                ranking.add(recordTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ranking;
    }

    public static void updateRecord(int step, Date recordTime) {
        String username = LoginJFrame.username;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = format.format(recordTime);
        Connection conn;
        PreparedStatement stmt;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.prepareStatement("INSERT INTO score(username,step,recordTime) values (?,?,?)");
            stmt.setString(1, username);
            stmt.setInt(2, step);
            stmt.setString(3, formattedTime);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (getRows() > 5) {
            delRecord();
        }
    }

    public static int getRows() {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.prepareStatement("SELECT COUNT(*) as '记录数' FROM score");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void delRecord() {
        Connection conn = null;
        PreparedStatement searchForMAXStep = null;
        PreparedStatement searchForMAXTime = null;
        PreparedStatement delMAX = null;
        int maxStep = 0;
        String maxTime = "";
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            searchForMAXStep = conn.prepareStatement("SELECT MAX(step) as '最多步数' from score");
            searchForMAXTime = conn.prepareStatement("SELECT MAX(recordTime) as '最晚时间' from score");
            ResultSet searchStepResult = searchForMAXStep.executeQuery();
            ResultSet searchTimeResult = searchForMAXTime.executeQuery();
            while (searchStepResult.next()) {
                maxStep = searchStepResult.getInt("最多步数");
            }
            while (searchTimeResult.next()){
                maxTime = searchTimeResult.getString("最晚时间");
            }
            delMAX = conn.prepareStatement("DELETE FROM score where step = ? and recordTime = ?");
            delMAX.setInt(1,maxStep);
            delMAX.setString(2,maxTime);
            delMAX.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(conn != null){
                    conn.close();
                }
                if(searchForMAXStep != null){
                    searchForMAXStep.close();
                }
                if (searchForMAXTime != null){
                    searchForMAXTime.close();
                }
                if (delMAX != null){
                    delMAX.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
