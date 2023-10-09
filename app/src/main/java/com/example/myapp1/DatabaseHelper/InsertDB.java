package com.example.myapp1.DatabaseHelper;

import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertDB {

    static Connection conn = null;

    public static void template(String username , String hashPass){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table ="`DB_ECC`.`nhom_ky` (`user_id`,`status`,`X`,`Rsum`,`c`,`hash_vanban`,`created_at`)";
            String value =                   "(<{user_id: }>,<{status: }>,<{X: }>,<{Rsum: }>,<{c: }>,<{hash_vanban: }>,current_timestamp)";
            String query = "insert into "+table+" value " + value + ";";

            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void insertNhomKy(String main_id , String Xgroup,String Rsumgroup,String cgroup,String hashVanban) {
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table = "`DB_ECC`.`nhom_ky` (`user_id`,`status`,`X`,`Rsum`,`c`,`hash_vanban`,`created_at`)";
            String value = "('" + main_id + "','Chưa xong','" + Xgroup + "','" + Rsumgroup + "','" + cgroup + "','" + hashVanban + "',current_timestamp)";
            String query = "insert into " + table + " value " + value + ";";

            rs = st.executeUpdate(query);
        } catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void createNhomKy(String main_id , String hashVanban) {
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table = "`DB_ECC`.`nhom_ky` (`user_id`,`status`,`hash_vanban`,`created_at`)";
            String value = "('" + main_id + "','Chưa xong','" + hashVanban + "',current_timestamp)";
            String query = "insert into " + table + " value " + value + ";";

            rs = st.executeUpdate(query);
        } catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void insertMemberToNhomky(String id_user ,  String id_vanban,String id_nhomky,String ki){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table ="`DB_ECC`.`trash`(`id_taiKhoan`,`id_vanban`,`id_nhomky`,`ki`,`created_at`,`status`)";
            String value =                "('"+id_user+"','"+id_vanban+"','"+ id_nhomky+ "','" + ki+"',current_timestamp,'Chưa ký')";
            String query = "insert into "+table+" value " + value + ";";

            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }

    }


}
