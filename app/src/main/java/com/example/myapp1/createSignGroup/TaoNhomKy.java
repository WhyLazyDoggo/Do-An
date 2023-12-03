package com.example.myapp1.createSignGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.myapp1.DatabaseHelper.DialogHelper;
import com.example.myapp1.DatabaseHelper.InsertDB;
import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.UpdateDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.LoginTheme;
import com.example.myapp1.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TaoNhomKy extends AppCompatActivity {
    RecyclerView recycler_view;
    List<TaoNhomModel> list = new ArrayList<>();
    TaoNhomAdapter adapter;
    private Toast mToast;
    private long backPressedTime;

    String textFilter = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_nhom_ky);
        Button button_confirm = findViewById(R.id.button_confirm);
        Button button_cancel = findViewById(R.id.button_cancel);

        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        System.out.println(prefs.getAll());
        addData();
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaoNhomAdapter(this, list);
        recycler_view.setAdapter(adapter);

        //Nhớ băm cái này ra
        String hashMsg = ecSHelper.getHashMsg_type2(this, prefs.getString("noi_dung_van_ban", ""));

        String pubkeyMain = prefs.getString("pubkey", "");

        final String[] privkeyMain = {"Khởi tạo hàm đọc file nhập mật khẩu để ký văn bản"};

        String id_vanBan = prefs.getString("id_van_ban", "");
        String id_main = prefs.getString("id_user", "");



        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    mToast.cancel();
                }
                backPressedTime = System.currentTimeMillis();




                if (adapter.getSelected().size() > 0) {
                    final String[] passwd = {""};
                    System.out.println("Mật khẩu trước tiên được là: " + passwd[0]);

//                    showWarning_forGet_privateKey();

                    //Sẽ rút gọn sau, nhma giờ chạy được cái đã :(

                    AlertDialog.Builder builder = new AlertDialog.Builder(TaoNhomKy.this, R.style.AlertDialogTheme);
                    View view = LayoutInflater.from(TaoNhomKy.this).inflate(
                            R.layout.popup_warning_gettext_dialog,
                            null
                    );
                    builder.setView(view);

                    ((TextView) view.findViewById(R.id.textMain)).setText("Xác nhận giao");


                    AlertDialog alertDialog = builder.create();

                    view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            passwd[0] = String.valueOf(((TextView) view.findViewById(R.id.textMessage)).getText());
                            alertDialog.dismiss();

                            System.out.println("Mật khẩu nhận được là: " + passwd[0]);

                            String encryptPrikeyFromFile = readfile(ecSHelper.sha256(prefs.getString("id_user", "XXXX") + "Lưu trữ khóa"));
                            System.out.println("Khóa được lưu trong file là: " + encryptPrikeyFromFile);
                            //Xong rồi giải mã lại để kiểm tra chéo
                            String decryptPrikey = ecSHelper.deCryptKey(getContentResolver(), String.valueOf(passwd[0] + prefs.getString("id_user", "XXXY")), encryptPrikeyFromFile);
                            System.out.println("Bản mã sau khi giải mã là: " + decryptPrikey);

                            String pubkeytemp = ecSHelper.getPubkey(v.getContext(), decryptPrikey);

                            System.out.println("Khóa công khai lấy được là: " + pubkeytemp);
                            if (!prefs.getString("pubkey", "XXX").equals(pubkeytemp)) {
                                System.out.println("Lỗi rồi, sai khóa r nhé");
                                DialogHelper.showErrorOneButton(TaoNhomKy.this, "Xác thực tài khoản", "Xác thực tài khoản thất bại, vui lòng thử lại sau!");

                            } else {

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(TaoNhomKy.this, R.style.AlertDialogTheme);
                                View viewProcess = LayoutInflater.from(TaoNhomKy.this).inflate(
                                        R.layout.popup_loading_process,
                                        null
                                );
                                builder2.setView(viewProcess);

                                ((TextView) viewProcess.findViewById(R.id.textMain)).setText("Quá trình tạo nhóm ký");

                                TextView textprocess = viewProcess.findViewById(R.id.textMessage);

                                AlertDialog alertProcess = builder2.create();

                                alertProcess.show();


                                privkeyMain[0] = decryptPrikey;
                                System.out.println("So sánh khóa giống nhau 100% y đúc");
                                textprocess.setText("Đang thực hiện hành động tạo nhóm");

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Thực hiện các đoạn mã sau khi độ trễ


                                        if (1 == 1) {
                                            Toast.makeText(TaoNhomKy.this, "Đang tạo nhóm ký bước 1", Toast.LENGTH_SHORT).show();

                                            String KiMain = ecSHelper.getKi(TaoNhomKy.this, prefs.getString("pubkey", ""), hashMsg);
                                            String tmpHashIdVanban = ecSHelper.getHashMsg_type2(TaoNhomKy.this, Instant.now().toEpochMilli() + id_main + id_vanBan);
                                            System.out.println("Giá trị tempt là: " + tmpHashIdVanban);

                                            //Bước 1: Tạo ra nhóm ký
                                            Toast.makeText(TaoNhomKy.this, "Đang tạo nhóm ký bước 2", Toast.LENGTH_SHORT).show();

                                            textprocess.setText("Đang tạo nhóm ký");
                                            InsertDB.createNhomKy(id_main, tmpHashIdVanban, id_vanBan);

                                            textprocess.setText("Đang lấy thông tin cơ bản của nhóm ký");
                                            String id_nhom_ky = SelectDB.getNhomKy_ByHash(tmpHashIdVanban);


                                            //Bước 2: Thêm UserMain vào trước / Coi như người này được xếp thứ tự đầu tiên
                                            //Làm xong nhớ bỏ comment để thử nghiệm, thử nghiệm 1 lần duy nhất :(
                                            Toast.makeText(TaoNhomKy.this, "Đang tạo nhóm ký bước 3", Toast.LENGTH_SHORT).show();
                                            textprocess.setText("Đang thêm trưởng nhóm vào nhóm ký");
                                            InsertDB.insertMemberToNhomky(prefs.getString("id_user", ""), id_nhom_ky, KiMain);


                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("Danh sách nhân viên gồm:").append("\n");
                                            String id_user;
                                            String ki_person;

                                            String ki_group = KiMain;
                                            String tmpXGroup = pubkeyMain;
                                            System.out.println(KiMain);
                                            for (int i = 0; i < adapter.getSelected().size(); i++) {

                                                id_user = adapter.getSelected().get(i).getId();
                                                String publicKey_user = adapter.getSelected().get(i).getPublickey();


                                                //Lấy giá trị của từng người, thì mình select toàn bộ luôn insert update các kiểu


                                                try {

                                                    Toast.makeText(TaoNhomKy.this, "Đang tạo chữ ký cho nhân viên " + adapter.getSelected().get(i).getUsername(), Toast.LENGTH_SHORT).show();

                                                    textprocess.setText("Đang thêm "+adapter.getSelected().get(i).getUsername()+" vào nhóm ký");
                                                    System.out.println("\n\n------------------Tính cá nhân-------------------------------");
                                                    System.out.println("Khóa công khai cá nhân: " + publicKey_user);
                                                    tmpXGroup += " " + publicKey_user;

                                                    //Thêm User vào db nhóm ký để tính toán giá trị ký riêng, còn về db lưu trữ ki_person thì sẽ lưu riêng...?
                                                    //Vì ki_main không có tác dụng gì lắm vì có xong giá trị ki của user_main sẽ ký thẳng luôn coi như là ký nháp

                                                    //Ở đây sẽ có thể createKi xong gửi cho từng người một
                                                    ki_person = "Hàm tạo ra giá trị ki mỗi người";
                                                    ki_person = ecSHelper.getKi(TaoNhomKy.this, publicKey_user, hashMsg);
                                                    System.out.println("Giá trị ki mỗi người là: " + ki_person);

                                                    //createKi xong lưu trữ lại giá trị Ki đó để tính Rsum ở phía dưới
                                                    ki_group += " " + ki_person; //Lưu lại tổng bộ các giá trị ki mà được lưu trữ để tính toán Rsum
                                                    System.out.println("Giá trị ki_nhóm khi lưu vào là: " + ki_group);

                                                    //Bước 3: Thêm các User vào nhóm ký, đây là bước để tạo ra queue yêu cầu ký xác nhận
                                                    //Tạo nhóm ký và gửi cho từng user về tham số ki của mỗi người để ký. Còn chủ nhóm sẽ ký trực tiếp khi mới tạo ra nhóm luôn
                                                    InsertDB.insertMemberToNhomky(id_user, id_nhom_ky, ki_person);


                                                } catch (Exception e) {
                                                    Log.e("error", e.getMessage());
//                                                    DialogHelper.showErrorOneButton(TaoNhomKy.this, "Lỗi chưa được sửa", "Bạn vô tình tạo ra lỗi. Xin vui lòng thử lại sau. Hãy check LogCat");
                                                    alertProcess.dismiss();
                                                    break;
                                                }
                                                stringBuilder.append(adapter.getSelected().get(i).getUsername());
                                                stringBuilder.append("\n");

                                            }
                                            try {
                                                System.out.println("\n\nGía trị ngoài");
                                                System.out.println("Giá trị tmp group:" + tmpXGroup);

                                                String Lgroup = ecSHelper.getL(TaoNhomKy.this, tmpXGroup);
                                                String Xgroup = ecSHelper.getX(TaoNhomKy.this, tmpXGroup);

                                                String Xgroup_sign = ecSHelper.getXcheck(TaoNhomKy.this, tmpXGroup);

                                                //Tính toán giá trị Rsum ở đây thông qua ki ở phía trên
                                                String Rsum = ecSHelper.getRsum(TaoNhomKy.this, ki_group);
                                                System.out.println("Giá trị Rsum tính ra được là: " + Rsum);
                                                //Tính toán giá trị c ở đây thông qua Rsum và Xgroup đã tính
                                                String c = ecSHelper.getC(TaoNhomKy.this, Rsum, Xgroup, hashMsg);
                                                System.out.println("Giá trị của C là: " + c);

                                                //==> Ném giá trị Rsum, Xgroup và c vào thông tin nhóm ký
                                                //==> Chuyển qua giao diện ký xác nhận văn bản sẽ tính toán giá trị si thông qua c chung và ki gửi cho từng người
                                                //Sau khi người cuối cùng ký thì sẽ tính sSum vào
                                                //1: Phiên đăng nhập mới nhất của người ký
                                                //2: Kệ thẳng, cứ ai ký 5/5 thì tạo ra chữ ký luôn vì si là phải công khai mà. Còn xác định si kiểu gì thì khum bik hihi.
                                                //Sẽ kiểm chứng lại si thông qua khóa bí mật của mỗi người sau, nhma giờ chưa phải lúc


//                                        InsertDB.insertNhomKy(prefs.getString("user",""),Xgroup,Rsum,c,hashMsg);


                                                System.out.println("Giá trị của các tham số lần lượt là:");
                                                System.out.println("privateKey = " + privkeyMain[0]);
                                                System.out.println("Cgroup = " + c);
                                                System.out.println("Ki person = " + KiMain);
                                                System.out.println("Lgroup = " + Lgroup);

                                                System.out.println("Tới bước tạo ra si");
                                                String Si = ecSHelper.getSi(TaoNhomKy.this, privkeyMain[0], c, KiMain, Lgroup, Xgroup_sign, Rsum);
                                                System.out.println("Si = " + Si);

                                                textprocess.setText("Đang thiết lập cuối cùng cho nhóm ký");
                                                Toast.makeText(TaoNhomKy.this, "Đang thiết lập cuối cùng cho nhóm ký", Toast.LENGTH_SHORT).show();
                                                UpdateDB.kyVanBan(prefs.getString("id_user", ""), id_nhom_ky, Si, "9999999999999999999999999999999");
//                                        UpdateDB.updateNhomKy(Lgroup, Xgroup, Rsum, c, ecSHelper.getHashMsg_type2(TaoNhomKy.this, prefs.getString("noi_dung_van_ban", "")), tmpHashIdVanban);
                                                textprocess.setText("Hoàn thành tạo nhóm");
                                                UpdateDB.updateNhomKy(Lgroup, Xgroup, Rsum, c, tmpHashIdVanban);

                                                System.out.println(Xgroup);
                                                alertProcess.dismiss();
                                                DialogHelper.showSuccessDialogWithReturn(TaoNhomKy.this, "Gửi yêu cầu thành công", stringBuilder, "Hoàn thành");
//                                        showSuccessDialog(stringBuilder);
                                            } catch (Exception ex) {
                                                Log.e("error", ex.getMessage());
                                                DialogHelper.showErrorOneButton(TaoNhomKy.this, "Lỗi khi tạo chữ ký nhóm", "Bạn vô tình tạo ra lỗi. Xin vui lòng thử lại sau. Hãy check LogCat");
                                            }
                                        }


                                        UpdateDB.dropbackLater();

                                    }
                                }, 1000); // Thời gian độ trễ trong milliseconds (ở đây là 2000ms = 2s)

                                if (alertProcess.getWindow() != null) {
                                    alertProcess.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                }

                            }


                        }
                    });

                    view.findViewById(R.id.buttonNo).setOnClickListener(v1 -> {
                        alertDialog.dismiss();
                    });

                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }

                    alertDialog.show();


                } else {
                    mToast = Toast.makeText(TaoNhomKy.this, "Bạn chưa chọn gì cả", Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Bạn đã bấm hủy toàn bộ");

                for (int i = 0; i < adapter.getItemCount(); i++) {
                    adapter.list.get(i).setChecked(false);
                }
                adapter.setList(list);
            }
        });


        setFilter();

    }

    private void setFilter() {
        ImageView filter = findViewById(R.id.filter);

        filter.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.filter_van_ban);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.TOP);
            dialog.show();

            SearchView svSearch = dialog.findViewById(R.id.svSearch);
            svSearch.setQuery(textFilter, false);
            svSearch.clearFocus();
            ((TextView) dialog.findViewById(R.id.tvMain)).setText("Tìm kiếm nhân viên");
            svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    System.out.println("Đang tìm kiếm văn bản");

                    filterList(newText);
                    textFilter = newText;
                    return true;
                }
            });


        });
    }

    @Override
    public void onBackPressed() {
        mToast = Toast.makeText(TaoNhomKy.this, "Bạn đang được quay lại giao diện trước", Toast.LENGTH_SHORT);
        SharedPreferences.Editor editor = getSharedPreferences("preference_user", MODE_PRIVATE).edit();
        editor.remove("id_van_ban");
        editor.commit();
        mToast.show();
        super.onBackPressed();

    }

    private void filterList(String newText) {
        List<TaoNhomModel> filter_list = new ArrayList<>();
        for (TaoNhomModel item : list) {
            if (item.getUsername().toLowerCase().contains(newText.toLowerCase())) {
                filter_list.add(item);
            }

        }

        if (filter_list.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setList(filter_list);
        }

    }

    private void addData() {
        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        ResultSet rs = SelectDB.getNhanVien(prefs.getString("id_user", ""), prefs.getString("room_user", ""));
        try {
            while (rs.next()) {
                list.add(new TaoNhomModel(ecSHelper.getAvatarPicture(rs.getString("avatar")),
                        rs.getString("id") + "",
                        rs.getString("ten_nhan_vien") + "",
                        rs.getString("chuc_vu") + " | " + rs.getString("phong_ban"),
                        rs.getString("khoa_cong_khai") + ""));
            }

        } catch (SQLException e) {
            System.out.println("Error");
            Log.e(null, "Error connection!!! Tạo bảng KyTucXa chưa Pa?");
        }

    }

    public String readfile(String filepath) {
        filepath = filepath + ".key";
        try {
            System.out.println("-----");
            System.out.println("Đang đọc trong file: " + filepath);
            InputStream inputStream = openFileInput(filepath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String output = "";
            output = bufferedReader.readLine();

            inputStream.close();
            System.out.println("Kết quả khi đọc file:" + output);
            System.out.println("-----");
            return String.valueOf(output);
        } catch (IOException e) {
            System.out.println("Error" + e);
            return "Error404";
        }
    }
}