package com.example.myapp1.comfirmKy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.R;
import com.example.myapp1.createSignGroup.TaoNhomModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KyXacNhanVanban extends AppCompatActivity {

    RecyclerView recycler_view;
    FileAdapter adapter;

    List<FileModel> file_list = new ArrayList<>();

    String textFilter ="";
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

        setFilter();


    }

    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FileAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<FileModel> getList(){


        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        ResultSet rs = SelectDB.getVanBanKy(prefs.getString("id_user",""));
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

            file_list.add(new FileModel(rs.getString("id"),rs.getString("id_cuavanban"),rs.getString("id_nhom_ky"),rs.getString("ten_van_ban"),rs.getString("noi_dung_tom_tat"),rs.getString("created_at"),picture));
            }

        }catch (SQLException e) {
            System.out.println("Error");
            Log.e(null, "Error connection!!! Tạo bảng KyTucXa chưa Pa?");
        }

        if (file_list.size()==0){
            TextView tvIfNull = findViewById(R.id.tvIfNull);
            tvIfNull.setVisibility(View.VISIBLE);
        }


        return file_list;
    }


    private void setFilter() {
        ImageView filter = findViewById(R.id.filter);

        filter.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.filter_van_ban);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.TOP);
            dialog.show();

            SearchView svSearch = dialog.findViewById(R.id.svSearch);
            svSearch.setQuery(textFilter,false);
            svSearch.clearFocus();
            svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    System.out.println("Đang tìm kiếm văn bản");

                    filterList(newText);
                    textFilter = newText;
                    return true;
                }
            });


        });
    }

    private void filterList(String newText) {
        List<FileModel> filter_list = new ArrayList<>();
        for (FileModel item : file_list){
            if (item.getFullname().toLowerCase().contains(newText.toLowerCase())){
                filter_list.add(item);
            }

        }

        if (filter_list.isEmpty()){
            Toast.makeText(this,"Không tìm thấy", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setList(filter_list);
        }

    }



    private void showChoiceDialog() {

        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.filter_van_ban);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.show();




    }


}