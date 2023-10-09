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

import com.airbnb.lottie.L;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.myapp1.DatabaseHelper.InsertDB;
import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.UpdateDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.R;

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

        //Nhớ băm cái này ra
        String hashMsg =ecSHelper.getHashMsg_type2(this,prefs.getString("noi_dung_van_ban",""));

        String pubkeyMain = prefs.getString("pubkey","");
        String privkeyMain = prefs.getString("privatekey","");

        String id_vanBan = prefs.getString("id_van_ban","");
        String id_main = prefs.getString("user","");



        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backPressedTime + 2000 > System.currentTimeMillis()){
                    mToast.cancel();
                }
                backPressedTime = System.currentTimeMillis();

                if (adapter.getSelected().size()>0){
                    String KiMain = ecSHelper.getKi(TaoNhomKy.this,prefs.getString("pubkey",""),hashMsg);

                    String tmpHashIdVanban = ecSHelper.getHashMsg_type2(TaoNhomKy.this,Instant.now().toEpochMilli()+id_main+id_vanBan);

                    System.out.println("Giá trị tempt là: "+tmpHashIdVanban);

                    //Bước 1: Tạo ra nhóm ký
                    InsertDB.createNhomKy(id_main,tmpHashIdVanban);
                    String id_nhom_ky = SelectDB.getNhomKy_ByHash(tmpHashIdVanban);

                    //Bước 2: Thêm UserMain vào trước / Coi như người này được xếp thứ tự đầu tiên
                    //Làm xong nhớ bỏ comment để thử nghiệm, thử nghiệm 1 lần duy nhất :(
                    InsertDB.insertMemberToNhomky(prefs.getString("user",""),id_vanBan,id_nhom_ky,KiMain);


                    StringBuilder stringBuilder = new StringBuilder();
                    String id_user;
                    String ki_person;

                    String ki_group= KiMain;
                    String tmpXGroup = pubkeyMain;
                    System.out.println(KiMain);
                    ResultSet rs;
                    for (int i = 0 ; i <adapter.getSelected().size() ; i++){
                        id_user = adapter.getSelected().get(i).getId();
                        rs = SelectDB.getPubKey(id_user);
                        try {
                            rs.next();
                            System.out.println("Khóa công khai cá nhân: "+ rs.getString("khoa_cong_khai"));
                            tmpXGroup +=" "+rs.getString("khoa_cong_khai");

                            //Thêm User vào db nhóm ký để tính toán giá trị ký riêng, còn về db lưu trữ ki_person thì sẽ lưu riêng...?
                            //Vì ki_main không có tác dụng gì lắm vì có xong giá trị ki của user_main sẽ ký thẳng luôn coi như là ký nháp

                            //Ở đây sẽ có thể createKi xong gửi cho từng người một
                            ki_person ="Hàm tạo ra giá trị ki mỗi người";
                            ki_person = ecSHelper.getKi(TaoNhomKy.this,rs.getString("khoa_cong_khai"),hashMsg);
                            System.out.println("Giá trị ki mỗi người là: "+ki_person);

                            //createKi xong lưu trữ lại giá trị Ki đó để tính Rsum ở phía dưới
                            ki_group +=" "+ ki_person; //Lưu lại tổng bộ các giá trị ki mà được lưu trữ để tính toán Rsum
                            System.out.println("Giá trị ki_nhóm khi lưu vào là: "+ki_group);

                            //Bước 3: Thêm các User vào nhóm ký, đây là bước để tạo ra queue yêu cầu ký xác nhận
                            //Tạo nhóm ký và gửi cho từng user về tham số ki của mỗi người để ký. Còn chủ nhóm sẽ ký trực tiếp khi mới tạo ra nhóm luôn
                            InsertDB.insertMemberToNhomky(id_user,id_vanBan,id_nhom_ky,ki_person);



                        } catch (SQLException e) {
                            Log.e("error",e.getMessage());
                        }
                        stringBuilder.append(adapter.getSelected().get(i).getUsername());
                        stringBuilder.append("\n");

                    }
                    System.out.println(tmpXGroup);

                    String Lgroup = ecSHelper.getL(TaoNhomKy.this,tmpXGroup);
                    String Xgroup = ecSHelper.getX(TaoNhomKy.this, tmpXGroup);

                    //Tính toán giá trị Rsum ở đây thông qua ki ở phía trên
                    String Rsum = ecSHelper.getRsum(TaoNhomKy.this,ki_group);
                    System.out.println("Giá trị Rsum tính ra được là: "+Rsum);
                    //Tính toán giá trị c ở đây thông qua Rsum và Xgroup đã tính
                    String c = ecSHelper.getC(TaoNhomKy.this,Rsum,Xgroup,hashMsg);
                    System.out.println("Giá trị của C là: "+c);

                    //==> Ném giá trị Rsum, Xgroup và c vào thông tin nhóm ký
                    //==> Chuyển qua giao diện ký xác nhận văn bản sẽ tính toán giá trị si thông qua c chung và ki gửi cho từng người
                    //Sau khi người cuối cùng ký thì sẽ tính sSum vào
                        //1: Phiên đăng nhập mới nhất của người ký
                        //2: Kệ thẳng, cứ ai ký 5/5 thì tạo ra chữ ký luôn vì si là phải công khai mà. Còn xác định si kiểu gì thì khum bik hihi.
                            //Sẽ kiểm chứng lại si thông qua khóa bí mật của mỗi người sau, nhma giờ chưa phải lúc
//                    InsertDB.insertNhomKy(prefs.getString("user",""),Xgroup,Rsum,c,hashMsg);


                    System.out.println("Giá trị của các tham số lần lượt là:");
                    System.out.println("privateKey = " + privkeyMain);
                    System.out.println("Cgroup = "+c);
                    System.out.println("Ki person = "+KiMain);
                    System.out.println("Lgroup = "+ Lgroup);

                    String Si = ecSHelper.getSi(TaoNhomKy.this,privkeyMain,c,KiMain,Lgroup);
                    UpdateDB.kyVanBan(prefs.getString("user",""),id_nhom_ky,Si,"9999999999999999999999999999999");
                    UpdateDB.updateNhomKy(Lgroup,Xgroup,Rsum,c,ecSHelper.getHashMsg_type2(TaoNhomKy.this,prefs.getString("noi_dung_van_ban","")),tmpHashIdVanban);

                    System.out.println(Xgroup);
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
        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        ResultSet rs = SelectDB.getNhanVien(prefs.getString("user",""));
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