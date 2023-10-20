package com.example.myapp1.ManagerTask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp1.DatabaseHelper.UpdateDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.R;

import java.util.concurrent.atomic.AtomicReference;

public class ShowDetailActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvRole;
    private TextView tvId;
    private TextView tvUserName;
    private TextView tvFullName;
    private TextView tvRoleBtm;
    private TextView tvRoom;
    private TextView tvStatus;
    private TextView tvDate;
    private TextView tvUpdate;
    ImageButton ivLock;
    ImageButton ivReset;
    ImageButton ivFroz;
    private UserModel object;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        
        
        initView();
        getItem();
        setCheckRole();
    }

    private void setCheckRole() {

        System.out.println(object.getRole());
        if (!object.getRole().equals("HR")){
            LinearLayout LinearRole = findViewById(R.id.LinearRole);
            LinearRole.setVisibility(View.GONE);

        }else{
            ivLock.setOnClickListener(v -> {
                try {
                    if (!object.getStatus().equals("Khóa")){
                        showDeny("Khóa tài khoàn","Trạng thái tài khoản đang là "+object.getStatus()+"\nbạn chắc muốn khóa tài khoản này không?",
                                "Xác nhận khóa","Hủy hành động","Khóa");
                    } else {
                        showDeny("Mở khóa tài khoản","Trạng thái tài khoản đang là "+object.getStatus()+"\nbạn chắc muốn mở khóa tài khoản này không?",
                                "Xác nhận mở khóa","Hủy hành động","Hoạt động");
                    }
                    System.out.println("Bạn đã bấm nút lock");
                }catch (Exception ex)
                {

                }
            });

            ivReset.setOnClickListener(v -> {

                try {
//                UpdateDB.updateStatus(object.getId(),"Đổi mật khẩu");

                    setResetPassword("Xác nhận reset mật khẩu","Bạn chắc chắn muốn reset lại mật khẩu của người dùng "+object.getFullname()+"?\nHãy xác nhận lại với chủ sở hữu thông qua số điện thoại",
                            "Xác nhận","Quay lại","1234444");
                    System.out.println("Bạn đã bấm Reset");

                }catch (Exception ex)
                {

                }
            });

            ivFroz.setOnClickListener(v -> {

                try {
                    UpdateDB.updateStatus(object.getId(),"Đình chỉ");
                    System.out.println("Bạn đã bấm Forz");

                }catch (Exception ex)
                {

                }


            });

        }
    }

    private void getItem() {
        object = (UserModel) getIntent().getSerializableExtra("object");
        tvName.setText(object.getFullname());
        tvRole.setText(object.getRole());
        tvId.setText(object.getId());
        tvUserName.setText(object.getUsername());
        tvFullName.setText(object.getFullname());
        tvRoleBtm.setText(object.getRole());
        tvRoom.setText(object.getPhong_ban());
        tvStatus.setText(object.getStatus());
        tvDate.setText(object.getCreated_at());
        tvUpdate.setText(object.getUpdate_at());

    }

    private void initView() {
        tvName = findViewById(R.id.tvName);
        tvRole = findViewById(R.id.tvRole);
        tvId = findViewById(R.id.tvId);
        tvUserName = findViewById(R.id.tvUserName);
        tvFullName = findViewById(R.id.tvFullName);
        tvRoleBtm = findViewById(R.id.tvRoleBtm);
        tvRoom = findViewById(R.id.tvRoom);
        tvStatus = findViewById(R.id.tvStatus);
        tvDate = findViewById(R.id.tvDate);
        tvUpdate = findViewById(R.id.tvUpdate);
        ivLock = findViewById(R.id.ivLock);
        ivReset = findViewById(R.id.ivReset);
        ivFroz = findViewById(R.id.ivFroz);

    }

    private void setResetPassword(String mainText, String comment, String buttonYes, String buttonNo,String hashPass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ShowDetailActivity.this).inflate(
                R.layout.popup_warning_dialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);

        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);

        ((Button) view.findViewById(R.id.buttonAction)).setText(buttonYes);

        ((Button) view.findViewById(R.id.buttonNo)).setText(buttonNo);

        AlertDialog alertDialog = builder.create();


        AtomicReference<Boolean> check = new AtomicReference<>(false);

        view.findViewById(R.id.buttonNo).setOnClickListener(v -> {
            alertDialog.dismiss();
            showSuccessDialogNoReturn("Xác nhận hủy hành động","Bạn đã hủy hành động khóa vừa rồi");
        });


        view.findViewById(R.id.buttonAction).setOnClickListener(v -> {
            alertDialog.dismiss();
//            UpdateDB.updatePassword(object.getId(), hashPass,"Đổi mật khẩu");
            showSuccessDialogNoReturn("Xác nhận Reset mật khẩu","Bạn đã reset mật khẩu\ncho tài khoản "+object.getFullname()+" thành công");

            String newpass = ecSHelper.getResetPass(this,object.getFullname());

            System.out.println(newpass);



        });


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }


    private void showDeny(String mainText, String comment, String buttonYes, String buttonNo,String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ShowDetailActivity.this).inflate(
                R.layout.popup_warning_dialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);

        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);

        ((Button) view.findViewById(R.id.buttonAction)).setText(buttonYes);

        ((Button) view.findViewById(R.id.buttonNo)).setText(buttonNo);

        AlertDialog alertDialog = builder.create();


        AtomicReference<Boolean> check = new AtomicReference<>(false);

        view.findViewById(R.id.buttonNo).setOnClickListener(v -> {
            alertDialog.dismiss();
            showSuccessDialogNoReturn("Xác nhận hủy hành động","Bạn đã hủy hành động khóa vừa rồi");
        });


        view.findViewById(R.id.buttonAction).setOnClickListener(v -> {
            alertDialog.dismiss();


            UpdateDB.updateStatus(object.getId(), status);
            showSuccessDialog("Xác nhận "+(String) ((status.equals("Khóa")) ? "khóa" : "mở khóa")+"\nthành công", "Bạn đã "+ (String) ((status.equals("Khóa")) ? "khóa" : "mở khóa")+" thành công tài khoản " + object.getFullname());

        });


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }


    private void showSuccessDialogNoReturn(String mainText, String comment){

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ShowDetailActivity.this).inflate(
                R.layout.textdialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);
        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);

        ((Button) view.findViewById(R.id.buttonAction)).setText("Xác nhận");



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

    private void showSuccessDialog(String mainText, String comment){

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ShowDetailActivity.this).inflate(
                R.layout.textdialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);
        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);

        ((Button) view.findViewById(R.id.buttonAction)).setText("Quay lại\ngiao diện");



        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("preference_user",MODE_PRIVATE).edit();
                editor.putString("manager_user","Res");
                editor.commit();
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