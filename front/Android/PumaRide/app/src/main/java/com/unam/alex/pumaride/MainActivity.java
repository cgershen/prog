package com.unam.alex.pumaride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }
    public void register(View v){
        Intent i  = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
    public void password(View v){
        Intent i  = new Intent(this,PassRecoveryActivity.class);
        startActivity(i);
    }
    public void mapa(View v){
        Intent i  = new Intent(this,MapActivity.class);
        startActivity(i);
    }
}
