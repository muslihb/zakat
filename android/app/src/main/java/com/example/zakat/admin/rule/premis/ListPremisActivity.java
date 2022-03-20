package com.example.zakat.admin.rule.premis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.admin.dataakun.ListAkun;
import com.example.zakat.admin.dataakun.ListAkunAdminActivity;
import com.example.zakat.model.LoginResponse;
import com.example.zakat.model.PremisResponse;
import com.example.zakat.model.ResponseData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPremisActivity extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton fab;
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;
    private List<PremisResponse> listpremisData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_premis);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        srlData = findViewById(R.id.srlData);
        pbData = findViewById(R.id.pbData);
        rvData = findViewById(R.id.rvdata);
        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // List Data
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        //Refresh Data
        srlData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlData.setRefreshing(true);
                retrievePremisData();
                srlData.setRefreshing(false);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertPremis insertPremis = new InsertPremis();
                insertPremis.show(getSupportFragmentManager(), "form");
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        retrievePremisData();
    }
    public void retrievePremisData() {
        pbData.setVisibility(View.VISIBLE);
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> tampilData = ardData.retrievefcpremiszakat();

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatapremisfc() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    listpremisData = response.body().getDatapremisfc();

                    adData = new ListPremis(listpremisData, ListPremisActivity.this);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    pbData.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(ListPremisActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(ListPremisActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }
}