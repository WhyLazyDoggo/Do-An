package com.example.myapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class InfoProgram extends AppCompatActivity {
    private static final String AES_ALGORITHM = "AES";
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int KEY_LENGTH = 256;


    final int REQUEST_CODE = 101;

    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_program);



//        getPhonestate();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // if permissions are not provided we are requesting for permissions.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // if permissions are not provided we are requesting for permissions.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }


        getMaHoa();




    }


    private void readfile(String filepath){
        filepath = filepath+".key";
        try {
            InputStream inputStream = openFileInput(filepath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String output ="";

            StringBuilder stringBuilder = new StringBuilder();
            while ((output = bufferedReader.readLine()) != null){
                stringBuilder.append("\n").append(output);
            }
            inputStream.close();
            System.out.println("Kết quả:"+stringBuilder);
        }catch (IOException e){
            System.out.println("Error"+e);
        }
    }

    private void createfile(String filepath, String msg){
        filepath = filepath+".key";
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(filepath, MODE_PRIVATE));
            outputStreamWriter.write(msg);
            outputStreamWriter.close();
            System.out.println("Viết thành công");
        }catch (IOException e){
            System.out.println("Error"+e);
        }
    }


    private void getMaHoa() {

        String deviceID = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        System.out.println("deviceID: "+deviceID);

        EditText edtHo,edtplaintext,edtencryptedText,edtresultencryptedText,edtresultplaintext;
        Button encrypt,decrypt;

        edtHo = findViewById(R.id.edtHo);
        edtplaintext = findViewById(R.id.edtplaintext);
        edtencryptedText = findViewById(R.id.edtencryptedText);
        edtresultencryptedText = findViewById(R.id.edtresultencryptedText);
        edtresultplaintext = findViewById(R.id.edtresultplaintext);
        edtHo.setText(deviceID);

        encrypt = findViewById(R.id.encrypt);
        encrypt.setOnClickListener(v -> {
            try {
                SecretKey secretKey = generateAESKey(edtHo.getText().toString());

                // Chuỗi cần mã hóa
                String plaintext = edtplaintext.getText().toString();

                // Mã hóa thông điệp
                byte[] encryptedText = encrypt(plaintext, secretKey);

                // In kết quả
                System.out.println("Plaintext: " + plaintext);
                System.out.println("Encrypted text: " + Base64.getEncoder().encodeToString(encryptedText));
                edtresultencryptedText.setText(Base64.getEncoder().encodeToString(encryptedText));

                createfile("Testthu123",Base64.getEncoder().encodeToString(encryptedText));

            } catch (Exception e) {
                System.out.println(e);
                System.out.println("error");
                edtresultencryptedText.setText("Error");
                edtresultplaintext.setText("Error");
            }


        });

        decrypt = findViewById(R.id.decrypt);
        decrypt.setOnClickListener(v -> {

            SecretKey secretKey = null;
            try {
                secretKey = generateAESKey(edtHo.getText().toString());

                String encryptedText =  edtencryptedText.getText().toString();

                // Giải mã thông điệp
                String decryptedText = decrypt(encryptedText, secretKey);

                System.out.println("Decrypted text: " + decryptedText);

                edtresultplaintext.setText(decryptedText);
                readfile("Testthu123");

            } catch (Exception e) {
                System.out.println(e);
                System.out.println("error");
                edtresultencryptedText.setText("Error");
                edtresultplaintext.setText("Error");
            }
        });
    }

    public void createFile() {
        try {
            // Tạo đối tượng File với đường dẫn và tên file
            File file = new File("path/to/your/file.txt");

            // Kiểm tra nếu file đã tồn tại
            if (file.exists()) {
                System.out.println("File đã tồn tại");
                return;
            }

            // Tạo file mới
            if (file.createNewFile()) {
                System.out.println("Tạo file thành công");
            } else {
                System.out.println("Tạo file thất bại");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile(String fileresource, String datafile){
        try {

            File root = new File(Environment.getExternalStorageDirectory(),"My Folder");
            if (!root.exists()){
                root.mkdir();
            }

            if (root.createNewFile()) {
                System.out.println("Tạo file thành công");
            } else {
                System.out.println("Tạo file thất bại");
            }


            File filepath = new File(root,fileresource+".key");

            if (filepath.createNewFile()) {
                System.out.println("Tạo file thành công");
            } else {
                System.out.println("Tạo file thất bại");
            }

            FileWriter writer = new FileWriter(filepath);
            writer.append(datafile);
            writer.flush();
            writer.close();

        }catch (IOException e){
            System.out.println("error: "+e);
        }

    }


    public static SecretKey generateAESKey(String input) throws Exception {
        byte[] salt = "SomeSalt".getBytes(StandardCharsets.UTF_8);
        KeySpec spec = new PBEKeySpec(input.toCharArray(), salt, 65536, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, AES_ALGORITHM);
    }

    public static byte[] encrypt(String plaintext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(String base64EncryptedText, SecretKey secretKey) throws Exception {
        byte[] encryptedText = Base64.getDecoder().decode(base64EncryptedText);

        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes = cipher.doFinal(encryptedText);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }


    public String getPhonestate(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {

        }


        return"A";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            // in the below line, we are checking if permission is granted.
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if permissions are granted we are displaying below toast message.
                Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                // in the below line, we are displaying toast message
                // if permissions are not granted.
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}