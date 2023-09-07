package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.User;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,mobile,password,address_line1,city,state,postal_code,country,cpassword;
    Button btnRegister;
    TextView alreadyhaveanaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        onClickRegister();
    }

    private void onClickRegister() {
        alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = validateUser();
                if (user != null){
                    RetrofitClient.getInstance().getApi().registerUser(user).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                                Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private User validateUser() {
        String pass = password.getText().toString();
        String cpass = cpassword.getText().toString();

        if (pass.equals(cpass)){
            User user = new User();

            user.setName(name.getText().toString());
            user.setEmail(email.getText().toString());
            user.setMobile(mobile.getText().toString());
            user.setPassword(password.getText().toString());
            user.setAddress_line1(address_line1.getText().toString());
            user.setCity(city.getText().toString());
            user.setState(state.getText().toString());
            user.setPostal_code(postal_code.getText().toString());
            user.setCountry(country.getText().toString());

            return user;
        }
        else {
            Toast.makeText(this, "Password Mis-match", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public void init(){
        name=findViewById(R.id.edtName);
        email=findViewById(R.id.edtEmail);
        mobile=findViewById(R.id.edtMobile);
        password=findViewById(R.id.edtPassword);
        address_line1=findViewById(R.id.edtAddressLine1);
        city=findViewById(R.id.edtCity);
        state=findViewById(R.id.edtState);
        postal_code=findViewById(R.id.edtPostalCode);
        country=findViewById(R.id.edtCountry);
        cpassword=findViewById(R.id.edtConfirmPassword);
        alreadyhaveanaccount=findViewById(R.id.alreadyhaveanaccount);

        btnRegister=findViewById(R.id.btnRegister);
    }
}