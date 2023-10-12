package com.example.myapp1.fragmentcontent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.MainActivity;
import com.example.myapp1.R;

import com.example.myapp1.comfirmKy.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FragmentTest extends Fragment {

//https://www.youtube.com/watch?v=xxW5xxkNtrM
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


        Button btnNull = (Button) mView.findViewById(R.id.btnNull);

        btnNull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(mView.getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    sendOTP();
                }
                else{
                    ActivityCompat.requestPermissions((Activity) mView.getContext(),new String[]{Manifest.permission.SEND_SMS},100);
                }



            }
        });

        return mView;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                sendOTP();
            }else {
                Toast.makeText(mView.getContext(),"Lỗi cmnr",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendOTP() {

        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage("Chúc mừng bạn đã bị hack");
        String phoneNumber = "0823176357";
        smsManager.sendMultipartTextMessage(phoneNumber,null,parts,null,null);
        System.out.println("Đã bấm gửi tin");


    }


}