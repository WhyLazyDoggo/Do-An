package com.example.myapp1.DatabaseHelper;

import android.content.Context;

import androidx.annotation.NonNull;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class ecSHelper {

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
}
