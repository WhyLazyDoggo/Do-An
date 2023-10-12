package com.example.myapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp1.DatabaseHelper.ConnectDatabase;
import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.ManagerTask.CreateingAccount;

import org.jetbrains.annotations.Contract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginTheme extends AppCompatActivity {

    private Toast mToast;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_theme);


//        MaterialButton ButtonLogin = (MaterialButton) findViewById(R.id.ButtonLogin);

        Button ButtonLoginn = (Button) findViewById(R.id.ButtonLogin);
        Button ButtonResent = (Button) findViewById(R.id.buttonResent);

        TextView textView = (TextView) findViewById(R.id.textView4);
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        ButtonResent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessDialog();
            }
        });


        ButtonLoginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Đã bấm nút này");

                SharedPreferences.Editor editor = getSharedPreferences("preference_user",MODE_PRIVATE).edit();

                String name = username.getText().toString();

                if (name.length()>1)
                {
                    mToast = Toast.makeText(LoginTheme.this, "Đang đăng nhập vào hệ thống", Toast.LENGTH_SHORT);
                    mToast.show();
                    ResultSet rs = null;
                    rs = SelectDB.checkLogin(name,"123");
                    String role ="";
                    //Lấy in4 người dùng
                    try {
                        editor.clear();
                        editor.commit();
                        editor.putString("user",rs.getString("id"));
                        editor.putString("pubkey",rs.getString("khoa_cong_khai"));
                        editor.putString("ten_nhan_vien",rs.getString("ten_nhan_vien"));
                        editor.putString("privatekey",getPrivateKey(rs.getString("id")));
                        editor.putString("role",rs.getString("role"));
                        role = rs.getString("role");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    editor.commit();
                    System.out.println(role);
                    if (role.equals("HR")){
                        Intent intent = new Intent( LoginTheme.this, CreateingAccount.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(LoginTheme.this, GiaoDienChinh.class);
                        startActivity(intent);
                        finish();
                    }
                }else {

                    mToast = Toast.makeText(LoginTheme.this, "Vui lòng nhập UserName", Toast.LENGTH_SHORT);
                    mToast.show();
                }
                if (backPressedTime + 2000 > System.currentTimeMillis()){
                    mToast.cancel();
                }
                backPressedTime = System.currentTimeMillis();




            }
        });

    }


    @SuppressLint("MissingInflatedId")
    private void showSuccessDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginTheme.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(LoginTheme.this).inflate(
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


    private String getPrivateKey(String IdName){
        String var="";
        ResultSet rs = null;

        rs = SelectDB.getPrivateKey(IdName);
        try {
            var = rs.getString("private_key");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return var;
    }

    public void checkUser(){
        String userName;
        String userPass;
        String query = "select * from sign_test";
        
        
        Statement stmt = ConnectDatabase.makeStatement();

        try {
            ResultSet rs = stmt.executeQuery(query);
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}