package com.example.myapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapp1.DatabaseHelper.ConnectDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {






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




                Intent intent = new Intent(MainActivity.this,LoginTheme.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}