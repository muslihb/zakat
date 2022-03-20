package com.example.zakat.amil.request;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.zakat.R;
import com.google.android.material.tabs.TabLayout;

public class AmilPengambilanActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 pager2;
    Toolbar toolbar;
    RequestFragment adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amil_pengambilan);
        tabLayout = findViewById(R.id.tablayouthomeamil);
        pager2 = findViewById(R.id.view_pager2);
        toolbar = findViewById(R.id.TBHomeAmil);

        //set Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = ((AppCompatActivity)AmilPengambilanActivity.this).getSupportFragmentManager();
        adapter =  new RequestFragment(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.addTab(tabLayout.newTab().setText("Berlangsung"));
        tabLayout.addTab(tabLayout.newTab().setText("Berhasil"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}