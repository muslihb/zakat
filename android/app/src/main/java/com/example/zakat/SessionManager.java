package com.example.zakat;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //user
    public static final String ID = "iduser";
    public static final String UN = "username";
    public static final String FN = "fn";
    public static final String TELEPON = "telepon";
    public static final String ADDRESS = "alamat";
    public static final String STATUS = "status";
    // zakat
    public static final String TZ = "tipe_zakat";
    public static final String JZ = "jenis_zakat";
    public static final String NS = "nishob";
    public static final String TLZ = "total_zakat";


    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("login", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }
    public void setLogin(boolean login){
        editor.putBoolean("Key_Login",login);
        editor.commit();
    }
    public boolean getLogin(){
        return sharedPreferences.getBoolean("Key_Login",false);
    }
    public void setDataUser(String iu, String un, String fn, String np, String alamat, String stat){
        editor.putString(ID, iu);
        editor.putString(UN, un);
        editor.putString(FN, fn);
        editor.putString(TELEPON, np);
        editor.putString(ADDRESS, alamat);
        editor.putString(STATUS, stat);
        editor.commit();
    }
    public HashMap<String, String> getDataUser(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID,null));
        user.put(UN, sharedPreferences.getString(UN,null));
        user.put(FN, sharedPreferences.getString(FN,null));
        user.put(TELEPON, sharedPreferences.getString(TELEPON,null));
        user.put(ADDRESS, sharedPreferences.getString(ADDRESS,null));
        user.put(STATUS, sharedPreferences.getString(STATUS,null));
        return user;
    }
    public void setZakat(String tz, String jz, String ns, String tlz){
        editor.putString(TZ, tz);
        editor.putString(JZ, jz);
        editor.putString(NS, ns);
        editor.putString(TLZ, tlz);
        editor.commit();
    }
    public HashMap<String, String> getZakat(){
        HashMap<String, String> user = new HashMap<>();
        user.put(TZ, sharedPreferences.getString(TZ,null));
        user.put(JZ, sharedPreferences.getString(JZ,null));
        user.put(NS, sharedPreferences.getString(NS,null));
        user.put(TLZ, sharedPreferences.getString(TLZ,null));
        return user;
    }
}
