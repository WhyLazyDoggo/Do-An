package com.example.myapp1.ManagerTask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapp1.R;

public class CreateingAccount extends AppCompatActivity {

    EditText edtHo;
    EditText edtTen;
    EditText edtUserName;
    EditText edtRole;
    EditText edtRoom;
    Button btnCreatAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createing_account);

        edtHo = findViewById(R.id.edtHo);
        edtTen = findViewById(R.id.edtTen);
        edtUserName = findViewById(R.id.edtUserName);
        edtRole = findViewById(R.id.edtRole);
        edtRoom = findViewById(R.id.edtRoom);
        btnCreatAccount = findViewById(R.id.btnCreatAccount);

        btnCreatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Bạn đã bấm lút này");
            }
        });

    }
}