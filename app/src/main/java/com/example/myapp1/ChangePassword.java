package com.example.myapp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp1.DatabaseHelper.InsertDB;
import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.UpdateDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.ManagerTask.ResetPassword;
import com.example.myapp1.ManagerTask.ShowDetailActivity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePassword extends AppCompatActivity {

    EditText edtNewpass,edtRetype,edtOldPass;
    Button btnConfirm;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        prefs  = getSharedPreferences("preference_user", MODE_PRIVATE);
        editor = getSharedPreferences("preference_user",MODE_PRIVATE).edit();
        System.out.println(prefs.getAll());

        setView();
        setAction();
    }

    private void setAction() {


        btnConfirm.setOnClickListener(v -> {
            if (edtNewpass.length()*edtRetype.length()==0){
                Toast.makeText(getApplicationContext(),"Vui lòng điền thông tin mật khẩu mới",Toast.LENGTH_SHORT).show();
            } else if (!edtNewpass.getText().toString().equals(edtRetype.getText().toString())){
                Toast.makeText(getApplicationContext(),"Mật khẩu mới không trùng nhau, vui lòng nhập lại",Toast.LENGTH_SHORT).show();
            } else{

                ResultSet rs = null;
                String username = prefs.getString("username_user","");
                rs = SelectDB.checkLogin(username,ecSHelper.sha256(edtOldPass.getText().toString()));
                try{
                    if (rs.next()){
                        String hashPass = ecSHelper.sha256(edtRetype.getText().toString());

                        //Lưu lại mật khẩu tại đây
                        UpdateDB.updatePassword(prefs.getString("id_user",""), hashPass,"Hoạt động");

                        showSuccessDialog("Đổi thành công","Mật khẩu đã được đổi thành công");

                    }else{
                        showErrorOneButton("Lỗi xác thực","Sai mật khẩu");
                    }
                }catch (SQLException e) {
                    showErrorOneButton("Lỗi xác thực","Quá trình xác thực gặp sự cố/nVui lòng thử lại sau");
                }

            }
        });
    }

    private void setView() {
        edtNewpass = findViewById(R.id.edtNewpass);
        edtRetype = findViewById(R.id.edtRetype);
        edtOldPass = findViewById(R.id.edtOldPass);
        btnConfirm = findViewById(R.id.btnConfirm);

    }
    private void showErrorOneButton(String main, String cmt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ChangePassword.this).inflate(
                R.layout.popup_error_dialog_one_option,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(main);

        ((TextView) view.findViewById(R.id.textMessage)).setText(cmt);

        ((Button) view.findViewById(R.id.buttonAction)).setText("Xác nhận");

        AlertDialog alertDialog = builder.create();


        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

    private void showSuccessDialog(String mainText, String comment){

        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ChangePassword.this).inflate(
                R.layout.textdialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);
        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);

        ((Button) view.findViewById(R.id.buttonAction)).setText("Quay lại giao diện");



        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
                onBackPressed();
            }
        });

        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }


}