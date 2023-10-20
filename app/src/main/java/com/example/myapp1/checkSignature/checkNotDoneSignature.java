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
import com.example.myapp1.GiaoDienChinh;
import com.example.myapp1.R;
import com.example.myapp1.comfirmKy.FileAdapter;
import com.example.myapp1.comfirmKy.FileModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class checkNotDoneSignature extends AppCompatActivity {

    RecyclerView recycler_view;

    checkNotSignatureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_not_done_signature);

        recycler_view = findViewById(R.id.recycler_view);

        setRecycleView();

        Button button_done = (Button) findViewById(R.id.button_done);

        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checkNotDoneSignature.this, checkSignatureDone.class);
                startActivity(intent);
            }
        });


    }

    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new checkNotSignatureAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<checkNotSignatureModel> getList(){
        List<checkNotSignatureModel> file_list = new ArrayList<>();
        String user_id;
        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        ResultSet rs = SelectDB.getProcessGroupSign(prefs.getString("id_user",""));
//        file_list.add(new checkNotSignatureModel("" + (1 + 1), "Mickery", "10000", "4/5",R.drawable.pdf_icon));

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
                file_list.add(new checkNotSignatureModel(rs.getString("id"),
                        rs.getString("ten_van_ban"), rs.getString("created_at"),
                        rs.getString("sl_ky")+"/"+rs.getString("tong_user"),picture));
            }

        }catch (SQLException e) {
            System.out.println("Error");
            System.out.println(e);
            Log.e(null, "Error connection!!! Tạo bảng KyTucXa chưa Pa?");
        }

        return file_list;
    }
}