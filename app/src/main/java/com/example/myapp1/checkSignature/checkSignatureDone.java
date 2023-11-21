package com.example.myapp1.checkSignature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class checkSignatureDone extends AppCompatActivity {

    RecyclerView recycler_view;

    checkSignatureAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_signature_done);
        getInfor();
        recycler_view = findViewById(R.id.recycler_view);

        setRecycleView();

        Button button_notdone = (Button) findViewById(R.id.button_notdone);

        button_notdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new checkSignatureAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<checkSignatureModel> getList(){
        List<checkSignatureModel> file_list = new ArrayList<>();
        ResultSet rs = SelectDB.getFileSignDone();

        String temp = "";
        String tmpmember ="";
        List<String> addMember = new ArrayList<>();
        checkSignatureModel add = new checkSignatureModel();
        int check = 1;
        int count = 0;
        try {
            while (rs.next()){
                count ++;
                String type_file = rs.getString("ten_van_ban");
                int picture = R.drawable.xls_icon;
                int lastDotIndex = type_file.lastIndexOf(".");
                if (lastDotIndex != -1) {
                    switch (type_file.substring(lastDotIndex)) {
                        case ".txt":
                            picture = R.drawable.txt_icon;
                            break;
                        case ".pdf":
                            picture = R.drawable.pdf_icon;
                            break;
                        case ".doc":
                            picture = R.drawable.doc_icon;
                            break;
                        default:
                            picture = R.drawable.xls_icon;
                            break;
                    }

                }

                checkSignatureModel tmp = new checkSignatureModel(rs.getString("id"),rs.getString("ten_van_ban"),
                        rs.getString("noi_dung_tom_tat"),rs.getString("created_at"),
                        rs.getString("signature"),rs.getString("X"),picture);



                if (check==1){
                    //Đây là giá trị đầu tiên, nên sẽ cho vào làm đầu và thêm toàn bộ thông tin như bình thường, khởi tạo id user các thứ luôn
                    add = tmp;
                    System.out.println("Thêm nhân viên vào văn bản "+rs.getString("id")+" / "+rs.getString("ten_nhan_vien"));

                    addMember.add(rs.getString("ten_nhan_vien"));
                    tmpmember = tmpmember+"- "+rs.getString("ten_nhan_vien") +"\n";

                    check=2;
                } else if ((add.getSignature().equals(tmp.getSignature()))){
                    //Cái này là thêm nhân viên vào bên trong thôi, không có gì đáng trách cả
                    System.out.println("Thêm nhân viên vào văn bản "+rs.getString("id")+" / "+rs.getString("ten_nhan_vien"));

                    addMember.add(rs.getString("ten_nhan_vien"));
                    tmpmember = tmpmember+"- "+rs.getString("ten_nhan_vien") +"\n";

                }else{
                    //Trường hợp cuối cùng, tức là đã lấy hết thông tin


                    add.setSubMember(addMember);
                    add.setTmp(tmpmember);
                    add.displayInfo();


                    System.out.println("---Dump---");
                    System.out.println("Trong này có các nhân viên: ");
                    for (String subject : add.getSubMember()) {
                        System.out.println(subject);
                    }

                    file_list.add(add);
                    System.out.println("----Check thôi---");
                    file_list.get(0).displayInfo();
                    System.out.println("--- ---");

                    addMember.clear();
                    tmpmember="";
                    add = tmp;
                    addMember.add(rs.getString("ten_nhan_vien"));
                    tmpmember = tmpmember+"- "+rs.getString("ten_nhan_vien") +"\n";
                }

//
//                if ((temp.equals(rs.getString("signature"))) || (check==1)){
//
//                    System.out.println("Thêm giá trị thặng dư");
//                    System.out.println("Thêm nhân viên vào văn bản "+rs.getString("id")+" / "+rs.getString("ten_nhan_vien"));
//                    addMember.add(rs.getString("ten_nhan_vien"));
//                    check = 2;
//                    System.out.println("Check count= "+count);
//                    temp = rs.getString("signature");
//
//
//                }else{
//                    System.out.println("Check count= "+count);
//                    temp = rs.getString("signature");
//                    checkSignatureModel tmp = new checkSignatureModel(rs.getString("id"),rs.getString("ten_van_ban"),
//                            rs.getString("noidungtomtat"),rs.getString("created_at"),
//                            rs.getString("signature"),rs.getString("X"),picture);
//                    tmp.setSubMember(addMember);
//                    System.out.println("---Dump---");
//                    tmp.displayInfo();
//                    System.out.println("---Dump---");
//                    System.out.println("Trong này có các nhân viên: ");
//                    for (String subject : tmp.getSubMember()) {
//                        System.out.println(subject);
//                    }
//                    file_list.add(tmp);
//
//
//                    addMember.clear();
//                    check = 1;
//                    System.out.println("-----");
//
//
//                }


            }
        }catch (Exception ex){
            System.out.println(ex);
            Log.e("error",ex.getMessage());
        }
        System.out.println("Check kết quả cuối");

        file_list.get(0).displayInfo();
        return file_list;
    }


    private void getInfor(){
        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        String name = prefs.getString("id_user","");
        System.out.println(name);

    }

}