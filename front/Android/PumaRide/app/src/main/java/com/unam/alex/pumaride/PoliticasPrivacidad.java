package com.unam.alex.pumaride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PoliticasPrivacidad extends AppCompatActivity {
    @BindView(R.id.activity_politicas_tv_politicas_privacidad)
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politicas_privacidad);
        ButterKnife.bind(this);

        tv.setMovementMethod(new ScrollingMovementMethod());
    }
}
