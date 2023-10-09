package com.example.myapp1.DatabaseHelper;

import android.content.Context;

import androidx.annotation.NonNull;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class ecSHelper {

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

    public static String getSi(Context context, String privkey, String cGroup, String ki, String lGroup){
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python py = Python.getInstance();
        PyObject creatX = py.getModule("test").get("createSi");
        PyObject result = creatX.call(privkey,cGroup,ki,lGroup);
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
