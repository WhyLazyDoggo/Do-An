package com.example.myapp1.DatabaseHelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapp1.ManagerTask.CreateingAccount;
import com.example.myapp1.R;
import com.example.myapp1.comfirmKy.KyXacNhanVanban;
import com.example.myapp1.createSignGroup.TaoNhomKy;

public class DialogHelper {


    public interface AlertDialogListener {
        void onDialogDismissed(int result);
    }

    public static void showSuccessDialogWithReturn(Context context, String mainText ,StringBuilder text, String buttonText) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(context).inflate(
                R.layout.textdialog,
                null
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);

        ((TextView) view.findViewById(R.id.textMessage)).setText(text);

        ((Button) view.findViewById(R.id.buttonAction)).setText(buttonText);


        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (context instanceof Activity)
                    ((Activity) context).onBackPressed();
                else System.out.println("Không có quay lại được đâu á má");
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

    public static void showSuccessDialogNoReturn(Context context, String mainText, String comment){

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(context).inflate(
                R.layout.textdialog,
                null
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);
        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);

        ((Button) view.findViewById(R.id.buttonAction)).setText("Xác nhận");


        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

    public static void showErrorOneButton(Context context, String main, String cmt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(context).inflate(
                R.layout.popup_error_dialog_one_option,
                null
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(main);

        ((TextView) view.findViewById(R.id.textMessage)).setText(cmt);

        ((Button) view.findViewById(R.id.buttonAction)).setText("Xác nhận");

        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Bạn đã bấm nút thoát. Giờ mới bấm đó");
                alertDialog.dismiss();

//                if (listener != null) {
//                    listener.onDialogDismissed(42); // Đổi giá trị theo nhu cầu của bạn
//                }

            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }




    private void showSuccessDialog(Context context,String text){

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(context).inflate(
                R.layout.textdialog,
                null
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
                if (context instanceof Activity)
                ((Activity) context).onBackPressed();
                else System.out.println("Không có quay lại được đâu á má");
            }
        });

        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }


    private void showDeny(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(context).inflate(
                R.layout.popup_warning_dialog,
                null
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText("Xác nhận hủy ký");

        ((TextView) view.findViewById(R.id.textMessage)).setText("Bạn chắc chắn muốn hủy không ký văn bản này?" +
                "\nHành động hủy này sẽ làm cả nhóm ký bị hủy theo");

        ((Button) view.findViewById(R.id.buttonAction)).setText("Xác hủy ký");

        ((Button) view.findViewById(R.id.buttonNo)).setText("Quay lại");

        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessDialog(context, "Bạn đã hủy thành công ký nhóm này");
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }


}
