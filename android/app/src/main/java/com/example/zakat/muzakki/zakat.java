package com.example.zakat.muzakki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.admin.dataakun.ListAkunAdminActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class zakat extends AppCompatActivity {
    SessionManager sessionManager;
    Toolbar toolbar;
    Button btnhitung, btnrequest;
    TextInputLayout ti1;
    TextInputEditText et3;
    CardView cvhasil;
    AutoCompleteTextView autoCompleteTextView, et1;
    TextView tvhasil, tvtlz;
    String hasil;
    int satuan, a, b, hasila, hasilb, totala, totalb, jumlahternak;
    double jumlah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize
        setContentView(R.layout.activity_zakat);
        toolbar = findViewById(R.id.toolbar);
        btnhitung = findViewById(R.id.btnhitung);
        btnrequest = findViewById(R.id.btnrequest);
        et1 = findViewById(R.id.et1);
        et3 = findViewById(R.id.et3);
        ti1 = findViewById(R.id.til1);
        tvtlz = findViewById(R.id.tvtlz);
        tvhasil = findViewById(R.id.tvhasil);
        cvhasil = findViewById(R.id.cvhasil);
        autoCompleteTextView = findViewById(R.id.a1);
        //get Intent
        String tlt = getIntent().getExtras().getString("z");
        toolbar.setTitle(tlt);
        // Create Sesi
        sessionManager = new SessionManager(getApplicationContext());
        //Set Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Set yang akan ditampilkan jika sudah melewati FC
        if (!toolbar.getTitle().toString().equals("Zakat Pertanian")){
            ti1.setVisibility(View.GONE);
        }else {
            ti1.setVisibility(View.VISIBLE);
        }
        if (toolbar.getTitle().toString().equals("Zakat Peternakan")){
            String[] sstatus = getResources().getStringArray(R.array.ternak);
            ArrayAdapter astatus = new ArrayAdapter(zakat.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
            autoCompleteTextView.setAdapter(astatus);
        }else if(toolbar.getTitle().toString().equals("Zakat Perhiasan")){
            String[] sstatus = getResources().getStringArray(R.array.perhiasan);
            ArrayAdapter astatus = new ArrayAdapter(zakat.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
            autoCompleteTextView.setAdapter(astatus);
        }else if (toolbar.getTitle().toString().equals("Zakat Pertanian")){
            String[] sstatus = getResources().getStringArray(R.array.pertanian);
            ArrayAdapter astatus = new ArrayAdapter(zakat.this, R.layout.support_simple_spinner_dropdown_item, sstatus);
            autoCompleteTextView.setAdapter(astatus);
            String[] air = getResources().getStringArray(R.array.perairan);
            ArrayAdapter air2 = new ArrayAdapter(zakat.this, R.layout.support_simple_spinner_dropdown_item, air);
            et1.setAdapter(air2);
        }
        btnhitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nishob = Integer.parseInt(et3.getText().toString());
                if (ti1.getVisibility() == View.GONE){
                    //Perhiasan
                    if (autoCompleteTextView.getText().toString().equals("Emas") &&
                            nishob >= 85){
                        cvhasil.setVisibility(View.VISIBLE);
                        jumlah = Integer.parseInt(et3.getText().toString()) * 0.025;
                        hasil = String.valueOf(jumlah) +" gram ";
                        tvhasil.setText("Zakat Perhiasan adalah 2,5%\n" +
                                "Nishob yang dimiliki yaitu "+ et3.getText().toString()+ " gram\n"
                                + "Perhitungannya adalah Nishob * 2,5%\n"+ et3.getText().toString() +
                                " x 2,5% = "+ jumlah + "\n Maka Zakat yang harus dikeluarkan adalah " + hasil
                                + " " + autoCompleteTextView.getText().toString());
                        tvtlz.setText(hasil+ autoCompleteTextView.getText().toString());
                    }else if (autoCompleteTextView.getText().toString().equals("Emas") &&
                            nishob < 85){
                        cvhasil.setVisibility(View.GONE);
                        et3.setError("nishob belum sampai 85 gram Emas");
                    }else if (autoCompleteTextView.getText().toString().equals("Perak") &&
                            nishob >= 595){
                        cvhasil.setVisibility(View.VISIBLE);
                        jumlah = Integer.parseInt(et3.getText().toString()) * 0.025;
                        hasil = String.valueOf(jumlah) +" gram ";
                        tvhasil.setText("Zakat Perhiasan adalah 2,5%\n" +
                                "Nishob yang dimiliki yaitu "+ et3.getText().toString()+ " gram\n"
                                + "Perhitungannya adalah Nishob * 2,5%\n"+ et3.getText().toString() +
                                " x 2,5% = "+ jumlah + "\n Maka Zakat yang harus dikeluarkan adalah " + hasil
                                + " " + autoCompleteTextView.getText().toString());
                        tvtlz.setText(hasil+ autoCompleteTextView.getText().toString());
                    }else if (autoCompleteTextView.getText().toString().equals("Perak") &&
                            nishob < 595){
                        cvhasil.setVisibility(View.GONE);
                        et3.setError("nishob belum sampai 595 gram Perak");
                        // peternakan
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                    nishob < 30){
                        cvhasil.setVisibility(View.GONE);
                        et3.setError("nishob belum sampai 30 Sapi");
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                            nishob < 40){
                        cvhasil.setVisibility(View.VISIBLE);
                        satuan = 1;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan sapi\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ hasil + " "
                                +autoCompleteTextView.getText().toString() + " tabi'" );
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                            nishob < 60){
                        cvhasil.setVisibility(View.VISIBLE);
                        satuan = 1;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan sapi\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ hasil + " "
                                +autoCompleteTextView.getText().toString() + " musinnah'" );
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                            nishob < 70){
                        cvhasil.setVisibility(View.VISIBLE);
                        satuan = 2;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan sapi\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ hasil + " "
                                +autoCompleteTextView.getText().toString() + " tabi'" );
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                            nishob < 80){
                        cvhasil.setVisibility(View.VISIBLE);
                        a = 1;
                        b = 1;
                        satuan = a + b;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan sapi\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ a + " "
                                +autoCompleteTextView.getText().toString() + " tabi' "
                                + b + " "
                                +autoCompleteTextView.getText().toString() + " Musinnah'");
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                            nishob < 90){
                        cvhasil.setVisibility(View.VISIBLE);
                        satuan = 2;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan sapi\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ hasil + " "
                                +autoCompleteTextView.getText().toString() + " Musinnah'" );
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                            nishob < 100){
                        cvhasil.setVisibility(View.VISIBLE);
                        satuan = 3;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan sapi\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ hasil + " "
                                +autoCompleteTextView.getText().toString() + " tabi'" );
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                            nishob < 110){
                        cvhasil.setVisibility(View.VISIBLE);
                        a = 2;
                        b = 1;
                        satuan = a + b;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan sapi\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ a + " "
                                +autoCompleteTextView.getText().toString() + " tabi'"
                                + b + " "
                                +autoCompleteTextView.getText().toString() + " Musinnah'");
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                            nishob < 120){
                        cvhasil.setVisibility(View.VISIBLE);
                        a = 1;
                        b = 2;
                        satuan = a + b;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan sapi\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ a + " "
                                +autoCompleteTextView.getText().toString() + " tabi' "
                                + b + " "
                                +autoCompleteTextView.getText().toString() + " Musinnah'");
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                            nishob < 130){
                        cvhasil.setVisibility(View.VISIBLE);
                        a = 4;
                        b = 3;
                        satuan = a + b;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan sapi\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ a + " "
                                +autoCompleteTextView.getText().toString() + " tabi' atau "
                                + b + " "
                                +autoCompleteTextView.getText().toString() + " Musinnah'");
                        tvtlz.setText(hasil+ " "+autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Sapi") &&
                            nishob >= 130){
                        cvhasil.setVisibility(View.VISIBLE);
                        a = 30;
                        b = 40;
                        hasila = nishob / a;
                        hasilb = nishob / b;
                        totala = hasila - 1;
                        totalb = hasilb - 1;
                        satuan = totala + totalb;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan sapi\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ totala + " "
                                +autoCompleteTextView.getText().toString() + " tabi' "
                                + totalb + " "
                                +autoCompleteTextView.getText().toString() + " Musinnah'");
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Kambing") &&
                            nishob < 40){
                        cvhasil.setVisibility(View.GONE);
                        et3.setError("nishob belum sampai 40 Kambing");
                    }else if(autoCompleteTextView.getText().toString().equals("Kambing") &&
                            nishob < 121){
                        cvhasil.setVisibility(View.VISIBLE);
                        satuan = 1;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan Kambing\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ hasil + " "
                                +autoCompleteTextView.getText().toString());
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Kambing") &&
                            nishob < 201){
                        cvhasil.setVisibility(View.VISIBLE);
                        satuan = 2;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan Kambing\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+  hasil + " "
                                +autoCompleteTextView.getText().toString());
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Kambing") &&
                            nishob < 301){
                        cvhasil.setVisibility(View.VISIBLE);
                        satuan = 3;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan Kambing\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+ hasil + " "
                                +autoCompleteTextView.getText().toString());
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }else if(autoCompleteTextView.getText().toString().equals("Kambing") &&
                            nishob >= 400){
                        cvhasil.setVisibility(View.VISIBLE);
                        a = 100;
                        satuan = nishob / a;
                        hasil = String.valueOf(satuan)+ " Ekor ";
                        tvhasil.setText("Zakat peternakan dengan hewan Kambing\n" +
                                "Nishob yang dimiliki yaitu " + et3.getText().toString()+ " "
                                +autoCompleteTextView.getText().toString()+
                                "\nMaka zakat yang harus dikeluarkan adalah "+  hasil + " "
                                +autoCompleteTextView.getText().toString());
                        tvtlz.setText(hasil+" "+ autoCompleteTextView.getText().toString());
                    }
                }else if (ti1.getVisibility() == View.VISIBLE){
                    // Pertanian
                    if (et1.getText().toString().equals("Hujan") && nishob >= 720){
                        cvhasil.setVisibility(View.VISIBLE);
                        jumlah = Integer.parseInt(et3.getText().toString()) * 0.1;
                        hasil = String.valueOf(jumlah) +" KG ";
                        tvhasil.setText("Zakat pertanian dengan perairan Hujan adalah 10%\n" +
                                "Nishob yang dimiliki yaitu "+ et3.getText().toString()+ " KG\n"
                                + "Perhitungannya adalah Nishob * Perairan\n"+ et3.getText().toString() +
                                " x 10% = "+ jumlah + "\n Maka Zakat yang harus dikeluarkan adalah " + hasil
                                + " " + autoCompleteTextView.getText().toString());
                        tvtlz.setText(hasil+ autoCompleteTextView.getText().toString());
                    }else if (et1.getText().toString().equals("Hujan") && nishob < 720) {
                        cvhasil.setVisibility(View.GONE);
                        et3.setError("nishob belum sampai 720");
                    }else if (et1.getText().toString().equals("Pompa Air") && nishob >= 720){
                        cvhasil.setVisibility(View.VISIBLE);
                        jumlah = Integer.parseInt(et3.getText().toString()) * 0.05;
                        hasil = String.valueOf(jumlah) +" KG ";
                        tvhasil.setText("Zakat pertanian dengan perairan pompa air(biaya tambahan sendiri) adalah 5%\n" +
                                "Nishob yang dimiliki yaitu "+ et3.getText().toString()+ " KG\n"
                                + "Perhitungannya adalah Nishob * Perairan\n"+ et3.getText().toString() +
                                " x 5% = "+ jumlah + "\nMaka Zakat yang harus dikeluarkan adalah " + hasil
                                + " " + autoCompleteTextView.getText().toString());
                        tvtlz.setText(hasil+ autoCompleteTextView.getText().toString());
                    }else if (et1.getText().toString().equals("Pompa Air") && nishob < 720) {
                        cvhasil.setVisibility(View.GONE);
                        et3.setError("nishob belum sampai 720");
                    }
                    else if (et1.getText().toString().equals("Hujan dan Pompa Air") && nishob >= 720){
                        cvhasil.setVisibility(View.VISIBLE);
                        jumlah = Integer.parseInt(et3.getText().toString()) * 0.075;
                        hasil = String.valueOf(jumlah) +" KG ";
                        tvhasil.setText("Zakat pertanian dengan perairan Hujan dan pompa air(biaya tambahan sendiri) adalah 7,5%\n" +
                                "Nishob yang dimiliki yaitu "+ et3.getText().toString()+ " KG\n"
                                + "Perhitungannya adalah Nishob * Perairan\n"+ et3.getText().toString() +
                                " x 7,5% = "+ jumlah + "\nMaka Zakat yang harus dikeluarkan adalah " + hasil
                                + " " + autoCompleteTextView.getText().toString());
                        tvtlz.setText(hasil+ autoCompleteTextView.getText().toString());
                    }else if (et1.getText().toString().equals("Hujan dan Pompa Air") && nishob < 720) {
                        cvhasil.setVisibility(View.GONE);
                        et3.setError("nishob belum sampai 720");
                    }
                }
            }
        });
        //Btn Request
        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Menyimpan Sesi
                if (autoCompleteTextView.getText().toString().equals("Sapi")
                        || autoCompleteTextView.getText().toString().equals("Kambing")){
                    sessionManager.setZakat(toolbar.getTitle().toString(),
                            autoCompleteTextView.getText().toString(), et3.getText().toString(), String.valueOf(satuan));
                }else {
                    sessionManager.setZakat(toolbar.getTitle().toString(),
                            autoCompleteTextView.getText().toString(), et3.getText().toString(), String.valueOf(jumlah));
                }

                startActivity(new Intent(getApplicationContext(), RequestActivity.class));
                finish();
            }
        });
    }
}