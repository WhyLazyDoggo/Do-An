package com.example.myapp1.checkSignature;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.R;
import com.example.myapp1.createSignGroup.TaoNhomKy;

import java.util.List;

public class checkSignatureAdapter extends RecyclerView.Adapter <checkSignatureAdapter.ViewHolder> {

    Context context;
    List<checkSignatureModel> sign_list;

    public checkSignatureAdapter(Context context, List<checkSignatureModel> sign_list) {
        this.context = context;
        this.sign_list = sign_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.table_row_item_with_button_layout,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.row_chon_van_ban_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull checkSignatureAdapter.ViewHolder holder, int position) {
        if (sign_list != null && sign_list.size()>0){
            checkSignatureModel model = sign_list.get(position);
            holder.imagine_view.setImageResource(model.getProfileImagine());
            holder.user_name.setText(model.getName());
            holder.date_tv.setText(model.getDate());
            holder.select_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.popup_info_file_for_signer,null);
                    TextView textMain, name_file, textDumpInfo, date_file, timeDumpGiao;
                
                    textMain = dialogView.findViewById(R.id.textMain);
                    name_file = dialogView.findViewById(R.id.name_file);
                    textDumpInfo = dialogView.findViewById(R.id.textDumpInfo);
                    date_file = dialogView.findViewById(R.id.date_file);

                    textMain.setText("Nội dung văn bản");
                    name_file.setText(model.getFullname());
                    textDumpInfo.setText(model.getDatafile());
                    date_file.setText(model.getDate()+model.getSignature());
                    builder.setView(dialogView);
//                    builder.setCancelable(true);
//                    builder.show();
                    ((Button) dialogView.findViewById(R.id.buttonAction)).setText("Kiểm tra");
                    ((Button) dialogView.findViewById(R.id.buttonNo)).setText("Thoát");
                    AlertDialog alertDialog = builder.create();

                    dialogView.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            System.out.println("-------------------------------------------------------------");
                            System.out.println("Bạn đã click thành công");
                            System.out.println("-------------------------------------------------------------");

                            //Thêm dữ liệu id vào kho
//                            SharedPreferences.Editor editor = context.getSharedPreferences("preference_user",MODE_PRIVATE).edit();
//                            editor.putString("id_van_ban",model.getId());
//                            editor.putString("noi_dung_van_ban",model.getDatafile());
//                            editor.commit();

//                            Intent intent = new Intent( v.getRootView().getContext(), TaoNhomKy.class);
//                            context.startActivity(intent);
                        }
                    });

                    dialogView.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
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
            });
//            holder.id_tv.setText(model.getId());


        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return sign_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagine_view;
        TextView user_name, date_tv ,timeDumpGiao;
        Button select_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagine_view = itemView.findViewById(R.id.imagine_view);
            user_name = itemView.findViewById(R.id.user_name);
            date_tv = itemView.findViewById(R.id.date_tv);
            select_btn = itemView.findViewById(R.id.select_btn);
            timeDumpGiao = itemView.findViewById(R.id.timeDumpGiao);

            itemView.setOnClickListener((v -> {
                Log.d("demo","onClick: item clicked" + "user"+user_name);
            }));

            itemView.findViewById(R.id.select_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo","onClick: Button" + "user"+user_name);

                }
            });

        }

    }
}
