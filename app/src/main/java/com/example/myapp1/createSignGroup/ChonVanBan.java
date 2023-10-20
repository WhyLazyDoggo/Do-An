package com.example.myapp1.createSignGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class ChonVanBan extends AppCompatActivity {

    RecyclerView recycler_view;

    ChonVanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_van_ban);
        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        System.out.println(prefs.getAll());

        recycler_view = findViewById(R.id.recycler_view);

        setRecycleView();
    }

    //Thêm chức năng khi quay lại sẽ tự động update lại văn bản

    public void testDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChonVanBan.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ChonVanBan.this).inflate(
                R.layout.popup_info_file_for_signer,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.name_file)).setText("Tệp tên xyz");

        ((TextView) view.findViewById(R.id.textDumpInfo)).setText("Nội dung cơ bản:" +
                "\n <<<Hình ảnh về tệp các thứ" +
                "\n" +
                "\n" +
                "\n" +
                "\n\t\t\t >>>>>");

        ((Button) view.findViewById(R.id.buttonAction)).setText("Xác nhận giao");
        ((Button) view.findViewById(R.id.buttonNo)).setText("Thoát");



        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                System.out.println("-------------------------------------------------------------");
                System.out.println("Bạn đã click thành công");
                System.out.println("-------------------------------------------------------------");
                Intent intent = new Intent( ChonVanBan.this, TaoNhomKy.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        }

        alertDialog.show();

    }

    private void showChoiceDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChonVanBan.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ChonVanBan.this).inflate(
                R.layout.textdialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText("Day chi la ban thu nghiem danh cho mot doan van viet dai vo cung dai de xem co tu xuong dong khong");

        ((TextView) view.findViewById(R.id.textMessage)).setText("Chúc mừng thành công");

        ((Button) view.findViewById(R.id.buttonAction)).setText("Hoàn thành");



        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        }

        alertDialog.show();

    }

    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChonVanAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<ChonVanModel> getList() {
        List<ChonVanModel> file_list = new ArrayList<>();

        ResultSet rs = SelectDB.getVanBan(null, null);
        try{
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

                file_list.add(new ChonVanModel(rs.getString("id"), rs.getString("ten_van_ban") ,rs.getString("noidungtomtat") , rs.getString("created_at"),picture));
            }
        }catch (SQLException e) {
            System.out.println("Error");
            Log.e(null, "Error connection!!! Tạo bảng KyTucXa chưa Pa?");
        }
        return file_list;
    }

}