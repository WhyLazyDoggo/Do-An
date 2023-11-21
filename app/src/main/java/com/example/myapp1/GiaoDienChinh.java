package com.example.myapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapp1.checkSignature.*;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.myapp1.comfirmKy.KyXacNhanVanban;
import com.example.myapp1.fragmentcontent.FragmentCreateGroup;
import com.example.myapp1.fragmentcontent.FragmentHome;
import com.example.myapp1.fragmentcontent.FragmentInfoAll;
import com.example.myapp1.fragmentcontent.FragmentSign;
import com.example.myapp1.fragmentcontent.FragmentTest;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class GiaoDienChinh extends AppCompatActivity {

    private long backPressedTime;
    private Toast mToast;

    private Fragment fragInfo = new FragmentInfoAll();

    private MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_chinh);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.show(1, true);
        replaceFragment(new FragmentHome());

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.home_icon));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.sign_icon));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.settingreplacefinger));

        bottomNavigation.setOnClickMenuListener(new Function1 < MeowBottomNavigation.Model, Unit > () {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {

                    case 1:
                        replaceFragment(new FragmentHome());
                        break;

                    case 2:
                        replaceFragment(new FragmentSign());
                        break;

                    case 3:
                        replaceFragment(fragInfo);
                        break;

                }

                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1 < MeowBottomNavigation.Model, Unit > () {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {

                    case 1:
                        replaceFragment(new FragmentHome());
                        break;

                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1 < MeowBottomNavigation.Model, Unit > () {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {

                    case 2:
                        replaceFragment(new FragmentSign());
                        break;

                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1 < MeowBottomNavigation.Model, Unit > () {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {

                    case 3:
                        replaceFragment(fragInfo);
                        break;

                }
                return null;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            mToast.cancel();

            finish();
            super.onBackPressed();
            return;
        } else {
            mToast = Toast.makeText(GiaoDienChinh.this, "Bấm lại lần nữa để thoát", Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}