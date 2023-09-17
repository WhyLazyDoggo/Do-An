package com.example.myapp1.fragmentcontent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapp1.createSignGroup.ChonVanBan;
import com.example.myapp1.MainActivity;
import com.example.myapp1.R;

import com.example.myapp1.comfirmKy.*;
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
                Intent intent = new Intent(getActivity(), ChonVanBan.class);
                startActivity(intent);
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