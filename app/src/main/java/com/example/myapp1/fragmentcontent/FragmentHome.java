package com.example.myapp1.fragmentcontent;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.LoginTheme;
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
        textNameId.setText(prefs.getString("name_user", ""));

        ImageView ivAvatar = view.findViewById(R.id.ivAvatar);
        ivAvatar.setImageResource(ecSHelper.getAvatarPicture(prefs.getString("avatar_user","")));



        switch (prefs.getString("role_user", "")) {
            case "Nhân viên":

                setItemUser(view, prefs.getString("count_sign","-1"));
                break;

            case "Trưởng phòng":

                setItemTruongPhong(view, prefs.getString("count_sign","-1"));
                break;
            case "HR":

                setItemQuanLy(view);
                break;
            case "Khách hàng":
                setItemGuess(view);
                break;
            default:
                showErrorOneButton("Gặp lỗi", "Chương trình đang gặp lỗi, vui lòng thử lại sau");
                break;
        }


        return view;
    }

    private void setItemGuess(View view) {
        LinearLayout LinBig = view.findViewById(R.id.LinBig);
        LinBig.setVisibility(View.GONE);


        TextView tV6 = view.findViewById(R.id.tV6);
        tV6.setText("Kiểm tra chữ ký");
        ImageView ivIvQuanLy = view.findViewById(R.id.ivIvExtra1);
        ivIvQuanLy.setImageResource(R.drawable.search_sign_icon);


    }

    private void setItemUser(View view, String count) {

        TextView tvCount = view.findViewById(R.id.tvCount);

        if (Integer.parseInt(count) > 10){
            ImageView imageView10 = view.findViewById(R.id.imageView4);
            imageView10.setImageResource(R.drawable.circle_green);
            count = "Ex";
        }

        tvCount.setText(count);

        LinearLayout SmalLin = view.findViewById(R.id.SmalLin);
        SmalLin.setVisibility(View.GONE);

    }

    private void setItemTruongPhong(View view, String count) {
        TextView tV6 = view.findViewById(R.id.tV6);
        tV6.setText("Quản Lý user");

        TextView tvCount = view.findViewById(R.id.tvCount);

        if (Integer.parseInt(count) > 10){
            ImageView imageView10 = view.findViewById(R.id.imageView4);
            imageView10.setImageResource(R.drawable.circle_green);
            count = "Ex";
        }


        tvCount.setText(count);

        ImageView ivIvQuanLy = view.findViewById(R.id.ivIvExtra1);
        ivIvQuanLy.setImageResource(R.drawable.manageruser);
    }

    private void setItemQuanLy(View view) {
        LinearLayout LinBig = view.findViewById(R.id.LinBig);
        LinBig.setVisibility(View.GONE);

        LinearLayout HR1 = view.findViewById(R.id.HR1);

        HR1.setVisibility(View.VISIBLE);

        ((ImageView) view.findViewById(R.id.imageView4)).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.tvCount)).setVisibility(View.GONE);

        TextView tV6 = view.findViewById(R.id.tV6);
        tV6.setText("Tạo tài khoản");
        ImageView ivIvQuanLy = view.findViewById(R.id.ivIvExtra1);
        ivIvQuanLy.setImageResource(R.drawable.createuser);

        TextView tV7 = view.findViewById(R.id.tV7);
        tV7.setText("Quản lý user");
        ImageView ivIvQuanLy2 = view.findViewById(R.id.ivIvExtra2);
        ivIvQuanLy2.setImageResource(R.drawable.manageruser);

    }


    private void showErrorOneButton(String main, String cmt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.popup_error_dialog_one_option,
                null
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(main);

        ((TextView) view.findViewById(R.id.textMessage)).setText(cmt);

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

}