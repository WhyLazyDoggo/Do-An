package com.example.myapp1.DatabaseHelper;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.myapp1.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;



public class ecSHelper {
    private static final String AES_ALGORITHM = "AES";
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int KEY_LENGTH = 256;


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


    public static String deCryptKey(ContentResolver resolver, String userPassword, String encryptKey){
        String deviceID = Settings.Secure.getString(resolver,Settings.Secure.ANDROID_ID);
        System.out.println("---\nĐang trong quá trình giải mã");
        //Đoạn đọc file lấy thông tin giải mã
        try {
            System.out.println("Khóa để giải mã khóa bí mật là: "+(deviceID+userPassword));

            SecretKey secretKey = generateAESKey(deviceID+userPassword);

            // Giải mã thông điệp
            String decryptedText = decrypt(encryptKey, secretKey);

            System.out.println("Decrypted text: " + decryptedText);
            return decryptedText;


        } catch (Exception e) {
            System.out.println(e);
            System.out.println("error");
            return "Error code 404";
        }
    }

    public static String enCryptKey(ContentResolver resolver, String userPassword, String privateKey) {

        String deviceID = Settings.Secure.getString(resolver,Settings.Secure.ANDROID_ID);
        System.out.println("deviceID: "+deviceID);

        try {
            System.out.println("\nKhóa bí mật được mã hóa là: "+privateKey);
            System.out.println("Khóa để mã hóa khóa bí mật là: "+(deviceID+userPassword));
            SecretKey secretKey = generateAESKey(deviceID+userPassword);

            // Chuỗi cần mã hóa: privateKey

            // Mã hóa thông điệp
            byte[] encryptedText = encrypt(privateKey, secretKey);

            // In kết quả
            System.out.println("Encrypted text: " + Base64.getEncoder().encodeToString(encryptedText));
            String saveText = Base64.getEncoder().encodeToString(encryptedText);
            System.out.println("**********");
            System.out.println("Giải mã thử lại 1 lần nữa");
            System.out.println(deCryptKey(resolver,userPassword,saveText));
            System.out.println("**********");
            return saveText;

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("error");
            return "404Error";
        }

    }


    public static int getAvatarPicture(String avatar){
        System.out.println("Kết quả của avatar là: "+avatar);
        switch (avatar){
            case "b1":
                return R.drawable.boy1;
            case "b2":
                return R.drawable.boy2;
            case "b3":
                return R.drawable.boy3;
            case "b4":
                return R.drawable.boy4;
            case "b5":
                return R.drawable.boy5;
            case "b6":
                return R.drawable.boy6;
            case "g1":
                return R.drawable.girl1;
            case "g2":
                return R.drawable.girl2;
            case "g3":
                return R.drawable.girl3;
            case "g4":
                return R.drawable.girl4;
            case "g5":
                return R.drawable.girl5;
            case "g6":
                return R.drawable.girl6;
            default:
                return R.drawable.logo;
        }
    }

    public static String checkSignature(Context context,String msg,String groupkey, String signature){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("checkvefi");
        PyObject result = creatX.call(msg,groupkey,signature);
        return String.valueOf(result);
    }

    public static String getPubkey(Context context,String input){

        try {
            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(context));
            }
            Python py = Python.getInstance();
            PyObject creatX = py.getModule("test").get("getpublickey");
            PyObject result = creatX.call(input);
            return String.valueOf(result);
        } catch (Exception ex){
            System.out.println("Đã xảy ra lỗi rồi");
            return "Error Get Publickey";
        }
    }

    public static String getPrikey(Context context, String username){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("getprivatekey");
        PyObject result = creatX.call(username);
        return String.valueOf(result);
    }






    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }




    public static String getResetPass(Context context,String input){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("resetPassword");
        PyObject result = creatX.call(input);
        return String.valueOf(result);
    }



    public static String getHashMsg_type2(Context context,String input){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("hashMsg_type_2");
        PyObject result = creatX.call(input);
        return String.valueOf(result);
    }

    public static String getSignature(Context context,String sSum, String Rsum,String Xgroup , String msg){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("createSignature");
        PyObject result = creatX.call(sSum,msg,Xgroup,Rsum);
        return String.valueOf(result);
    }

    public static String getSi(Context context, String privkey, String cGroup, String ki, String lGroup, String Xgroup, String rSum){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("createSi");
        PyObject result = creatX.call(privkey,cGroup,ki,lGroup,Xgroup,rSum);
        return String.valueOf(result);
    }

    public static String getHashMsg(Context context,String input){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("hashMsg");
        PyObject result = creatX.call(input);
        return String.valueOf(result);
    }

    public static String getXcheck(Context context, String input){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("creatXpoint");
        PyObject result = creatX.call(input);
        return String.valueOf(result);
    }


    @NonNull
    public static String getX(Context context, String input){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("creatX");
        PyObject result = creatX.call(input);
        return String.valueOf(result);
    }

    public static String getL(Context context, String input){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("creatL");
        PyObject result = creatX.call(input);
        return String.valueOf(result);
    }


    public static String getKi(Context context, String publickey, String msg){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("createKi");
        PyObject result = creatX.call(publickey,msg);
        return String.valueOf(result);
    }

    public static String getRsum(Context context,String input){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("createRSum");
        PyObject result = creatX.call(input);
        return String.valueOf(result);
    }

    public static String getC(Context context, String Rsum,String Xgroup , String msg){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("createC");
        PyObject result = creatX.call(Rsum,Xgroup,msg);
        return String.valueOf(result);
    }

}
