package com.android.kabila;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class OpenScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen);
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("KabilaPreferences",MODE_PRIVATE);
        final boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLoggedIn) {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "There was a problem, please report", Toast.LENGTH_LONG).show();
                    sharedPreferences.edit().clear().commit();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        }, 1500);
    }
}
