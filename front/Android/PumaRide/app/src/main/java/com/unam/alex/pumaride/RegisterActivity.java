package com.unam.alex.pumaride;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.unam.alex.pumaride.models.User;
import com.unam.alex.pumaride.retrofit.WebServices;
import com.unam.alex.pumaride.utils.Statics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.activity_register_btn_recovery)
    Button recovery;
    @BindView(R.id.activity_register_btn_register)
    TextView register;
    @BindView(R.id.activity_register_edt_password)
    EditText etPassword;
    @BindView(R.id.activity_register_edt_password2)
    EditText etPassword2;
    @BindView(R.id.activity_register_edt_email)
    EditText etEmail;
    @BindView(R.id.activity_register_edt_name)
    EditText etName;
    @BindView(R.id.activity_register_edt_lastname)
    EditText etLastname;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        activity = this;

    }
    @OnClick(R.id.activity_register_btn_register)
    public void register(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebServices webServices = retrofit.create(WebServices.class);
        User u = new User();
        u.setId(0);
        u.setNombre(etName.getText().toString());
        u.setApellido(etLastname.getText().toString());
        u.setPassword(etPassword.getText().toString());
        u.setEmail(etEmail.getText().toString());
        Call<User> call = webServices.createUser(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Registro exitoso!")
                        .setContentText("En un momento recibirás un correo de confirmación")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                            }
                        })
                        .show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Error!")
                        .setContentText("Hubo un error al registrar el usuario")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
    }
    @OnClick(R.id.activity_register_btn_recovery)
    public void recovery(View v){
        finish();
    }
    public void politicasDePrivacidad(View v)
    {
        Intent i = new Intent(this, PoliticasPrivacidad.class);
        startActivity(i);
    }
}
