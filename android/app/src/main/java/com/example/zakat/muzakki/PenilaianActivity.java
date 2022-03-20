package com.example.zakat.muzakki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.model.EvidenceResponse;
import com.example.zakat.model.HipotesaResponse;
import com.example.zakat.model.ResponseData;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenilaianActivity extends AppCompatActivity {
    TextView tvselesai, nilai;
    ListView lvperilaku;
    Toolbar toolbar;
    ScrollView sv1;
    Button btnsimpan;
    String idrequest, sifat1, sifat2, sifat3, sifat4, nilaicf;
    int itemselected1 = 1;
    int itemselected2 = 2;
    int itemselected3 = 4;
    int itemselected4 = 3;
    int hasil;
    double cf_gabungan1, cf_gabungan2, cf_gabungan3, cf_gabungan4;
    double cf1, cf2 , cf3, cf4;
    private List<EvidenceResponse> listevidence;
    private List<HipotesaResponse> listhipotesa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penilaian);
        toolbar = findViewById(R.id.toolbar);
        lvperilaku = findViewById(R.id.list_evidence);
        nilai = findViewById(R.id.nilaicf);
        sv1 = findViewById(R.id.sv);
        btnsimpan = findViewById(R.id.btn_hasil);
        tvselesai = findViewById(R.id.tvselesai);
        idrequest = getIntent().getStringExtra("id");
        retrievekonklusiData();
        ev();
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ev2();
               sv1.setVisibility(View.GONE);
               nilai.setVisibility(View.VISIBLE);
               tvselesai.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        inputnilai();
                    }
                },500);
            }
        });
    }
    private void ev(){
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> tampilData = ardData.retrievecfevidencepenilaian();

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDataevidencecf() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    listevidence = response.body().getDataevidencecf();
                    List<String> listdata = new ArrayList<String>();
                    for (int i = 0; i < listevidence.size(); i++){
                        listdata.add(listevidence.get(i).getNama());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_multiple_choice, listdata);
                    lvperilaku.setAdapter(adapter);
                }else {
                    Toast.makeText(getApplicationContext(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ev2(){
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> tampilData = ardData.retrievecfevidencepenilaian();

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDataevidencecf() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    listevidence = response.body().getDataevidencecf();

                    for (int i = 0; i < lvperilaku.getCount(); i++) {
                        if (lvperilaku.isItemChecked(i)) {
                            if (lvperilaku.getCheckedItemCount() > 1 &&
                                    listevidence.get(i).getKodehipotesa().equals("H1")) {
                                cf1 = Double.parseDouble(listevidence.get(i).getMb()) * Double.parseDouble(listevidence.get(i).getMd());
                                cf_gabungan1 = cf1 + (cf_gabungan1*(1-cf1));
                                itemselected1 = (int) Math.round(cf_gabungan1 * 100);
//                                itemselected1 = hasil + " %\n";
                            }else if (listevidence.get(i).getKodehipotesa().equals("H1")
                                    && lvperilaku.getCheckedItemCount() > 0){
                                cf1 = Double.parseDouble(listevidence.get(i).getMb()) * Double.parseDouble(listevidence.get(i).getMd());
                                itemselected1 = (int) Math.round(cf1 * 100);
//                                itemselected1 = hasil + " %\n";
                            }
                            if (listevidence.get(i).getKodehipotesa().equals("H2")
                                    && lvperilaku.getCheckedItemCount() > 1) {
                                cf2 = Double.parseDouble(listevidence.get(i).getMb()) * Double.parseDouble(listevidence.get(i).getMd());
                                cf_gabungan2 = cf2 + (cf_gabungan2*(1-cf2));
                                itemselected2 = (int) Math.round(cf_gabungan2 * 100);
//                                itemselected2 = hasil + " %\n";
                            }else if (listevidence.get(i).getKodehipotesa().equals("H2")
                                    && lvperilaku.getCheckedItemCount() > 0){
                                cf2 = Double.parseDouble(listevidence.get(i).getMb()) * Double.parseDouble(listevidence.get(i).getMd());
                                itemselected2 = (int) Math.round(cf2 * 100);
//                                itemselected2 = hasil + " %\n";
                            }
                            if (listevidence.get(i).getKodehipotesa().equals("H3")
                                    && lvperilaku.getCheckedItemCount() > 1) {
                                cf3 = Double.parseDouble(listevidence.get(i).getMb()) * Double.parseDouble(listevidence.get(i).getMd());
                                cf_gabungan3 = cf3 + (cf_gabungan3*(1-cf3));
                                itemselected3 = (int) Math.round(cf_gabungan3 * 100);
//                                itemselected3 = hasil + " %\n";
                            }else if (listevidence.get(i).getKodehipotesa().equals("H3")
                                    && lvperilaku.getCheckedItemCount() > 0){
                                cf3 = Double.parseDouble(listevidence.get(i).getMb()) * Double.parseDouble(listevidence.get(i).getMd());
                                itemselected3 = (int) Math.round(cf3 * 100);
//                                itemselected3 = hasil + " %\n";
                            }
                            if (listevidence.get(i).getKodehipotesa().equals("H4")
                                    && lvperilaku.getCheckedItemCount() > 1) {
                                cf4 = Double.parseDouble(listevidence.get(i).getMb()) * Double.parseDouble(listevidence.get(i).getMd());
                                cf_gabungan4 = cf4 + (cf_gabungan4*(1-cf4));
                                itemselected4 = (int) Math.round(cf_gabungan4 * 100);
//                                itemselected4 = hasil + " %\n";
                            }else if (listevidence.get(i).getKodehipotesa().equals("H4")
                                    && lvperilaku.getCheckedItemCount()> 0){
                                cf4 = Double.parseDouble(listevidence.get(i).getMb()) * Double.parseDouble(listevidence.get(i).getMd());
                                itemselected4 = (int) Math.round(cf4 * 100);
//                                itemselected4 = hasil + " % \n";
                            }
                            int data[] = {itemselected1, itemselected2, itemselected3, itemselected4};
                            int ib = data[0];
                            for (int j = 0; j < data.length; j++) {
                                if (ib < data[j]) {
                                    ib = data[j];
                                }
                            }
                            if (ib == data[0]) {
                                nilaicf = sifat1 + " " + itemselected1 + " %";
                            } else if (ib == data[1]) {
                                nilaicf = sifat2 + " " + itemselected2 + " %";
                            } else if (ib == data[2]) {
                                nilaicf = sifat3 + " " + itemselected3 + " %";
                            } else if (ib == data[3]) {
                                nilaicf = sifat4 + " " + itemselected4 + " %";
                            }
                            nilai.setText(nilaicf);

                        }
                    }
                    if (lvperilaku.getCheckedItemCount() <=0) {
                        Toast.makeText(getApplicationContext(), "Tolong Pilih salah Satu", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void retrievekonklusiData() {
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> tampilData = ardData.retrievecfhipotesapenilaian();

        tampilData.enqueue(new Callback<ResponseData>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatahipotesacf() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    //Toast.makeText(ListAdminActivity.this, "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    listhipotesa = response.body().getDatahipotesacf();
                    sifat1 = listhipotesa.get(0).getNama();
                    sifat2 = listhipotesa.get(1).getNama();
                    sifat3 = listhipotesa.get(2).getNama();
                    sifat4 = listhipotesa.get(3).getNama();

//                    nilaicf = sifat1+" "+itemselected1+"\n"+
//                              sifat2 +" "+itemselected2+"\n"+
//                              sifat3 +" "+itemselected3+"\n"+
//                              sifat4 +" "+itemselected4;
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void inputnilai(){
            APIAdapter ardData2 = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> insertNilai = ardData2.insertnilai(idrequest,nilai.getText().toString());

            insertNilai.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if (response.body().getDatapenilaian() != null) {
                        int kode = response.body().getKode();
                        String pesan = response.body().getPesan();
                        Toast.makeText(PenilaianActivity.this, pesan, Toast.LENGTH_SHORT).show();

                    }else {
                        String pesan = response.body().getPesan();
                        Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

                }
            });
    }
}