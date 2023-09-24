package com.example.myapp1.createSignGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.GiaoDienChinh;
import com.example.myapp1.LoginTheme;
import com.example.myapp1.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaoNhomKy extends AppCompatActivity {
    RecyclerView recycler_view;
    List<TaoNhomModel> list = new ArrayList<>();
    TaoNhomAdapter adapter;
    private Toast mToast;
    private long backPressedTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_nhom_ky);


        Button button_confirm = (Button) findViewById(R.id.button_confirm);
        Button button_cancel = (Button) findViewById(R.id.button_cancel);


        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        System.out.println(prefs.getAll());


        addData();
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaoNhomAdapter(this,list);
        recycler_view.setAdapter(adapter);

        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id_vanBan = prefs.getString("id_van_ban","");
                if (backPressedTime + 2000 > System.currentTimeMillis()){
                    mToast.cancel();
                }
                backPressedTime = System.currentTimeMillis();


                if (adapter.getSelected().size()>0){
                    StringBuilder stringBuilder = new StringBuilder();
                    String id_user;

                    for (int i = 0 ; i <adapter.getSelected().size() ; i++){
                        id_user = adapter.getSelected().get(i).getId();

                        stringBuilder.append(adapter.getSelected().get(i).getUsername());
                        stringBuilder.append("\n");

                    }
                    showSuccessDialog(stringBuilder);
                }
                else {
                    mToast = Toast.makeText(TaoNhomKy.this, "Bạn chưa chọn gì cả", Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
            mToast = Toast.makeText(TaoNhomKy.this,"Bạn đang được quay lại giao diện trước",Toast.LENGTH_SHORT);
            SharedPreferences.Editor editor = getSharedPreferences("preference_user",MODE_PRIVATE).edit();
            editor.remove("id_van_ban");
            editor.commit();
            mToast.show();
            super.onBackPressed();

    }

    private void addData() {

        ResultSet rs = SelectDB.getNhanVien();
        try {
            while (rs.next()) {
                list.add(new TaoNhomModel(R.drawable.home_icon,rs.getString("id"),rs.getString("ten_nhan_vien"),rs.getString("role")));
            }

        }catch (SQLException e) {
            System.out.println("Error");
            Log.e(null, "Error connection!!! Tạo bảng KyTucXa chưa Pa?");
        }

    }

    private void showSuccessDialog(StringBuilder text){

        AlertDialog.Builder builder = new AlertDialog.Builder(TaoNhomKy.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(TaoNhomKy.this).inflate(
                R.layout.textdialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText("Gửi yêu cầu thành công");

        ((TextView) view.findViewById(R.id.textMessage)).setText(text);

        ((Button) view.findViewById(R.id.buttonAction)).setText("Hoàn thành");



        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                onBackPressed();
            }
        });

        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

}