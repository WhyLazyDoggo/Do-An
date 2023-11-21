package com.example.myapp1.DatabaseHelper;

import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

            String query = "select * from `Data_Sign`.`tai_khoan`;";// as tk join `Data_Sign`.`chu_ky_ca_nhan` as ckcn on tk.id = ckcn.id_tai_khoan;";
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

            String query = "Select distinct tmp.id_taiKhoan as id_nhan_vien, tk.ten_nhan_vien , nk.id, nk.signature, nk.X, nk.user_id, nk.hash_vanban, nk.created_at, vb.ten_van_ban, vb.noidungtomtat from  " +
                    "`DB_ECC`.`nhom_ky` as nk join `DB_ECC`.`trash` as tmp on nk.id = tmp.id_nhomky join `DB_ECC`.`van_ban` as vb on tmp.id_vanban = vb.id join `DB_ECC`.`tai_khoan` as tk on tk.id = tmp.id_taiKhoan where nk.signature is not NULL; ";

            query = "Select distinct tmp.id_tai_khoan as id_nhan_vien, tk.ten_nhan_vien , nk.id, nk.signature, nk.X, nk.id_tai_khoan, vb.noi_dung_tom_tat, nk.created_at, vb.ten_van_ban from   " +
                                        "`Data_Sign`.`nhom_ky` as nk  " +
                                            "join `Data_Sign`.`tien_trinh_ky` as tmp on nk.id = tmp.id_nhom_ky  " +
                                                "join `Data_Sign`.`van_ban` as vb on nk.id_van_ban = vb.id  " +
                                                    "join `Data_Sign`.`tai_khoan` as tk on tk.id = tmp.id_tai_khoan where nk.signature is not NULL; ";

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
            String query = "select tmp.id, tmp.id_taiKhoan, tmp.id_nhomky, tmp.id_vanban, tmp.Si from `DB_ECC`.`trash` as tmp join `DB_ECC`.`nhom_ky` as nk on tmp.id_nhomky = nk.id where ( " +
                    "    SELECT COUNT(case when status = 'Đã ký' then 1 end) " +
                    "    FROM `DB_ECC`.`trash` " +
                    "    WHERE id_nhomky = tmp.id_nhomky) = " +
                    " ( " +
                    "    SELECT COUNT(*) " +
                    "    FROM `DB_ECC`.`trash` " +
                    "    WHERE id_nhomky = tmp.id_nhomky) and nk.status != 'Hoàn thành';";


            query ="select tmp.id, tmp.id_tai_khoan, tmp.id_nhom_ky, tmp.chu_ky_ca_nhan " +
                        "from `Data_Sign`.`tien_trinh_ky` as tmp join `Data_Sign`.`nhom_ky` as nk on tmp.id_nhom_ky = nk.id " +
                            "where ( " +
                                    "SELECT COUNT(case when status = 'Đã ký' then 1 end) " +
                                    "FROM `Data_Sign`.`tien_trinh_ky` " +
                                    "WHERE id_nhom_ky = tmp.id_nhom_ky) = " +
                                  "( " +
                                    "SELECT COUNT(*) " +
                                    "FROM `Data_Sign`.`tien_trinh_ky` " +
                                    "WHERE id_nhom_ky = tmp.id_nhom_ky) and nk.status != 'Hoàn thành';";
            System.out.println(query);
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
            query = "select X, Rsum, noi_dung_tom_tat from `Data_Sign`.`nhom_ky` as nk join  `Data_Sign`.`van_ban` as vb on nk.id_van_ban = vb.id where nk.id = "+id_nhom+";";
            System.out.println(query);
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet getProcessGroupSign(String user_id,String user_role, String user_room){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String query = "select distinct nk.id, vb.ten_van_ban, vb.noidungtomtat, vb.created_at, nk.user_id , " +
                    "    ( " +
                    "    SELECT COUNT(*) " +
                    "    FROM `DB_ECC`.`trash` " +
                    "    WHERE id_nhomky = nk.id and status = 'Đã ký') AS sl_ky,  " +
                    " ( " +
                    "    SELECT COUNT(*) " +
                    "    FROM `DB_ECC`.`trash` " +
                    "    WHERE id_nhomky = nk.id) AS tong_user " +
                    " from `DB_ECC`.`nhom_ky` as nk join `DB_ECC`.`trash` as tmp on nk.id = tmp.id_nhomky join `DB_ECC`.`van_ban` as vb on tmp.id_vanban = vb.id join `DB_ECC`.`tai_khoan` as tk on nk.user_id = tk.id " +
                    "where nk.status = 'Chưa xong' and IF ('"+user_role+"' ='Trưởng phòng', tk.phong_ban = '"+user_room+"', nk.user_id = '"+user_id+"');";
            
            query = "select distinct nk.id, vb.ten_van_ban, vb.noi_dung_tom_tat, vb.created_at, nk.id_tai_khoan, " +
                            "(" +
                                "SELECT COUNT(*) " +
                                "FROM `Data_Sign`.`tien_trinh_ky` " +
                                "WHERE id_nhom_ky = nk.id and status = 'Đã ký') AS sl_ky,  " +
                            "( " +
                                "SELECT COUNT(*) " +
                                "FROM `Data_Sign`.`tien_trinh_ky` " +
                                "WHERE id_nhom_ky = nk.id) AS tong_user " +
                        "from `Data_Sign`.`nhom_ky` as nk  " +
                                "join `Data_Sign`.`tien_trinh_ky` as tmp on nk.id = tmp.id_nhom_ky  " +
                                    "join `Data_Sign`.`van_ban` as vb on nk.id_van_ban = vb.id  " +
                                        "join `Data_Sign`.`tai_khoan` as tk on nk.id_tai_khoan = tk.id " +
                        "where nk.status = 'Chưa xong' and IF ('"+user_role+"' ='Trưởng phòng', tk.phong_ban = '"+user_room+"', nk.id_tai_khoan = '"+user_id+"');";
            System.out.println(query);
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet getProcessGroupSigntmp(String user_id){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String query = "select distinct nk.id, vb.ten_van_ban, vb.noidungtomtat, vb.created_at, nk.user_id , " +
                    "    ( " +
                    "    SELECT COUNT(*) " +
                    "    FROM `DB_ECC`.`trash` " +
                    "    WHERE id_nhomky = nk.id and status = 'Đã ký') AS sl_ky,  " +
                    " ( " +
                    "    SELECT COUNT(*) " +
                    "    FROM `DB_ECC`.`trash` " +
                    "    WHERE id_nhomky = nk.id) AS tong_user " +
                    " from `DB_ECC`.`nhom_ky` as nk join `DB_ECC`.`trash` as tmp on nk.id = tmp.id_nhomky join `DB_ECC`.`van_ban` as vb on tmp.id_vanban = vb.id  " +
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

            query = "SELECT ckcn.id_tai_khoan, ttk.id_tai_khoan, ckcn.khoa_cong_khai " +
                        "FROM `Data_Sign`.`tien_trinh_ky` as ttk join `Data_Sign`.`chu_ky_ca_nhan` as ckcn on ttk.id_tai_khoan = ckcn.id_tai_khoan " +
                            "where ttk.id_nhom_ky ='"+id_nhomky+"' order by created_at;";

            System.out.println(query);
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


            query ="SELECT ttk.id, ttk.id_tai_khoan, ttk.id_nhom_ky, nk.L, nk.c , ttk.ki, nk.X, nk.Rsum " +
                        "FROM `Data_Sign`.`nhom_ky` as nk join `Data_Sign`.`tien_trinh_ky` as ttk on nk.id = ttk.id_nhom_ky " +
                            "WHERE ttk.id_nhom_ky ='"+id_nhomky+"' and ttk.id = '"+id_process+"';";

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
            String table ="`Data_Sign`.`nhom_ky`";
            String where ="where status = '"+hashFuntion.substring(0, 49)+"'";

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

    public static String getCount(String id_user){
        ResultSet rs = null;
        String count = "0";
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String query = "select count(*) from " +
                    "`DB_ECC`.`trash` as tmp join DB_ECC.van_ban as vb on tmp.id_vanban = vb.id " +
                    "where status = 'Chưa ký' and id_taiKhoan = '"+id_user+"';";

            query = "select count(*) from " +
                    "\t\t`Data_Sign`.`tien_trinh_ky` as tmp join `Data_Sign`.`nhom_ky` as nk on tmp.id_nhom_ky = nk.id join `Data_Sign`.`van_ban` as vb on nk.id_van_ban = vb.id " +
                    "                    where tmp.status = 'Chưa ký' and tmp.id_tai_khoan = '"+id_user+"';";
            System.out.println(query);
            rs = st.executeQuery(query);
            if (rs.next()){
                count = rs.getString(1);
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return count;
    }


    public static ResultSet getVanBanKy(String id_user){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String col = "tmp.id, tmp.id_taiKhoan, tmp.id_nhomky, tmp.status, tmp.ki, vb.id as id_cuavanban, vb.ten_van_ban, vb.noidungtomtat, vb.trang_thai, vb.created_at ";
            String table ="`DB_ECC`.`trash` as tmp join DB_ECC.van_ban as vb on tmp.id_vanban = vb.id";
            String where ="where status = 'Chưa ký' and id_taiKhoan = '"+id_user+"'";

            String query = "select "+col+" from "+table+" "+where;


            query = "select ttk.id, ttk.id_tai_khoan, ttk.id_nhom_ky, ttk.status, ttk.ki, vb.id as id_cuavanban, vb.ten_van_ban, vb.noi_dung_tom_tat, vb.status, vb.created_at " +
                    "from `Data_Sign`.`tien_trinh_ky` as ttk join `Data_Sign`.`nhom_ky` as nk on nk.id = ttk.id_nhom_ky " +
                                    "join `Data_Sign`.`van_ban` as vb on nk.id_van_ban = vb.id " +
                                        "where ttk.status = 'Chưa ký' and ttk.id_tai_khoan = '"+id_user+"';";

            System.out.println(query);
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

            String col = "id, ten_van_ban, noi_dung_tom_tat, created_at";
            String table ="`Data_Sign`.`van_ban`";
            String where ="";

            String query = "select "+col+" from "+table+" "+where;
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet getNhanVien(String withouUser, String phong_ban){
        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();

            String query = "SELECT tk.id, tk.ten_nhan_vien, tk.avatar, tk.chuc_vu, tk.phong_ban, ckcn.khoa_cong_khai, ckcn.ngay_het_han " +
                    "FROM `Data_Sign`.`tai_khoan` as tk left join `Data_Sign`.`chu_ky_ca_nhan` as ckcn on tk.id = ckcn.id_tai_khoan " +

                    "where (ngay_het_han > current_timestamp() or ngay_het_han is NULL) and tk.id != '"+withouUser+"'  " +
                                                                                                                    //and ckcn.khoa_cong_khai is NOT NULL
                    "ORDER BY ckcn.khoa_cong_khai DESC, " +
                        "CASE WHEN tk.phong_ban ='"+phong_ban+"' THEN 0 " +
                            "ELSE 1 " +
                        "END, tk.phong_ban, tk.chuc_vu DESC;";

            System.out.println(query);
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return rs;
    }

    public static ResultSet checkLogin(String username , String hashPass) {
        ResultSet rs = null;
        String query = null;
        try {

            Connection conn = ConnectDatabase.getConn();

            Statement st = ConnectDatabase.ConnectionClass();
            String col = "*";
            String table = "`Data_Sign`.`tai_khoan` as tk left join `Data_Sign`.`chu_ky_ca_nhan` as ckcn on tk.id = ckcn.id_tai_khoan";
            String where = "where (ngay_het_han > current_timestamp() or ngay_het_han is NULL) and" +
                    " tk.username = '" + username + "' and tk.password = '" + hashPass + "';";
            query = "select " + col + " from " + table + " " + where;

            //'or 1=1 --

            query = "select * from `Data_Sign`.`tai_khoan` as tk left join `Data_Sign`.`chu_ky_ca_nhan` as ckcn on tk.id = ckcn.id_tai_khoan where (ngay_het_han > current_timestamp() or ngay_het_han is NULL) and tk.username = ? and tk.password = ? ;";

            try  {
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashPass);
                rs = preparedStatement.executeQuery();

                return rs;


            }
            catch (SQLException e) {
                System.out.println("Error+"+e);
            e.printStackTrace();
        }


        System.out.println(query);
        rs = st.executeQuery(query);
//            rs.next();

    }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }

}
