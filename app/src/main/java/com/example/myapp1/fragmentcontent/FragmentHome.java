package com.example.myapp1.fragmentcontent;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        textNameId.setText(prefs.getString("ten_nhan_vien",""));


        return view;
    }
}