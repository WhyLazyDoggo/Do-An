package com.example.myapp1.createSignGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapp1.R;

import java.util.ArrayList;
import java.util.List;

public class TaoNhomKy extends AppCompatActivity {
    RecyclerView recycler_view;
    List<TaoNhomModel> list = new ArrayList<>();
    TaoNhomAdapter adapter;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_nhom_ky);


        Button button_confirm = (Button) findViewById(R.id.button_confirm);
        Button button_cancel = (Button) findViewById(R.id.button_cancel);



        addData();
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaoNhomAdapter(this,list);
        recycler_view.setAdapter(adapter);

        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelected().size()>0){
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 0 ; i <adapter.getSelected().size() ; i++){
                        stringBuilder.append(adapter.getSelected().get(i).getUsername());
                        stringBuilder.append("\n");
                    }
                    showSuccessDialog(stringBuilder);
                }

            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void addData() {
        for (int i = 0; i<31 ; i++){
            list.add(new TaoNhomModel(R.drawable.home_icon,"User "+ i,"Phó phòng"));
        }

    }

    private void showSuccessDialog(StringBuilder text){

        AlertDialog.Builder builder = new AlertDialog.Builder(TaoNhomKy.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(TaoNhomKy.this).inflate(
                R.layout.textdialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText("Gửi yêu cầu thành công");

        ((TextView) view.findViewById(R.id.textMessage)).setText(text);

        ((Button) view.findViewById(R.id.buttonAction)).setText("Hoàn thành");



        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                onBackPressed();
            }
        });

        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

}