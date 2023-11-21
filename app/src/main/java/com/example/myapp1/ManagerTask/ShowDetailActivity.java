package com.example.myapp1.ManagerTask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp1.DatabaseHelper.UpdateDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.R;
import com.example.myapp1.createSignGroup.TaoNhomKy;

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

        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);

        System.out.println("Giá trị chức vụ:" + prefs.getString("role_user", ""));

        System.out.println("Giá trị của id_user là: " + prefs.getString("id_user", ""));


        Button editPro5 = findViewById(R.id.btnFix);


        if ((!prefs.getString("id_user", "").equals(object.getId())) && !prefs.getString("role_user", "").equals("HR")) {
            editPro5.setVisibility(View.GONE);
        } else {
            editPro5.setOnClickListener(v -> {
                System.out.println("Bạn đã bấm btnFix");


                if (editPro5.getText().toString().equals("Sửa thông tin")){
                    findViewById(R.id.editName).setVisibility(View.VISIBLE);

                    editPro5.setText("Lưu thông tin");

                }else{
                    findViewById(R.id.editName).setVisibility(View.GONE);
                    editPro5.setText("Sửa thông tin");
                }










            });
        }


        if (!prefs.getString("role_user", "").equals("HR")) {
            LinearLayout LinearRole = findViewById(R.id.LinearRole);
            LinearRole.setVisibility(View.GONE);

        } else {


            //Kiểm tra nếu là chức vụ của HR thì có thể thực hiện thêm 3 chức năng bổ trợ khác nè
            ivLock.setOnClickListener(v -> {
                try {

                    if (object.getStatus().equals("Tạo mới")) {


                        System.out.println("Option if Tạo mới");

                        showDeny("Khóa tài khoản", "Trạng thái tài khoản đang là " + object.getStatus() +
                                "\nbạn chắc muốn khóa tài khoản này không?", "Xác nhận khóa", "Quay lại", "Khóa tạo mới");
                    } else if (object.getStatus().equals("Khóa tạo mới")) {

                        System.out.println("Option if đang khóa Tạo mới");
                        showDeny("Mở khóa tài khoản", "Trạng thái tài khoản đang là " + object.getStatus() +
                                "\nbạn chắc muốn mở khóa tài khoản này không?", "Xác nhận mở khóa", "Quay lại", "Tạo mới");


                    } else if (!object.getStatus().equals("Hoạt động")) {
                        System.out.println("Option if khum phải hoạt động");
                        showDeny("Mở khóa tài khoản", "Trạng thái tài khoản đang là " + object.getStatus() + "\nbạn chắc muốn mở khóa tài khoản này không?", "Xác nhận mở khóa", "Quay lại", "Hoạt động");
                    } else {
                        System.out.println("Option Còn lại");
                        showDeny("Khóa tài khoàn", "Trạng thái tài khoản đang là " + object.getStatus() + "\nbạn chắc muốn khóa tài khoản này không?", "Xác nhận khóa", "Quay lại", "Khóa");
                    }
                    System.out.println("Bạn đã bấm nút lock");
                } catch (Exception ex) {

                }
            });

            ivReset.setOnClickListener(v -> {

                try {
//                UpdateDB.updateStatus(object.getId(),"Đổi mật khẩu");

                    String newpass = ecSHelper.getResetPass(this, object.getFullname());
                    setResetPassword("Xác nhận reset mật khẩu", "Bạn chắc chắn muốn reset lại mật khẩu của người dùng " + object.getFullname() + "?\nHãy xác nhận lại với chủ sở hữu thông qua số điện thoại", "Xác nhận", "Quay lại", newpass);
                    System.out.println("Bạn đã bấm Reset");

                } catch (Exception ex) {

                }
            });

            ivFroz.setOnClickListener(v -> {

                try {
//                    UpdateDB.updateStatus(object.getId(),"Đình chỉ");

                    System.out.println("Bạn đã bấm Forz");
                    setDeleteUser("Xóa tài khoản", "Xác nhận xóa tài khoản, tài khoản sẽ bị xóa sau 7 ngày kể từ ngày lên lệnh", "Xác nhận", "Quay lại");

                } catch (Exception ex) {

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
        ImageView ivAvatar = findViewById(R.id.avatar);
        ivAvatar.setImageResource(object.getProfileImage());

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


    private void setDeleteUser(String mainText, String comment, String buttonYes, String buttonNo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ShowDetailActivity.this).inflate(R.layout.popup_warning_dialog, null);
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);

        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);

        ((Button) view.findViewById(R.id.buttonAction)).setText(buttonYes);

        ((Button) view.findViewById(R.id.buttonNo)).setText(buttonNo);

        AlertDialog alertDialog = builder.create();


        view.findViewById(R.id.buttonNo).setOnClickListener(v -> {
            alertDialog.dismiss();
            showSuccessDialogNoReturn("Xác nhận hủy hành động", "Bạn đã hủy hành động xóa tài khoản vừa rồi");
        });


        view.findViewById(R.id.buttonAction).setOnClickListener(v -> {

            //Khởi động view 2 để bắt đầu thực thi
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            View viewmini = LayoutInflater.from(this).inflate(R.layout.popup_warning_gettext_dialog, null);
            builder2.setView(viewmini);

            ((TextView) viewmini.findViewById(R.id.textMain)).setText("Xác thực tài khoản");
            ((EditText) viewmini.findViewById(R.id.textMessage)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            ((EditText) viewmini.findViewById(R.id.textMessage)).setHint("Hãy nhập \"YES\" để xác nhận xóa");

            EditText tMess = viewmini.findViewById(R.id.textMessage);


            AlertDialog alertDialog2 = builder2.create();


            viewmini.findViewById(R.id.buttonAction).setOnClickListener(v1 -> {

                System.out.println(tMess.getText());

                if (tMess.getText().toString().equals("YES")) {
                    alertDialog2.dismiss();
                    UpdateDB.updateStatus(object.getId(), "Xóa tài khoản");
                    showSuccessDialog("Xác nhận xóa tài khoản \nthành công", "Sau 7 ngày tài khoản " + object.getFullname() + " sẽ bị xóa!!!");
                } else {
                    System.out.println("Bạn đã hủy chức năng này");
                }

                alertDialog2.dismiss();
                alertDialog.dismiss();


            });


            alertDialog2.show();

        });


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }


    private void setResetPassword(String mainText, String comment, String buttonYes, String buttonNo, String hashPass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ShowDetailActivity.this).inflate(R.layout.popup_warning_dialog, (ConstraintLayout) findViewById(R.id.layoutDialogContainer));
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);

        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);

        ((Button) view.findViewById(R.id.buttonAction)).setText(buttonYes);

        ((Button) view.findViewById(R.id.buttonNo)).setText(buttonNo);

        AlertDialog alertDialog = builder.create();


        AtomicReference<Boolean> check = new AtomicReference<>(false);

        view.findViewById(R.id.buttonNo).setOnClickListener(v -> {
            alertDialog.dismiss();
            showSuccessDialogNoReturn("Xác nhận hủy hành động", "Bạn đã hủy hành động khóa vừa rồi");
        });


        view.findViewById(R.id.buttonAction).setOnClickListener(v -> {
            alertDialog.dismiss();

            System.out.println(hashPass);
            UpdateDB.updatePassword(object.getId(), ecSHelper.sha256(hashPass), "Cài lại mật khẩu");
            showSuccessDialogNoReturn("Xác nhận Reset mật khẩu", "Bạn đã reset mật khẩu\ncho tài khoản " + object.getFullname() + " thành công\n" + "Mật khẩu mới là: " + hashPass);


        });


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }


    private void showDeny(String mainText, String comment, String buttonYes, String buttonNo, String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ShowDetailActivity.this).inflate(R.layout.popup_warning_dialog, (ConstraintLayout) findViewById(R.id.layoutDialogContainer));
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);

        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);

        ((Button) view.findViewById(R.id.buttonAction)).setText(buttonYes);

        ((Button) view.findViewById(R.id.buttonNo)).setText(buttonNo);

        AlertDialog alertDialog = builder.create();


        AtomicReference<Boolean> check = new AtomicReference<>(false);

        view.findViewById(R.id.buttonNo).setOnClickListener(v -> {
            alertDialog.dismiss();
            showSuccessDialogNoReturn("Xác nhận hủy hành động", "Bạn đã hủy hành động khóa vừa rồi");
        });


        view.findViewById(R.id.buttonAction).setOnClickListener(v -> {
            alertDialog.dismiss();


            UpdateDB.updateStatus(object.getId(), status);
            showSuccessDialog("Xác nhận " + (String) ((status.contains("Khóa")) ? "khóa" : "mở khóa") + "\nthành công", "Bạn đã " + (String) ((status.contains("Khóa")) ? "khóa" : "mở khóa") + " thành công tài khoản " + object.getFullname());

        });


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }


    private void showSuccessDialogNoReturn(String mainText, String comment) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ShowDetailActivity.this).inflate(R.layout.textdialog, (ConstraintLayout) findViewById(R.id.layoutDialogContainer));
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

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

    private void showSuccessDialog(String mainText, String comment) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ShowDetailActivity.this).inflate(R.layout.textdialog, (ConstraintLayout) findViewById(R.id.layoutDialogContainer));
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);
        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);

        ((Button) view.findViewById(R.id.buttonAction)).setText("Quay lại giao diện");


        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("preference_user", MODE_PRIVATE).edit();
                editor.putString("manager_user", "Res");
                System.out.println("\n\n\n\n\n\n********************");
                System.out.println("Warning yêu cầu bấm res lại toàn bộ mọi thứ");
                System.out.println("\n\n\n\n\n\n********************");
                editor.commit();
                alertDialog.dismiss();
                onBackPressed();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }


}