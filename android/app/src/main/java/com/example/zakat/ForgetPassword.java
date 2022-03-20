package com.example.zakat;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.zakat.model.ResponseData;
import com.example.zakat.muzakki.Profile.GantiPasswordActivity;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends DialogFragment {
    TextInputEditText etusername, etpassword, etpasswordconfirm;
    Button btnsubmit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_forget_password, container, false);
        etusername = view.findViewById(R.id.etnamapengguna);
        etpassword = view.findViewById(R.id.etpassword);
        etpasswordconfirm = view.findViewById(R.id.etpasswordkonfirmasi);
        btnsubmit = view.findViewById(R.id.btnsimpan);
        //btn submit
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etusername.getText().toString())){
                    etusername.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(etusername.getText().toString())){
                    etpassword.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(etusername.getText().toString())){
                    etpasswordconfirm.setError("Tidak Boleh Kosong");
                }
                if (!TextUtils.isEmpty(etusername.getText().toString()) ||
                        !TextUtils.isEmpty(etpasswordconfirm.getText().toString())||
                                !TextUtils.isEmpty(etpasswordconfirm.getText().toString())){
                    cekusername();
                }
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

    private void cekusername() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> cekpw = apiAdapter.cekusername(etusername.getText().toString());
        cekpw.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    if (etpassword.getText().toString().equals(etpasswordconfirm.getText().toString())){
                        simpan();
                    }else {
                        Toast.makeText(getActivity(), "cek kembali password anda", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void simpan() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> ubahpw = apiAdapter.updatepw(etusername.getText().toString(),
                etpasswordconfirm.getText().toString());
        ubahpw.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                    dismiss();
                }else {
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
