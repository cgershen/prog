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
    @BindView(R.id.activity_login_btn_login)
    LinearLayout login;
    @BindView(R.id.activity_login_recovery)
    Button recovery;
    @BindView(R.id.activity_login_register)
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.activity_login_register)
    public void register(View v){
        Intent i  = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.activity_login_recovery)
    public void recovery(View v){
        Intent i  = new Intent(this,PassRecoveryActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.activity_login_btn_login)
    public void login(View view) {
        Intent i  = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
