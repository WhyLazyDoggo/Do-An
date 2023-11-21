package com.example.myapp1.fragmentcontent;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp1.ChangePassword;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.HistorySign;
import com.example.myapp1.InfoProgram;
import com.example.myapp1.LoginTheme;
import com.example.myapp1.MainActivity;
import com.example.myapp1.ManagerTask.ShowDetailActivity;
import com.example.myapp1.ManagerTask.UserModel;
import com.example.myapp1.R;
import com.example.myapp1.comfirmKy.KyXacNhanVanban;

public class FragmentInfoAll extends Fragment {

    private ConstraintLayout LayoutInfo;
    private ConstraintLayout LayoutChangePass;
    private ConstraintLayout LayoutHistory;
    private ConstraintLayout LayoutProgram;
    private ConstraintLayout LayoutLogOut;

    private TextView tvName,tvRole;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_all, container, false);

        SharedPreferences.Editor editor = getContext().getSharedPreferences("preference_user",MODE_PRIVATE).edit();
        SharedPreferences prefs = getContext().getSharedPreferences("preference_user", MODE_PRIVATE);

        LayoutInfo = view.findViewById(R.id.LayoutInfo);

        LayoutChangePass = view.findViewById(R.id.LayoutChangePass);
        LayoutHistory = view.findViewById(R.id.LayoutHistory);
        LayoutProgram = view.findViewById(R.id.LayoutProgram);
        LayoutLogOut = view.findViewById(R.id.LayoutLogOut);

        tvName = view.findViewById(R.id.tvName);
        tvRole = view.findViewById(R.id.tvRole);
        ImageView ivAvatar = view.findViewById(R.id.avatar);
        ivAvatar.setImageResource(ecSHelper.getAvatarPicture(prefs.getString("avatar_user","")));

        tvName.setText(prefs.getString("name_user",""));
        tvRole.setText(prefs.getString("role_user",""));

        LayoutInfo.setOnClickListener(v -> {
            UserModel user = new UserModel(prefs.getString("id_user",""),
                    ecSHelper.getAvatarPicture(prefs.getString("avatar_user","0")),
                    prefs.getString("username_user",""),
                    prefs.getString("name_user",""),
                    prefs.getString("role_user",""),
                    prefs.getString("room_user",""),
                    prefs.getString("status_user",""),
                    prefs.getString("created_time_user",""),
                    prefs.getString("update_time_user",""));
            Intent intent = new Intent(getContext(), ShowDetailActivity.class);
            intent.putExtra("object",user);

            startActivity(intent);
        });

        LayoutChangePass.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangePassword.class);
            startActivity(intent);
        });

        LayoutHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistorySign.class);
            startActivity(intent);

        });

        LayoutProgram.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), InfoProgram.class);
            startActivity(intent);
        });

        LayoutLogOut.setOnClickListener(v -> {
//            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            editor.clear();
            editor.commit();



            Intent intent = new Intent(getContext(), LoginTheme.class);
            startActivity(intent);
            getActivity().finish();

//            Activity.finish();


        });




        return view;
    }
}