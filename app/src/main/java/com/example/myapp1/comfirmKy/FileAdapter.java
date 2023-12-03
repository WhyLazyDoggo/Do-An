package com.example.myapp1.comfirmKy;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.DatabaseHelper.DialogHelper;
import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.UpdateDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.R;
import com.example.myapp1.createSignGroup.TaoNhomKy;
import com.example.myapp1.createSignGroup.TaoNhomModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter <FileAdapter.ViewHolder>{

    Context context;
    List<FileModel> file_list;

    public FileAdapter(Context context, List<FileModel> file_list) {
        this.context = context;
        this.file_list = file_list;
    }

    public void setList(List<FileModel> filterlist) {
        this.file_list = filterlist;
        notifyDataSetChanged();
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

            SharedPreferences.Editor editor = context.getSharedPreferences("preference_user", MODE_PRIVATE).edit();
            SharedPreferences prefs = context.getSharedPreferences("preference_user", MODE_PRIVATE);

            FileModel model = file_list.get(position);
            holder.imagine_view.setImageResource(model.getProfileImagine());
            holder.user_name.setText(model.getName());
            holder.date_tv.setText(model.getDate());
            holder.select_btn.setText("Ký văn bản");
            holder.select_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.popup_info_file_for_signer,null);
                    TextView textMain, name_file, textDumpInfo, date_file;

                    textMain = dialogView.findViewById(R.id.textMain);
                    name_file = dialogView.findViewById(R.id.name_file);
                    date_file = dialogView.findViewById(R.id.date_file);

                    textMain.setText("Ký xác nhận văn bản");
                    name_file.setText(model.getFullname());

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


                    date_file.setText(model.getDate());
                    builder.setView(dialogView);
//                    builder.setCancelable(true);
//                    builder.show();
                    ((Button) dialogView.findViewById(R.id.buttonAction)).setText("Xác nhận ký");
                    ((Button) dialogView.findViewById(R.id.buttonNo)).setText("Từ chối ký");
                    AlertDialog alertDialog = builder.create();

                    dialogView.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String[] passwd = {""};
                            System.out.println("Mật khẩu trước tiên được là: " + passwd[0]);
                            Toast.makeText(v.getRootView().getContext(), "Đang ký văn bản", Toast.LENGTH_SHORT).show();

                            System.out.println("-------------------------------------------------------------");
                            System.out.println("Bạn đã click thành công");
                            System.out.println("Id bạn đang chọn là:"+model.getId());
                            System.out.println("-------------------------------------------------------------");

                            SharedPreferences prefs = context.getSharedPreferences("preference_user", MODE_PRIVATE);
                            System.out.println(prefs.getAll());
                            String id_nhomky = model.getId_nhomKy();
                            final String[] privkeyMain = {""};



                            AlertDialog.Builder builder2 = new AlertDialog.Builder(v.getRootView().getContext(), R.style.AlertDialogTheme);
                            View view = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.popup_warning_gettext_dialog, null
                            );
                            builder2.setView(view);

                            ((TextView) view.findViewById(R.id.textMain)).setText("Ký văn bản");



                            AlertDialog alertDialogMini = builder2.create();

                            view.findViewById(R.id.buttonAction).setOnClickListener(v1 -> {
                                passwd[0] = String.valueOf(((TextView) view.findViewById(R.id.textMessage)).getText());
                                alertDialogMini.dismiss();

                                System.out.println("Mật khẩu nhận được là: " + passwd[0]);

                                String encryptPrikeyFromFile = readfile(ecSHelper.sha256(prefs.getString("id_user", "XXXX") + "Lưu trữ khóa"));
                                System.out.println("Khóa được lưu trong file là: " + encryptPrikeyFromFile);
                                //Xong rồi giải mã lại để kiểm tra chéo
                                String decryptPrikey = ecSHelper.deCryptKey(context.getContentResolver(), String.valueOf(passwd[0] + prefs.getString("id_user", "XXXY")), encryptPrikeyFromFile);
                                System.out.println("Bản mã sau khi giải mã là: " + decryptPrikey);

                                String pubkeytemp = ecSHelper.getPubkey(v.getContext(), decryptPrikey);

                                System.out.println("Khóa công khai lấy được là: " + pubkeytemp);
                                if (!prefs.getString("pubkey", "XXX").equals(pubkeytemp)) {
                                    System.out.println("Lỗi rồi, sai khóa r nhé");
                                    DialogHelper.showErrorOneButton(context,"Xác thực tài khoản", "Xác thực tài khoản thất bại, vui lòng thử lại sau!");

                                } else {
                                    privkeyMain[0] = decryptPrikey;
                                    System.out.println("So sánh khóa giống nhau 100% y đúc");

                                    alertDialog.dismiss();



                                    AlertDialog.Builder builder3 = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                                    View viewProcess = LayoutInflater.from(context).inflate(
                                            R.layout.popup_loading_process,
                                            null
                                    );
                                    builder3.setView(viewProcess);

                                    ((TextView) viewProcess.findViewById(R.id.textMain)).setText("Quá trình ký văn bản");

                                    TextView textprocess = viewProcess.findViewById(R.id.textMessage);

                                    AlertDialog alertProcess = builder3.create();

                                    alertProcess.show();
                                    textprocess.setText("Đang thực hiện hành động ký xác nhận văn bản");

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run(){
                                            if (1==1) {

                                                ResultSet rs = null;


                                                rs = SelectDB.getXcheck(id_nhomky);
                                                String Xcheck = "";
                                                String pubkey = "";

                                                try {
                                                    while (rs.next()) {
                                                        pubkey += rs.getString("khoa_cong_khai") + " ";

                                                    }
                                                } catch (Exception ex) {
                                                    Log.e("error", ex.getMessage());
                                                }

                                                System.out.println("Giá trị pubkey:" + pubkey);
                                                String Xgroup_check = ecSHelper.getXcheck(context, pubkey);

                                                Toast.makeText(v.getRootView().getContext(), "Đang trong giai đoạn ký", Toast.LENGTH_SHORT).show();
                                                rs = SelectDB.getDataForSign(id_nhomky, model.getId());
                                                try {
                                                    while (rs.next()) {
                                                        String c = rs.getString("c");
                                                        String KiMain = rs.getString("ki");
                                                        String Lgroup = rs.getString("L");
                                                        String Xgroup = rs.getString("X");
                                                        String Rsum = rs.getString("Rsum");

                                                        System.out.println("Giá trị id_nhomky:" + model.getId_nhomKy());
                                                        System.out.println("Giá trị id_process:" + model.getId());
                                                        System.out.println("Giá trị c:" + c);
                                                        System.out.println("Giá trị ki:" + KiMain);
                                                        System.out.println("Giá trị L:" + Lgroup);
                                                        System.out.println("Giá trị private:" + privkeyMain[0]);
                                                        String Si = ecSHelper.getSi(context, privkeyMain[0], c, KiMain, Lgroup, Xgroup_check, Rsum);


                                                        System.out.println("Giá trị Si tính ra là:" + Si);
                                                        UpdateDB.kyVanBan("9999999999", "9999999999", Si, model.getId());

                                                        editor.putString("count_sign", String.valueOf(Integer.parseInt(prefs.getString("count_sign", "0")) - 1));
                                                        editor.commit();

                                                    }
                                                } catch (Exception ex) {
                                                    alertProcess.dismiss();
//                                                    DialogHelper.showErrorOneButton(context,"Lỗi ký xác nhận","Bạn đang gặp lỗi trong quá trình ký.\n" +
//                                                            "Vui lòng thử lại sau");
                                                    Log.e("error", ex.getMessage());
                                                }


                                                ResultSet rs3 = null;
                                                ResultSet rs2 = null;
                                                rs3 = SelectDB.getSiGroup();
                                                String msg = "";
                                                String Xgroup = "";
                                                String Rsum = "";
                                                String id_nhom = "";
                                                String sSumArray = "";
                                                Toast.makeText(v.getRootView().getContext(), "Tới bước 2 của ký văn bản", Toast.LENGTH_SHORT).show();
                                                try {
                                                    while (rs3.next()) {

                                                        if (id_nhom.equals(rs3.getString("id_nhom_ky"))) {
                                                            sSumArray += rs3.getString("chu_ky_ca_nhan") + " ";
                                                            System.out.println(id_nhom + " Giá trị của tệp là khi trùng nhóm: " + sSumArray);
                                                            System.out.println("Các giá trị của file là: ");
                                                            System.out.println(msg);
                                                            System.out.println(Xgroup);
                                                            System.out.println(Rsum);
                                                            System.out.println("--------------------------");
                                                        } else {

                                                            try {
                                                                String signature = ecSHelper.getSignature(context, sSumArray, Rsum, Xgroup, msg);
                                                                System.out.println("Giá trị chữ ký là:" + signature);
                                                                UpdateDB.updateChuKy(signature, id_nhom);
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }

                                                            id_nhom = rs3.getString("id_nhom_ky");
                                                            sSumArray = rs3.getString("chu_ky_ca_nhan") + " ";
                                                            System.out.println(id_nhom + " Giá trị của tệp là khi khác nhóm: " + sSumArray);
                                                            System.out.println("Lấy giá trị msg, XGroup và Rsum mới");
                                                            rs2 = SelectDB.getMsgXgroupRsum(id_nhom);
                                                            rs2.next();
                                                            msg = ecSHelper.getHashMsg_type2(context, rs2.getString("noi_dung_tom_tat"));
                                                            Xgroup = rs2.getString("X");
                                                            Rsum = rs2.getString("Rsum");
                                                        }

                                                        try {
                                                            String signature = ecSHelper.getSignature(context, sSumArray, Rsum, Xgroup, msg);
                                                            System.out.println("Giá trị chữ ký là:" + signature);
                                                            UpdateDB.updateChuKy(signature, id_nhom);
                                                            Toast.makeText(v.getRootView().getContext(), "Ký thành công", Toast.LENGTH_SHORT).show();
                                                        } catch (Exception e) {
                                                            System.out.println("Chữ ký không được tạo ra, vô cùng xin lỗi hihi");
                                                            System.out.println(e);
//                                                            DialogHelper.showErrorOneButton(context,"Lỗi ký xác nhận","Bạn đang gặp lỗi trong quá trình ký.\n" +
//                                                                    "Vui lòng thử lại sau");
                                                        }

                                                    }
                                                } catch (SQLException ex) {
                                                    Log.e("error", ex.getMessage());
                                                }

                                                alertProcess.dismiss();
                                                DialogHelper.showSuccessDialogNoReturn(context,"Ký số thành công", "Chúc mừng bạn đã ký thành công văn bản");
                                                alertDialog.dismiss();

                                            }
                                        }}, 1000); // Thời gian độ trễ trong milliseconds (ở đây là 2000ms = 2s)




                                    if (alertProcess.getWindow() != null) {
                                        alertProcess.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                    }
                                }

                                });

                            view.findViewById(R.id.buttonNo).setOnClickListener(v1 -> {
                                alertDialogMini.dismiss();
                            });

                            if (alertDialogMini.getWindow() != null) {
                                alertDialogMini.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }

                            alertDialogMini.show();



                        }
                    });

                    dialogView.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            showSuccessDialog("Hủy ký thành công", "Chức năng này không khả dụng, vui lòng đừng thử",v);

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

    public String readfile(String filepath) {
        filepath = filepath + ".key";
        try {
            System.out.println("-----");
            System.out.println("Đang đọc trong file: " + filepath);
            InputStream inputStream = context.openFileInput(filepath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String output = "";
            output = bufferedReader.readLine();

            inputStream.close();
            System.out.println("Kết quả khi đọc file:" + output);
            System.out.println("-----");
            return String.valueOf(output);
        } catch (IOException e) {
            System.out.println("Error" + e);
            return "Error404";
        }
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
