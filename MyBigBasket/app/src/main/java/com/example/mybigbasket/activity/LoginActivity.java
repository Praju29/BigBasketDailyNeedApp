package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.User;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button btnLogin,btnCancle;
    CheckBox ckbxRememberMe;
    TextView createaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        onClickLogin();
    }

    private void onClickLogin() {
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());

                //to keep user login
                if (ckbxRememberMe.isChecked())
                    getSharedPreferences("bb", MODE_PRIVATE)
                            .edit()
                            .putBoolean("login_status", true)
                            .apply();

                RetrofitClient.getInstance().getApi().loginUser(user).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
                        JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                        if (array.size() != 0) {
                            JsonObject object = array.get(0).getAsJsonObject();
                            getSharedPreferences("bb", MODE_PRIVATE).edit().putInt("uid", object.get("id").getAsInt()).apply();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void init(){
        email=findViewById(R.id.edtEmail);
        password=findViewById(R.id.edtPassword);
        ckbxRememberMe=findViewById(R.id.ckbxRememberMe);
        createaccount=findViewById(R.id.createaccount);

        btnLogin=findViewById(R.id.btnLogin);
        btnCancle=findViewById(R.id.btnCancle);
    }
}