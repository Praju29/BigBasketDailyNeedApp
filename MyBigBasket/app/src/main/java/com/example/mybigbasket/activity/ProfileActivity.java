package com.example.mybigbasket.activity;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybigbasket.R;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView textViewName,textViewEmail,textViewMobile,textViewAddress,textViewCity,textViewState,textViewPostalCode,textViewCountry;
    Button buttonEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewMobile = findViewById(R.id.textViewMobile);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewCity = findViewById(R.id.textViewCity);
        textViewState = findViewById(R.id.textViewState);
        textViewPostalCode = findViewById(R.id.textViewPostalCode);
        textViewCountry = findViewById(R.id.textViewCountry);
        buttonEditProfile = findViewById(R.id.buttonEditProfile);

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });
        //getUserDetails();
    }
    @Override
    public void onResume() {
        super.onResume();
        getUserDetails();
    }
    private void getUserDetails() {

        int id = this.getSharedPreferences("bb", Context.MODE_PRIVATE)
                .getInt("uid",0);

        RetrofitClient.getInstance().getApi().getUserById(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                    JsonObject object = response.body().getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
                    textViewName.setText(object.get("name").getAsString());
                    textViewEmail.setText(object.get("email").getAsString());
                    textViewMobile.setText(object.get("mobile").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        RetrofitClient.getInstance().getApi().getAddressByUSerId(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                    JsonObject object = response.body().getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
                    textViewAddress.setText(object.get("address_line1").getAsString()+", ");
                    textViewCity.setText(object.get("city").getAsString()+", ");
                    textViewState.setText(object.get("state").getAsString()+", ");
                    textViewPostalCode.setText(object.get("postal_code").getAsString()+", ");
                    textViewCountry.setText(object.get("country").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getSharedPreferences("bb",MODE_PRIVATE)
                .edit()
                .putBoolean("login_status",false)
                .apply();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }

}