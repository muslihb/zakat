package com.example.zakat.muzakki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zakat.LoginActivity;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.muzakki.Profile.GantiPasswordActivity;
import com.example.zakat.muzakki.Profile.ProfileMuzakkiActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class AkunMuzakkiActivity extends AppCompatActivity {
    ListView listView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_muzakki);
        //initialize
//        tvfullname = findViewById(R.id.tvfullname);
//        tvusername = findViewById(R.id.tvusername);
//        tvtelepon = findViewById(R.id.tvnomor);
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.lv);
        //Create Sesi
//        sessionManager = new SessionManager(getApplicationContext());
        //User Details
//        HashMap<String, String> user = sessionManager.getDataUser();
//        String sUsername = user.get(sessionManager.UN);
//        String sFullname = user.get(sessionManager.FN);
//        String sTelepon = user.get(sessionManager.TELEPON);
//        tvusername.setText(sUsername);
//        tvfullname.setText(sFullname);
//        tvtelepon.setText(sTelepon);
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set ListView
        ArrayList<String> arrayList = new ArrayList<>();
        String[] profil = getResources().getStringArray(R.array.profilefragment);
        ArrayAdapter arrayAdapter = new ArrayAdapter(AkunMuzakkiActivity.this, android.R.layout.simple_list_item_1, profil);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    startActivity(new Intent(AkunMuzakkiActivity.this, ProfileMuzakkiActivity.class));
                }else if (position == 1){
                    startActivity(new Intent(AkunMuzakkiActivity.this, GantiPasswordActivity.class));
                }
//                else if (position == 2){
//                    sessionManager.setLogin(false);
//                    sessionManager.setDataUser(null, null,null,null,null, null);
//                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                    finish();
//                }
            }
        });
    }
//    @Override
//    protected void onRestart() {
//        //Create Sesi
//        sessionManager = new SessionManager(getApplicationContext());
//        //User Details
//        HashMap<String, String> user = sessionManager.getDataUser();
//        String sUsername = user.get(sessionManager.UN);
//        String sFullname = user.get(sessionManager.FN);
//        String sTelepon = user.get(sessionManager.TELEPON);
//        tvusername.setText(sUsername);
//        tvfullname.setText(sFullname);
//        tvtelepon.setText(sTelepon);
//        super.onRestart();
//    }
}