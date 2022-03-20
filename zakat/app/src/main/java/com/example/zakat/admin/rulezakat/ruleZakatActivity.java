package com.example.zakat.admin.rulezakat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.admin.rulezakat.masuk.ZakatMasukAdminActivity;
import com.example.zakat.amil.penyaluran.ZakatPenyaluranActivity;
import com.example.zakat.amil.request.AmilPengambilanActivity;
import com.example.zakat.amil.terkumpul.ZakatTerkumpulAmilActivity;

public class ruleZakatActivity extends AppCompatActivity {
    CardView cvpengambilan, cvterkumpul, cvpenyaluran;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_zakat);
        cvpengambilan = findViewById(R.id.cvrequest);
        cvpenyaluran = findViewById(R.id.cvpenyaluran);
        cvterkumpul = findViewById(R.id.cvterkumpul);
        toolbar = findViewById(R.id.toolbar);

        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //cvpengambilan
        cvpengambilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ZakatMasukAdminActivity.class));
            }
        });
        cvterkumpul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ZakatTerkumpulAmilActivity.class));
            }
        });
        cvpenyaluran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ZakatPenyaluranActivity.class));
            }
        });
    }
}