package com.example.zakat.muzakki.History;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.admin.rule.konklusi.ListKonklusiActivity;
import com.example.zakat.admin.rule.konklusi.UpdateKonklusi;
import com.example.zakat.amil.request.PendingamilFragment;
import com.example.zakat.model.RequestTersediaResponse;
import com.example.zakat.model.RequestTersediaResponse;
import com.example.zakat.model.RequestTersediaResponse;
import com.example.zakat.model.ResponseData;
import com.example.zakat.muzakki.HistoryMuzakkiActivity;
import com.example.zakat.muzakki.PenilaianActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListZakat extends RecyclerView.Adapter<ListZakat.HolderData> {
    private List<RequestTersediaResponse> listrequestzakat ;
    private Activity activity;
    public ListZakat(List<RequestTersediaResponse> listrequestzakat, Activity activity) {
        this.listrequestzakat = listrequestzakat;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_muzakki_riwayat, parent, false);
        HolderData holder = new HolderData(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        RequestTersediaResponse dm = listrequestzakat.get(position);

        holder.tvid.setText(dm.getTbrequestzakatIdRequest());
        holder.tvtipezakat.setText(dm.getTbrequestzakatTipeZakat());
        holder.tvtglrequest.setText(dm.getTbrequestzakatTglRequest());
        holder.tvstatus.setText(dm.getTbrequestzakatStatuspengambilan());
        holder.tvjz.setText(dm.getTbrequestzakatJenisZakat());
        if (holder.tvjz.getText().toString().equals("Sapi") || holder.tvjz.getText().toString().equals("Kambing")){
            holder.tvtlz.setText(dm.getTbrequestzakatTotalZakat()+ " Ekor");
        }else if (holder.tvjz.getText().toString().equals("Emas") || holder.tvjz.getText().toString().equals("Perak")){
            holder.tvtlz.setText(dm.getTbrequestzakatTotalZakat()+ " gram");
        }else{
        holder.tvtlz.setText(dm.getTbrequestzakatTotalZakat()+ " KG");
        }
        if (holder.tvstatus.getText().toString().equals("Selesai")){
            holder.btnkonfirmasi.setVisibility(View.VISIBLE);
            holder.btnkonfirmasi.setText("Beri Nilai");
        }else {
            holder.btnkonfirmasi.setVisibility(View.GONE);
        }
        if (holder.tvjz.getText().toString().equals("Sapi") || holder.tvjz.getText().toString().equals("Kambing")){
            holder.tersedia = Integer.parseInt(dm.getTbtersediaJmlZakat());
            holder.req = Integer.parseInt(dm.getTbrequestzakatTotalZakat());
            holder.hasil = holder.tersedia + holder.req;
        }else {
            holder.tersediant = Double.parseDouble(dm.getTbtersediaJmlZakat());
            holder.reqnt = Double.parseDouble(dm.getTbrequestzakatTotalZakat());
            holder.hasilnt = holder.tersediant + holder.reqnt;
        }
        holder.penilaian();
        holder.btnkonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.btnkonfirmasi.getText().toString().equals("Konfirmasi")) {
                    if (holder.tvjz.getText().toString().equals("Sapi") || holder.tvjz.getText().toString().equals("Kambing")){
                        holder.getforupdate();
                    }else {
                        holder.getforupdatenonternak();
                    }

                }else
                if (holder.btnkonfirmasi.getText().toString().equals("Beri Nilai")){
                    Intent i = new Intent(activity, PenilaianActivity.class);
                    i.putExtra("id", holder.tvid.getText().toString());
                    activity.startActivity(i);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, InfoZakatActivity.class);
                i.putExtra("tvid", dm.getTbrequestzakatIdRequest());
                i.putExtra("tvidAmil", dm.getTbrequestzakatIdAmil());
                i.putExtra("tvJZ", dm.getTbrequestzakatJenisZakat());
                i.putExtra("tvNishob", dm.getTbrequestzakatNishob());
                i.putExtra("tvtlz", dm.getTbrequestzakatTotalZakat());
                i.putExtra("tvnmmuzakki", dm.getTbrequestzakatNamamuzakki());
                i.putExtra("tvnomormuzakki", dm.getTbrequestzakatNomortelepon());
                i.putExtra("tvalamat", dm.getTbrequestzakatAlamat());
                i.putExtra("otp", dm.getTbrequestzakatOtp());
                i.putExtra("tvstatus", dm.getTbrequestzakatStatuspengambilan());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listrequestzakat.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvid, tvtipezakat, tvtglrequest, tvstatus, tvjz, tvtlz;
        Button btnkonfirmasi;
        int tersedia, hasil ,req;
        double  tersediant, reqnt, hasilnt;
        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvid = itemView.findViewById(R.id.tvid);
            tvtipezakat = itemView.findViewById(R.id.tvtipezakat);
            tvtglrequest = itemView.findViewById(R.id.tvtgltransaksi);
            tvjz = itemView.findViewById(R.id.tvJZ);
            tvtlz = itemView.findViewById(R.id.tvtlz);
            tvstatus = itemView.findViewById(R.id.tvstatus);
            btnkonfirmasi = itemView.findViewById(R.id.btnkonfirmasi);

        }
        private void konfirmasi() {
            // Data Now
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String sDate = simpleDateFormat.format(date);
            String statusUpdate = "Selesai";
            APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> UpdateData = ardData.UpdateBerlangsungMuzzaki(tvid.getText().toString(), sDate, statusUpdate);
            UpdateData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    ((HistoryMuzakkiActivity) activity).onResume();
                    Toast.makeText(activity, pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Toast.makeText(activity, "Gagal Menghubungkan ke Server  : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        private void getforupdate(){
            APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> cekData = ardData.updatezakat(tvjz.getText().toString(), String.valueOf(hasil));
            cekData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    String kode = String.valueOf(response.body().getKode());
                    String pesan = response.body().getPesan();
                    if (!kode.equals("0")){
                        konfirmasi();
                        Toast.makeText(activity, pesan, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(activity, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Toast.makeText(activity, "Gagal Menghubungkan ke Server  : " +t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void getforupdatenonternak(){
            APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> cekData = ardData.updatezakat(tvjz.getText().toString(), String.valueOf(hasilnt));
            cekData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    String kode = String.valueOf(response.body().getKode());
                    String pesan = response.body().getPesan();
                    if (!kode.equals("0")){
                        konfirmasi();
                        Toast.makeText(activity, pesan, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(activity, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Toast.makeText(activity, "Gagal Menghubungkan ke Server  : " +t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void penilaian(){
            APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> cekData = ardData.getnilai(tvid.getText().toString());
            cekData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    String kode = String.valueOf(response.body().getKode());
                    String pesan = response.body().getPesan();
                    if (!kode.equals("0")){
                        btnkonfirmasi.setVisibility(View.GONE);
                    }else {
//                    Toast.makeText(InfoZakatActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Toast.makeText(activity, "Gagal Menghubungkan ke Server  : " +t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
