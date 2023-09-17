package com.example.myapp1.checkSignature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp1.GiaoDienChinh;
import com.example.myapp1.R;
import com.example.myapp1.comfirmKy.FileAdapter;
import com.example.myapp1.comfirmKy.FileModel;

import java.util.ArrayList;
import java.util.List;

public class checkNotDoneSignature extends AppCompatActivity {

    RecyclerView recycler_view;

    checkNotSignatureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_not_done_signature);

        recycler_view = findViewById(R.id.recycler_view);

        setRecycleView();

        Button button_done = (Button) findViewById(R.id.button_done);

        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checkNotDoneSignature.this, checkSignatureDone.class);
                startActivity(intent);
            }
        });


    }

    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new checkNotSignatureAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<checkNotSignatureModel> getList(){
        List<checkNotSignatureModel> file_list = new ArrayList<>();
        file_list.add(new checkNotSignatureModel("" + (1 + 1), "Mickery", "10000", "4/5"));

        for (int i=0;i<19;i++) {

            file_list.add(new checkNotSignatureModel("" + (i + 1), "Mickery", "10000", "4/5"));

        }
        return file_list;
    }
}