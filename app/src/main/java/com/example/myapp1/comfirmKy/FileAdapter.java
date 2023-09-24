package com.example.myapp1.comfirmKy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.LoginTheme;
import com.example.myapp1.R;
import com.example.myapp1.createSignGroup.TaoNhomKy;

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
//        View view = LayoutInflater.from(context).inflate(R.layout.table_row_item_with_button_layout,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.row_chon_van_ban_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.ViewHolder holder, int position) {
        if (file_list != null && file_list.size()>0){
            FileModel model = file_list.get(position);
            holder.imagine_view.setImageResource(model.getProfileImagine());
            holder.user_name.setText(model.getName());
            holder.date_tv.setText(model.getPerson());
            holder.select_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.popup_info_file_for_signer,null);
                    TextView textMain, name_file, textDumpInfo, date_file;

                    textMain = dialogView.findViewById(R.id.textMain);
                    name_file = dialogView.findViewById(R.id.name_file);
                    textDumpInfo = dialogView.findViewById(R.id.textDumpInfo);
                    date_file = dialogView.findViewById(R.id.date_file);

                    textMain.setText("Ký xác nhận văn bản");
                    name_file.setText(model.getName());
                    textDumpInfo.setText(model.getPerson());
                    date_file.setText(model.getPerson());
                    builder.setView(dialogView);
//                    builder.setCancelable(true);
//                    builder.show();
                    ((Button) dialogView.findViewById(R.id.buttonAction)).setText("Xác nhận ký");
                    ((Button) dialogView.findViewById(R.id.buttonNo)).setText("Từ chối ký");
                    AlertDialog alertDialog = builder.create();

                    dialogView.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getRootView().getContext(), "Đã ký thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            System.out.println("-------------------------------------------------------------");
                            System.out.println("Bạn đã click thành công");
                            System.out.println("-------------------------------------------------------------");
                            showSuccessDialog("Ký số thành công", "Chúc mừng bạn đã ký thành công văn bản",v);
                            alertDialog.dismiss();


                        }
                    });

                    dialogView.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            showSuccessDialog("Hủy ký thành công", "Chức năng này không khả dụng, vui lòng đừng thử",v);

                            alertDialog.dismiss();
                        }
                    });

                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                    }

                    alertDialog.show();

                }
            });
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return file_list.size();
    }

    private void showSuccessDialog(String main, String text, View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(v.getRootView().getContext()).inflate(
                R.layout.textdialog,
                null);

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


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagine_view;
        TextView user_name, date_tv;
        Button select_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagine_view = itemView.findViewById(R.id.imagine_view);

            user_name = itemView.findViewById(R.id.user_name);
            date_tv = itemView.findViewById(R.id.date_tv);
            select_btn = itemView.findViewById(R.id.select_btn);

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
