package com.thirafikaz.jakfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.thirafikaz.jakfood.MainActivity;
import com.thirafikaz.jakfood.Model.Response;
import com.thirafikaz.jakfood.Network.ConfigRetrofit;
import com.thirafikaz.jakfood.R;
import com.thirafikaz.jakfood.RegisterActivity;
import com.thirafikaz.jakfood.helper.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends SessionManager {

    @BindView(R.id.login_username)
    TextInputEditText loginUsername;
    @BindView(R.id.login_password)
    TextInputEditText loginPassword;
    @BindView(R.id.rg_user_admin_sign)
    RadioButton rgUserAdminSign;
    @BindView(R.id.rg_user_biasa_sign)
    RadioButton rgUserBiasaSign;
    @BindView(R.id.sign_in)
    Button signIn;
    @BindView(R.id.register)
    TextView register;

    String username, password, levelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);

        setUpStartView();
    }

    private void setUpStartView() {
        if (rgUserAdminSign.isChecked()) levelUser = "Admin" ;
        else levelUser = "User Biasa";
    }

    @OnClick({R.id.rg_user_admin_sign, R.id.rg_user_biasa_sign, R.id.sign_in, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rg_user_admin_sign:
                levelUser = "Admin";
                break;
            case R.id.rg_user_biasa_sign:
                levelUser = "User Biasa";
                break;
            case R.id.sign_in:
                login();
                break;
            case R.id.register:
                intent(RegisterActivity.class);
                break;
        }
    }

    private void login() {
        username = loginUsername.getText().toString().trim();
        password = loginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            loginUsername.getText().toString().trim();
        } else if (TextUtils.isEmpty(password)) {
            loginPassword.setError(getString(R.string.isEmpyField));
        } else {
            fetchLogin();
        }
    }

    private void fetchLogin() {
        showProgressDialog("Login Process...");
        ConfigRetrofit.getInstance().requestLogin(username, password, levelUser)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            String result  = response.body().getResult();
                            String message = response.body().getMsg();
                            String iduser = response.body().getUser().getIdUser();

                            if (result.equals("1")){
                                sessionManager.createSession(username);
                                sessionManager.setIdUser(iduser);
                                intent(MainActivity.class);
                                finish();
                            } else {
                                shortToast(message);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        longToast(t.getMessage());
                    }
                });
    }
}
