package com.example.zakat.amil.request;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.admin.dataakun.ListAkunAdminActivity;
import com.example.zakat.model.RequestResponse;
import com.example.zakat.model.ResponseData;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPending extends RecyclerView.Adapter<ListPending.HolderData> {
    private List<RequestResponse> listrequestzakat ;
    private Fragment fragment;

    public ListPending(List<RequestResponse> listrequestzakat, Fragment fragment) {
        this.listrequestzakat = listrequestzakat;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_pending_amil, parent, false);
        HolderData holder = new HolderData(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        RequestResponse dm = listrequestzakat.get(position);
        holder.tvid.setText(dm.getIdRequest());
        holder.tvtipezakat.setText(dm.getTipeZakat());
        holder.tvtglrequest.setText(dm.getTglRequest());
        holder.tvstatus.setText(dm.getStatuspengambilan());
        holder.tvjz.setText(dm.getJenisZakat());
        if (holder.tvjz.getText().toString().equals("Sapi") || holder.tvjz.getText().toString().equals("Kambing")){
            holder.tvtlz.setText(dm.getTotalZakat()+ " Ekor");
        }else if (holder.tvjz.getText().toString().equals("Emas") || holder.tvjz.getText().toString().equals("Perak")){
            holder.tvtlz.setText(dm.getTotalZakat()+ " gram");
        }else {
            holder.tvtlz.setText(dm.getTotalZakat()+ " KG");
        }
        holder.btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.konfirmasi();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(fragment.getActivity(), InfoPengambilanActivity.class);
                i.putExtra("tvid", dm.getIdRequest());
                i.putExtra("tvJZ", dm.getJenisZakat());
                i.putExtra("tvNishob", dm.getNishob());
                i.putExtra("tvtlz", dm.getTotalZakat());
                i.putExtra("tvnmmuzakki", dm.getNamamuzakki());
                i.putExtra("tvnomormuzakki", dm.getNomortelepon());
                i.putExtra("tvalamat", dm.getAlamat());
                i.putExtra("tvstatus", dm.getStatuspengambilan());
                fragment.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listrequestzakat.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvid, tvtipezakat, tvtglrequest, tvstatus, tvjz, tvtlz;
        Button btnconfirm;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvid = itemView.findViewById(R.id.tvid);
            tvtipezakat = itemView.findViewById(R.id.tvtipezakat);
            tvtglrequest = itemView.findViewById(R.id.tvtgltransaksi);
            tvjz = itemView.findViewById(R.id.tvJZ);
            tvtlz = itemView.findViewById(R.id.tvtlz);
            tvstatus = itemView.findViewById(R.id.tvstatus);
            btnconfirm = itemView.findViewById(R.id.btnkonfirmasi);

        }
        private void konfirmasi() {
            SessionManager sessionManager;
            //create sesi
            sessionManager = new SessionManager(fragment.getActivity());
            //User Details
            HashMap<String, String> user = sessionManager.getDataUser();
            String sID = user.get(sessionManager.ID);
            String statusUpdate = "Berlangsung";
            APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> updateData = ardData.UpdatePendingAmil(tvid.getText().toString(), sID, statusUpdate);
            updateData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    ((PendingamilFragment) fragment).onResume();
                    Toast.makeText(fragment.getActivity(), pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Toast.makeText(fragment.getActivity(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
