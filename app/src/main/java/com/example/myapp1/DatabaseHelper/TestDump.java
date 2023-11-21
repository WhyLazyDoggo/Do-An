package com.example.myapp1.DatabaseHelper;


import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDump {
    public static void main(String[] args) {
        System.out.println("Kiểm tra chạy thử một tệp duy nhất để check in tư");


        ResultSet rs = null;
        try {
            Statement st = ConnectDatabase.ConnectionClass();
            String query = "select * from `DB_ECC`.`tai_khoan` as tk left join `DB_ECC`.`chu_ky_ca_nhan` as ckcn on tk.id = ckcn.id_taiKhoan where tk.username = 'UserD' and tk.password = '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b';";
            System.out.println(query);
            rs = st.executeQuery(query);
            if(rs.next())
                System.out.println(rs.getString(3));
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }

    }

}
