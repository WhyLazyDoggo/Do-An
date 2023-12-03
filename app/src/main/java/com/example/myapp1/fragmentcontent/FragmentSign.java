package com.example.myapp1.fragmentcontent;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp1.GiaoDienChinh;
import com.example.myapp1.ManagerTask.CreateingAccount;
import com.example.myapp1.ManagerTask.ManagerUser;
import com.example.myapp1.checkSignature.checkNotDoneSignature;
import com.example.myapp1.checkSignature.checkSignatureDone;
import com.example.myapp1.createSignGroup.ChonVanBan;
import com.example.myapp1.R;
import com.example.myapp1.comfirmKy.KyXacNhanVanban;


public class FragmentSign extends Fragment {


    private View mView;
    

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = getContext().getSharedPreferences("preference_user", MODE_PRIVATE);
        System.out.println(prefs.getAll());

        switch (prefs.getString("role_user","")){
            case "Nhân viên":


                setItemUser(mView, prefs.getString("count_sign","-1"));
                break;

            case "Trưởng phòng":

                setItemTruongPhong(mView, prefs.getString("count_sign","-1"));
                break;
            case "HR":

                setItemQuanLy(mView);
                break;

            case "Khách hàng":
                setItemKhachHang(mView);
                break;
        }
    }

    private void setItemKhachHang(View view) {

        LinearLayout linearLayout5,linerSignerComfirm,linearLayout6;

        linearLayout5 = view.findViewById(R.id.linearLayout5);
        linerSignerComfirm = view.findViewById(R.id.linerSignerComfirm);
        linearLayout6 = view.findViewById(R.id.linearLayout6);

        linearLayout5.setVisibility(View.GONE);
        linerSignerComfirm.setVisibility(View.GONE);
        linearLayout6.setVisibility(View.GONE);

        ImageView ivCreatGroup = view.findViewById(R.id.ivCreatGroup);
        ivCreatGroup.setImageResource(R.drawable.icons8_rescan_document_100);

        ((TextView) view.findViewById(R.id.tvCreateGroup)).setText("Kiểm tra chữ ký");

        LinearLayout linerCreateGroup = view.findViewById(R.id.linerCreateGroup);
        linerCreateGroup.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), checkSignatureDone.class);
            startActivity(intent);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_sign, container, false);





        SharedPreferences prefs = getContext().getSharedPreferences("preference_user", MODE_PRIVATE);
        System.out.println(prefs.getAll());

        switch (prefs.getString("role_user","")){
            case "Nhân viên":


                setItemUser(mView, prefs.getString("count_sign","-1"));
                break;

            case "Trưởng phòng":

                setItemTruongPhong(mView, prefs.getString("count_sign","-1"));
                break;
            case "HR":

                setItemQuanLy(mView);
                break;
        }



        return mView;
    }

    private void setItemQuanLy(View view) {

        TextView tvCreateGroup = view.findViewById(R.id.tvCreateGroup);
        tvCreateGroup.setText("Tạo tài khoản");
        ImageView ivCreatGroup = view.findViewById(R.id.ivCreatGroup);
        ivCreatGroup.setImageResource(R.drawable.createuser);
        LinearLayout linerCreateGroup = view.findViewById(R.id.linerCreateGroup);

        linerCreateGroup.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateingAccount.class);
            startActivity(intent);
        });

        TextView tvSignerComfirm = view.findViewById(R.id.tvSignerComfirm);
        tvSignerComfirm.setText("Quản lý user");
        ImageView ivSignerComfirm = view.findViewById(R.id.ivSignerComfirm);
        ivSignerComfirm.setImageResource(R.drawable.manageruser);
        LinearLayout linerSignerComfirm = view.findViewById(R.id.linerSignerComfirm);
        linerSignerComfirm.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ManagerUser.class);
            startActivity(intent);
        });

        LinearLayout linearLayout5 = view.findViewById(R.id.linearLayout5);
        linearLayout5.setVisibility(View.GONE);

        LinearLayout linearLayout6 = view.findViewById(R.id.linearLayout6);
        linearLayout6.setVisibility(View.GONE);


        ((ImageView) view.findViewById(R.id.imageView10)).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.tvNum)).setVisibility(View.GONE);

    }

    private void setItemTruongPhong(View view, String count) {

        TextView tvNum = view.findViewById(R.id.tvNum);

        if (Integer.parseInt(count) > 10){
            ImageView imageView10 = view.findViewById(R.id.imageView10);
            imageView10.setImageResource(R.drawable.circle_green);
            count = "Ex";
        }

        tvNum.setText(count);


        LinearLayout linerCreateGroup = (LinearLayout) view.findViewById(R.id.linerCreateGroup);

        linerCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChonVanBan.class);
                startActivity(intent);
            }
        });

        LinearLayout linerSignerComfirm = (LinearLayout) view.findViewById(R.id.linerSignerComfirm);

        linerSignerComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KyXacNhanVanban.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayout5 = view.findViewById(R.id.linearLayout5);
        linearLayout5.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), checkNotDoneSignature.class);
            startActivity(intent);
        });


        TextView tvNullInfo = view.findViewById(R.id.tvNullInfo);
        tvNullInfo.setText("Quản lý user");
        ImageView ivNull2 = view.findViewById(R.id.ivNull2);
        ivNull2.setImageResource(R.drawable.manageruser);

        LinearLayout linearLayout6 = view.findViewById(R.id.linearLayout6);
        linearLayout6.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ManagerUser.class);
            startActivity(intent);


        });

    }

    private void setItemUser(View view, String count) {

        LinearLayout linerCreateGroup = (LinearLayout) view.findViewById(R.id.linerCreateGroup);

        linerCreateGroup.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChonVanBan.class);
            startActivity(intent);
        });


        LinearLayout linerSignerComfirm = (LinearLayout) view.findViewById(R.id.linerSignerComfirm);

        linerSignerComfirm.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), KyXacNhanVanban.class);
            startActivity(intent);
        });


        LinearLayout linearLayout5 = view.findViewById(R.id.linearLayout5);

        linearLayout5.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), checkNotDoneSignature.class);
            startActivity(intent);
        });


        LinearLayout linearLayout6 = view.findViewById(R.id.linearLayout6);
        linearLayout6.setVisibility(View.GONE);

        TextView tvNum = view.findViewById(R.id.tvNum);
        if (Integer.parseInt(count) > 10){
            ImageView imageView10 = view.findViewById(R.id.imageView10);
            imageView10.setImageResource(R.drawable.circle_green);
            count = "Ex";
        }

        tvNum.setText(count);


    }
}