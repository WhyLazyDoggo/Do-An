package com.example.myapp1.DatabaseHelper;

import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDatabase {
    static Connection conn = null;
    static String id, pass, ip, port, database;
    @Nullable
    public static Statement makeStatement(){
        ip ="db-sig.cosncuwagf24.us-east-1.rds.amazonaws.com";
        id ="admin";
        pass = "Shota4ever";
        port = "3306";
        database = "DB_ECC";

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        String ConnectionURL = null;

        ConnectionURL = "jdbc:mysql://db-sig.cosncuwagf24.us-east-1.rds.amazonaws.com:3306/DB_ECC?user=admin&password=Shota4ever&useSSL=false&autoReconnect=true";
        System.out.println(ConnectionURL);
        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (Exception ex){
            System.out.println(ex);
            Log.e("Error",ex.getMessage());
        }
        StringBuilder records = new StringBuilder();

        try {
            Statement stmt = conn.createStatement();
            System.out.println("Ket noi thanh cong.");
            System.out.println(conn.getCatalog());


//            ResultSet rs = stmt.executeQuery("SELECT * FROM test_sign");
//            while (rs.next()) {
//                records.append("Name: ").append(rs.getString(1)).append(", Place: ").append(rs.getString(3)).append("\n");
//
//            }
//            System.out.println(records);

            return stmt;
        }
        catch (SQLException e) {
            System.out.println("Error");
            Log.e(null, "Error connection!!! Tạo bảng KyTucXa chưa Pa?");
            return null;
        }

        catch (Exception e) {
            System.out.println("Error");
            Log.e(null, "Error connection!!! Uhm, lỗi khác rồi. Đọc word dùm pa");
            return null;
        }
    }
}
