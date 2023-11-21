package com.example.myapp1.ManagerTask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp1.DatabaseHelper.DialogHelper;
import com.example.myapp1.DatabaseHelper.InsertDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.LoginTheme;
import com.example.myapp1.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

public class CreateingAccount extends AppCompatActivity {

    EditText edtHo;
    EditText edtTen;
    EditText edtUserName;
    AutoCompleteTextView edtRole;
    AutoCompleteTextView edtRoom;
    Button btnCreatAccount;
    TextInputLayout TILRoom;

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
        TILRoom = findViewById(R.id.TILRoom);


        String[] itemRole = {"Trưởng phòng", "Nhân viên", "Khách hàng"};
        String[] itemRoom = {"Nhân sự", "Hành chính", "Kế toán", "Marketing"};
        ArrayAdapter<String> adapterItem;

        adapterItem = new ArrayAdapter<String>(this, R.layout.list_item, itemRole);
        edtRole.setAdapter(adapterItem);

        adapterItem = new ArrayAdapter<String>(this, R.layout.list_item, itemRoom);
        edtRoom.setAdapter(adapterItem);

        getAction();

        createUser();

    }

    private void getAction() {


        edtRole.setOnClickListener(v -> {

            if (edtRole.length() > 1) {
                System.out.println("Chưa trống đâu");
                edtRole.setText("");
                edtRole.showDropDown();
                edtRole.showContextMenu();
                edtRole.setShowSoftInputOnFocus(true);
            }
        });

        edtRole.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TILRoom.setEnabled(true);

                if (parent.getItemAtPosition(position).equals("Khách hàng")) {
                    //Set cái l kia là false

                    TILRoom.setEnabled(false);
                    edtRoom.setText("");
                //Thêm cái set thời gian bấm nút nữa, bị lười quá
                }


            }
        });

    }


    private void createUser() {
        btnCreatAccount.setOnClickListener(v -> {

            System.out.println("Bạn đã bấm lút này");


            if (edtHo.length() * edtTen.length() * edtRole.length() * ((edtRole.getText().toString().equals("Khách hàng") || edtRoom.length() > 0) ? 1 : 0) * edtUserName.length() == 0) {
                Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Đang tạo tài khoản", Toast.LENGTH_SHORT).show();
                String username = edtUserName.getText() + "";
                String fullname = edtHo.getText() + " " + edtTen.getText();

                String password = ecSHelper.getResetPass(this, fullname);


                System.out.println(edtRole.getText());
                System.out.println(edtRoom.getText());

                int result = InsertDB.insertUser(username, ecSHelper.sha256(password), fullname, edtRole.getText().toString(), edtRoom.getText().toString());

                System.out.println(result);
                if (result == 1)
                    DialogHelper.showSuccessDialogNoReturn(CreateingAccount.this, "Tạo tài khoản thành công", "Bạn đã tạo thành công tài khoản cho \n" + edtRole.getText() + " " + fullname + "\nvới username là: " + username
                            + " và mật khẩu là: " + password);
                else
                    DialogHelper.showErrorOneButton(CreateingAccount.this, "Lỗi tạo tài khoản", "Tài khoản đã có sẵn, vui lòng tạo lại");
            }

        });
    }

}