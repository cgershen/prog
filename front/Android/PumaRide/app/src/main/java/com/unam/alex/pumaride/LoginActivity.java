package com.unam.alex.pumaride;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.unam.alex.pumaride.models.User;
import com.unam.alex.pumaride.models.WebServiceError;
import com.unam.alex.pumaride.retrofit.WebServices;
import com.unam.alex.pumaride.services.MessageService;
import com.unam.alex.pumaride.utils.Statics;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity  implements Validator.ValidationListener {
    @BindView(R.id.activity_login_btn_login)
    LinearLayout login;
    @BindView(R.id.activity_login_recovery)
    Button recovery;
    @NotEmpty
    @Email(message = "Correo no valido.")
    @BindView(R.id.activity_login_tv_email)
    TextView tvEmail;
    @NotEmpty(message = "Este campo es requerido.")
    @BindView(R.id.activity_login_tv_password)
    TextView tvPassword;
    SweetAlertDialog pDialog;
    Validator validator;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activity = this;
        if(isLogged()){
            finish();

            Intent i  = new Intent(this,MainTabActivity.class);
            startActivity(i);
        }
        validator = new Validator(this);
        validator.setValidationListener(this);
        //login.callOnClick();

    }
    @OnClick(R.id.activity_login_register)
    public void register(View v){
        Intent i  = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.activity_login_recovery)
    public void recovery(View v){
        Intent i  = new Intent(this,ResetPasswordActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.activity_login_btn_login)
    public void login(View view) {
        validator.validate();

    }
    public void loginInServer(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        WebServices webServices = retrofit.create(WebServices.class);

        Call<User> call = webServices.loginUser(tvEmail.getText().toString(),tvPassword.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Call<User> c = call;
                if(response.isSuccessful()) {
                    User u = response.body();
                    SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token", u.getToken());
                    editor.putString("email", tvEmail.getText().toString());
                    editor.putString("password", tvPassword.getText().toString());
                    editor.putString("first_name", u.getFirst_name());
                    editor.putString("last_name", u.getLast_name());
                    editor.commit();
                    pDialog.dismissWithAnimation();
                    finish();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }else{
                    pDialog.dismissWithAnimation();
                    try {
                        WebServiceError wse = new Gson().fromJson(response.errorBody().string(),WebServiceError.class);
                        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Error!")
                                .setContentText(wse.getDetail())
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText("¡Hubo un error de conexión!")
                        .show();
            }
        });
    }
    public boolean isLogged(){
        SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
        String token = sp.getString("token", "");
        return (token.equals(""))? false: true;
    }
    @Override
    public void onValidationSucceeded() {
        loginInServer();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

}
