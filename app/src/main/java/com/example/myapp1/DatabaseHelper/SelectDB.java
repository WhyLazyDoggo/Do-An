package com.example.myapp1.DatabaseHelper;

import android.os.StrictMode;
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

    public static ResultSet template(String username , String hashPass){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

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

    public static ResultSet getVanBan(String username , String trangthai){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "id, ten_van_ban, noidungtomtat, created_at";
            String table ="`DB_ECC`.`van_ban`";
            String where ="";

            String query = "select "+col+" from "+table+" "+where;
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet getNhanVien(){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "id,ten_nhan_vien, role";
            String table ="`DB_ECC`.`tai_khoan`";
            String where =" ";
            String query = "select "+col+" from "+table+" "+where;
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet checkLogin(String username , String hashPass){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();
            String col = "*";
            String table ="`DB_ECC`.`tai_khoan`";
            String where ="where username = '"+username+"' and password = '"+hashPass+"';";
            String query = "select "+col+" from "+table+" "+where;
            System.out.println(query);
            rs = st.executeQuery(query);
            rs.next();

        }
        catch (SQLException ex) {
            System.out.println(ex);;
        }
        return rs;
    }

}
