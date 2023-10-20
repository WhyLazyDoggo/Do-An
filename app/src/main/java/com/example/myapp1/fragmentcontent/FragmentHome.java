package com.example.myapp1.fragmentcontent;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp1.R;


public class FragmentHome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textNameId;
        textNameId = view.findViewById(R.id.textNameId);
        SharedPreferences prefs = getContext().getSharedPreferences("preference_user", MODE_PRIVATE);
        System.out.println(prefs.getAll());
        textNameId.setText(prefs.getString("name_user",""));


        switch (prefs.getString("role_user","")){
            case "Nhân viên":


                setItemUser(view);
                break;

            case "Trưởng phòng":

                setItemTruongPhong(view);
                break;
            case "HR":

                setItemQuanLy(view);
                break;
        }





        return view;
    }

    private void setItemUser(View view) {

        LinearLayout SmalLin = view.findViewById(R.id.SmalLin);
        SmalLin.setVisibility(View.GONE);

    }

    private void setItemTruongPhong(View view) {
        TextView tV6 = view.findViewById(R.id.tV6);
        tV6.setText("Quản Lý user");
        ImageView ivIvQuanLy = view.findViewById(R.id.ivIvExtra1);
        ivIvQuanLy.setImageResource(R.drawable.doc_icon);
    }

    private void setItemQuanLy(View view){
        LinearLayout LinBig = view.findViewById(R.id.LinBig);
        LinBig.setVisibility(View.GONE);

        TextView tV6 = view.findViewById(R.id.tV6);
        tV6.setText("Tạo tài khoản");
        ImageView ivIvQuanLy = view.findViewById(R.id.ivIvExtra1);
        ivIvQuanLy.setImageResource(R.drawable.pdf_icon);

        TextView tV7 = view.findViewById(R.id.tV7);
        tV7.setText("Quản lý user");
        ImageView ivIvQuanLy2 = view.findViewById(R.id.ivIvExtra2);
        ivIvQuanLy2.setImageResource(R.drawable.txt_icon);

    }
}