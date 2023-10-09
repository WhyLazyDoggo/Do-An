package com.example.myapp1.comfirmKy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.R;
import com.example.myapp1.createSignGroup.TaoNhomModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KyXacNhanVanban extends AppCompatActivity {

    RecyclerView recycler_view;
    FileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ky_xac_nhan_vanban);

        recycler_view = findViewById(R.id.recycler_view);

        setRecycleView();

        Button button_confirm = (Button) findViewById(R.id.button_confirm);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoiceDialog();

            }
        });


    }

    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FileAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<FileModel> getList(){
        List<FileModel> file_list = new ArrayList<>();

        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        ResultSet rs = SelectDB.getVanBanKy(prefs.getString("user",""));
//      Cho thêm thông tin về Nội dung cơ bản của các tệp, revert Tên tệp dài theo file ChonVanBan
        try {
            while (rs.next()) {
                String type_file = rs.getString("ten_van_ban");
                int picture = R.drawable.xls_icon;
                int lastDotIndex = type_file.lastIndexOf(".");
                if (lastDotIndex != -1) {

                    switch (type_file.substring(lastDotIndex)) {
                        case ".txt":
                            picture = R.drawable.txt_icon;
                            break;
                        case ".pdf":
                            picture = R.drawable.pdf_icon;
                            break;
                        case ".doc":
                            picture = R.drawable.doc_icon;
                            break;
                        default:
                            picture = R.drawable.xls_icon;
                            break;
                    }

                }

            file_list.add(new FileModel(rs.getString("id"),rs.getString("id_cuavanban"),rs.getString("id_nhomky"),rs.getString("ten_van_ban"),rs.getString("noidungtomtat"),rs.getString("created_at"),picture));
            }

        }catch (SQLException e) {
            System.out.println("Error");
            Log.e(null, "Error connection!!! Tạo bảng KyTucXa chưa Pa?");
        }

        return file_list;
    }


    private void showDeny() {
        AlertDialog.Builder builder = new AlertDialog.Builder(KyXacNhanVanban.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(KyXacNhanVanban.this).inflate(
                R.layout.popup_warning_dialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
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
                showSuccessDialog("Hủy ký thành công", "Bạn đã hủy thành công ký nhóm này");
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

    private void showChoiceDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(KyXacNhanVanban.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(KyXacNhanVanban.this).inflate(
                R.layout.popup_info_file_for_signer,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText("Thông tin về tệp\nchuẩn bị ký");

        ((TextView) view.findViewById(R.id.name_file)).setText("tệp x/y/z");

        ((TextView) view.findViewById(R.id.textDumpInfo)).setText("<<Hình ảnh tệp" +
                "\n Nội dung tệp" +
                "\n Nội dung nữa");

        ((Button) view.findViewById(R.id.buttonAction)).setText("Xác nhận ký");

        ((Button) view.findViewById(R.id.buttonNo)).setText("Từ chối ký");

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
                showSuccessDialog("Ký số thành công", "Chúc mừng bạn đã ký thành công văn bản");
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

    private void showSuccessDialog(String main, String text) {

        AlertDialog.Builder builder = new AlertDialog.Builder(KyXacNhanVanban.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(KyXacNhanVanban.this).inflate(
                R.layout.textdialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        ((TextView) view.findViewById(R.id.textMain)).setText(main);

        ((TextView) view.findViewById(R.id.textMessage)).setText(text);

        ((Button) view.findViewById(R.id.buttonAction)).setText("Kiểm tra");

        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

}