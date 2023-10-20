package com.example.myapp1.checkSignature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class checkSignatureDone extends AppCompatActivity {

    RecyclerView recycler_view;

    checkSignatureAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_signature_done);
        getInfor();
        recycler_view = findViewById(R.id.recycler_view);

        setRecycleView();

        Button button_notdone = (Button) findViewById(R.id.button_notdone);

        button_notdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new checkSignatureAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<checkSignatureModel> getList(){
        List<checkSignatureModel> file_list = new ArrayList<>();
        ResultSet rs = SelectDB.getFileSignDone();

        try {
            while (rs.next()){
                String type_file = rs.getString("ten_van_ban");
                int picture = R.drawable.xls_icon;
                int lastDotIndex = type_file.lastIndexOf(".");
                if (lastDotIndex != -1) {
                    switch (type_file.substring(lastDotIndex)) {
                        case ".txt":
                            picture = R.drawable.txt_icon;
                            break;
                        case ".pdf":
                            picture = R.drawable.pdf_icon;
                            break;
                        case ".doc":
                            picture = R.drawable.doc_icon;
                            break;
                        default:
                            picture = R.drawable.xls_icon;
                            break;
                    }

                }

                file_list.add(new checkSignatureModel(rs.getString("id"),rs.getString("ten_van_ban"),
                        rs.getString("noidungtomtat"),rs.getString("created_at"),
                        rs.getString("signature"),rs.getString("X"),picture));

            }
        }catch (SQLException ex){
            System.out.println(ex);
            Log.e("error",ex.getMessage());
        }

        return file_list;
    }


    private void getInfor(){
        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        String name = prefs.getString("id_user","");
        System.out.println(name);

    }

}