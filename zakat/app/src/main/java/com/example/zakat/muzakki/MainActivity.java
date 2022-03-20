package com.example.zakat.muzakki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zakat.LoginActivity;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    CardView cvzakatperhiasan, cvzakatternak, cvzakattani, cvhistory, cvsetting;
    SessionManager sessionManager;
    TextView tvfullname, tvusername, tvtelepon;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize
        cvzakatperhiasan = findViewById(R.id.cvzakatperhiasan);
        cvzakatternak = findViewById(R.id.cvzakatternak);
        cvzakattani = findViewById(R.id.cvzakatpertanian);
        cvhistory = findViewById(R.id.cvhistory);
        cvsetting = findViewById(R.id.cvsettings);
        tvfullname = findViewById(R.id.tvfullname);
        tvusername = findViewById(R.id.tvusername);
        tvtelepon = findViewById(R.id.tvnomor);
        toolbar = findViewById(R.id.toolbar);
        //toolbar
        setSupportActionBar(toolbar);
        //Create Session
        sessionManager = new SessionManager(getApplicationContext());
        //User Details
        HashMap<String, String> user = sessionManager.getDataUser();
        String sUsername = user.get(sessionManager.UN);
        String sFullname = user.get(sessionManager.FN);
        String sTelepon = user.get(sessionManager.TELEPON);
        tvfullname.setText(sFullname);
        tvusername.setText(sUsername);
        tvtelepon.setText(sTelepon);
        cvzakatperhiasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FC.class);
                i.putExtra("fst", "P3");
                startActivity(i);
            }
        });
        cvzakatternak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FC.class);
                i.putExtra("fst", "P1");
                startActivity(i);
            }
        });
        cvzakattani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FC.class);
                i.putExtra("fst", "P2");
                startActivity(i);
            }
        });
        cvsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AkunMuzakkiActivity.class));
            }
        });
        cvhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HistoryMuzakkiActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_exit,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.keluar:
                sessionManager.setLogin(false);
                    sessionManager.setDataUser(null, null,null,null,null, null);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
            case R.id.home:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onRestart() {
        //Create Sesi
        sessionManager = new SessionManager(getApplicationContext());
        //User Details
        HashMap<String, String> user = sessionManager.getDataUser();
        String sUsername = user.get(sessionManager.UN);
        String sFullname = user.get(sessionManager.FN);
        String sTelepon = user.get(sessionManager.TELEPON);
        tvusername.setText(sUsername);
        tvfullname.setText(sFullname);
        tvtelepon.setText(sTelepon);
        super.onRestart();
    }
}