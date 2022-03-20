package com.example.zakat.muzakki;

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
import com.example.zakat.SessionManager;
import com.example.zakat.model.ResponseData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {
    SessionManager sessionManager;
    TextView tvnama, tvnomor, tvalamat, tvjenis, tvtotal;
    TextInputLayout tilnama, tilnomor, tilalamat;
    TextInputEditText etnama, etnomor, etalamat;
    Button btnsu, btnrequest;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        //initialize
        toolbar = findViewById(R.id.toolbar);
        tvnama = findViewById(R.id.tvnama);
        tvnomor = findViewById(R.id.tvnomor);
        tvalamat = findViewById(R.id.tvalamat);
        tvjenis = findViewById(R.id.tvjeniszakat);
        tvtotal = findViewById(R.id.tvtotalzakat);
        tilnama = findViewById(R.id.tilnama);
        tilnomor = findViewById(R.id.tiltelepon);
        tilalamat = findViewById(R.id.tilalamat);
        etnama = findViewById(R.id.etnamalengkap);
        etnomor = findViewById(R.id.ettelepon);
        etalamat = findViewById(R.id.etalamat);
        btnsu = findViewById(R.id.btnsu);
        btnrequest = findViewById(R.id.btnrequest);
        //set Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set sesi
        sessionManager = new SessionManager(getApplicationContext());
        //user details
        //User Details
        HashMap<String, String> user = sessionManager.getDataUser();
        String sFullname = user.get(sessionManager.FN);
        String sTelepon = user.get(sessionManager.TELEPON);
        String sAlamat = user.get(sessionManager.ADDRESS);
        tvnama.setText(sFullname);
        tvnomor.setText(sTelepon);
        tvalamat.setText(sAlamat);
        //zakat detail
        HashMap<String, String> zakat = sessionManager.getZakat();
        String sJZ = zakat.get(sessionManager.JZ);
        String sTLZ = zakat.get(sessionManager.TLZ);
        tvjenis.setText(sJZ);
        if (sJZ.equals("Sapi") || sJZ.equals("Kambing")){
            tvtotal.setText(sTLZ + " Ekor");
        }else if (sJZ.equals("Emas") || sJZ.equals("Perak")){
            tvtotal.setText(sTLZ + " gram");
        }else {
            tvtotal.setText(sTLZ + " KG");
        }
        //btnrequest
        btnsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnsu.getText().toString().equals("EDIT")) {
                    tvnama.setVisibility(View.GONE);
                    tvnomor.setVisibility(View.GONE);
                    tvalamat.setVisibility(View.GONE);
                    tilnama.setVisibility(View.VISIBLE);
                    tilnomor.setVisibility(View.VISIBLE);
                    tilalamat.setVisibility(View.VISIBLE);
                    etnama.setText(tvnama.getText().toString());
                    etnomor.setText(tvnomor.getText().toString());
                    etalamat.setText(tvalamat.getText().toString());
                    btnsu.setText("SIMPAN");
                } else {
                    tilnama.setVisibility(View.GONE);
                    tilnomor.setVisibility(View.GONE);
                    tilalamat.setVisibility(View.GONE);
                    tvnama.setText(etnama.getText().toString());
                    tvnomor.setText(etnomor.getText().toString());
                    tvalamat.setText(etalamat.getText().toString());
                    tvnama.setVisibility(View.VISIBLE);
                    tvnomor.setVisibility(View.VISIBLE);
                    tvalamat.setVisibility(View.VISIBLE);
                    btnsu.setText("EDIT");
                }
            }
        });
        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvnama.getText().toString().equals("")
                        || tvnomor.getText().toString().equals("")
                        ||tvalamat.getText().toString().equals("")){
                    Toast.makeText(RequestActivity.this,"Data Muzakki Belum Lengkap", Toast.LENGTH_SHORT).show();
                }else {
                    requestzakat();
                }

            }
        });
    }
    private void requestzakat(){
        sessionManager = new SessionManager(getApplicationContext());
        //id user
        HashMap<String, String> user = sessionManager.getDataUser();
        String sIdUser = user.get(sessionManager.ID);
        //zakat
        //zakat detail
        HashMap<String, String> zakat = sessionManager.getZakat();
        String sTZ = zakat.get(sessionManager.TZ);
        String sJZ = zakat.get(sessionManager.JZ);
        String sNishob = zakat.get(sessionManager.NS);
        String sTLZ = zakat.get(sessionManager.TLZ);
        // Data Now
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String sDate = simpleDateFormat.format(date);
        // status Pengambilan
        String sPengambilan = "Pending";
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> registerData = apiAdapter.insertrequest(sIdUser,
                tvnama.getText().toString(), tvnomor.getText().toString(),
                tvalamat.getText().toString(), sTZ, sJZ, sNishob, sTLZ, sDate, sPengambilan);
        registerData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();

                if (!kode.equals("0")){
                    Toast.makeText(RequestActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(RequestActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(RequestActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}