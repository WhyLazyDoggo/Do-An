package com.example.myapp1.checkSignature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.GiaoDienChinh;
import com.example.myapp1.R;
import com.example.myapp1.comfirmKy.FileAdapter;
import com.example.myapp1.comfirmKy.FileModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class checkNotDoneSignature extends AppCompatActivity {

    RecyclerView recycler_view;

    checkNotSignatureAdapter adapter;
    List<checkNotSignatureModel> file_list = new ArrayList<>();
    String textFilter ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_not_done_signature);

        recycler_view = findViewById(R.id.recycler_view);

        setRecycleView();

        Button button_done = (Button) findViewById(R.id.button_done);

        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checkNotDoneSignature.this, checkSignatureDone.class);
                startActivity(intent);
            }
        });

        setFilter();

    }

    private void setRecycleView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new checkNotSignatureAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<checkNotSignatureModel> getList(){

        String user_id;
        SharedPreferences prefs = getSharedPreferences("preference_user", MODE_PRIVATE);
        ResultSet rs = SelectDB.getProcessGroupSign(prefs.getString("id_user",""),prefs.getString("role_user",""),prefs.getString("room_user",""));
//        file_list.add(new checkNotSignatureModel("" + (1 + 1), "Mickery", "10000", "4/5",R.drawable.pdf_icon));

        try {
            while (rs.next()){
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
                file_list.add(new checkNotSignatureModel(rs.getString("id"),
                        rs.getString("ten_van_ban"), rs.getString("created_at"),
                        rs.getString("sl_ky")+"/"+rs.getString("tong_user"),picture));
            }

        }catch (SQLException e) {
            System.out.println("Error");
            System.out.println(e);
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
        List<checkNotSignatureModel> filter_list = new ArrayList<>();
        for (checkNotSignatureModel item : file_list){
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

}