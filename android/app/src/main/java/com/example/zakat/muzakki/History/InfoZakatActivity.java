package com.example.zakat.muzakki.History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.LoginActivity;
import com.example.zakat.R;
import com.example.zakat.model.LoginResponse;
import com.example.zakat.model.RequestResponse;
import com.example.zakat.model.ResponseData;
import com.example.zakat.muzakki.HistoryMuzakkiActivity;
import com.example.zakat.muzakki.PenilaianActivity;
import com.example.zakat.muzakki.Profile.GantiPasswordActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoZakatActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvidRequest, tvjenisZakat, tvNishob, tvtotalzakat, tvnamamuzakki,
            tvnomormuzakki, tvalamat, tvstatus, tvidamil, tvnamaamil, tvkode, tvotp;
    Button btnKonfirmasi;
    private List<LoginResponse> getuser;
    private List<RequestResponse> listzakat;
    View v_kode;
    String idamiil;
    int tersedia, req, hasil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_zakat);
        //initialize
        toolbar = findViewById(R.id.toolbar);
        tvidRequest = findViewById(R.id.tvid);
        tvjenisZakat = findViewById(R.id.tvJZ);
        tvkode = findViewById(R.id.tvkodekonf);
        tvotp = findViewById(R.id.tvotp);
        v_kode = findViewById(R.id.v_kode);
        tvNishob = findViewById(R.id.tvjumlahnishob);
        tvtotalzakat = findViewById(R.id.tvtlz);
        tvnamamuzakki = findViewById(R.id.tvnamamuzakki);
        tvnomormuzakki = findViewById(R.id.tvnomormuzakki);
        tvalamat = findViewById(R.id.tvalamat);
        tvstatus = findViewById(R.id.tvstatus);
        tvidamil = findViewById(R.id.tvidamil);
        btnKonfirmasi = findViewById(R.id.btnkonfirmasi);
        tvnamaamil = findViewById(R.id.tvnamaamil);
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Get Intent
        tvidRequest.setText(getIntent().getStringExtra("tvid"));
        tvjenisZakat.setText(getIntent().getStringExtra("tvJZ"));
        tvNishob.setText(getIntent().getStringExtra("tvNishob"));
        if (tvjenisZakat.getText().toString().equals("Sapi") || tvjenisZakat.getText().toString().equals("Kambing")){
            tvtotalzakat.setText("Zakat yang dikeluarkan"+ " "+getIntent().getStringExtra("tvtlz")+ " Ekor");
            tvNishob.setText("Nishob Zakat"+ " "+getIntent().getStringExtra("tvNishob")+ " Ekor");
        }else if (tvjenisZakat.getText().toString().equals("Emas") || tvjenisZakat.getText().toString().equals("Perak")){
            tvtotalzakat.setText("Zakat yang dikeluarkan"+ " "+getIntent().getStringExtra("tvtlz")+ " gram");
            tvNishob.setText("Nishob Zakat"+ " "+getIntent().getStringExtra("tvNishob")+ " gram");
        }else {
            tvtotalzakat.setText("Zakat yang dikeluarkan"+ " "+getIntent().getStringExtra("tvtlz")+ " KG");
            tvNishob.setText("Nishob Zakat"+ " "+getIntent().getStringExtra("tvNishob")+ " KG");
        }
        tvnamamuzakki.setText(getIntent().getStringExtra("tvnmmuzakki"));
        tvnomormuzakki.setText(getIntent().getStringExtra("tvnomormuzakki"));
        tvalamat.setText(getIntent().getStringExtra("tvalamat"));
        tvstatus.setText(getIntent().getStringExtra("tvstatus"));
        tvidamil.setText(getIntent().getStringExtra("tvidAmil"));
        if (tvstatus.getText().toString().equals("Berlangsung")){
            btnKonfirmasi.setVisibility(View.GONE);
            tvotp.setText(getIntent().getStringExtra("otp"));
            getdataamil();
//            cekforupdate();
        }else if(tvstatus.getText().toString().equals("Pending")){
            btnKonfirmasi.setVisibility(View.GONE);
            tvkode.setVisibility(View.GONE);
            v_kode.setVisibility(View.GONE);
            tvotp.setVisibility(View.GONE);
            tvnamaamil.setText(null);
        }else {
            tvkode.setVisibility(View.GONE);
            v_kode.setVisibility(View.GONE);
            tvotp.setVisibility(View.GONE);
            getdataamil();
            btnKonfirmasi.setText("Beri Nilai");
            penilaian();
        }

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnKonfirmasi.getText().toString().equals("Beri Nilai")){
                    Intent i = new Intent(getApplicationContext(), PenilaianActivity.class);
                    i.putExtra("id", getIntent().getStringExtra("tvid") );
                    startActivity(i);
                }
//                else if (btnKonfirmasi.getText().toString().equals("Konfirmasi Pengambilan")){
//                    getforupdate();
//                    konfirmasi();
//                    btnKonfirmasi.setText("Beri Nilai");
//                }
                else {
                    getforupdate();
                    konfirmasi();
                    btnKonfirmasi.setVisibility(View.GONE);
                }
            }
        });
    }
    private void getdataamil(){
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> dataAmil = ardData.ardgetDataAmil(tvidamil.getText().toString());
        dataAmil.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatalogin() != null) {
                    getuser = response.body().getDatalogin();
                    tvnamaamil.setText(getuser.get(0).getNama());
                } else {
                    String pesan = response.body().getPesan();
                    tvnamaamil.setText(null);
//                    Toast.makeText(InfoZakatActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    btnKonfirmasi.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoZakatActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void cekforupdate(){
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> cekData = ardData.cektersedia(tvjenisZakat.getText().toString());
        cekData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatarequest() != null) {
                    listzakat = response.body().getDatarequest();
                    tersedia = Integer.parseInt(listzakat.get(0).getJmlZakat());
                    req = Integer.parseInt(getIntent().getStringExtra("tvtlz"));
                    hasil = req + tersedia;
                }else {
                    String pesan = response.body().getPesan();
                    Toast.makeText(InfoZakatActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoZakatActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getforupdate(){
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> cekData = ardData.updatezakat(tvjenisZakat.getText().toString(), String.valueOf(hasil));
        cekData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
//                    Toast.makeText(InfoZakatActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(InfoZakatActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoZakatActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void penilaian(){
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> cekData = ardData.getnilai(tvidRequest.getText().toString());
        cekData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    btnKonfirmasi.setVisibility(View.GONE);
                }else {
                    Toast.makeText(InfoZakatActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoZakatActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        if (tvstatus.getText().toString().equals("Berlangsung")){
            btnKonfirmasi.setVisibility(View.VISIBLE);
            getdataamil();
            cekforupdate();
        }else if(tvstatus.getText().toString().equals("Pending")){
            btnKonfirmasi.setVisibility(View.GONE);
            tvnamaamil.setText(null);
        }else {
            getdataamil();
            btnKonfirmasi.setText("Beri Nilai");
            penilaian();
        }
        super.onRestart();
    }

    private void konfirmasi() {
        // Data Now
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String sDate = simpleDateFormat.format(date);
        String statusUpdate = "Selesai";
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> UpdateData = ardData.UpdateBerlangsungMuzzaki(tvidRequest.getText().toString(), sDate, statusUpdate);
        UpdateData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                tvstatus.setText("Selesai");
                Toast.makeText(InfoZakatActivity.this, pesan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoZakatActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

            }
        });
    }
}