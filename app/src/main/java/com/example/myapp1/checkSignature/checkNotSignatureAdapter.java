package com.example.myapp1.checkSignature;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.R;

import java.util.List;

public class checkNotSignatureAdapter extends RecyclerView.Adapter <checkNotSignatureAdapter.ViewHolder> {

    Context context;
    List<checkNotSignatureModel> sign_list;

    public checkNotSignatureAdapter(Context context, List<checkNotSignatureModel> sign_list){
        this.context = context;
        this.sign_list = sign_list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.table_row_item_layout,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.row_show_info_van_ban,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull checkNotSignatureAdapter.ViewHolder holder, int position) {
        if (sign_list != null && sign_list.size()>0){
            checkNotSignatureModel model = sign_list.get(position);
            holder.imagine_view.setImageResource(model.getProfileImagine());
            holder.user_name.setText(model.getName());
            holder.decreption_tv.setText(model.getPerson());
            holder.process.setText(model.getCount());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return sign_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView user_name, decreption_tv, process;

        ImageView imagine_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagine_view = itemView.findViewById(R.id.imagine_view);
            user_name = itemView.findViewById(R.id.user_name);
            decreption_tv = itemView.findViewById(R.id.decreption_tv);
            process = itemView.findViewById(R.id.process);

            itemView.setOnClickListener((v -> {
                Log.d("demo","onClick: item clicked" + "user"+user_name);
            }));

        }
    }}
