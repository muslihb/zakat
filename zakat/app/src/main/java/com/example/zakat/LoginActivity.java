package com.example.zakat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.admin.HomeAdmin;
import com.example.zakat.amil.HomeAmil;
import com.example.zakat.model.LoginResponse;
import com.example.zakat.model.ModelLogin;
import com.example.zakat.model.ResponseData;
import com.example.zakat.muzakki.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.zakat.SessionManager.STATUS;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText etusername, etpassword;
    TextView forget, btndaftar;
    SessionManager sessionManager;
    Button btnmasuk;
    private List<LoginResponse> getuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initialize
        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etpassword);
        btnmasuk = findViewById(R.id.btnmasuk);
        btndaftar = findViewById(R.id.btndaftar);
        forget = findViewById(R.id.tvlupapassword);
        //Create Session
        sessionManager = new SessionManager(getApplicationContext());
        //Button Login
        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUsername = etusername.getText().toString().trim();
                String spassword = etpassword.getText().toString().trim();
                if (sUsername.equals("") && spassword.equals("")){
                    etusername.setError("Please Enter Username");
                    etpassword.setError("Please Enter Password");
                }else if (sUsername.equals("")){
                    etusername.setError("Please Enter Username");
                }else if (spassword.equals("")){
                    etpassword.setError("Please Enter Password");
                }else{
                    login();
                }

            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPassword lupa = new ForgetPassword();
                lupa.show(getSupportFragmentManager(), "form");
            }
        });
        //Button Daftar
        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register register = new Register();
                register.show(getSupportFragmentManager(), "form");
            }
        });
        //Sesi jika sudah Login
        if (sessionManager.getLogin() && sessionManager.getDataUser().get(STATUS).equals("Muzakki")){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else if (sessionManager.getLogin() && sessionManager.getDataUser().get(STATUS).equals("Admin")){
            startActivity(new Intent(getApplicationContext(), HomeAdmin.class));
            finish();
        }else if (sessionManager.getLogin() && sessionManager.getDataUser().get(STATUS).equals("Amil")){
            startActivity(new Intent(getApplicationContext(), HomeAmil.class));
            finish();
        }
    }

    private void login() {
        APIAdapter apiAdapter = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ModelLogin> masuk = apiAdapter.ardlogin(etusername.getText().toString(),
                etpassword.getText().toString());
        masuk.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                int kode = response.body().getKode();

                if(kode == 1) {
                    switch (response.body().getStatus()){
                        case "Admin":
                            getdatalogin();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(getApplicationContext(), HomeAdmin.class));
                                    finish();
                                }
                            }, 500);
                            break;
                        case "Muzakki":
                            getdatalogin();
                            Handler handler2 = new Handler();
                            handler2.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            }, 500);
                            break;
                        case "Amil":
                            getdatalogin();
                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(getApplicationContext(), HomeAmil.class));
                                    finish();
                                }
                            },500);
                            break;
                    }
                }else {
                        Toast.makeText(LoginActivity.this, "Nama Pengguna atau Kata Sandi Salah", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getdatalogin(){
        APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
        Call<ResponseData> datalogin = ardData.ardgetDataLogin(etusername.getText().toString());
        datalogin.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
               getuser = response.body().getDatalogin();
               String sIduser = getuser.get(0).getIduser();
               String sUsername = getuser.get(0).getUsername();
               String sfullname = getuser.get(0).getNama();
               String stelepon = getuser.get(0).getNomortelepon();
               String sAlamat = getuser.get(0).getAlamat();
               String sStat = getuser.get(0).getStatus();
                // menerima sesi
                sessionManager.setLogin(true);
                // menyimpan data
                sessionManager.setDataUser(sIduser, sUsername, sfullname, stelepon, sAlamat,sStat);
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}