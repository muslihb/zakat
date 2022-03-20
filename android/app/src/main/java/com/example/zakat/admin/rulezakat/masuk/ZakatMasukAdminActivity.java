package com.example.zakat.admin.rulezakat.masuk;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.amil.penyaluran.ZakatPenyaluranActivity;
import com.example.zakat.amil.terkumpul.ListTerkumpul;
import com.example.zakat.amil.terkumpul.ZakatTerkumpulAmilActivity;
import com.example.zakat.model.RequestResponse;
import com.example.zakat.model.ResponseData;
import com.example.zakat.muzakki.History.ListZakat;
import com.example.zakat.muzakki.HistoryMuzakkiActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZakatMasukAdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    AutoCompleteTextView autoCompleteTextView;
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;
    private List<RequestResponse> listrequestzakat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat_masuk_admin);
        //initialize
        toolbar = findViewById(R.id.toolbar);
        srlData = findViewById(R.id.srlData);
        pbData = findViewById(R.id.pbData);
        rvData = findViewById(R.id.rvdata);
        autoCompleteTextView = findViewById(R.id.a1);
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
                autoCompleteTextView.setText("Semua");
                String[] sstatus = getResources().getStringArray(R.array.statusmuzakki);
                ArrayAdapter astatus = new ArrayAdapter(ZakatMasukAdminActivity.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
                autoCompleteTextView.setAdapter(astatus);
                autoCompleteTextView.dismissDropDown();
                retrieveData();
                srlData.setRefreshing(false);
            }
        });
        //AutoCompleteText
        String[] sstatus = getResources().getStringArray(R.array.statusmuzakki);
        ArrayAdapter astatus = new ArrayAdapter(ZakatMasukAdminActivity.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
        autoCompleteTextView.setAdapter(astatus);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    retrieveData();
                    rvData.setVisibility(View.VISIBLE);
                }else {
                    APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
                    Call<ResponseData> tampilData = ardData.ardgetRequestAdmintatus(autoCompleteTextView.getText().toString());
                    tampilData.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if (response.body().getDatarequest() != null) {
                                int kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                                listrequestzakat = response.body().getDatarequest();
                                adData = new ListZakatMasuk(listrequestzakat, ZakatMasukAdminActivity.this);
                                rvData.setAdapter(adData);
                                rvData.setVisibility(View.VISIBLE);
                                adData.notifyDataSetChanged();
                                pbData.setVisibility(View.INVISIBLE);
                            } else {
                                rvData.setVisibility(View.GONE);
                                Toast.makeText(ZakatMasukAdminActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            Toast.makeText(ZakatMasukAdminActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData(){
        pbData.setVisibility(View.VISIBLE);
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> tampilData = ardData.retrieveZakatall();

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatarequest() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    listrequestzakat = response.body().getDatarequest();

                    adData = new ListZakatMasuk(listrequestzakat, ZakatMasukAdminActivity.this);
                    rvData.setAdapter(adData);
                    rvData.setVisibility(View.VISIBLE);
                    adData.notifyDataSetChanged();
                    pbData.setVisibility(View.INVISIBLE);
                }else {
                    rvData.setVisibility(View.GONE);
                    Toast.makeText(ZakatMasukAdminActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(ZakatMasukAdminActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }
}