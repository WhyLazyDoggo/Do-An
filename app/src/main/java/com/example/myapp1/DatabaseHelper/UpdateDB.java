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
            String where ="where ";
            String set="";
            String query = "update "+table+" set = '"+ set +"' "+where;

            rs = st.executeUpdate(query);
        }
        catch (SQLException ex) {
            Log.e("Error when connect SQL", ex.getMessage());
            ex.printStackTrace();
        }
    }


}