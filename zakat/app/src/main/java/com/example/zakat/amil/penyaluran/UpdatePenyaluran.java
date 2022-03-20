package com.example.zakat.amil.penyaluran;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.example.zakat.SessionManager;
import com.example.zakat.model.RequestResponse;
import com.example.zakat.model.ResponseData;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePenyaluran extends DialogFragment {
    TextInputEditText etnamalengkap, ettlz, etzakatkeluar;
    AutoCompleteTextView actvJZ, actvgolongan;
    Button btnsimpan;
    float s, ss, jumlah;
    int st, sst, jumlaht;
    String hasil, idmutashik, nama, JZ, golongan, jmlh;
    private List<RequestResponse> listzakat;
    public UpdatePenyaluran(String tbmutashikidmutashik, String tbmutashiknamamutashik, String tbmutashikgolonganmutashik, String tbmutashikjumlah, String tbmutashikjenis_zakat) {
        this.idmutashik = tbmutashikidmutashik;
        this.nama = tbmutashiknamamutashik;
        this.golongan = tbmutashikgolonganmutashik;
        this.JZ = tbmutashikjenis_zakat;
        this.jmlh = tbmutashikjumlah;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_mutashik, container, false);
        //initialize
        etnamalengkap = view.findViewById(R.id.etnamalengkap);
        ettlz = view.findViewById(R.id.ettlz);
        etzakatkeluar = view.findViewById(R.id.etzakatkeluar);
        actvJZ = view.findViewById(R.id.actvJZ);
        actvgolongan = view.findViewById(R.id.actvgolongan);
        btnsimpan = view.findViewById(R.id.btnsimpan);

        //set sebelum Update
        btnsimpan.setText("Update");
        etnamalengkap.setText(nama);
        actvJZ.setText(JZ);
        actvJZ.setEnabled(false);
        etzakatkeluar.setText(jmlh);
        actvgolongan.setText(golongan);
        cekforupdate();

        //jumlah Tersedia Zakat
        String[] sstatus1 = getResources().getStringArray(R.array.listinfotersedia);
        ArrayAdapter astatus1 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, sstatus1);
        actvJZ.setAdapter(astatus1);
        actvJZ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cekforupdate();
            }
        });

        //Zakat Keluar
        etzakatkeluar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (actvJZ.getText().toString().equals("Sapi") || actvJZ.getText().toString().equals("Kambing")) {
                        sst = Integer.parseInt(etzakatkeluar.getText().toString());
                        jumlaht = st - sst;
                        hasil = String.valueOf(jumlaht);
                        if (jumlaht < 0) {
                            etzakatkeluar.setError("Ketersedian Melebihi Batas");
                            cekforupdate();
                        } else {
                            ettlz.setInputType(InputType.TYPE_CLASS_NUMBER);
                            ettlz.setText(hasil);
                        }
                    } else {
                        ss = Float.parseFloat(etzakatkeluar.getText().toString());
                        jumlah = s - ss;
                        hasil = String.valueOf(jumlah);
                        if (jumlah < 0) {
                            etzakatkeluar.setError("Ketersedian Melebihi Batas");
                            cekforupdate();
                        } else {
                            ettlz.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            ettlz.setText(hasil);
                        }
                    }
                } catch (Exception IO) {
                    cekforupdate();
                }
            }
        });

        //List Golongan
        String[] sstatus = getResources().getStringArray(R.array.listmutashik);
        ArrayAdapter astatus = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, sstatus);
        actvgolongan.setAdapter(astatus);
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etnamalengkap.getText().toString())){
                    etnamalengkap.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(actvJZ.getText().toString())){
                    actvJZ.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(ettlz.getText().toString())){
                    ettlz.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(etzakatkeluar.getText().toString())){
                    etzakatkeluar.setError("Tidak Boleh Kosong");
                }
                if (TextUtils.isEmpty(actvgolongan.getText().toString())){
                    actvgolongan.setError("Tidak Boleh Kosong");
                }
                if (!TextUtils.isEmpty(etnamalengkap.getText().toString()) &&
                        !TextUtils.isEmpty(actvJZ.getText().toString())
                        && !TextUtils.isEmpty(ettlz.getText().toString())
                        && !TextUtils.isEmpty(etzakatkeluar.getText().toString())
                        && !TextUtils.isEmpty(actvgolongan.getText().toString())){
                    if (etzakatkeluar.getError() != null){
                        Toast.makeText(getActivity(), "Zakat keluar melebihi dari yang tersedia"
                                , Toast.LENGTH_SHORT).show();
                    }else {
                        getforupdate();
                    }
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

    private void cekforupdate(){
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> cekData = ardData.cektersedia(actvJZ.getText().toString());
        cekData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().getDatarequest() != null) {
                    listzakat = response.body().getDatarequest();
                    if (actvJZ.getText().toString().equals("Sapi") || actvJZ.getText().toString().equals("Kambing")){
                        st = Integer.parseInt(listzakat.get(0).getJmlZakat());
                        ettlz.setText(listzakat.get(0).getJmlZakat());
                    }else {
                        s = Float.parseFloat(listzakat.get(0).getJmlZakat());
                        ettlz.setText(listzakat.get(0).getJmlZakat());
                    }
                }else {
                    String pesan = response.body().getPesan();
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getforupdate(){
        int jml = Integer.parseInt(ettlz.getText().toString());
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> cekData = ardData.updatezakat(actvJZ.getText().toString(), String.valueOf(jml));
        cekData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    ubah();
                }else {
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Menghubungkan ke Server  : " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ubah() {
        SessionManager sessionManager;
        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getDataUser();
        String sID = user.get(sessionManager.ID);
        // Data Now
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String sDate = simpleDateFormat.format(date);
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> registerData = apiAdapter.ubahmutashik(idmutashik ,sID,
                actvJZ.getText().toString(),etzakatkeluar.getText().toString()
                ,etnamalengkap.getText().toString(),actvgolongan.getText().toString(), sDate);
        registerData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                    ((ZakatPenyaluranActivity) getActivity()).retrieveDataPenyaluran();
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
