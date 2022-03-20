package com.example.zakat.amil.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.model.RequestResponse;
import com.example.zakat.model.ResponseData;
import com.example.zakat.muzakki.History.ListZakat;
import com.example.zakat.muzakki.HistoryMuzakkiActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingamilFragment extends Fragment {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;
    private List<RequestResponse> listrequestzakat;
    String statusnow = "Pending";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pending_amil, container, false);
        srlData = v.findViewById(R.id.srlData);
        pbData = v.findViewById(R.id.pbData);
        rvData = v.findViewById(R.id.rvdata);
        lmData = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        srlData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlData.setRefreshing(true);
                retrieveDataPending();
                srlData.setRefreshing(false);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveDataPending();
    }


    public void retrieveDataPending() {
        pbData.setVisibility(View.VISIBLE);
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> tampilData = ardData.RequestPengambilanAmil(statusnow);

        tampilData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatarequest() != null) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
//                    Toast.makeText(getActivity(), "Kode : " +kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                    listrequestzakat = response.body().getDatarequest();

                    adData = new ListPending(listrequestzakat, PendingamilFragment.this);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    pbData.setVisibility(View.INVISIBLE);
                    rvData.setVisibility(View.VISIBLE);
                }else {
                    rvData.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), "Gagal Menghubungkan ke Server  : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }
}
