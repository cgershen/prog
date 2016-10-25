package com.unam.alex.pumaride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.activity_login_login)
    LinearLayout login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    public void register(View v){
        Intent i  = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
    public void password(View v){
        Intent i  = new Intent(this,PassRecoveryActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.activity_login_login)
    public void login(View view) {
        Intent i  = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
