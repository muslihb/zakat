package com.example.zakat.admin.reviewamil;

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
import com.example.zakat.admin.cfreview.evidence.EvidenceActivity;
import com.example.zakat.admin.cfreview.evidence.InsertEvidence;
import com.example.zakat.admin.cfreview.evidence.ListEvidence;
import com.example.zakat.model.EvidenceResponse;
import com.example.zakat.model.PenilaianResponse;
import com.example.zakat.model.ResponseData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cfAmilActivity extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;
    private List<PenilaianResponse> listnilai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cf_amil);
        toolbar = findViewById(R.id.toolbar);
        srlData = findViewById(R.id.srlData);
        pbData = findViewById(R.id.pbData);
        rvData = findViewById(R.id.rvdata);
        //      Toolbar
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
//                retrievePremisData();
                srlData.setRefreshing(false);
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
        Call<ResponseData> tampilData = ardData.retrievenilai();

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatapenilaian() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    listnilai = response.body().getDatapenilaian();

                    adData = new Listreview(listnilai, cfAmilActivity.this);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    pbData.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }
}