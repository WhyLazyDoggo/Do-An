package com.example.myapp1.createSignGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapp1.R;
import com.example.myapp1.comfirmKy.FileAdapter;
import com.example.myapp1.comfirmKy.FileModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChonVanBan extends AppCompatActivity {

    RecyclerView recycler_view;

    ChonVanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_van_ban);

        recycler_view = findViewById(R.id.recycler_view);




        ImageButton pictureTest = (ImageButton) findViewById(R.id.info_btn);

        pictureTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDialog();
            }
        });
        setRecycleView();
    }

    public void testDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChonVanBan.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ChonVanBan.this).inflate(
                R.layout.popup_info_file_for_signer,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textDump)).setText("Tệp tên xyz");

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
        for (int i =0; i<19; i++){
            file_list.add(new ChonVanModel(""+(i+1), "Mickey", "30/11/2021"));
        }
        return file_list;
    }

}