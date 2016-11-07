package com.unam.alex.pumaride;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.unam.alex.pumaride.models.User;
import com.unam.alex.pumaride.models.WebServiceError;
import com.unam.alex.pumaride.retrofit.WebServices;
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

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {
    @BindView(R.id.activity_register_btn_register)
    TextView register;
    @Password(min = 6, scheme = Password.Scheme.ANY,message="La contraseña debe ser mayor a 6 caracteres")
    @BindView(R.id.activity_register_edt_password)
    EditText etPassword;
    @ConfirmPassword(message="Las contraseñas no coinciden.")
    @BindView(R.id.activity_register_edt_password2)
    EditText etPassword2;
    @Email(message = "Correo no valido.")
    @BindView(R.id.activity_register_edt_email)
    EditText etEmail;
    @NotEmpty(message = "Este campo es requerido.")
    @BindView(R.id.activity_register_edt_name)
    EditText etName;
    @NotEmpty(message = "Este campo es requerido.")
    @BindView(R.id.activity_register_edt_lastname)
    EditText etLastname;
    @Checked(message = "Debes estar deacuerdo con las politicas.")
    @BindView(R.id.activity_register_chk_box_policy)
    CheckBox chkPolicy;
    Activity activity;
    Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        activity = this;
        validator = new Validator(this);
        validator.setValidationListener(this);

    }
    @OnClick(R.id.activity_register_btn_register)
    public void register(View v){
        validator.validate();
    }
    @OnClick(R.id.activity_register_btn_privacy_policy)
    public void privacyPolicy(View v)
    {
        Intent i = new Intent(this, PrivacyPolicyActivity.class);
        startActivity(i);
    }
    public void registerInServer(){
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

                if(response.isSuccessful()){
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
                }else{
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
                        Toast.makeText(getApplicationContext(),response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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
    @Override
    public void onValidationSucceeded() {
        registerInServer();
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
