package com.example.zakat.amil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zakat.LoginActivity;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.admin.HomeAdmin;
import com.example.zakat.amil.penyaluran.ZakatPenyaluranActivity;
import com.example.zakat.amil.request.AmilPengambilanActivity;
import com.example.zakat.amil.terkumpul.ZakatTerkumpulAmilActivity;
import com.example.zakat.muzakki.Profile.GantiPasswordActivity;
import com.example.zakat.muzakki.Profile.ProfileMuzakkiActivity;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class HomeAmil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CardView cvpengambilan, cvterkumpul, cvpenyaluran;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private SessionManager sessionManager;
    TextView tvusername, tvfullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize
        setContentView(R.layout.activity_home_amil);
        cvpengambilan = findViewById(R.id.cvrequest);
        cvpenyaluran = findViewById(R.id.cvpenyaluran);
        cvterkumpul = findViewById(R.id.cvterkumpul);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        //Create Sesi
        sessionManager = new SessionManager(getApplicationContext());
        //User Details
        HashMap<String, String> user = sessionManager.getDataUser();
        /*-----  Toolbar -----*/
        setSupportActionBar(toolbar);
        /*----- Navigation Drawer -----*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeAmil.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //Navigation Drawer
        View headerView = navigationView.getHeaderView(0);
        tvfullname = headerView.findViewById(R.id.tvfullname);
        tvusername = headerView.findViewById(R.id.tvusername);
        String sFullname = user.get(sessionManager.FN);
        String sUsername = user.get(sessionManager.UN);
        tvusername.setText(sFullname);
        tvfullname.setText(sUsername);
        //cvpengambilan
        cvpengambilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AmilPengambilanActivity.class));
            }
        });
        cvterkumpul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ZakatTerkumpulAmilActivity.class));
            }
        });
        cvpenyaluran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ZakatPenyaluranActivity.class));
            }
        });

    }
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_Logout:
                    sessionManager.setLogin(false);
                    sessionManager.setDataUser(null ,null,null,null,null, null);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    break;
                case R.id.nav_Profile:
                    Intent i = new Intent(HomeAmil.this, ProfileMuzakkiActivity.class);
                    i.putExtra("admin", true);
                    startActivity(i);
                    break;
                case R.id.nav_changePassword:
                    startActivity(new Intent(getApplicationContext(), GantiPasswordActivity.class));
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

    @Override
    protected void onRestart() {
        //User Details
        HashMap<String, String> user = sessionManager.getDataUser();
        //Navigation Drawer
        View headerView = navigationView.getHeaderView(0);
        tvfullname = headerView.findViewById(R.id.tvfullname);
        tvusername = headerView.findViewById(R.id.tvusername);
        String sFullname = user.get(sessionManager.FN);
        String sUsername = user.get(sessionManager.UN);
        tvusername.setText(sFullname);
        tvfullname.setText(sUsername);
        super.onRestart();
    }
}