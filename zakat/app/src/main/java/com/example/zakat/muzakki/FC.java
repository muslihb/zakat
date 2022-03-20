package com.example.zakat.muzakki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.LoginActivity;
import com.example.zakat.R;
import com.example.zakat.model.KonklusiResponse;
import com.example.zakat.model.LoginResponse;
import com.example.zakat.model.PremisResponse;
import com.example.zakat.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FC extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvquest, tvkode, tvkodeyes, tvkodeno;
    Button btnyes, btnno;
    private List<PremisResponse> listpremisdata;
    private List<KonklusiResponse> getkonklusi;
    String fst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fc);
        toolbar = findViewById(R.id.toolbar);
        tvquest = findViewById(R.id.tvquest);
        tvkode = findViewById(R.id.tvkode);
        tvkodeyes =findViewById(R.id.tv1);
        tvkodeno = findViewById(R.id.tv2);
        btnyes = findViewById(R.id.btnyes);
        btnno = findViewById(R.id.btnno);
        fst = getIntent().getExtras().getString("fst");
        //Set Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fwch();
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ya();
            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tidak();
            }
        });

    }
    private void fwch() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> listpremis = apiAdapter.ardgetfc(fst);
        listpremis.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    listpremisdata = response.body().getDatapremisfc();
                    tvquest.setText(listpremisdata.get(0).getQuest());
                    tvkodeyes.setText(listpremisdata.get(0).getIfyes());
                    tvkodeno.setText(listpremisdata.get(0).getIfno());
//                    Toast.makeText(FC.this, pesan, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(FC.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(FC.this, "Gagal Menghubungkan Server",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void tidak() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> listpremis = apiAdapter.ardgetfc(tvkodeno.getText().toString());
        listpremis.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    listpremisdata = response.body().getDatapremisfc();
                    tvkode.setText(tvkodeno.getText().toString());
                    tvquest.setText(listpremisdata.get(0).getQuest());
                    tvkodeyes.setText(listpremisdata.get(0).getIfyes());
                    tvkodeno.setText(listpremisdata.get(0).getIfno());
//                    Toast.makeText(FC.this, pesan, Toast.LENGTH_SHORT).show();
                }else {
                    konklusino();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(FC.this, "Gagal Menghubungkan Server",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ya() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> listpremis = apiAdapter.ardgetfc(tvkodeyes.getText().toString());
        listpremis.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    listpremisdata = response.body().getDatapremisfc();
                    tvkode.setText(tvkodeyes.getText().toString());
                    tvquest.setText(listpremisdata.get(0).getQuest());
                    tvkodeyes.setText(listpremisdata.get(0).getIfyes());
                    tvkodeno.setText(listpremisdata.get(0).getIfno());
//                    Toast.makeText(FC.this, pesan, Toast.LENGTH_SHORT).show();
                }else {
                    konklusifcyes();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(FC.this, "Gagal Menghubungkan Server",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }private void konklusifcyes() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> listpremis = apiAdapter.getkonklusizakat(tvkodeyes.getText().toString());
        listpremis.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    getkonklusi = response.body().getDatakonklusifc();
                    String nm = getkonklusi.get(0).getNama();
                    Intent i = new Intent(getApplicationContext(), zakat.class);
                    i.putExtra("z", nm);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(FC.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(FC.this, "Gagal Menghubungkan Server",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }private void konklusino() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> listpremis = apiAdapter.getkonklusizakat(tvkodeno.getText().toString());
        listpremis.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    getkonklusi = response.body().getDatakonklusifc();
                    String nm = getkonklusi.get(0).getNama();
                    finish();
                    Toast.makeText(FC.this, nm, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(FC.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(FC.this, "Gagal Menghubungkan Server",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}