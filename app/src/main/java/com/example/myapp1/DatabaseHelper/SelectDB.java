package com.example.myapp1.DatabaseHelper;

import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectDB {

    static Connection conn = null;
    @Nullable
    public static Statement makeStatement(){
        String ConnectionURL = null;

        ConnectionURL = "jdbc:mysql://db-sig.cosncuwagf24.us-east-1.rds.amazonaws.com:3306/DB_ECC?user=admin&password=Shota4ever&useSSL=false&autoReconnect=true";
        System.out.println(ConnectionURL);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception ex){
            System.out.println(ex);
            Log.e("Error",ex.getMessage());
        }
        try {
            Statement stmt = conn.createStatement();
            System.out.println("Ket noi thanh cong.");
            System.out.println(conn.getCatalog());
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

    public static Object template(String username , String hashPass){
        ResultSet rs = null;
        try {
            Statement st = makeStatement();

            String col = "*";
            String table ="";
            String where ="where ";

            String query = "select "+col+" from "+table+" "+where;
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ResultSet rs;
        rs = checkLogin("UserA","123");
        rs.next();
        String ketqua = rs.getString(4);
        System.out.println(ketqua);
    }

    public static ResultSet checkLogin(String username , String hashPass){
        ResultSet rs = null;
        try {
            Statement st = makeStatement();

            String col = "*";
            String table ="`DB_ECC`.`tai_khoan`";
            String where ="where username = '"+username+"' and password = '"+hashPass+"';";
            String query = "select "+col+" from "+table+" "+where;
            System.out.println(query);

            rs = st.executeQuery(query);


        }
        catch (SQLException ex) {
            System.out.println(ex);;
        }
        return rs;
    }

    public static Object getCalender(){
        ResultSet rs = null;
        try {
            Statement st = makeStatement();
            String query = "select *  from tin_chi2";
            rs = st.executeQuery(query);
            }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }




}
