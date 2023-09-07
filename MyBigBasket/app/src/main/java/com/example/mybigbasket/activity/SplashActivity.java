package com.example.mybigbasket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.mybigbasket.R;

public class SplashActivity extends AppCompatActivity {

    ImageView imglogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imglogo = findViewById(R.id.imglogo);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.logo_anim);
        imglogo.setAnimation(animation);
        animation.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if(getSharedPreferences("bb",MODE_PRIVATE).getBoolean("login_status",false))
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    else
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}