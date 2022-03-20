package com.example.zakat.admin.dataakun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.model.LoginResponse;
import com.example.zakat.model.ResponseData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAkunAdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    AutoCompleteTextView autoCompleteTextView;
    FloatingActionButton fab;
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;
    private List<LoginResponse> listDataAkun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_akun);
        toolbar = findViewById(R.id.toolbar);
        autoCompleteTextView = findViewById(R.id.a1);
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
                autoCompleteTextView.setText("Semua");
                String[] sstatus = getResources().getStringArray(R.array.statusAkun);
                ArrayAdapter astatus = new ArrayAdapter(ListAkunAdminActivity.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
                autoCompleteTextView.setAdapter(astatus);
                autoCompleteTextView.dismissDropDown();
                retrieveData();
                srlData.setRefreshing(false);
            }
        });
        //floatActionBar
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertAkunAdmin insertAdmin = new InsertAkunAdmin();
                insertAdmin.show(getSupportFragmentManager(), "form");
            }
        });
        //Status
        String[] sstatus = getResources().getStringArray(R.array.statusAkun);
        ArrayAdapter astatus = new ArrayAdapter(ListAkunAdminActivity.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
        autoCompleteTextView.setAdapter(astatus);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    retrieveData();
                    rvData.setVisibility(View.VISIBLE);
                }else {
                    APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
                    Call<ResponseData> tampilData = ardData.retrievestatusakun(autoCompleteTextView.getText().toString());
                    tampilData.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if (response.body().getDatalogin() != null) {
                                int kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                                listDataAkun = response.body().getDatalogin();
                                adData = new ListAkun(listDataAkun, ListAkunAdminActivity.this);
                                rvData.setAdapter(adData);
                                rvData.setVisibility(View.VISIBLE);
                                adData.notifyDataSetChanged();
                                pbData.setVisibility(View.INVISIBLE);
                            } else {
                                rvData.setVisibility(View.GONE);
                                Toast.makeText(ListAkunAdminActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            Toast.makeText(ListAkunAdminActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
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
        Call<ResponseData> tampilData = ardData.ardRetrieveakun();

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatalogin() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    listDataAkun = response.body().getDatalogin();

                    adData = new ListAkun(listDataAkun, ListAkunAdminActivity.this);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    pbData.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(ListAkunAdminActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(ListAkunAdminActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
                Call<ResponseData> searchData = ardData.search(s);

                searchData.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (response.body().getDatalogin() != null) {
                            rvData.setVisibility(View.VISIBLE);
                            int kode = response.body().getKode();
                            String pesan = response.body().getPesan();
                            //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                            listDataAkun = response.body().getDatalogin();

                            adData = new ListAkun(listDataAkun, ListAkunAdminActivity.this);
                            rvData.setAdapter(adData);
                            adData.notifyDataSetChanged();
                            pbData.setVisibility(View.INVISIBLE);
                        }else {
                            rvData.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        Toast.makeText(ListAkunAdminActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

                        pbData.setVisibility(View.INVISIBLE);
                    }
                });
                return false;
            }
        });
        return true;
    }
}