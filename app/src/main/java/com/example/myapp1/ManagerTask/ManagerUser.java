package com.example.myapp1.ManagerTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManagerUser extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    SharedPreferences prefs;
    RecyclerView recycler_view;

    List<UserModel> file_list = new ArrayList<>();
    SharedPreferences.Editor editFilter;
    SharedPreferences prefsFilter;
    int[] filter_input = new int[3];


    UserAdapter adapter;
    private long backPressedTime;

    @Override
    protected void onRestart() {
        super.onRestart();
        prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("preference_user", MODE_PRIVATE).edit();
        String option = prefs.getString("manager_user", "");
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


        editFilter = getSharedPreferences("filter", MODE_PRIVATE).edit();

        editFilter.putString("filtermode", "btnAll");
        editFilter.putString("filterRoom", prefs.getString("room_user", ""));
        editFilter.putString("filterRole", prefs.getString("role_user", ""));
        editFilter.commit();


        recycler_view = findViewById(R.id.recycler_view);

        setRecycleView();

        filter_input = getFilter_input();

        //Trong này sẽ có các chức năng chính là cài đặt lại mật khẩu user/ Nhma sẽ cần phải xem được thông tin của tài khoản trước

        //2 hướng option là tạo nguyên 1 cái trang nữa để hiển thị thông tin cá nhân. Admin chỉ có quyền reset mật khẩu chứ không có quyền chỉnh sửa thông tin cá nhân.
        //Thì có thể dùng luôn giao diện này làm giao diện hiển thị info tài khoản kèm các nút ở dưới như là :"Khóa tài khoản / Reset mật khẩu"

        ImageButton filter = findViewById(R.id.filter);

        filter.setOnClickListener(v -> {


            prefsFilter = getSharedPreferences("filter", MODE_PRIVATE);


            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.filter_nhan_vien);


            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.TOP);


            RadioGroup filterRoom2, filterRoom1;
            filterRoom1 = dialog.findViewById(R.id.filterRoom1);
            filterRoom2 = dialog.findViewById(R.id.filterRoom2);

            RadioGroup filtermode, filterRole;
            filtermode = dialog.findViewById(R.id.filtermode);
            filterRole = dialog.findViewById(R.id.filterRole);


            filtermode.check(filter_input[0]);

            if (filter_input[1]!=0) {
                filterRoom1.check(filter_input[1]);
                filterRoom2.check(filter_input[1]);
            }
            filterRole.check(filter_input[2]);


            RadioButton btnKeToan, btnHanhChinh, btnMarketing, btnNghienCuu, btnTaiChinh;
            btnKeToan = dialog.findViewById(R.id.btnKeToan);
            btnKeToan.getText();

            btnHanhChinh = dialog.findViewById(R.id.btnHanhChinh);
            btnMarketing = dialog.findViewById(R.id.btnMarketing);
            btnNghienCuu = dialog.findViewById(R.id.btnNghienCuu);
            btnTaiChinh = dialog.findViewById(R.id.btnTaiChinh);


//            EditText etSearch = dialog.findViewById(R.id.etSearch);



            filtermode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    filter_input[0] = checkedId;
                }
            });

            filterRoom1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (btnKeToan.isChecked() || btnHanhChinh.isChecked() || btnMarketing.isChecked()) {
                        filterRoom2.clearCheck();
                        filter_input[1] = checkedId;

                        System.out.println("Filter Room đổi thành: " + getTextFilter(dialog.findViewById(checkedId)));
                    }
                }
            });

            filterRoom2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    System.out.println("Cái 2 thay đổi");

                    if (btnNghienCuu.isChecked() || btnTaiChinh.isChecked()) {
                        filterRoom1.clearCheck();
                        filter_input[1] = checkedId;
                        System.out.println("Filter Room đổi thành: " + getTextFilter(dialog.findViewById(checkedId)));
                    }
                }
            });

            filterRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    filter_input[2] = checkedId;
                }
            });


            dialog.findViewById(R.id.buttonAction).setOnClickListener(v1 -> {
                //Lấy hết giá trị đang set để lưu vào đây
                System.out.println("Chọn thành công");


                System.out.println("Đầu vào tìm kiếm gồm là: " + getTextFilter(dialog.findViewById(filter_input[0])));
                System.out.println("Đầu vào tìm kiếm gồm là: " + getTextFilter(dialog.findViewById(filter_input[1])));
                System.out.println("Đầu vào tìm kiếm gồm là: " + getTextFilter(dialog.findViewById(filter_input[2])));

                setFilter(file_list , getTextFilter(dialog.findViewById(filter_input[0]))
                        , getTextFilter(dialog.findViewById(filter_input[1]))
                        , getTextFilter(dialog.findViewById(filter_input[2])));
                dialog.dismiss();

            });

            dialog.findViewById(R.id.buttonNo).setOnClickListener(v1 -> {
                filter_input = getFilter_input();
                dialog.dismiss();
            });

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            dialog.show();

        });

    }

    private int[] getFilter_input() {
        int[] result = {1, 2, 3};
        prefsFilter = getSharedPreferences("filter", MODE_PRIVATE);

        String mode = prefsFilter.getString("filtermode", "");
        String room = prefsFilter.getString("filterRoom", "");
        String role = prefsFilter.getString("filterRole", "");

        System.out.println("Giá trị mode: " + mode);
        System.out.println("Giá trị room: " + room);
        System.out.println("Giá trị role: " + role);

        switch (mode) {
            case "btnAll":
                result[0] = R.id.btnAll;
                break;
            case "btnTrue":
                result[0] = R.id.btnTrue;
                break;
            default:
                result[1] = R.id.btnAll;
                break;
        }

        switch (room) {
            case "Kế toán":
                result[1] = R.id.btnKeToan;
                break;
            case "Hành chính":
                result[1] = R.id.btnHanhChinh;
                break;
            case "Marketing":
                result[1] = R.id.btnMarketing;
                break;
            case "Nghiên cứu":
                result[1] = R.id.btnNghienCuu;
                break;
            case "Tài chính":
                result[1] = R.id.btnTaiChinh;
                break;
            default:
                result[1] = 0;
                break;
        }

        switch (role) {
            case "Nhân viên":
                result[2] = R.id.btnNhanVien;
                break;
            case "Trưởng phòng":
                result[2] = R.id.btnTruongPhong;
                break;
            case "HR":
                result[2] = R.id.btnTruongPhong;
                break;
        }


        return result;

    }

    public String getTextFilter(Object input) {
        RadioButton rdtmp = (RadioButton) input;
        return rdtmp.getText().toString().trim();
    }
    public void setFilter(List<UserModel> file_list, String mode_pick, String room, String role) {
        System.out.println("Sắp xếp theo phòng: "+room);
        System.out.println("Sắp xếp theo chức vụ: "+role);



        Collections.sort(file_list, new Comparator<UserModel>() {
            @Override
            public int compare(UserModel record1, UserModel record2) {

                //Kiểm tra sự là lấy tất cả hay chỉ đúng mỗi bản rõ

                // Đưa phòng cụ thể lên đầu
                if (record1.getPhong_ban().equals(room) && !record2.getPhong_ban().equals(room)) {
                    return -1;
                } else if (!record1.getPhong_ban().equals(room) && record2.getPhong_ban().equals(room)) {
                    return 1;
                } else if (!record1.getPhong_ban().equals(room) && !record2.getPhong_ban().equals(room)){
                    // Nếu cả hai không có "T" ở đầu hoặc cả hai đều có, thì sắp xếp theo bảng chữ cái
                    return record1.getFullname().compareTo(record1.getFullname());
                }

                // Sắp xếp theo room
                int roomComparison = record1.getPhong_ban().compareTo(record2.getPhong_ban());
                if (roomComparison == 0) {
                    if (role.equals("Nhân viên")) {
                        return record1.getRole().compareTo(record2.getRole());
                    }else return -record1.getRole().compareTo(record2.getRole());
                }

                return roomComparison;

            }
        });
        List<UserModel> myList = new ArrayList<>();
        System.out.println("Giá trị trên cùng là: " + file_list.get(0).getPhong_ban());

        if (mode_pick.equals("Tìm đúng")) {
            int check = 1;
            for (UserModel us : file_list) {
                if (us.getPhong_ban().equalsIgnoreCase(room)) {
                    myList.add(us);
                    check = 0;
                }
            }
            if (check == 1) {
                Toast.makeText(this, "Trả về kết quả mặc định không tìm thấy", Toast.LENGTH_SHORT).show();
                adapter.setList(file_list);
                recycler_view.setAdapter(adapter);
            } else adapter.setList(myList);
//        recycler_view.setAdapter(adapter);
        } else {
            adapter.setList(file_list);
            recycler_view.setAdapter(adapter);
        }



    }


    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, getList());
        recycler_view.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemswipe = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(ManagerUser.this, "Bạn đang swipe ở ", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper swap = new ItemTouchHelper(itemswipe);
        swap.attachToRecyclerView(recycler_view);
    }

    private List<UserModel> getList() {
        prefs = getSharedPreferences("preference_user", MODE_PRIVATE);

//        UpdateDB.updatetmp("4",R.drawable.home_icon);
//
//        UpdateDB.updatetmp("7",R.drawable.pdf_icon);


        ResultSet rs = SelectDB.getAllUser();
        try {
            while (rs.next()) {

                if (prefs.getString("role_user", "").equals("HR") ||
                        (prefs.getString("role_user", "").equals("Trưởng phòng") && rs.getString("phong_ban").equals(prefs.getString("room_user", ""))))

                    file_list.add(new UserModel(rs.getString(1),
//                            rs.getInt("avatar"),
                            ecSHelper.getAvatarPicture(rs.getString("avatar")),
                            rs.getString("username"),
                            rs.getString("ten_nhan_vien"),
                            rs.getString("chuc_vu"),
                            rs.getString("phong_ban"),
                            rs.getString("status"),
                            rs.getString("created_at"),
                            rs.getString("update_at")));
            }
        } catch (SQLException e) {
            System.out.println("Error");
            Log.e(null, "Error connection!!! Tạo bảng KyTucXa chưa Pa?");
        }

        return file_list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        editFilter.clear();
        editFilter.commit();
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

}