package com.example.myapp1.createSignGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.R;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=PJx1JLGUiDk
public class TaoNhomAdapter extends RecyclerView.Adapter <TaoNhomAdapter.ViewHolder> {

    Context context;
    List<TaoNhomModel> list;
    int size;

    public TaoNhomAdapter(Context context, List<TaoNhomModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<TaoNhomModel> filterlist) {
        this.list = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaoNhomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tao_nhom_item,parent,false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaoNhomAdapter.ViewHolder holder, int position) {
        TaoNhomModel model = list.get(position);
        holder.imagine_view.setImageResource(model.getProfileImage());
        holder.user_name.setText(model.getUsername());

        System.out.println(model.getPublickey());

        holder.radio_btn.setChecked(model.isChecked());

        holder.decreption_tv.setText(model.getDecreption());


        holder.radio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("You was click this");
                list.get(position).setChecked(!list.get(position).isChecked());
                return;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagine_view;
        TextView user_name;
        TextView decreption_tv;
        CheckBox radio_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagine_view = itemView.findViewById(R.id.imagine_view);
            user_name = itemView.findViewById(R.id.user_name);
            decreption_tv = itemView.findViewById(R.id.decreption_tv);
            radio_btn = itemView.findViewById(R.id.radio_btn);
        }


        void bind(final TaoNhomModel list){
            user_name.setText(list.getUsername());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.setChecked(!list.isChecked());
                }
            });
        }

    }

    public ArrayList<TaoNhomModel> getSelected(){
        ArrayList<TaoNhomModel> selected = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            if (list.get(i).isChecked()){
                selected.add(list.get(i));
            }
        }
        return selected;
    }

    public void deSelected(){
        for (int i = 0; i<list.size();i++){
            if (list.get(i).isChecked()){
            list.get(i).setChecked(false);


            }
        }
    }


}
