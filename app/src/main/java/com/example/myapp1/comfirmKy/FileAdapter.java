package com.example.myapp1.comfirmKy;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp1.DatabaseHelper.SelectDB;
import com.example.myapp1.DatabaseHelper.UpdateDB;
import com.example.myapp1.DatabaseHelper.ecSHelper;
import com.example.myapp1.LoginTheme;
import com.example.myapp1.R;
import com.example.myapp1.createSignGroup.TaoNhomKy;
import com.example.myapp1.fragmentcontent.FragmentTest;

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
            holder.date_tv.setText(model.getDate());
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
                    name_file.setText(model.getFullname());
                    textDumpInfo.setText(model.getDatafile());
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
                            Toast.makeText(v.getRootView().getContext(), "Đã ký thành công", Toast.LENGTH_SHORT).show();

                            System.out.println("-------------------------------------------------------------");
                            System.out.println("Bạn đã click thành công");
                            System.out.println("Id bạn đang chọn là:"+model.getId());
                            System.out.println("-------------------------------------------------------------");
                            SharedPreferences prefs = context.getSharedPreferences("preference_user", MODE_PRIVATE);
                            System.out.println(prefs.getAll());
                            String id_nhomky = model.getId_nhomKy();
                            String privkeyMain =prefs.getString("privatekey","");

                            ResultSet rs = null;

                            rs = SelectDB.getDataForSign(id_nhomky,model.getId());
                            try{
                                while (rs.next()){
                                    String c = rs.getString("c");
                                    String KiMain = rs.getString("ki");
                                    String Lgroup = rs.getString("L");

                                    System.out.println("Giá trị id_nhomky:"+model.getId_nhomKy());
                                    System.out.println("Giá trị id_process:"+model.getId());
                                    System.out.println("Giá trị c:"+c);
                                    System.out.println("Giá trị ki:"+KiMain);
                                    System.out.println("Giá trị L:"+Lgroup);
                                    System.out.println("Giá trị private:"+privkeyMain);
                                    String Si = ecSHelper.getSi(context,privkeyMain,c,KiMain,Lgroup);

                                    System.out.println("Giá trị Si tính ra là:"+Si);
                                    UpdateDB.kyVanBan("pass","pass",Si,model.getId());
                                }
                            } catch (Exception ex){
                                Log.e("error",ex.getMessage());
                            }


                            ResultSet rs3 = null;
                            ResultSet rs2 = null;
                            rs3 = SelectDB.getSiGroup();
                            String msg ="";
                            String Xgroup ="";
                            String Rsum ="";
                            String id_nhom = "";
                            String sSumArray ="";
                            try {
                                while (rs3.next()){

                                    if (id_nhom.equals(rs3.getString("id_nhomky"))){
                                        sSumArray += rs3.getString("Si")+" ";
                                        System.out.println(id_nhom+" Giá trị của tệp là khi trùng nhóm: "+sSumArray);
                                        System.out.println("Các giá trị của file là: ");
                                        System.out.println(msg);
                                        System.out.println(Xgroup);
                                        System.out.println(Rsum);
                                    }else {

                                        try {
                                            String signature = ecSHelper.getSignature(context,sSumArray,Rsum,Xgroup,msg);
                                            System.out.println("Giá trị chữ ký là:"+signature);
                                            UpdateDB.updateChuKy(signature,id_nhom);
                                        }catch (Exception e){
                                            System.out.println(e);
                                        }

                                        id_nhom = rs3.getString("id_nhomky");
                                        sSumArray = rs3.getString("Si")+" ";
                                        System.out.println(id_nhom+" Giá trị của tệp là khi khác nhóm: "+sSumArray);
                                        System.out.println("Lấy giá trị msg, XGroup và Rsum mới");
                                        rs2 = SelectDB.getMsgXgroupRsum(id_nhom);
                                        rs2.next();
                                        msg = rs2.getString("hash_vanban");
                                        Xgroup = rs2.getString("X");
                                        Rsum = rs2.getString("Rsum");
                                    }

                                    try {
                                        String signature = ecSHelper.getSignature(context,sSumArray,Rsum,Xgroup,msg);
                                        System.out.println("Giá trị chữ ký là:"+signature);
                                        UpdateDB.updateChuKy(signature,id_nhom);
                                    }catch (Exception e){
                                        System.out.println("Chữ ký không được tạo ra, vô cùng xin lỗi hihi");
                                        System.out.println(e);
                                    }

                                }
                            }catch (SQLException ex){
                                Log.e("error",ex.getMessage());
                            }




                            alertDialog.dismiss();





//

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
