package com.example.myapp1.DatabaseHelper;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapp1.R;
import com.example.myapp1.comfirmKy.KyXacNhanVanban;
import com.example.myapp1.createSignGroup.TaoNhomKy;

public class DialogHelper {



//
//    private void showErrorOneButton() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(KyXacNhanVanban.this, R.style.AlertDialogTheme);
//        View view = LayoutInflater.from(KyXacNhanVanban.this).inflate(
//                R.layout.popup_error_dialog_one_option,
//                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
//        );
//        builder.setView(view);
//
//        ((TextView) view.findViewById(R.id.textMain)).setText("Xác nhận hủy ký");
//
//        ((TextView) view.findViewById(R.id.textMessage)).setText("Bạn chắc chắn muốn hủy không ký văn bản này?" +
//                "\nHành động hủy này sẽ làm cả nhóm ký bị hủy theo");
//
//        ((Button) view.findViewById(R.id.buttonAction)).setText("Xác hủy ký");
//
//        AlertDialog alertDialog = builder.create();
//
//
//        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        if (alertDialog.getWindow() != null) {
//            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        }
//
//        alertDialog.show();
//
//    }
//

//
//
//
//    private void showSuccessDialog(Context context,String text){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
//        View view = LayoutInflater.from(context).inflate(
//                R.layout.textdialog,
//                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
//        );
//        builder.setView(view);
//
//        ((TextView) view.findViewById(R.id.textMain)).setText("Gửi yêu cầu thành công");
//
//        ((TextView) view.findViewById(R.id.textMessage)).setText(text);
//
//        ((Button) view.findViewById(R.id.buttonAction)).setText("Hoàn thành");
//
//
//
//        AlertDialog alertDialog = builder.create();
//
//        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//                onBackPressed();
//            }
//        });
//
//        if(alertDialog.getWindow()!=null){
//            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        }
//
//        alertDialog.show();
//
//    }
//
//
//    private void showDeny() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(KyXacNhanVanban.this, R.style.AlertDialogTheme);
//        View view = LayoutInflater.from(KyXacNhanVanban.this).inflate(
//                R.layout.popup_warning_dialog,
//                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
//        );
//        builder.setView(view);
//
//        ((TextView) view.findViewById(R.id.textMain)).setText("Xác nhận hủy ký");
//
//        ((TextView) view.findViewById(R.id.textMessage)).setText("Bạn chắc chắn muốn hủy không ký văn bản này?" +
//                "\nHành động hủy này sẽ làm cả nhóm ký bị hủy theo");
//
//        ((Button) view.findViewById(R.id.buttonAction)).setText("Xác hủy ký");
//
//        ((Button) view.findViewById(R.id.buttonNo)).setText("Quay lại");
//
//        AlertDialog alertDialog = builder.create();
//
//        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSuccessDialog("Hủy ký thành công", "Bạn đã hủy thành công ký nhóm này");
//                alertDialog.dismiss();
//            }
//        });
//
//        if (alertDialog.getWindow() != null) {
//            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        }
//
//        alertDialog.show();
//
//    }
//

}
