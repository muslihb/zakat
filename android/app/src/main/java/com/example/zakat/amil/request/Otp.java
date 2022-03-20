package com.example.zakat.amil.request;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.admin.rulezakat.masuk.InfoPengambilanAdminActivity;
import com.example.zakat.model.ResponseData;
import com.example.zakat.muzakki.History.InfoZakatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Otp extends DialogFragment {
    TextInputEditText etotp;
    Button btnkonfirmasi;
    String idRequest, stat;

    public Otp(String idRequest, String stat) {
        this.idRequest = idRequest;
        this.stat = stat;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_konfirmasi_otp, container, false);
       // initialize
        btnkonfirmasi = view.findViewById(R.id.btnkonfirmasi);
        etotp = view.findViewById(R.id.otp);
        // batas lenght
        etotp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etotp.getText().length() < 4){
                    btnkonfirmasi.setEnabled(false);
                }else {
                    btnkonfirmasi.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //btn konfirmasi
        btnkonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekOTP();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void konfirmasi() {
        // Data Now
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String sDate = simpleDateFormat.format(date);
        String statusUpdate = "Selesai";
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> UpdateData = ardData.UpdateBerlangsungMuzzaki(idRequest,sDate,statusUpdate);
        UpdateData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                Toast.makeText(getActivity(), "Pengambilan Selesai", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void cekOTP(){
        //Create Session
        SessionManager sessionManager = new SessionManager(getActivity());
        //detail sesi
        HashMap<String, String> user = sessionManager.getDataUser();
        String sId = user.get(sessionManager.ID);
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> cekotp = apiAdapter.cekotp(idRequest, sId, stat, etotp.getText().toString());
        cekotp.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    konfirmasi();
                }else {
                    etotp.setError("Kode OTP Invalid");
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}