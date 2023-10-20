package com.example.myapp1.ManagerTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.UpdateDB;
import com.example.myapp1.R;
import com.example.myapp1.createSignGroup.ChonVanAdapter;
import com.example.myapp1.createSignGroup.ChonVanModel;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManagerUser extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;

    RecyclerView recycler_view;

    UserAdapter adapter;


    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("preference_user",MODE_PRIVATE).edit();
        String option = prefs.getString("manager_user","");
        if (option.equals("Res")) {

            System.out.println("Đang thực hiện lại hành động");
            setContentView(R.layout.activity_manager_user);
            recycler_view = findViewById(R.id.recycler_view);
            setRecycleView();
            editor.remove("manager_user");
            editor.commit();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user);
        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        System.out.println(prefs.getAll());


        recycler_view = findViewById(R.id.recycler_view);

        setRecycleView();



        //Trong này sẽ có các chức năng chính là cài đặt lại mật khẩu user/ Nhma sẽ cần phải xem được thông tin của tài khoản trước

        //2 hướng option là tạo nguyên 1 cái trang nữa để hiển thị thông tin cá nhân. Admin chỉ có quyền reset mật khẩu chứ không có quyền chỉnh sửa thông tin cá nhân.
        //Thì có thể dùng luôn giao diện này làm giao diện hiển thị info tài khoản kèm các nút ở dưới như là :"Khóa tài khoản / Reset mật khẩu"




//        sendOtp("+84 837 222 637",false);
//        sendOtp2("+84837222637");


    }

    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this,getList());
        recycler_view.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemswipe = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(ManagerUser.this,"Bạn đang swipe",Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper swap = new ItemTouchHelper(itemswipe);
        swap.attachToRecyclerView(recycler_view);
    }

    private List<UserModel> getList() {
        List<UserModel> file_list = new ArrayList<>();


//        UpdateDB.updatetmp("4",R.drawable.home_icon);
//
//        UpdateDB.updatetmp("7",R.drawable.pdf_icon);


        ResultSet rs = SelectDB.getAllUser();
        try{
            while (rs.next()){
                file_list.add(new UserModel(rs.getString(1),
                        rs.getInt("avatar"),
                        rs.getString("username"),
                        rs.getString("ten_nhan_vien"),
                        rs.getString("role"),
                        rs.getString("phong_ban"),
                        rs.getString("status"),
                        rs.getString("created_at"),
                        rs.getString("update_at")));
            }
        }catch (SQLException e) {
            System.out.println("Error");
            Log.e(null, "Error connection!!! Tạo bảng KyTucXa chưa Pa?");
        }

        return file_list;
    }

    private void sendOtp2(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                System.out.println("Xác thực thành công");
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                System.out.println("Xác thực thất bại");

                            }
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                System.out.println("Mã OTP đang được gửi đi");
                                System.out.println(verificationCode);
                            }
                        })         // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private void sendOtp(String phoneNumber, boolean isResend) {

        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                System.out.println("Xác thực thành công");
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                System.out.println("Xác thực thất bại");

                            }
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                System.out.println("Mã OTP đang được gửi đi");
                                System.out.println(verificationCode);
                            }
                        });      // OnVerificationStateChangedCallbacks


        if(isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }



    }
}