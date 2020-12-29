package com.thirafikaz.jakfood;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.thirafikaz.jakfood.Activity.LoginActivity;
import com.thirafikaz.jakfood.Model.ResponseMakanan;
import com.thirafikaz.jakfood.Network.ConfigRetrofit;
import com.thirafikaz.jakfood.helper.MyFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends MyFunction {

    @BindView(R.id.regis_name)
    TextInputEditText regisName;
    @BindView(R.id.regis_alamat)
    TextInputEditText regisAlamat;
    @BindView(R.id.regis_no_tlp)
    TextInputEditText regisNoTlp;
    @BindView(R.id.spin_kelamin)
    Spinner spinKelamin;
    @BindView(R.id.regis_username)
    TextInputEditText regisUsername;
    @BindView(R.id.regis_pass)
    TextInputEditText regisPass;
    @BindView(R.id.regis_confir_pass)
    TextInputEditText regisConfirPass;
    @BindView(R.id.rg_user_admin)
    RadioButton rgUserAdmin;
    @BindView(R.id.rg_user_biasa)
    RadioButton rgUserBiasa;
    @BindView(R.id.sign_up)
    Button signUp;
    @BindView(R.id.login)
    TextView login;

    String jeniskelamin[] = {"Laki - Laki", "Perempuan"};
    String nama, alamat, noTelp,  username, password, conPassword, jenKel, levelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setStartUpView();
    }

    private void setStartUpView() {
        // Setup  Radio Button
        if (rgUserAdmin.isChecked()) levelUser = "Admin";
        else levelUser = "User Biasa";

        // Setup Spinner Jenis  Kelamin
        ArrayAdapter adapter  = new ArrayAdapter(this, android.R.layout.simple_spinner_item, jeniskelamin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKelamin.setAdapter(adapter);
        spinKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenKel = jeniskelamin[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Something To Do
            }
        });

    }

    @OnClick({R.id.rg_user_admin, R.id.rg_user_biasa, R.id.sign_up, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rg_user_admin:
                levelUser = "Admin";
                break;
            case R.id.rg_user_biasa:
                levelUser = "User Biasa";
                break;
            case R.id.sign_up:
                register();
                break;
            case R.id.login:
                break;
        }
    }

    private void register() {
        nama = regisName.getText().toString().trim();
        alamat = regisAlamat.getText().toString().trim();
        noTelp = regisNoTlp.getText().toString().trim();
        username = regisUsername.getText().toString().trim();
        password = regisPass.getText().toString().trim();
        conPassword = regisConfirPass.getText().toString().trim();

        if (TextUtils.isEmpty(nama)) {
            regisName.setError(getString(R.string.isEmpyField));
        } else if (TextUtils.isEmpty(alamat)) {
            regisAlamat.setError(getString(R.string.isEmpyField));
        } else if (TextUtils.isEmpty(noTelp)) {
            regisNoTlp.setError(getString(R.string.isEmpyField));
        } else if (TextUtils.isEmpty(username)) {
            regisUsername.setError(getString(R.string.isEmpyField));
        } else if (TextUtils.isEmpty(password)) {
            regisPass.setError(getString(R.string.isEmpyField));
        } else if (password.length() <6) {
            regisPass.setError(getString(R.string.minimum));
        } else if (TextUtils.isEmpty(conPassword)) {
            regisConfirPass.setError(getString(R.string.isEmpyField));
        } else if (!password.equals(conPassword))  {
            regisConfirPass.setError(getString(R.string.length));
        } else {
            fetchRegister();
        }
    }

    private void fetchRegister() {
        showProgressDialog("Fetch Data");
        ConfigRetrofit.getInstance().requestRegister(
                nama,alamat,jenKel,noTelp,username,levelUser,password
        ).enqueue(new Callback<com.thirafikaz.jakfood.Model.Response>() {
            @Override
            public void onResponse(Call<com.thirafikaz.jakfood.Model.Response> call, Response<com.thirafikaz.jakfood.Model.Response> response) {
                if (response.isSuccessful()) {
                    String result = response.body().getResult();
                    String msg = response.body().getMsg();

                    if (result.equals("1")) {
                        intent(LoginActivity.class);
                        finish();
                        shortToast(msg + "Silahkan Login");
                    }
                }
            }

            @Override
            public void onFailure(Call<com.thirafikaz.jakfood.Model.Response> call, Throwable t) {
                longToast(t.getMessage());
            }
        });
    }
}
