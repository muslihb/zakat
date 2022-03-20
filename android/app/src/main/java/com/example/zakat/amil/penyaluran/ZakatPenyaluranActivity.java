package com.example.zakat.amil.penyaluran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.admin.dataakun.InsertAkunAdmin;
import com.example.zakat.admin.dataakun.ListAkun;
import com.example.zakat.admin.dataakun.ListAkunAdminActivity;
import com.example.zakat.model.MasterMutashikResponse;
import com.example.zakat.model.MutashikResponse;
import com.example.zakat.model.ResponseData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZakatPenyaluranActivity extends AppCompatActivity {
    Toolbar toolbar;
    AutoCompleteTextView autoCompleteTextView;
    FloatingActionButton fab;
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;
    private List<MasterMutashikResponse> listmutashik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat_penyaluran);
        toolbar = findViewById(R.id.toolbar);
        autoCompleteTextView = findViewById(R.id.a1);
        fab = findViewById(R.id.fab);
        srlData = findViewById(R.id.srlData);
        pbData = findViewById(R.id.pbData);
        rvData = findViewById(R.id.rvdata);
        //Toolbar
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
                autoCompleteTextView.setText("Semua");
                String[] sstatus = getResources().getStringArray(R.array.mutashik);
                ArrayAdapter astatus = new ArrayAdapter(ZakatPenyaluranActivity.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
                autoCompleteTextView.setAdapter(astatus);
                autoCompleteTextView.dismissDropDown();
                retrieveDataPenyaluran();
                srlData.setRefreshing(false);
            }
        });
        //floatActionBar
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertPenyaluran insertPenyaluran = new InsertPenyaluran();
                insertPenyaluran.show(getSupportFragmentManager(), "form");
            }
        });
        //Status
        String[] sstatus = getResources().getStringArray(R.array.mutashik);
        ArrayAdapter astatus = new ArrayAdapter(ZakatPenyaluranActivity.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
        autoCompleteTextView.setAdapter(astatus);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    retrieveDataPenyaluran();
                    rvData.setVisibility(View.VISIBLE);
                }else {
                    APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
                    Call<ResponseData> tampilData = ardData.getretrievepenyaluranzakat(autoCompleteTextView.getText().toString());
                    tampilData.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if (response.body().getDatamamut() != null) {
                                int kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                                listmutashik = response.body().getDatamamut();
                                adData = new ListPenyaluran(listmutashik, ZakatPenyaluranActivity.this);
                                rvData.setAdapter(adData);
                                rvData.setVisibility(View.VISIBLE);
                                adData.notifyDataSetChanged();
                                pbData.setVisibility(View.INVISIBLE);
                            } else {
                                rvData.setVisibility(View.GONE);
                                Toast.makeText(ZakatPenyaluranActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            Toast.makeText(ZakatPenyaluranActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        retrieveDataPenyaluran();
    }

    public void retrieveDataPenyaluran() {
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> tampilData = ardData.retrievepenyaluranzakat();
        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatamamut() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    listmutashik = response.body().getDatamamut();
                    adData = new ListPenyaluran(listmutashik, ZakatPenyaluranActivity.this);
                    rvData.setAdapter(adData);
                    rvData.setVisibility(View.VISIBLE);
                    adData.notifyDataSetChanged();
                    pbData.setVisibility(View.INVISIBLE);
                } else {
                    rvData.setVisibility(View.GONE);
                    Toast.makeText(ZakatPenyaluranActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(ZakatPenyaluranActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}