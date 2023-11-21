package com.example.myapp1.createSignGroup;
import static android.content.Context.MODE_PRIVATE;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.ManagerTask.UserModel;
import com.example.myapp1.R;

import java.util.List;

public class ChonVanAdapter extends RecyclerView.Adapter <ChonVanAdapter.ViewHolder> {

    Context context;
    List<ChonVanModel> file_list;

    public ChonVanAdapter(Context context, List<ChonVanModel> file_list){
        this.context = context;
        this.file_list = file_list;
    }

    public void setList(List<ChonVanModel> filterlist) {
        this.file_list = filterlist;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ChonVanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chon_van_ban_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChonVanAdapter.ViewHolder holder, int position) {
        if (file_list != null && file_list.size()>0){
            ChonVanModel model = file_list.get(position);
            holder.imagine_view.setImageResource(model.getProfileImagine());
            holder.user_name.setText(model.getName());
            holder.date_tv.setText(model.getDate());
            holder.select_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.popup_info_file_for_signer,null);
                    TextView textMain, name_file, date_file, timeDumpGiao;

                    textMain = dialogView.findViewById(R.id.textMain);
                    name_file = dialogView.findViewById(R.id.name_file);
                    timeDumpGiao = dialogView.findViewById(R.id.timeDumpGiao);
                    timeDumpGiao.setText("Thời gian tạo:");

                    WebView webView = dialogView.findViewById(R.id.textDumpInfo);

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.getSettings().setUseWideViewPort(true);
                    webView.getSettings().setSupportZoom(true);
                    webView.setWebViewClient(new WebViewClient(){

                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            System.out.println("Đang load");
                            view.loadUrl(url);
                            return true;
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            System.out.println("Load thành công "+url);
                        }
                    });

                    String URL = "https://docs.google.com/gview?embedded=true&url=" + "https://drive.google.com/file/d/1BSdLBM_nIh5niI5lKsmtVy00NYeDn5J_/view";
                    URL = "https://drive.google.com/file/d/1BSdLBM_nIh5niI5lKsmtVy00NYeDn5J_/view";
                    URL ="https://drive.google.com/file/d/1huxoE3lIn7JWFrZt-ihbnE60h60MOcP8/view";
                    URL = "https://maimaidx-eng.com/maimai-mobile/home/";
//                    URL = "https://docs.google.com/document/d/1uPaA8OwtduwOao5wCeBV2OPPJYodqyjS/edit?usp=share_link&ouid=100193860160107751936&rtpof=true&sd=true";
                    URL = model.getDatafile();
                    System.out.println(URL);
                    webView.loadUrl(URL);


                    date_file = dialogView.findViewById(R.id.date_file);

                    textMain.setText("Chọn văn bản");
                    name_file.setText(model.getFullname());
                    date_file.setText(model.getDate());
                    builder.setView(dialogView);
//                    builder.setCancelable(true);
//                    builder.show();
                    ((Button) dialogView.findViewById(R.id.buttonAction)).setText("Xác nhận giao");
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
                            SharedPreferences.Editor editor = context.getSharedPreferences("preference_user",MODE_PRIVATE).edit();
                            System.out.println("Giá trị hiện tại:"+model.getDatafile());
                            editor.putString("id_van_ban",model.getId());
                            editor.putString("noi_dung_van_ban",model.getDatafile());
                            editor.commit();

                            Intent intent = new Intent(v.getRootView().getContext(), TaoNhomKy.class);
                            context.startActivity(intent);
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
        return file_list.size();
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
