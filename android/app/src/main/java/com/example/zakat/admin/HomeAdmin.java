package com.example.zakat.admin;

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
import com.example.zakat.admin.cfreview.CfruleActivity;
import com.example.zakat.admin.dataakun.ListAkunAdminActivity;
import com.example.zakat.admin.reviewamil.cfAmilActivity;
import com.example.zakat.admin.rule.RulesZakatActivity;
import com.example.zakat.admin.rulezakat.ruleZakatActivity;
import com.example.zakat.muzakki.Profile.GantiPasswordActivity;
import com.example.zakat.muzakki.Profile.ProfileMuzakkiActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class HomeAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private SessionManager sessionManager;
    CardView cvlistAkun, cvrules, cvzakatrule, cvcfpenilaian, cvreview;
    TextView tvusername, tvfullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        cvrules = findViewById(R.id.cvrules);
        cvcfpenilaian = findViewById(R.id.cvpenilaiancf);
        cvlistAkun = findViewById(R.id.cvListAkun);
        cvzakatrule = findViewById(R.id.cvzakatrule);
        cvreview = findViewById(R.id.cvreviewamil);

        //Create Sesi
        sessionManager = new SessionManager(getApplicationContext());
        //User Details
        HashMap<String, String> user = sessionManager.getDataUser();
        /*-----  Toolbar -----*/
        setSupportActionBar(toolbar);
        /*----- Navigation Drawer -----*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeAdmin.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        // List Akun
        cvlistAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeAdmin.this, ListAkunAdminActivity.class));
            }
        });
        cvrules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RulesZakatActivity.class));
            }
        });
        cvzakatrule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ruleZakatActivity.class));
            }
        });
        cvcfpenilaian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CfruleActivity.class));
            }
        });
        cvreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), cfAmilActivity.class));
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_Logout:
                sessionManager.setLogin(false);
                sessionManager.setDataUser(null ,null,null,null,null, null);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            case R.id.nav_Profile:
                Intent i = new Intent(HomeAdmin.this, ProfileMuzakkiActivity.class);
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