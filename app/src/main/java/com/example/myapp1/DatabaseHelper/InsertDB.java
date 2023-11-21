package com.example.myapp1.DatabaseHelper;

import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public static void insertChuKyCaNhan(String id_name , String pubkey){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table ="`Data_Sign`.`chu_ky_ca_nhan` (`id_tai_khoan`,`khoa_cong_khai`, `ngay_het_han`)";
            String value =                   "('"+id_name+"', '"+pubkey+"', DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 1 WEEK))";
            String query = "insert into "+table+" value " + value + ";";

            rs = st.executeUpdate(query);
            System.out.println(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static int insertUser(String username , String hashPass,String ten_nhan_vien, String role, String room){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

//            String table ="`Data_Sign`.`tai_khoan` (`username`, `password`, `avatar`, `ten_nhan_vien`, `chuc_vu`, `phong_ban`, `status`, `created_at`)";
//            String value =                     "('"+username+"', '"+hashPass+"', 'b1', '"+ten_nhan_vien+"', '"+role+"', '"+room+"', 'Tạo mới', current_timestamp)";
//            String query = "insert into "+table+" value " + value + ";";


            Connection connection = ConnectDatabase.getConn();

            String table = "`Data_Sign`.`tai_khoan` (`username`, `password`, `avatar`, `ten_nhan_vien`, `chuc_vu`, `phong_ban`, `status`, `created_at`)";
            String values = "(?, ?, 'b1', ?, ?, ?, 'Tạo mới', current_timestamp)";

            // Sử dụng PreparedStatement để tạo truy vấn và thiết lập tham số
            String query = "INSERT INTO " + table + " VALUES " + values;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashPass);
            preparedStatement.setString(3, ten_nhan_vien);
            preparedStatement.setString(4, role);
            preparedStatement.setString(5, room);

            // Thực hiện truy vấn
            rs = preparedStatement.executeUpdate();

            System.out.println(query);


            return rs;



//            rs = st.executeUpdate(query);

//            System.out.println(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
            return 404;
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

    public static void createNhomKy(String main_id , String hashVanban, String id_vanBan) {
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table = "`Data_Sign`.`nhom_ky` (`id_tai_khoan`,`status`, `id_van_ban`, `created_at`)";
            String value = "('" + main_id + "','"+hashVanban.substring(0, 49)+"', '"+id_vanBan+"' ,current_timestamp)";
            String query = "insert into " + table + " value " + value + ";";
            System.out.println(query);
            rs = st.executeUpdate(query);
        } catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void insertMemberToNhomky(String id_user , String id_nhomky,String ki){
        int rs;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String table ="`Data_Sign`.`tien_trinh_ky`(`id_tai_khoan`,`id_nhom_ky`,`ki`,`created_at`,`status`)";
            String value =                "('"+id_user+"','"+ id_nhomky+ "','" + ki+"',current_timestamp,'Chưa ký')";
            String query = "insert into "+table+" value " + value + ";";
            System.out.println(query);
            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }

    }


}
