package com.example.myapp1.ManagerTask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp1.DatabaseHelper.InsertDB;
import com.example.myapp1.DatabaseHelper.UpdateDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.GiaoDienChinh;
import com.example.myapp1.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ResetPassword extends AppCompatActivity {

    EditText edtNewpass, edtRetype;
    Button btnConfirm;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        editor = getSharedPreferences("preference_user", MODE_PRIVATE).edit();
        System.out.println(prefs.getAll());

        setView();

        setAction();

    }

    private void setAction() {
        btnConfirm.setOnClickListener(v -> {
            if (edtNewpass.length() * edtRetype.length() == 0) {
                Toast.makeText(getApplicationContext(), "Vui lòng điền thông tin mật khẩu mới", Toast.LENGTH_SHORT).show();
            } else if (!edtNewpass.getText().toString().equals(edtRetype.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Mật khẩu mới không trùng nhau, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
            } else {
                String hashPass = ecSHelper.sha256(edtRetype.getText().toString());

                if (!prefs.getString("role_user","").equals("Khách hàng"))
                    makeNewPrivatekey();

                UpdateDB.updatePassword(prefs.getString("id_user", ""), hashPass, "Hoạt động");

                Intent intent = new Intent(ResetPassword.this, GiaoDienChinh.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void makeNewPrivatekey() {

        //Lấy mới khóa bí mật và khóa công khai mới ở đây | Ở đây prikey là plaintext -> encryptKey -> decryptKey
        String prikey = ecSHelper.getPrikey(this);

        //Mã hóa lại prikey bằng id_máy + mật khẩu vừa nhập ở trên kia + hàm băm mật khẩu (Tăng độ dài khóa, vét cạn lâu và khó hơn, tìm kiêm kết quả khổ hơn
        //String enCryptKey(ContentResolver resolver, String userPassword, String plainText)    Với userPassword là mật khẩu + id của user
        String encryptPrikey = ecSHelper.enCryptKey(this.getContentResolver(), String.valueOf(edtRetype.getText()+prefs.getString("id_user","XYXX")),prikey);

        System.out.println("Bản mã thu được là: " + encryptPrikey);

        //Lưu kbm vào file riêng đã pick
        createfile(ecSHelper.sha256(prefs.getString("id_user","XXXX")+"Lưu trữ khóa"),encryptPrikey);

        //Kiểm tra lại bằng cách đọc thử lại file
        String encryptPrikeyFromFile = readfile(ecSHelper.sha256(prefs.getString("id_user","XXXX")+"Lưu trữ khóa"));
        System.out.println("Khóa được lưu trong file là: "+encryptPrikeyFromFile);
        //Xong rồi giải mã lại để kiểm tra chéo
        String decryptPrikey = ecSHelper.deCryptKey(this.getContentResolver(),String.valueOf(edtRetype.getText()+prefs.getString("id_user","XXXY")),encryptPrikeyFromFile);
        System.out.println("Bản mã sau khi giải mã là: "+decryptPrikey);

        String pubkey = ecSHelper.getPubkey(this, prikey);
        UpdateDB.updateDeleteAllChuKy(prefs.getString("id_user","XXX"));
        InsertDB.insertChuKyCaNhan(prefs.getString("id_user", ""), pubkey);
        editor.putString("pubkey", pubkey);
//                editor.putString("privatekey", prikey);
        editor.commit();

    }

    private void createfile(String filepath, String msg){
        filepath = filepath+".key";
        try {
            System.out.println("-----");
            System.out.println("Đang tạo trong file: "+filepath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(filepath, MODE_PRIVATE));
            outputStreamWriter.write(msg);
            outputStreamWriter.close();
            System.out.println("Viết thành công, nội dung tệp là: "+msg);
            System.out.println("-----");
        }catch (IOException e){
            System.out.println("Error"+e);
        }
    }



    private String readfile(String filepath){
        filepath = filepath+".key";
        try {
            System.out.println("-----");
            System.out.println("Đang đọc trong file: "+filepath);
            InputStream inputStream = openFileInput(filepath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String output ="";
            output = bufferedReader.readLine();

            inputStream.close();
            System.out.println("Kết quả khi đọc file:"+output);
            System.out.println("-----");
            return String.valueOf(output);
        }catch (IOException e){
            System.out.println("Error"+e);
            return "Error404";
        }
    }

    private void setView() {
        edtNewpass = findViewById(R.id.edtNewpass);
        edtRetype = findViewById(R.id.edtRetype);
        btnConfirm = findViewById(R.id.btnConfirm);

    }


}