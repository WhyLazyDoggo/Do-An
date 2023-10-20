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

    public static ResultSet getAllUser(){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String query = "select * from `DB_ECC`.`tai_khoan` as tk join `DB_ECC`.`chu_ky_ca_nhan` as ckcn on tk.id = ckcn.id_taiKhoan;";
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }


    public static ResultSet getFileSignDone(){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String query = "Select distinct nk.id, nk.signature, nk.X, nk.user_id, nk.hash_vanban, nk.created_at, vb.ten_van_ban, vb.noidungtomtat from `DB_ECC`.`nhom_ky` as nk join `DB_ECC`.`trash` as tmp on nk.id = tmp.id_nhomky join `DB_ECC`.`van_ban` as vb on tmp.id_vanban = vb.id  where nk.signature is not NULL;\n";
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet getSiGroup(){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();
            String query = "select id, id_taiKhoan, id_nhomky, id_vanban, Si from `DB_ECC`.`trash` as tmp where (\n" +
                    "    SELECT COUNT(*)\n" +
                    "    FROM `DB_ECC`.`trash`\n" +
                    "    WHERE id_nhomky = tmp.id_nhomky and status = 'Đã ký') =\n" +
                    " (\n" +
                    "    SELECT COUNT(*)\n" +
                    "    FROM `DB_ECC`.`trash`\n" +
                    "    WHERE id_nhomky = tmp.id_nhomky );";
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet getMsgXgroupRsum(String id_nhom){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "X,Rsum,hash_vanban";
            String table =" `DB_ECC`.`nhom_ky`";
            String where ="where id = "+id_nhom;

            String query = "select "+col+" from "+table+" "+where;
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet getProcessGroupSign(String user_id){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String query = "select distinct nk.id, vb.ten_van_ban, vb.noidungtomtat, vb.created_at, nk.user_id ,\n" +
                    "    (\n" +
                    "    SELECT COUNT(*)\n" +
                    "    FROM `DB_ECC`.`trash`\n" +
                    "    WHERE id_nhomky = nk.id and status = 'Đã ký') AS sl_ky, \n" +
                    " (\n" +
                    "    SELECT COUNT(*)\n" +
                    "    FROM `DB_ECC`.`trash`\n" +
                    "    WHERE id_nhomky = nk.id) AS tong_user\n" +
                    " from `DB_ECC`.`nhom_ky` as nk join `DB_ECC`.`trash` as tmp on nk.id = tmp.id_nhomky join `DB_ECC`.`van_ban` as vb on tmp.id_vanban = vb.id \n" +
                    "where nk.status = 'Chưa xong' and nk.user_id = '"+user_id+"';";
            System.out.println(query);
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet getXcheck(String id_nhomky){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "ckcn.id_taiKhoan, tmp.id_nhomky, ckcn.khoa_cong_khai";
            String table ="`DB_ECC`.`trash` as tmp join `DB_ECC`.`chu_ky_ca_nhan` as ckcn on tmp.id_taiKhoan = ckcn.id_taiKhoan";
            String where ="where tmp.id_nhomky ='"+id_nhomky+"'  order by created_at ";

            String query = "select "+col+" from "+table+" "+where;
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }


    public static ResultSet getDataForSign(String id_nhomky, String id_process){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "tmp.id, tmp.id_taiKhoan, tmp.id_nhomky, nk.L, nk.c , tmp.ki,nk.X, nk.Rsum";
            String table ="`DB_ECC`.`nhom_ky` as nk join `DB_ECC`.`trash` as tmp on nk.id = tmp.id_nhomky ";
            String where ="where tmp.id_nhomky ='"+id_nhomky+"' and tmp.id = '"+id_process+"'";

            String query = "select "+col+" from "+table+" "+where;
            System.out.println(query);
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet getPrivateKey(String username){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "*";
            String table ="DB_ECC.chu_ky_ca_nhan";
            String where ="where id_taiKhoan = "+username;

            String query = "select "+col+" from "+table+" "+where;
            rs = st.executeQuery(query);
            rs.next();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }


    public static String getNhomKy_ByHash(String hashFuntion){
        ResultSet rs = null;
        String result ="";
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "id";
            String table ="`DB_ECC`.`nhom_ky`";
            String where ="where hash_vanban = '"+hashFuntion+"'";

            String query = "select "+col+" from "+table+" "+where;
            rs = st.executeQuery(query);
            rs.next();
            result = rs.getString("id");
        }
        catch (SQLException ex) {
            Log.e("error",ex.getMessage());
        }

        return result;
    }
    public static ResultSet getVanBanKy(String id_user){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "tmp.id, tmp.id_taiKhoan, tmp.id_nhomky, tmp.status, tmp.ki, vb.id as id_cuavanban, vb.ten_van_ban, vb.noidungtomtat, vb.trang_thai, vb.created_at ";
            String table ="`DB_ECC`.`trash` as tmp join DB_ECC.van_ban as vb on tmp.id_vanban = vb.id";
            String where ="where status = 'Chưa ký' and id_taiKhoan = '"+id_user+"'";

            String query = "select "+col+" from "+table+" "+where;
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet getPubKey(String id_user){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "khoa_cong_khai";
            String table ="`DB_ECC`.`chu_ky_ca_nhan`";
            String where ="where id_taiKhoan='"+id_user+"'";

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

    public static ResultSet getNhanVien(String withouUser){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "id,ten_nhan_vien, role";
            String table ="`DB_ECC`.`tai_khoan`";
            String where ="where id !='"+withouUser+"'";
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
            String table ="`DB_ECC`.`tai_khoan` as tk join `DB_ECC`.`chu_ky_ca_nhan` as ckcn on tk.id = ckcn.id_taiKhoan";
            String where ="where tk.username = '"+username+"' and tk.password = '"+hashPass+"';";
            String query = "select "+col+" from "+table+" "+where;
            System.out.println(query);
            rs = st.executeQuery(query);
//            rs.next();

        }
        catch (SQLException ex) {
            System.out.println(ex);;
        }
        return rs;
    }

}
