package com.example.myapp1.ManagerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.R;
import com.example.myapp1.createSignGroup.ChonVanAdapter;
import com.example.myapp1.createSignGroup.TaoNhomAdapter;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter <UserAdapter.ViewHolder> {

    Context context;

    List<UserModel> list;

    public UserAdapter(Context context, List<UserModel> list){
        this.context = context;
        this.list=list;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chon_van_ban_item,parent,false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        if (list != null && list.size()>0){
            UserModel model = list.get(position);
            holder.imagine_view.setImageResource(model.getProfileImage());
            holder.user_name.setText(model.getFullname());
            holder.second_tv.setText(model.getRole()+" / "+model.getPhong_ban());
            holder.select_btn.setText("Xem");

            holder.select_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(),ShowDetailActivity.class);
                    intent.putExtra("object",list.get(position));
                    holder.itemView.getContext().startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagine_view;
        TextView user_name, second_tv;
        Button select_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagine_view = itemView.findViewById(R.id.imagine_view);
            user_name = itemView.findViewById(R.id.user_name);
            second_tv = itemView.findViewById(R.id.date_tv);
            select_btn = itemView.findViewById(R.id.select_btn);


        }

    }

}
