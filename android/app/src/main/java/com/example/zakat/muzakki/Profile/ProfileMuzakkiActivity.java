package com.example.zakat.muzakki.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.model.ResponseData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileMuzakkiActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText nl, np, nt, alamat;
    TextInputLayout tilalamat;
    SessionManager sessionManager;
    TextView textViewiu;
    String stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_muzakki);
        // initialize
        textViewiu = findViewById(R.id.tvid);
        toolbar = findViewById(R.id.toolbar);
        np = findViewById(R.id.np);
        nl = findViewById(R.id.nl);
        nt = findViewById(R.id.nt);
        alamat = findViewById(R.id.alamat);
        tilalamat = findViewById(R.id.tilalamat);
        if (getIntent().getBooleanExtra("admin", false)){
            tilalamat.setVisibility(View.GONE);
        }else {
            tilalamat.setVisibility(View.VISIBLE);
        }
        //Create Sesi
        sessionManager = new SessionManager(getApplicationContext());
        //User Details
        HashMap<String, String> user = sessionManager.getDataUser();
        String sIdUser = user.get(sessionManager.ID);
        String sUsername = user.get(sessionManager.UN);
        String sFullname = user.get(sessionManager.FN);
        String sTelepon = user.get(sessionManager.TELEPON);
        String sAlamat = user.get(sessionManager.ADDRESS);
        String sStat = user.get(sessionManager.STATUS);
        stat = sStat;
        textViewiu.setText(sIdUser);
        np.setText(sUsername);
        nl.setText(sFullname);
        nt.setText(sTelepon);
        alamat.setText(sAlamat);
        //Set Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.includetoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.checklist:
                if (getIntent().getBooleanExtra("admin", false)){
                    String fn = nl.getText().toString().trim();
                    String un = np.getText().toString().trim();
                    String pn = nt.getText().toString().trim();
                    if (fn.equals("")){
                        nl.setError("Tidak Boleh Kosong");
                    }
                    if (un.equals("")){
                        np.setError("Tidak Boleh Kosong");
                    }
                    if (pn.equals("")){
                        nt.setError("Tidak Boleh Kosong");
                    }
                    if (!TextUtils.isEmpty(fn) && !TextUtils.isEmpty(un)
                            && !TextUtils.isEmpty(pn)){
                        updatedata();
                    }
                }else {
                    String fn = nl.getText().toString().trim();
                    String un = np.getText().toString().trim();
                    String pn = nt.getText().toString().trim();
                    String address = alamat.getText().toString().trim();
                    if (fn.equals("")){
                        nl.setError("Tidak Boleh Kosong");
                    }
                    if (un.equals("")){
                        np.setError("Tidak Boleh Kosong");
                    }
                    if (pn.equals("")){
                        nt.setError("Tidak Boleh Kosong");
                    }
                    if (address.equals("")){
                        alamat.setError("Tidak Boleh Kosong");
                    }
                    if (!TextUtils.isEmpty(fn) && !TextUtils.isEmpty(un)
                            && !TextUtils.isEmpty(pn) && !TextUtils.isEmpty(address)){
                        updatedata();
                    }
                }
               break;
            case R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updatedata() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> ubahprofile = apiAdapter.updateakun(textViewiu.getText().toString(),
                np.getText().toString(), nl.getText().toString(), nt.getText().toString(),
                alamat.getText().toString());
        ubahprofile.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                String sIduser = textViewiu.getText().toString();
                String sUsername = np.getText().toString();
                String sfullname = nl.getText().toString();
                String stelepon =  nt.getText().toString();
                String sAlamat = alamat.getText().toString();
                if (!kode.equals("0")){
                    Toast.makeText(ProfileMuzakkiActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    sessionManager.setDataUser(sIduser, sUsername, sfullname, stelepon, sAlamat, stat);
                    finish();
                }else{
                    Toast.makeText(ProfileMuzakkiActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(ProfileMuzakkiActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}