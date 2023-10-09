package com.example.myapp1.fragmentcontent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.MainActivity;
import com.example.myapp1.R;

import com.example.myapp1.comfirmKy.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FragmentTest extends Fragment {


    private Button btnSuccess, btnFail,btnNull;
    private EditText edtNext, edtFreeText;
    MainActivity mainActivity;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        mView = inflater.inflate(R.layout.fragment_test, container, false);
        Button btnSuccess = (Button) mView.findViewById(R.id.btnSuccess);


        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ChonVanBan.class);
//                startActivity(intent);
                ResultSet rs3 = null;
                ResultSet rs2 = null;
                rs3 = SelectDB.getSiGroup();
                String msg ="";
                String Xgroup ="";
                String Rsum ="";
                String id_nhom = "";
                String sSumArray ="";
                try {
                    while (rs3.next()){

                        if (id_nhom.equals(rs3.getString("id_nhomky"))){
                            sSumArray += rs3.getString("Si")+" ";
                            System.out.println(id_nhom+" Giá trị của tệp là khi trùng nhóm: "+sSumArray);
                            System.out.println("Các giá trị của file là: ");
                            System.out.println(msg);
                            System.out.println(Xgroup);
                            System.out.println(Rsum);
                        }else {

                            try {
                                String signature = ecSHelper.getSignature(FragmentTest.this.getContext(),sSumArray,Rsum,Xgroup,msg);
                                System.out.println("Giá trị chữ ký là:"+signature);
                            }catch (Exception e){
                                System.out.println(e);
                            }

                            id_nhom = rs3.getString("id_nhomky");
                            sSumArray = rs3.getString("Si")+" ";
                            System.out.println(id_nhom+" Giá trị của tệp là khi khác nhóm: "+sSumArray);
                            System.out.println("Lấy giá trị msg, XGroup và Rsum mới");
                            rs2 = SelectDB.getMsgXgroupRsum(id_nhom);
                            rs2.next();
                            msg = rs2.getString("hash_vanban");
                            Xgroup = rs2.getString("X");
                            Rsum = rs2.getString("Rsum");
                        }

                        try {
                            String signature = ecSHelper.getSignature(FragmentTest.this.getContext(),sSumArray,Rsum,Xgroup,msg);
                            System.out.println("Giá trị chữ ký là:"+signature);
                        }catch (Exception e){
                            System.out.println("Chữ ký không được tạo ra, vô cùng xin lỗi hihi");
                            System.out.println(e);
                        }

                    }
                }catch (SQLException ex){
                    Log.e("error",ex.getMessage());
                }


            }
        });


        Button btnFail = (Button) mView.findViewById(R.id.btnFail);

        btnFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getContext(), KyXacNhanVanban.class);
                    startActivity(intent);
                }catch (Exception e ){
                    System.out.println(e);
                }

            }
        });

        return mView;
    }


}