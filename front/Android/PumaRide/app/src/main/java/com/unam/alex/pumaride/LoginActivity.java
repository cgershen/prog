package com.unam.alex.pumaride;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unam.alex.pumaride.models.User;
import com.unam.alex.pumaride.retrofit.WebServices;
import com.unam.alex.pumaride.services.MessageService;
import com.unam.alex.pumaride.utils.Statics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.activity_login_btn_login)
    LinearLayout login;
    @BindView(R.id.activity_login_recovery)
    Button recovery;
    @BindView(R.id.activity_login_tv_email)
    TextView tvEmail;
    @BindView(R.id.activity_login_tv_password)
    TextView tvPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //startService(new Intent(this, MessageService.class));
        //Intent i  = new Intent(this,PassRecoveryActivity.class);
        //startActivity(i);
        //login.callOnClick();

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebServices webServices = retrofit.create(WebServices.class);

        Call<User> call = webServices.loginUser(tvEmail.getText().toString(),tvPassword.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
            Call<User> c = call;
                User u = response.body();
                SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("token", u.getToken());
                editor.putString("email",tvEmail.getText().toString());
                editor.putString("password",tvPassword.getText().toString());
                editor.putString("nombre",tvPassword.getText().toString());
                editor.putString("apellido",tvPassword.getText().toString());
                editor.commit();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        Intent i  = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
