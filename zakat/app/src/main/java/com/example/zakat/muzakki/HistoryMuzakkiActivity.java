package com.example.zakat.muzakki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.admin.dataakun.ListAkun;
import com.example.zakat.admin.dataakun.ListAkunAdminActivity;
import com.example.zakat.admin.rule.konklusi.ListKonklusi;
import com.example.zakat.admin.rule.konklusi.ListKonklusiActivity;
import com.example.zakat.model.RequestResponse;
import com.example.zakat.model.RequestTersediaResponse;
import com.example.zakat.model.ResponseData;
import com.example.zakat.muzakki.History.ListZakat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryMuzakkiActivity extends AppCompatActivity {
    private TextView tvid;
    private AutoCompleteTextView autoCompleteTextView;
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;
    private List<RequestTersediaResponse> listrequestzakat;
    Toolbar toolbar;
    String sIdUser;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_muzakki);
        //initialize
        tvid = findViewById(R.id.tvid);
        srlData = findViewById(R.id.srlData);
        pbData = findViewById(R.id.pbData);
        rvData = findViewById(R.id.rvdata);
        autoCompleteTextView = findViewById(R.id.a1);
        toolbar = findViewById(R.id.toolbar);
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Create Sesi
        sessionManager = new SessionManager(getApplicationContext());
        //User Details
        HashMap<String, String> user = sessionManager.getDataUser();
        sIdUser = user.get(sessionManager.ID);
        // List Data
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        //Refresh Data
        srlData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlData.setRefreshing(true);
                autoCompleteTextView.setText("Semua");
                String[] sstatus = getResources().getStringArray(R.array.statusAkun);
                ArrayAdapter astatus = new ArrayAdapter(HistoryMuzakkiActivity.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
                autoCompleteTextView.setAdapter(astatus);
                autoCompleteTextView.dismissDropDown();
                retrieverequestzakat();
                srlData.setRefreshing(false);
            }
        });
        //Status
        String[] sstatus = getResources().getStringArray(R.array.statusmuzakki);
        ArrayAdapter astatus = new ArrayAdapter(HistoryMuzakkiActivity.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
        autoCompleteTextView.setAdapter(astatus);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    retrieverequestzakat();
                    rvData.setVisibility(View.VISIBLE);
                }else {
                    APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
                    Call<ResponseData> tampilData = ardData.ardgetRequestMuzakkistatus(sIdUser
                    , autoCompleteTextView.getText().toString());
                    tampilData.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if (response.body().getDatareq() != null) {
                                int kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                                listrequestzakat = response.body().getDatareq();
                                adData = new ListZakat(listrequestzakat, HistoryMuzakkiActivity.this);
                                rvData.setAdapter(adData);
                                rvData.setVisibility(View.VISIBLE);
                                adData.notifyDataSetChanged();
                                pbData.setVisibility(View.INVISIBLE);
                            } else {
                                rvData.setVisibility(View.GONE);
                                Toast.makeText(HistoryMuzakkiActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            Toast.makeText(HistoryMuzakkiActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        retrieverequestzakat();
    }

    private void retrieverequestzakat() {
        pbData.setVisibility(View.VISIBLE);
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> tampilData = ardData.ardgetRequestMuzakki(sIdUser);

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatareq() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    listrequestzakat = response.body().getDatareq();

                    adData = new ListZakat(listrequestzakat, HistoryMuzakkiActivity.this);
                    rvData.setAdapter(adData);
                    rvData.setVisibility(View.VISIBLE);
                    adData.notifyDataSetChanged();
                    pbData.setVisibility(View.INVISIBLE);
                }else {
                    rvData.setVisibility(View.GONE);
                    Toast.makeText(HistoryMuzakkiActivity.this, "Belum Melakukan Pembayaran Zakat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(HistoryMuzakkiActivity.this, "Gagal Menghubungkan ke Server  : " +t.getMessage(), Toast.LENGTH_SHORT).show();
                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        overridePendingTransition(0,0);
        super.onBackPressed();
    }
}