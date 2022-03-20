package com.example.zakat.admin.rule.konklusi;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.admin.dataakun.ListAkunAdminActivity;
import com.example.zakat.model.ResponseData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertKonklusi extends DialogFragment {
    TextInputLayout textInputLayoutquest, textInputLayoutifyes, textInputLayoutifno;
    TextInputEditText etkode, etnama, etquest,etifyes, etifno;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.iu_konpre, container, false);
        Button button = view.findViewById(R.id.btniu);
        textInputLayoutquest = view.findViewById(R.id.tilquest);
        textInputLayoutifyes = view.findViewById(R.id.tilifyes);
        textInputLayoutifno = view.findViewById(R.id.tilifno);
        etkode = view.findViewById(R.id.etkode);
        etnama = view.findViewById(R.id.etnama);
        etquest = view.findViewById(R.id.etquest);
        etifyes = view.findViewById(R.id.etifyes);
        etifno = view.findViewById(R.id.etifno);
        textInputLayoutquest.setVisibility(View.GONE);
        textInputLayoutifyes.setVisibility(View.GONE);
        textInputLayoutifno.setVisibility(View.GONE);
        button.setText("Simpan");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etnama.getText().toString())){
                    etnama.setError("Tidak Boleh Kosong");
                }else {
                    inserkonklusi();
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

    private void inserkonklusi() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> registerData = apiAdapter.insertkonklusizakat(etkode.getText().toString(),
                etnama.getText().toString()
        );
        registerData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();

                if (!kode.equals("0")){
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                    ((ListKonklusiActivity) getActivity()).retrievekonklusiData();
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