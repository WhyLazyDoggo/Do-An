package com.example.myapp1.createSignGroup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.R;
import com.example.myapp1.comfirmKy.FileModel;

import java.util.List;

public class ChonVanAdapter extends RecyclerView.Adapter <ChonVanAdapter.ViewHolder> {

    Context context;
    List<ChonVanModel> file_list;

    public ChonVanAdapter(Context context, List<ChonVanModel> file_list){
        this.context = context;
        this.file_list = file_list;
    }

    @NonNull
    @Override
    public ChonVanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_row_item_chon_van_ban_layout,parent,false);
//        View view = LayoutInflater.from(context).inflate(R.layout.row_chon_van_ban_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChonVanAdapter.ViewHolder holder, int position) {
        if (file_list != null && file_list.size()>0){
            ChonVanModel model = file_list.get(position);
            holder.id_tv.setText(model.getId());
            holder.name_tv.setText(model.getName());
            holder.date_tv.setText(model.getDate());
//            holder.id_tv.setText(model.getId());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return file_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id_tv, name_tv, date_tv;
        Button info_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_tv = itemView.findViewById(R.id.id_tv);
            name_tv = itemView.findViewById(R.id.name_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            info_btn = itemView.findViewById(R.id.info_btn);

            itemView.setOnClickListener((v -> {
                Log.d("demo","onClick: item clicked" + "user"+name_tv);
            }));

            itemView.findViewById(R.id.info_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo","onClick: Button" + "user"+name_tv);

                }
            });

        }

    }
}
