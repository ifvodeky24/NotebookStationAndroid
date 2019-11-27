package com.idw.project.notebookstation.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public static final String IS_LOGGED_IN ="isLoggedIn";
    public static final String ID_KONSUMEN ="id_konsumen";
    public static final String USERNAME="username";
    public static final String NAMA_LENGKAP="nama_lengkap";
    public static final String EMAIL="email";
    public static final String PASSWORD="password";
    public static final String NOMOR_HP="nomor_hp";
    public static final String ALAMAT="alamat";
    public static final String FOTO="foto";

    public Context getContext() {
        return context;
    }

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLogginSession(String id_konsumen, String username, String nama_lengkap, String email, String password, String nomor_hp, String alamat, String foto ){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(ID_KONSUMEN, id_konsumen);
        editor.putString(USERNAME, username);
        editor.putString(NAMA_LENGKAP, nama_lengkap);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.putString(NOMOR_HP, nomor_hp);
        editor.putString(ALAMAT, alamat);
        editor.putString(FOTO, foto);
        editor.commit();
    }

    public HashMap<String, String> getLoginDetail(){
        HashMap<String, String> user= new HashMap<>();
        user.put(ID_KONSUMEN, sharedPreferences.getString(ID_KONSUMEN, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(NAMA_LENGKAP, sharedPreferences.getString(NAMA_LENGKAP, ""));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        user.put(NOMOR_HP, sharedPreferences.getString(NOMOR_HP, null));
        user.put(ALAMAT, sharedPreferences.getString(ALAMAT, null));
        user.put(FOTO, sharedPreferences.getString(FOTO, null));

        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
