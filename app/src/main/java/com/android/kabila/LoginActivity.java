package com.android.kabila;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private TextView loginOrRegister, registerOrLogin;
    private LinearLayout loginView, registerView;
    private ProgressBar loading;
    private boolean login = true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginOrRegister = findViewById(R.id.loginOrRegister);
        registerOrLogin = findViewById(R.id.registerOrLogin);
        loginView = findViewById(R.id.login_login);
        registerView = findViewById(R.id.login_register);
        loading = findViewById(R.id.login_loading);

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("KabilaPreferences", MODE_PRIVATE);

        loginOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                loginView.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                registerView.setVisibility(View.VISIBLE);
                login = !login;
                //Toast.makeText(getApplicationContext(), "hi"+login, Toast.LENGTH_SHORT).show();
            }
        });

        registerOrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                registerView.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                loginView.setVisibility(View.VISIBLE);
                login = !login;
            }
        });

        if(login) {
            final TextView uname = findViewById(R.id.login_uname);
            final TextView password = findViewById(R.id.login_password);
            final Button loginBtn = findViewById(R.id.login_loginBtn);
            final String storedEmail = sharedPreferences.getString("email", "NOUSERSTORED");
            final String storedMobile = sharedPreferences.getString("mobile", "NOVAL");

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //IF NO USER IS REGISTERED
                    if (storedEmail.equals("NOUSERSTORED")) {
                        uname.setText("");
                        password.setText("");
                        loading.setVisibility(View.VISIBLE);
                        loginView.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                        registerView.setVisibility(View.VISIBLE);
                        login = !login;
                        Toast.makeText(getApplicationContext(), "Please Register!", Toast.LENGTH_LONG).show();
                    }
                    //End of above commented code
                    else {
                        String name_entered = uname.getText().toString();
                        String password_entered = password.getText().toString();
                        String password_stored = sharedPreferences.getString("password", "NOPASSWORD");
                        if ((name_entered.equals(storedEmail) || name_entered.equals(storedMobile)) && password_entered.equals(password_stored)) {
                            sharedPreferences.edit().putBoolean("isLoggedIn", true);
                        } else {
                            uname.setText("");
                            password.setText("");
                            Snackbar.make(findViewById(R.id.loginpage), "Wrong Details! Try Again", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
            final TextView email = findViewById(R.id.register_email);
            final TextView mobile = findViewById(R.id.register_mobile);
            final TextView password = findViewById(R.id.register_password);
            final TextView rpassword = findViewById(R.id.register_repeat_password);
            final Button registerBtn = findViewById(R.id.registerBtn);

            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                    String errors = "Please fill in ";
                    String email_entered = email.getText().toString();
                    if(email_entered.equals(""))    errors+="Email ";
                    String mobile_entered = mobile.getText().toString();
                    if(mobile_entered.equals(""))   errors+="Mobile ";
                    String pass = password.getText().toString();
                    if(pass.equals("")) errors+="Password fields";
                    String rpass = rpassword.getText().toString();
                    if(rpass.equals(""))    errors+="Re-Enter Password ";
                    if(errors.length()>15) {
                        Toast.makeText(getApplicationContext(), errors+"fields.", Toast.LENGTH_LONG).show();
                    } else if(rpass.equals(pass)) {
                        Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_SHORT).show();
                        sharedPreferences.edit().putString("email", email_entered).commit();
                        sharedPreferences.edit().putString("mobile", mobile_entered).commit();
                        sharedPreferences.edit().putString("password", pass).commit();
                        sharedPreferences.edit().putBoolean("isLoggedIn", true).commit();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
