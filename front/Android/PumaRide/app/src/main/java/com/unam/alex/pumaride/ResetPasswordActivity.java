package com.unam.alex.pumaride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.unam.alex.pumaride.models.User;
import com.unam.alex.pumaride.retrofit.WebServices;
import com.unam.alex.pumaride.utils.Statics;

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

public class ResetPasswordActivity extends AppCompatActivity implements Validator.ValidationListener{
    @BindView(R.id.activity_pass_recovery_ll_reset)
    LinearLayout btnReset;
    @Email(message = "Correo no valido.")
    @BindView(R.id.activity_pass_recovery_et_email)
    EditText etEmail;
    Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }
    @OnClick(R.id.activity_pass_recovery_ll_reset)
    public void resetpassword(View v){
        validator.validate();
    }
    public void resetPasswordServer(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebServices webServices = retrofit.create(WebServices.class);

        Call<User> call = webServices.resetPassword(etEmail.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Bien!")
                        .setContentText("En un momento recibirás un correo con instrucciones para recuperar tu contraseña")
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
                new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Error!")
                        .setContentText("Al parecer no estas registrado aún!")
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
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
        resetPasswordServer();
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
