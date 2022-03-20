package com.example.zakat.amil.request;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.Register;
import com.example.zakat.SessionManager;
import com.example.zakat.model.ResponseData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPengambilanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvidRequest, tvjenisZakat, tvNishob, tvtotalzakat, tvnamamuzakki,
            tvnomormuzakki, tvalamat, tvstatus;
    Button btnUpdate, btnKonfirmasi;
    TextInputLayout tilnishob, tiltotalzakat;
    TextInputEditText etnishob,ettotalzakat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pengambilan);
        //initialize
        toolbar = findViewById(R.id.toolbar);
        tvidRequest = findViewById(R.id.tvid);
        tvjenisZakat = findViewById(R.id.tvJZ);
        tvNishob = findViewById(R.id.tvjumlahnishob);
        tvtotalzakat = findViewById(R.id.tvtlz);
        tvnamamuzakki = findViewById(R.id.tvnamamuzakki);
        tvnomormuzakki = findViewById(R.id.tvnomormuzakki);
        tvalamat = findViewById(R.id.tvalamat);
        tvstatus = findViewById(R.id.tvstatus);
        btnKonfirmasi = findViewById(R.id.btnkonfirmasi);
        btnUpdate = findViewById(R.id.btnupdate);
        tilnishob = findViewById(R.id.tiljumlahnishob);
        tiltotalzakat = findViewById(R.id.tiltotalzakat);
        etnishob = findViewById(R.id.etjumlahnishob);
        ettotalzakat = findViewById(R.id.ettotalzakat);
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //get Intent
        tvidRequest.setText(getIntent().getStringExtra("tvid"));
        tvjenisZakat.setText(getIntent().getStringExtra("tvJZ"));
        tvNishob.setText(getIntent().getStringExtra("tvNishob"));
        if (tvjenisZakat.getText().toString().equals("Sapi") || tvjenisZakat.getText().toString().equals("Kambing")){
            tvtotalzakat.setText(getIntent().getStringExtra("tvtlz")+ " Ekor");
        }else if (tvjenisZakat.getText().toString().equals("Emas") || tvjenisZakat.getText().toString().equals("Perak")){
            tvtotalzakat.setText(getIntent().getStringExtra("tvtlz")+ " gram");
        }else {
            tvtotalzakat.setText(getIntent().getStringExtra("tvtlz")+ " KG");
        }
        tvnamamuzakki.setText(getIntent().getStringExtra("tvnmmuzakki"));
        tvnomormuzakki.setText(getIntent().getStringExtra("tvnomormuzakki"));
        tvalamat.setText(getIntent().getStringExtra("tvalamat"));
        tvstatus.setText(getIntent().getStringExtra("tvstatus"));
        etnishob.setText(tvNishob.getText().toString());
        ettotalzakat.setText(tvtotalzakat.getText().toString());
        if (tvstatus.getText().toString().equals("Pending")){
            btnUpdate.setVisibility(View.VISIBLE);
        }else if (tvstatus.getText().toString().equals("Berlangsung")) {
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setText("Konfirmasi Pengambilan");
        }
        else {
            btnUpdate.setVisibility(View.GONE);
        }
        if (tvjenisZakat.getText().toString().equals("Sapi") &&
                tvstatus.getText().toString().equals("Berlangsung")) {
            btnKonfirmasi.setVisibility(View.VISIBLE);
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnUpdate.getText().toString().equals("Konfirmasi Pengambilan")){
                    Otp otp = new Otp(tvidRequest.getText().toString(), tvstatus.getText().toString());
                    otp.show(getSupportFragmentManager(), "form");
                }else {
                    konfirmasi();
                }
            }
        });
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnKonfirmasi.getText().toString().equals("Konfirmasi")){
                    tilnishob.setVisibility(View.GONE);
                    tiltotalzakat.setVisibility(View.GONE);
                    tvNishob.setVisibility(View.VISIBLE);
                    tvtotalzakat.setVisibility(View.VISIBLE);
                    updateNishob();
                }else {
                    tvNishob.setVisibility(View.GONE);
                    tvtotalzakat.setVisibility(View.GONE);
                    tilnishob.setVisibility(View.VISIBLE);
                    tiltotalzakat.setVisibility(View.VISIBLE);
                    btnKonfirmasi.setText("Konfirmasi");
                }
            }
        });
    }
    private void konfirmasi() {
        SessionManager sessionManager;
        //create sesi
        sessionManager = new SessionManager(getApplicationContext());
        //User Details
        HashMap<String, String> user = sessionManager.getDataUser();
        String sID = user.get(sessionManager.ID);
        String statusUpdate = "Berlangsung";
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> updateData = ardData.UpdatePendingAmil(tvidRequest.getText().toString(), sID, statusUpdate);
        updateData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                Toast.makeText(InfoPengambilanActivity.this, pesan, Toast.LENGTH_SHORT).show();
                tvstatus.setText(statusUpdate);
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setText("Konfirmasi Pengambilan");
                if (tvjenisZakat.getText().toString().equals("Sapi") &&
                        tvstatus.getText().toString().equals("Berlangsung")) {
                    btnKonfirmasi.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoPengambilanActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void updateNishob(){
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> updateData = ardData.updateNishob(tvidRequest.getText().toString(),
                etnishob.getText().toString(), ettotalzakat.getText().toString());
        updateData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                tvNishob.setText(etnishob.getText().toString());
                tvtotalzakat.setText(ettotalzakat.getText().toString());
                Toast.makeText(InfoPengambilanActivity.this, pesan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoPengambilanActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

            }
        });
    }
}