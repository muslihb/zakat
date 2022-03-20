package com.example.zakat.admin.dataakun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.model.ResponseData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertAkunAdmin extends DialogFragment {
    TextInputLayout textInputLayoutnama, textInputLayouttelepon, textInputLayoutalamat;
    TextInputEditText etnama, ettelepon, etusername,etpassword, etalamat;
    AutoCompleteTextView actvakses;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_register_admin, container, false);
        Button button = view.findViewById(R.id.btndaftar);
        textInputLayoutnama = view.findViewById(R.id.tilnamalengkap);
        textInputLayouttelepon = view.findViewById(R.id.tiltelepon);
        textInputLayoutalamat = view.findViewById(R.id.tilalamat);
        etnama = view.findViewById(R.id.etnamalengkap);
        ettelepon = view.findViewById(R.id.ettelepon);
        etusername = view.findViewById(R.id.etnamapengguna);
        etpassword = view.findViewById(R.id.etpassword);
        etalamat = view.findViewById(R.id.etalamat);
        actvakses = view.findViewById(R.id.actvakses);

        //Hak akses
        String[] sstatus = getResources().getStringArray(R.array.AksesAkun);
        ArrayAdapter astatus = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, sstatus);
        actvakses.setAdapter(astatus);
        actvakses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        textInputLayoutalamat.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        textInputLayoutalamat.setVisibility(View.GONE);
                        break;
                    case 2:
                        textInputLayoutalamat.setVisibility(View.GONE);
                        break;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etnama.getText().toString();
                String telepon = ettelepon.getText().toString();
                String un = etusername.getText().toString();
                String pw = etpassword.getText().toString();
                if (TextUtils.isEmpty(nama)){
                    etnama.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(telepon)){
                    ettelepon.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(un)){
                    etusername.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(pw)){
                    etpassword.setError("Tidak Boleh Kosong");
                }
                if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(telepon)
                        && !TextUtils.isEmpty(un) && !TextUtils.isEmpty(pw)){
                    daftar();
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

    private void daftar() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> registerData = apiAdapter.registerakun(etnama.getText().toString(),
                ettelepon.getText().toString(),etalamat.getText().toString(),
                etusername.getText().toString(), etpassword.getText().toString(),
                actvakses.getText().toString());
        registerData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();

                if (!kode.equals("0")){
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                    ((ListAkunAdminActivity) getActivity()).retrieveData();
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