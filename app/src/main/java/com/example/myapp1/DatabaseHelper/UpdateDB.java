package com.example.myapp1.DatabaseHelper;

import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateDB {
    static Connection conn = null;

    @Nullable

    public static void template(String username , String hashPass){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table ="";
            String set="";
            String where ="where ";
            String query = "update "+table+" set "+ set +" "+where;
            System.out.println(query);

            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void updatePassword(String id , String hashPass,String status){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table ="";
            String set="";
            String where ="where ";
            String query = "UPDATE `DB_ECC`.`tai_khoan` SET `password` = "+hashPass+", `status` = '"+status+"', `update_at` = current_timestamp()  WHERE `id` = "+id+";";
            System.out.println(query);

            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }


    public static void updateStatus(String id , String status){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String query = "UPDATE `DB_ECC`.`tai_khoan` SET `status` = '"+status+"', `update_at` = current_timestamp() WHERE `id` = "+id+";";
            System.out.println(query);

            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }



    public static void updatetmp(String id , int avatar){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String query = "UPDATE `DB_ECC`.`tai_khoan` SET `avatar` = "+avatar+" WHERE `id` = "+id+";";
            System.out.println(query);

            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }


    public static void updateChuKy(String Signature , String id_nhom){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table ="`DB_ECC`.`nhom_ky`";
            String set="signature = '"+Signature+"', status = 'Hoàn thành'";
            String where ="where id = "+id_nhom;
            String query = "update "+table+" set "+ set +" "+where;
            System.out.println(query);

            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void updateNhomKy(String khoa_cong_khai_nhom,String Xgroup, String Rsum,String cGroup,String hash_vanban,String find){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table ="`DB_ECC`.`nhom_ky`";
            String set="`L` = '"+khoa_cong_khai_nhom+"', `X` = '"+Xgroup+"', `Rsum` = '"+ Rsum +"', `c` = '"+cGroup+"', `hash_vanban` = \""+hash_vanban+"\"";
            String where ="where `hash_vanban` = '"+find+"'";
            String query = "update "+table+" set "+ set +" "+where;
            System.out.println(query);

            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static void kyVanBan(String id_taiKhoan , String id_nhomky, String Si,String id_canKy){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table ="`DB_ECC`.`trash`";
            String set="`status` = 'Đã ký', `Si` = '"+Si+"'";
            String where ="where (`id_taiKhoan` = '"+ id_taiKhoan +"' AND `id_nhomky` = '"+ id_nhomky +"') or id = '"+id_canKy+"'";

            String query = "update "+table+" set "+ set +" "+where;

            System.out.println(query);
            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }

}