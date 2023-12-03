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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.ManagerTask.ShowDetailActivity;
import com.example.myapp1.R;
import com.example.myapp1.createSignGroup.TaoNhomKy;

import java.util.List;

public class checkSignatureAdapter extends RecyclerView.Adapter < checkSignatureAdapter.ViewHolder > {

    Context context;
    List < checkSignatureModel > sign_list;

    public checkSignatureAdapter(Context context, List < checkSignatureModel > sign_list) {
        this.context = context;
        this.sign_list = sign_list;
    }
    public void setList(List<checkSignatureModel> filterlist) {
        this.sign_list = filterlist;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //        View view = LayoutInflater.from(context).inflate(R.layout.table_row_item_with_button_layout,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.row_chon_van_ban_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull checkSignatureAdapter.ViewHolder holder, int position) {
        if (sign_list != null && sign_list.size() > 0) {
            checkSignatureModel model = sign_list.get(position);
            holder.imagine_view.setImageResource(model.getProfileImagine());
            holder.user_name.setText(model.getName());
            holder.date_tv.setText(model.getDate());
            holder.select_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.popup_info_file_for_signer, null);
                    TextView textMain, name_file, date_file, timeDumpGiao;

                    textMain = dialogView.findViewById(R.id.textMain);
                    name_file = dialogView.findViewById(R.id.name_file);
                    date_file = dialogView.findViewById(R.id.date_file);

                    textMain.setText("Nội dung văn bản");
                    name_file.setText(model.getFullname());

                    WebView webView = dialogView.findViewById(R.id.textDumpInfo);

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.getSettings().setUseWideViewPort(true);
                    webView.getSettings().setSupportZoom(true);
                    webView.setWebViewClient(new WebViewClient() {

                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            System.out.println("Đang load");
                            view.loadUrl(url);
                            return true;
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            System.out.println("Load thành công " + url);
                        }
                    });

                    String URL = "https://docs.google.com/gview?embedded=true&url=" + "https://drive.google.com/file/d/1BSdLBM_nIh5niI5lKsmtVy00NYeDn5J_/view";
                    URL = model.getDatafile();
                    System.out.println(URL);
                    webView.loadUrl(URL);


//                    date_file.setText(model.getDate().split(" ")[0] + "\n" + model.getSubMember());
                    date_file.setText(model.getDate().split(" ")[0]);
                    model.displayInfo();
                    builder.setView(dialogView);
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


                            System.out.println(model.getDatafile());
                            System.out.println(ecSHelper.getHashMsg_type2(context, model.getDatafile()));
                            System.out.println(model.getGroup_key());
                            System.out.println(model.getSignature());



                            String resultSig = ecSHelper.checkSignature(context, ecSHelper.getHashMsg_type2(context, model.getDatafile()), model.getGroup_key(), model.getSignature());

                            System.out.println(resultSig);
                            if (resultSig.equals("hong")) {
                                showErrorDialogNoReturn(v, "Kiểm tra thất bại", "Có vẻ đã có lỗi gì đó, hãy thông báo lại cho quản trị viên nhé");
                            } else {
                                showSuccessDialogNoReturn(v, "Kiểm tra thành công", "Xác thực thành công, chữ ký đã trùng khớp");
                            }

                        }
                    });

                    dialogView.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    ImageView iv = dialogView.findViewById(R.id.ivInfo);
                    iv.setVisibility(View.VISIBLE);

                    iv.setOnClickListener(v1 -> {

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(v1.getRootView().getContext());
                        View dialogView2 = LayoutInflater.from(v1.getRootView().getContext()).inflate(R.layout.popup_info_sign, null);

                        ((TextView) dialogView2.findViewById(R.id.tvName)).setText(model.getTmp());
                        builder2.setView(dialogView2);




                        AlertDialog alertDialog2 = builder2.create();

                        dialogView2.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog2.dismiss();
                            }
                        });



                        if (alertDialog2.getWindow() != null) {
                            alertDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }

                        alertDialog2.show();

                    });

                    if (alertDialog.getWindow() != null) {
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

    private void showSuccessDialogNoReturn(View v, String mainText, String comment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
        View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.textdialog, null);

        builder.setView(dialogView);

        ((TextView) dialogView.findViewById(R.id.textMain)).setText(mainText);
        ((TextView) dialogView.findViewById(R.id.textMessage)).setText(comment);

        ((Button) dialogView.findViewById(R.id.buttonAction)).setText("Xác nhận");

        AlertDialog alertDialog = builder.create();

        dialogView.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
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

    private void showErrorDialogNoReturn(View v, String mainText, String comment) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
        View view = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.popup_error_dialog_one_option, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textMain)).setText(mainText);
        ((TextView) view.findViewById(R.id.textMessage)).setText(comment);
        ((Button) view.findViewById(R.id.buttonAction)).setText("Xác nhận");

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagine_view;
        TextView user_name, date_tv, timeDumpGiao;
        Button select_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagine_view = itemView.findViewById(R.id.imagine_view);
            user_name = itemView.findViewById(R.id.user_name);
            date_tv = itemView.findViewById(R.id.date_tv);
            select_btn = itemView.findViewById(R.id.select_btn);
            timeDumpGiao = itemView.findViewById(R.id.timeDumpGiao);

            itemView.setOnClickListener((v -> {
                Log.d("demo", "onClick: item clicked" + "user" + user_name);
            }));

            itemView.findViewById(R.id.select_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo", "onClick: Button" + "user" + user_name);

                }
            });

        }

    }
}