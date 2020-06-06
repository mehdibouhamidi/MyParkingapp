package com.example.myparking.userlogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.Editor;

import com.example.myparking.Home;
import com.example.myparking.LoginActivity;
import com.google.firebase.database.DatabaseReference;

public class SaveSharedPreference {

    SharedPreferences sharedPreferences;


    SharedPreferences.Editor editor;

    Context context;

    int mode=0;
    public static final String Filename="sdfile";

    String Data="b";

    public SaveSharedPreference(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(Filename,mode);
        editor=sharedPreferences.edit();
    }

    public SaveSharedPreference(Context context, int mode) {
        this.context = context;
        this.mode = mode;
        sharedPreferences=context.getSharedPreferences(Filename,mode);
    }

    public void secondtime(){
        editor.putBoolean(Data,true);
        editor.commit();
    }

    public void finishtask(){
        editor.putBoolean(Data,false);
        editor.commit();
    }

    public void firsttime(){
        if (this.login()){
            Intent intent =new Intent(context, Home.class);
          //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }else {
            Intent intent =new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }

    private boolean login() {
        return sharedPreferences.getBoolean(Data,false);
    }
}
