package com.example.zakat.muzakki.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.model.ResponseData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GantiPasswordActivity extends AppCompatActivity {
    TextInputEditText tvpasswordnow, tvpasswordnew, tvpasswordconfirm;
    TextView tvid;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);
        //initialize
        tvid = findViewById(R.id.tvid);
        Toolbar toolbar = findViewById(R.id.toolbar);
        tvpasswordnow = findViewById(R.id.tvpasswordnow);
        tvpasswordnew = findViewById(R.id.tvpasswordnew);
        tvpasswordconfirm = findViewById(R.id.tvpasswordconfirm);
        //Set Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Create Sesi
        sessionManager = new SessionManager(getApplicationContext());
        //User Details
        HashMap<String, String> user = sessionManager.getDataUser();
        String sUsername = user.get(sessionManager.UN);
        tvid.setText(sUsername);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.includetoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.checklist:
                String pnow = tvpasswordnow.getText().toString().trim();
                String pnew = tvpasswordnew.getText().toString().trim();
                String pconfirm = tvpasswordconfirm.getText().toString().trim();
                if (TextUtils.isEmpty(pnow)){
                    tvpasswordnow.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(pnew)){
                    tvpasswordnew.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(pconfirm)){
                    tvpasswordconfirm.setError("Tidak Boleh Kosong");
                }
                if (!TextUtils.isEmpty(pnow) && !TextUtils.isEmpty(pnew) &&
                        !TextUtils.isEmpty(pconfirm)){
                   cekpwnow();
                }
                break;
            case R.id.home:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void gantipw() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> ubahpw = apiAdapter.updatepw(tvid.getText().toString(),
                tvpasswordconfirm.getText().toString());
        ubahpw.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    Toast.makeText(GantiPasswordActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(GantiPasswordActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(GantiPasswordActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void cekpwnow(){
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> cekpw = apiAdapter.cekpw(tvid.getText().toString(),
                tvpasswordnow.getText().toString());
        cekpw.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    if (tvpasswordnew.getText().toString().equals(tvpasswordconfirm.getText().toString())){
                        gantipw();
                    }else {
                        Toast.makeText(GantiPasswordActivity.this, "cek kembali password anda", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(GantiPasswordActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(GantiPasswordActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}