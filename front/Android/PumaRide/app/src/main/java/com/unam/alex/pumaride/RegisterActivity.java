package com.unam.alex.pumaride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.activity_register_btn_recovery)
    Button recovery;
    @BindView(R.id.activity_register_btn_cancel)
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.activity_register_btn_recovery)
    public void recovery(View v){
        finish();
    }
    @OnClick(R.id.activity_register_btn_cancel)
    public void cancel(View v){
        finish();
    }
}
