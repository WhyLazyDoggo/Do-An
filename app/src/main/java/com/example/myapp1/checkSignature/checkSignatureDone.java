package com.example.myapp1.checkSignature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.myapp1.R;

import java.util.ArrayList;
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
    }

    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new checkSignatureAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<checkSignatureModel> getList(){
        List<checkSignatureModel> file_list = new ArrayList<>();

        for (int i=0;i<19;i++){
            file_list.add(new checkSignatureModel(""+(i+1),"MickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickeyMickey","10000"));

        }
        return file_list;
    }


    private void getInfor(){
        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        String name = prefs.getString("user","");
        System.out.println(name);

    }

}