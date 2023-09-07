package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybigbasket.R;
import com.example.mybigbasket.entity.User;
import com.example.mybigbasket.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    EditText name,email,mobile,address_line1,city,state,postal_code,country;
    Button btnUpdateProfile;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name=findViewById(R.id.edtName);
        email=findViewById(R.id.edtEmail);
        mobile=findViewById(R.id.edtMobile);
        address_line1=findViewById(R.id.edtAddressLine1);
        city=findViewById(R.id.edtCity);
        state=findViewById(R.id.edtState);
        postal_code=findViewById(R.id.edtPostalCode);
        country=findViewById(R.id.edtCountry);

        btnUpdateProfile=findViewById(R.id.btnUpdateProfile);

        getUserDetails();
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }
    private void updateProfile() {
        int id = this.getSharedPreferences("bb", Context.MODE_PRIVATE)
                .getInt("uid", 0);

        User user = new User();
        user.setId(id);
        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setMobile(mobile.getText().toString());
        user.setAddress_line1(address_line1.getText().toString());
        user.setCity(city.getText().toString());
        user.setState(state.getText().toString());
        user.setPostal_code(postal_code.getText().toString());
        user.setCountry(country.getText().toString());

        RetrofitClient.getInstance().getApi().updateProfileHere(user).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseObject = response.body();
                    if (responseObject.get("status").getAsString().equals("success")) {
                        JsonObject data = responseObject.getAsJsonObject("data");
                        int changedRows = data.get("changedRows").getAsInt();
                        if (changedRows > 0) {
                            Toast.makeText(EditProfileActivity.this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "No changes were made", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditProfileActivity.this, "User Update Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("API Error", "Error message: " + t.getMessage());
                Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }




//    private void updateProfile() {
//        int id = this.getSharedPreferences("bb", Context.MODE_PRIVATE)
//                .getInt("uid", 0);
//
//        User user = new User();
//        user.setId(id);
//        user.setName(name.getText().toString());
//        user.setEmail(email.getText().toString());
//        user.setMobile(mobile.getText().toString());
//        user.setAddress_line1(address_line1.getText().toString());
//        user.setCity(city.getText().toString());
//        user.setState(state.getText().toString());
//        user.setPostal_code(postal_code.getText().toString());
//        user.setCountry(country.getText().toString());
//
//        RetrofitClient.getInstance().getApi().updateProfileHere(user).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                Toast.makeText(EditProfileActivity.this, "Order Cancled Successfully", Toast.LENGTH_SHORT).show();
//
////                if (response.isSuccessful() && response.body() != null) {
////                    JsonObject responseObject = response.body().getAsJsonObject();
////                    if (responseObject.has("status") && responseObject.get("status").getAsString().equals("success")) {
////                        Toast.makeText(EditProfileActivity.this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
////                    } else {
////                        Toast.makeText(EditProfileActivity.this, "User Update Failed", Toast.LENGTH_SHORT).show();
////                    }
////                }
////                else {
////                    Toast.makeText(EditProfileActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
////                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Log.e("API Error", "Error message: " + t.getMessage());
//                Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void getUserDetails() {
        int id = this.getSharedPreferences("bb", Context.MODE_PRIVATE)
                .getInt("uid",0);

        RetrofitClient.getInstance().getApi().getUserById(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                    JsonObject object = response.body().getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
                    name.setText(object.get("name").getAsString());
                    email.setText(object.get("email").getAsString());
                    mobile.setText(object.get("mobile").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        RetrofitClient.getInstance().getApi().getAddressByUSerId(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().getAsJsonObject().get("status").getAsString().equals("success")){
                    JsonObject object = response.body().getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
                    address_line1.setText(object.get("address_line1").getAsString()+", ");
                    city.setText(object.get("city").getAsString()+", ");
                    state.setText(object.get("state").getAsString()+", ");
                    postal_code.setText(object.get("postal_code").getAsString()+", ");
                    country.setText(object.get("country").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}