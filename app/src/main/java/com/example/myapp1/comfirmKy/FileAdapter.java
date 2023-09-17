package com.example.myapp1.comfirmKy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.R;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter <FileAdapter.ViewHolder>{

    Context context;
    List<FileModel> file_list;

    public FileAdapter(Context context, List<FileModel> file_list) {
        this.context = context;
        this.file_list = file_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_row_item_with_button_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.ViewHolder holder, int position) {
        if (file_list != null && file_list.size()>0){
            FileModel model = file_list.get(position);
            holder.id_tv.setText(model.getId());
            holder.name_tv.setText(model.getName());
            holder.person_tv.setText(model.getPerson());
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
        TextView id_tv, name_tv, person_tv;
        Button info_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_tv = itemView.findViewById(R.id.id_tv);
            name_tv = itemView.findViewById(R.id.name_tv);
            person_tv = itemView.findViewById(R.id.person_tv);
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
