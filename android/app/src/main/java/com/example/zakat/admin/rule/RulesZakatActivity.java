package com.example.zakat.admin.rule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.zakat.R;
import com.example.zakat.admin.rule.konklusi.ListKonklusiActivity;
import com.example.zakat.admin.rule.premis.ListPremisActivity;

public class RulesZakatActivity extends AppCompatActivity {
    CardView cvpremis, cvkonklusi;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_zakat);
//        initialize
        cvpremis = findViewById(R.id.cvpremis);
        cvkonklusi = findViewById(R.id.cvkonklusi);
        toolbar = findViewById(R.id.toolbar);
//      Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//      CVpremis
        cvpremis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RulesZakatActivity.this, ListPremisActivity.class));
            }
        });
//      CVkonklusi
        cvkonklusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RulesZakatActivity.this, ListKonklusiActivity.class));
            }
        });
    }
}