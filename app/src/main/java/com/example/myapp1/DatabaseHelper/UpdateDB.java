package com.example.myapp1.DatabaseHelper;

import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateDB {
    TextView t1;
    public static final String DATABASE_NAME = "DB_ECC";
    public static final String url = "jdbc:mysql://db-sig.cosncuwagf24.us-east-1.rds.amazonaws.com:3306/" +
            DATABASE_NAME+ "?useSSL=false";
    public static final String username = "testsign", password = "shot@4ever";

    public static final String TABLE_NAME = "test_sign";

    public static void main(String[] args) {


        System.out.println(url);
        try {
            utilFun();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void utilFun() throws SQLException {
        new Thread(() -> {
            //do your work

            StringBuilder records = new StringBuilder();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
                while (rs.next()) {
                    records.append("Name: ").append(rs.getString(1)).append(", Place: ").append(rs.getString(3)).append("\n");
                }

                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }).start();


    }

    public static void addTemp(String name_str, String place_str) {
        new Thread(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                // add to RDS DB:

                statement.execute("INSERT INTO " + TABLE_NAME + "(name, place) VALUES('" + name_str + "', '" + place_str + "')");

                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}