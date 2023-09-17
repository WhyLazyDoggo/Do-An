package com.example.myapp1.fragmentcontent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myapp1.createSignGroup.ChonVanBan;
import com.example.myapp1.R;
import com.example.myapp1.comfirmKy.KyXacNhanVanban;


public class FragmentSign extends Fragment {


    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_sign, container, false);


        LinearLayout linerCreateGroup = (LinearLayout) mView.findViewById(R.id.linerCreateGroup);

        linerCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChonVanBan.class);
                startActivity(intent);
            }
        });

        LinearLayout linerSignerComfirm = (LinearLayout) mView.findViewById(R.id.linerSignerComfirm);

        linerSignerComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KyXacNhanVanban.class);
                startActivity(intent);
            }
        });



        return mView;
    }
}