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


            String query = "update";
            System.out.println(query);

            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void dropbackLater(){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();


            String query = "DELETE FROM `Data_Sign`.`tien_trinh_ky` " +
                    " WHERE `Data_Sign`.`tien_trinh_ky`.`id_nhom_ky` IN (SELECT `id` FROM `Data_Sign`.`nhom_ky` WHERE `L` is null); ";

            String query2 = "DELETE FROM `Data_Sign`.`nhom_ky` WHERE `L` is null;";
            System.out.println(query);

            rs = st.executeUpdate(query);
            st.executeUpdate(query2);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }


    public static void updateDeleteAllChuKy(String id_user){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String query = "update `Data_Sign`.`chu_ky_ca_nhan` " +
                            "SET `ngay_het_han` = DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 1 WEEK) " +
                                "WHERE `id_tai_khoan` = '"+id_user+"';";
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
            String query = "UPDATE `Data_Sign`.`tai_khoan` SET `password` = '"+hashPass+"', `status` = '"+status+"', `update_at` = current_timestamp()  WHERE `id` = "+id+";";
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

            query ="UPDATE `Data_Sign`.`tai_khoan` SET `status` = '"+status+"', `update_at` = current_timestamp() WHERE `id` = "+id+";";

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

            String table ="`Data_Sign`.`nhom_ky`";
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

    public static void updateNhomKy(String khoa_cong_khai_nhom,String Xgroup, String Rsum,String cGroup,String find){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table ="`Data_Sign`.`nhom_ky`";
            String set="`L` = '"+khoa_cong_khai_nhom+"', `X` = '"+Xgroup+"', `Rsum` = '"+ Rsum +"', `c` = '"+cGroup+"', `status` = 'Chưa xong'";
            String where ="where `status` = '"+find.substring(0, 49)+"'";
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

            String table ="`Data_Sign`.`tien_trinh_ky`";
            String set="`status` = 'Đã ký', `chu_ky_ca_nhan` = '"+Si+"'";
            String where ="where (`id_tai_khoan` = '"+ id_taiKhoan +"' AND `id_nhom_ky` = '"+ id_nhomky +"') or id = '"+id_canKy+"'";

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