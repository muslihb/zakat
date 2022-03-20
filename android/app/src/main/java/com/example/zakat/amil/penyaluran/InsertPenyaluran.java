package com.example.zakat.amil.penyaluran;

import android.app.Dialog;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.admin.dataakun.ListAkunAdminActivity;
import com.example.zakat.model.RequestResponse;
import com.example.zakat.model.ResponseData;
import com.example.zakat.muzakki.History.InfoZakatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertPenyaluran extends DialogFragment {
    TextInputEditText etnamalengkap, ettlz, etzakatkeluar, actvgolongan;
    AutoCompleteTextView actvJZ, actvbait, actvsimpanan, actvkerja
            , actvmualaf, actvsafar, actvsafar2,actvgharim,actvfisik ;
    Button btnsimpan;
    LinearLayout btnback, secondary, primary;
    TextInputLayout q1,q2,q3,q4,q5,q6,q7,q8;
    float s, ss, jumlah;
    int st, sst, jumlaht;
    String hasil;
    private List<RequestResponse> listzakat;
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
        btnback = view.findViewById(R.id.btnback);
        primary = view.findViewById(R.id.primary);
        secondary = view.findViewById(R.id.secondary);
        q1 = view.findViewById(R.id.q1);
        q2 = view.findViewById(R.id.q2);
        q3 = view.findViewById(R.id.q3);
        q4 = view.findViewById(R.id.q4);
        q5 = view.findViewById(R.id.q5);
        q6 = view.findViewById(R.id.q6);
        q7 = view.findViewById(R.id.q7);
        q8 = view.findViewById(R.id.q8);
        actvbait = view.findViewById(R.id.actvbait);
        actvsimpanan = view.findViewById(R.id.actvsimpanan);
        actvfisik = view.findViewById(R.id.actvfisik);
        actvkerja = view.findViewById(R.id.actvkerja);
        actvmualaf = view.findViewById(R.id.actvmualaf);
        actvgharim = view.findViewById(R.id.actvgharim);
        actvsafar = view.findViewById(R.id.actvsafar);
        actvsafar2 = view.findViewById(R.id.actvsafar2);

        //Hilangkan Sementara
        q2.setVisibility(View.GONE);
        q3.setVisibility(View.GONE);
        q4.setVisibility(View.GONE);
        q5.setVisibility(View.GONE);
        q6.setVisibility(View.GONE);
        q7.setVisibility(View.GONE);
        q8.setVisibility(View.GONE);

        //Cek Golongan Zakat
        //Quest 1
        String[] squest1 = getResources().getStringArray(R.array.quest1);
        ArrayAdapter aquest1 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, squest1);
        actvbait.setAdapter(aquest1);
        actvbait.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(getActivity(),"Ahlul Bait Tidak Dapat Menerima Zakat", Toast.LENGTH_SHORT).show();
                        dismiss();
                        break;
                    case 1:
                        q2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        //Quest 2
        String[] squest2 = getResources().getStringArray(R.array.quest2);
        ArrayAdapter aquest2 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, squest2);
        actvsimpanan.setAdapter(aquest2);
        actvsimpanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        q3.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        q3.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        //Quest 3
        String[] squest3 = getResources().getStringArray(R.array.quest3);
        ArrayAdapter aquest3 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, squest3);
        actvfisik.setAdapter(aquest3);
        actvfisik.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        q4.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        actvgolongan.setText("Fakir");
                        q2.setVisibility(View.GONE);
                        q3.setVisibility(View.GONE);
                        actvbait.setText(null);
                        actvsimpanan.setText(null);
                        actvfisik.setText(null);
                        primary.setVisibility(View.VISIBLE);
                        secondary.setVisibility(View.GONE);
                        break;
                }
            }
        });
        //Quest 4
        String[] squest4 = getResources().getStringArray(R.array.quest4);
        ArrayAdapter aquest4 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, squest4);
        actvkerja.setAdapter(aquest4);
        actvkerja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        actvgolongan.setText("Amil");
                        q2.setVisibility(View.GONE);
                        q3.setVisibility(View.GONE);
                        q4.setVisibility(View.GONE);
                        actvbait.setText(null);
                        actvsimpanan.setText(null);
                        actvfisik.setText(null);
                        actvkerja.setText(null);
                        primary.setVisibility(View.VISIBLE);
                        secondary.setVisibility(View.GONE);
                        break;
                    case 1:
                        actvgolongan.setText("Fi Sabililah");
                        q2.setVisibility(View.GONE);
                        q3.setVisibility(View.GONE);
                        q4.setVisibility(View.GONE);
                        actvbait.setText(null);
                        actvsimpanan.setText(null);
                        actvfisik.setText(null);
                        actvkerja.setText(null);
                        primary.setVisibility(View.VISIBLE);
                        secondary.setVisibility(View.GONE);
                        break;
                    case 2:
                        actvgolongan.setText("Budak");
                        q2.setVisibility(View.GONE);
                        q3.setVisibility(View.GONE);
                        q4.setVisibility(View.GONE);
                        actvbait.setText(null);
                        actvsimpanan.setText(null);
                        actvfisik.setText(null);
                        actvkerja.setText(null);
                        primary.setVisibility(View.VISIBLE);
                        secondary.setVisibility(View.GONE);
                        break;
                    case 3:
                        q5.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        //Quest 5
        String[] squest5 = getResources().getStringArray(R.array.quest5);
        ArrayAdapter aquest5 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, squest5);
        actvmualaf.setAdapter(aquest5);
        actvmualaf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        actvgolongan.setText("Muallaf");
                        q2.setVisibility(View.GONE);
                        q3.setVisibility(View.GONE);
                        q4.setVisibility(View.GONE);
                        q5.setVisibility(View.GONE);
                        actvbait.setText(null);
                        actvsimpanan.setText(null);
                        actvfisik.setText(null);
                        actvkerja.setText(null);
                        actvmualaf.setText(null);
                        primary.setVisibility(View.VISIBLE);
                        secondary.setVisibility(View.GONE);
                        break;
                    case 1:
                        q6.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        //Quest 6
        String[] squest6 = getResources().getStringArray(R.array.quest6);
        ArrayAdapter aquest6 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, squest6);
        actvgharim.setAdapter(aquest6);
        actvgharim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        q7.setVisibility(View.VISIBLE);
                        q7.setHint("Alasan Berhutang?");
                        String[] squest6more = getResources().getStringArray(R.array.quest6more);
                        ArrayAdapter aquest6more = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, squest6more);
                        actvsafar.setAdapter(aquest6more);
                        break;
                    case 1:
                        q7.setVisibility(View.VISIBLE);
                        q7.setHint("Apakah Sedang Safar?");
                        String[] squest7 = getResources().getStringArray(R.array.quest7);
                        ArrayAdapter aquest7 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, squest7);
                        actvsafar.setAdapter(aquest7);
                        break;
                }
            }
        });
        //Quest 7
        actvsafar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (q7.getHint().toString().equals("Alasan Berhutang?")){
                    switch (i){
                        case 2:
                            dismiss();
                            Toast.makeText(getActivity(),"Maaf Tidak Bisa Memberikan Zakat untuk orang yang berhutang dalam hal lain",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            actvgolongan.setText("Gharim");
                            q2.setVisibility(View.GONE);
                            q3.setVisibility(View.GONE);
                            q4.setVisibility(View.GONE);
                            q5.setVisibility(View.GONE);
                            q6.setVisibility(View.GONE);
                            q7.setVisibility(View.GONE);
                            actvbait.setText(null);
                            actvsimpanan.setText(null);
                            actvfisik.setText(null);
                            actvkerja.setText(null);
                            actvmualaf.setText(null);
                            actvgharim.setText(null);
                            actvsafar.setText(null);
                            primary.setVisibility(View.VISIBLE);
                            secondary.setVisibility(View.GONE);
                            break;
                    }
                }else {
                    switch (i){
                        case 0:
                         q8.setVisibility(View.VISIBLE);
                         break;
                        case 1:
                            if (actvsimpanan.getText().toString().equals("Tidak Mencukupi")){
                                actvgolongan.setText("Miskin");
                                q2.setVisibility(View.GONE);
                                q3.setVisibility(View.GONE);
                                q4.setVisibility(View.GONE);
                                q5.setVisibility(View.GONE);
                                q6.setVisibility(View.GONE);
                                q7.setVisibility(View.GONE);
                                actvbait.setText(null);
                                actvsimpanan.setText(null);
                                actvfisik.setText(null);
                                actvkerja.setText(null);
                                actvmualaf.setText(null);
                                actvgharim.setText(null);
                                actvsafar.setText(null);
                                primary.setVisibility(View.VISIBLE);
                                secondary.setVisibility(View.GONE);
                            }else {
                                Toast.makeText(getActivity(),"Maaf tidak berhak memberi zakat ke orang tersebut", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                            break;
                    }
                }
            }
        });
        //Quest 8
        String[] squest8 = getResources().getStringArray(R.array.quest8);
        ArrayAdapter aquest8 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, squest8);
        actvsafar2.setAdapter(aquest8);
        actvsafar2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        dismiss();
                        Toast.makeText(getActivity(),"Maaf Tidak Bisa Memberikan Zakat dalam hal tersebut",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        actvgolongan.setText("Ibnu Sabil");
                        q2.setVisibility(View.GONE);
                        q3.setVisibility(View.GONE);
                        q4.setVisibility(View.GONE);
                        q5.setVisibility(View.GONE);
                        q6.setVisibility(View.GONE);
                        q7.setVisibility(View.GONE);
                        actvbait.setText(null);
                        actvsimpanan.setText(null);
                        actvfisik.setText(null);
                        actvkerja.setText(null);
                        actvmualaf.setText(null);
                        actvgharim.setText(null);
                        actvsafar.setText(null);
                        primary.setVisibility(View.VISIBLE);
                        secondary.setVisibility(View.GONE);
                        break;
                }
            }
        });
        //btn Back
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primary.setVisibility(View.VISIBLE);
                secondary.setVisibility(View.GONE);
            }
        });

        //jumlah Tersedia Zakat
        String[] sstatus1 = getResources().getStringArray(R.array.listinfotersedia);
        ArrayAdapter astatus1 = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, sstatus1);
        actvJZ.setAdapter(astatus1);
        actvJZ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cekforupdate();
                etzakatkeluar.setText(null);
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
                        if (sst == 0){
                            etzakatkeluar.setError("Tidak Boleh 0");
                        }else {
                            if (jumlaht < 0) {
                                etzakatkeluar.setError("Ketersedian Melebihi Batas");
                                cekforupdate();
                            } else {
                                ettlz.setInputType(InputType.TYPE_CLASS_NUMBER);
                                ettlz.setText(hasil);
                            }
                        }
                    } else {
                        ss = Float.parseFloat(etzakatkeluar.getText().toString());
                        jumlah = s - ss;
                        hasil = String.valueOf(jumlah);
                        if (ss == 0){
                            etzakatkeluar.setError("Tidak Boleh 0");
                        }else {
                            if (jumlah < 0) {
                                etzakatkeluar.setError("Ketersedian Melebihi Batas");
                                cekforupdate();
                            } else {
                                ettlz.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                ettlz.setText(hasil);
                            }
                        }
                    }
                } catch (Exception IO) {
                    cekforupdate();
                }
            }
        });

        //List Golongan
//        String[] sstatus = getResources().getStringArray(R.array.listmutashik);
//        ArrayAdapter astatus = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, sstatus);
//        actvgolongan.setAdapter(astatus);
        actvgolongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primary.setVisibility(View.GONE);
                secondary.setVisibility(View.VISIBLE);
            }
        });

        //Button Simpan
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
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> cekData = ardData.updatezakat(actvJZ.getText().toString(), ettlz.getText().toString());
        cekData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String kode = String.valueOf(response.body().getKode());
                String pesan = response.body().getPesan();
                if (!kode.equals("0")){
                    simpan();
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
        SessionManager sessionManager;
        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getDataUser();
        String sID = user.get(sessionManager.ID);
        // Data Now
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String sDate = simpleDateFormat.format(date);
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> registerData = apiAdapter.insertmutashik(sID,
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
