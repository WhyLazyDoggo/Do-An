package com.example.myapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.ManagerTask.CreateingAccount;
import com.example.myapp1.ManagerTask.ManagerUser;
import com.example.myapp1.ManagerTask.ResetPassword;
import com.example.myapp1.ManagerTask.UserModel;
import com.example.myapp1.comfirmKy.KyXacNhanVanban;
import com.example.myapp1.fragmentcontent.FragmentInfoAll;

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

        username.setText("UserD");
        password.setText("1");

        ButtonResent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessDialog();
            }
        });


        ButtonLoginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("preference_user",MODE_PRIVATE).edit();



                String name = username.getText().toString();
                String pass = password.getText().toString();


                if (name.length()>0 && pass.length()>0)
                {
                    System.out.println("Bản mã băm thử của hàm: " + ecSHelper.sha256(pass));

                    //Lấy thông tin check xem mật khẩu trùng với pass trước chưa gòi tính típ

                    mToast = Toast.makeText(LoginTheme.this, "Đang đăng nhập vào hệ thống", Toast.LENGTH_SHORT);
                    mToast.show();
                    ResultSet rs = null;
                    rs = SelectDB.checkLogin(name,ecSHelper.sha256(pass));
                    String role ="";

                    //Trùng thì kiểm tra tới trạng thái hoạt động - Tùy vào trạng thái hoạt động rồi sẽ hiển thị thông tin khác biệt sau
                try{

                    if (rs.next()) {
                        if (rs.getString("status").equals("Khóa")) {
                            showErrorOneButton("Tài khoản bị đình chỉ", "Vui lòng liên hệ với quản lý hoặc trưởng phòng để mở khóa tài khoản!");
                        }   else if(rs.getString("status").equals("Cài lại mật khẩu")){
                            Intent intent = new Intent(LoginTheme.this, ResetPassword.class);
                            startActivity(intent);
                        } else {




                            switch (rs.getString("role")) {
                                case "Nhân viên" :

                                    editor.clear();
                                    editor.putString("user", rs.getString("id"));
                                    editor.putString("pubkey", rs.getString("khoa_cong_khai"));
                                    editor.putString("ten_nhan_vien", rs.getString("ten_nhan_vien"));
                                    editor.putString("privatekey", getPrivateKey(rs.getString("id")));
                                    editor.putString("role", rs.getString("role"));



                                    editor.putString("id_user",rs.getString(1));
                                    editor.putString("avatar_user",rs.getString("avatar"));
                                    editor.putString("username_user",rs.getString("username"));
                                    editor.putString("name_user",rs.getString("ten_nhan_vien"));
                                    editor.putString("role_user",rs.getString("role"));
                                    editor.putString("room_user",rs.getString("phong_ban"));
                                    editor.putString("status_user",rs.getString("status"));
                                    editor.putString("created_time_user",rs.getString("created_at"));
                                    editor.putString("update_time_user",rs.getString("update_at"));



                                    break;

                                case "Trưởng phòng":

                                    editor.clear();
                                    editor.putString("user", rs.getString("id"));
                                    editor.putString("pubkey", rs.getString("khoa_cong_khai"));
                                    editor.putString("ten_nhan_vien", rs.getString("ten_nhan_vien"));
                                    editor.putString("privatekey", getPrivateKey(rs.getString("id")));
                                    editor.putString("role", rs.getString("role"));




                                    editor.putString("id_user",rs.getString(1));
                                    editor.putString("avatar_user",rs.getString("avatar"));
                                    editor.putString("username_user",rs.getString("username"));
                                    editor.putString("name_user",rs.getString("ten_nhan_vien"));
                                    editor.putString("role_user",rs.getString("role"));
                                    editor.putString("room_user",rs.getString("phong_ban"));
                                    editor.putString("status_user",rs.getString("status"));
                                    editor.putString("created_time_user",rs.getString("created_at"));
                                    editor.putString("update_time_user",rs.getString("update_at"));


                                    break;


                                case "HR":


                                    editor.clear();
                                    editor.putString("user", rs.getString("id"));
                                    editor.putString("ten_nhan_vien", rs.getString("ten_nhan_vien"));
                                    editor.putString("role", rs.getString("role"));






                                    editor.putString("id_user",rs.getString(1));
                                    editor.putString("avatar_user",rs.getString("avatar"));
                                    editor.putString("username_user",rs.getString("username"));
                                    editor.putString("name_user",rs.getString("ten_nhan_vien"));
                                    editor.putString("role_user",rs.getString("role"));
                                    editor.putString("room_user",rs.getString("phong_ban"));
                                    editor.putString("status_user",rs.getString("status"));
                                    editor.putString("created_time_user",rs.getString("created_at"));
                                    editor.putString("update_time_user",rs.getString("update_at"));


                                    break;

                                default:
                                    showErrorOneButton("Hả????", "Oh wow you have a bug");
                                    break;

                            }

                            Intent intent = new Intent(LoginTheme.this, GiaoDienChinh.class);
                            startActivity(intent);
                            finish();

                        }

                        editor.commit();

                    }else {
                        showErrorOneButton("Sai thông tin","Bạn đã nhập sai tài khoản hoặc mật khẩu");
                    }

                } catch (SQLException e) {

                    showErrorOneButton("Lỗi đăng nhập","Quá trình đăng nhập gặp sự cố/nVui lòng thử lại sau");



//                    throw new RuntimeException(e);
                }










                    //Lấy in4 người dùng
//                    try {
//                        editor.clear();
//                        editor.commit();
//                        editor.putString("user",rs.getString("id"));
//                        editor.putString("pubkey",rs.getString("khoa_cong_khai"));
//                        editor.putString("ten_nhan_vien",rs.getString("ten_nhan_vien"));
//                        editor.putString("privatekey",getPrivateKey(rs.getString("id")));
//                        editor.putString("role",rs.getString("role"));
//                        role = rs.getString("role");
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                    editor.commit();
//                    System.out.println(role);
//                    if (role.equals("HR")){
//                        Intent intent = new Intent( LoginTheme.this, ManagerUser.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                    else {
//                        Intent intent = new Intent(LoginTheme.this, GiaoDienChinh.class);
//                        startActivity(intent);
//                        finish();
//                    }
//




                }else {
                    mToast = Toast.makeText(LoginTheme.this, "Vui lòng điền đẩy đủ các trường thông tin", Toast.LENGTH_SHORT);
                    mToast.show();
                }
                if (backPressedTime + 2000 > System.currentTimeMillis()){
                    mToast.cancel();
                }
                backPressedTime = System.currentTimeMillis();




            }
        });

    }


    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            mToast.cancel();
            super.onBackPressed();
            return;
        }else{
            mToast = Toast.makeText(LoginTheme.this,"Bấm lại lần nữa để thoát",Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void showErrorOneButton(String main, String cmt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginTheme.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(LoginTheme.this).inflate(
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
            var = rs.getString("khoa_bi_mat");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return var;
    }


}