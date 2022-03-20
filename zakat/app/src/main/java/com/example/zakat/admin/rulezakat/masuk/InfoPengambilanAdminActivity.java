package com.example.zakat.admin.rulezakat.masuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.admin.dataakun.ListAkun;
import com.example.zakat.admin.dataakun.ListAkunAdminActivity;
import com.example.zakat.amil.request.InfoPengambilanActivity;
import com.example.zakat.amil.request.PendingamilFragment;
import com.example.zakat.model.LoginResponse;
import com.example.zakat.model.RequestResponse;
import com.example.zakat.model.ResponseData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPengambilanAdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvidRequest, tvjenisZakat, tvNishob, tvtotalzakat, tvnamamuzakki,
            tvnomormuzakki, tvalamat, tvstatus, tvidamil;
    Button btnKonfirmasi;
    TextInputLayout t1;
    AutoCompleteTextView a1;
    private List<LoginResponse> getuser;
    private List<RequestResponse> listzakat;
    int tersedia, req, hasil;
    String sIDAmil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pengambilan2);
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
        tvidamil = findViewById(R.id.tvidamil);
        btnKonfirmasi = findViewById(R.id.btnkonfirmasi);
        t1 = findViewById(R.id.t1);
        a1 = findViewById(R.id.a1);
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Get Intent
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
        tvidamil.setText(getIntent().getStringExtra("tvidAmil"));
        if (tvstatus.getText().toString().equals("Berlangsung")){
            t1.setEnabled(false);
            btnKonfirmasi.setVisibility(View.GONE);
            getdataamil();
        }else if(tvstatus.getText().toString().equals("Pending")){
            t1.setVisibility(View.VISIBLE);
            retrieveData();
            btnKonfirmasi.setVisibility(View.VISIBLE);
            a1.setText(null);
            a1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String str = a1.getText().toString();
                    str = str.replaceAll("\\D+","");
                    int number = Integer.parseInt(str);
                    sIDAmil = String.valueOf(number);
                }
            });
            btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    konfirmasi();
                    btnKonfirmasi.setVisibility(View.GONE);
                }
            });
        }else {
            t1.setEnabled(false);
            btnKonfirmasi.setVisibility(View.GONE);
            getdataamil();
        }
    }
    public void retrieveData(){
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> tampilData = ardData.ardRetrieveakun();

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatalogin() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    getuser = response.body().getDatalogin();
                    List<String> listamil = new ArrayList<String>();
                    for (int i = 0; i < getuser.size(); i++){
                            if (getuser.get(i).getStatus().equals("Amil")){
                                listamil.add(getuser.get(i).getIduser() + " - " + getuser.get(i).getNama());
                                String sIDAmil = getuser.get(i).getIduser();
                            }
                    }
                    ArrayAdapter astatus = new ArrayAdapter(InfoPengambilanAdminActivity.this, R.layout.support_simple_spinner_dropdown_item, listamil);
                    a1.setAdapter(astatus);
                }else {
                    Toast.makeText(InfoPengambilanAdminActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoPengambilanAdminActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
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
                    a1.setText(tvidamil.getText().toString() +" - "+ getuser.get(0).getNama());
                } else {
                    String pesan = response.body().getPesan();
                    Toast.makeText(InfoPengambilanAdminActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoPengambilanAdminActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(InfoPengambilanAdminActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoPengambilanAdminActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(InfoPengambilanAdminActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(InfoPengambilanAdminActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(InfoPengambilanAdminActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void konfirmasi() {
        String statusUpdate = "Berlangsung";
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> updateData = ardData.UpdatePendingAmil(tvidRequest.getText().toString(), sIDAmil, statusUpdate);
        updateData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                tvstatus.setText(statusUpdate);
                Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

            }
        });
    }
}