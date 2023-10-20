package com.example.myapp1.DatabaseHelper;

import android.content.Context;

import androidx.annotation.NonNull;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class ecSHelper {





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
