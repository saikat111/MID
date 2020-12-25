package com.codingburg.mid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codingburg.mid.R;

public class SplashScreenctivity extends AppCompatActivity {
    Animation top_anumation, buttom_animation, middel_animation;
    View first, secound, third, four, five, six, seven;
    TextView name, tag;
    private  static int SPLASH_Time = 4000;
    ProgressBar progressBar_cyclic ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screenctivity);
        top_anumation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        buttom_animation = AnimationUtils.loadAnimation(this,R.anim.buttom_animation);
        middel_animation = AnimationUtils.loadAnimation(this,R.anim.middel_animation);
        first = findViewById(R.id.first_line);
        secound = findViewById(R.id.secound_line);
        third = findViewById(R.id.third_line);
        four = findViewById(R.id.fourth_line);
        five = findViewById(R.id.five_line);
        six = findViewById(R.id.six_line);
        seven = findViewById(R.id.seven_line);
        name = findViewById(R.id.name);
        tag = findViewById(R.id.tag);
        progressBar_cyclic = findViewById(R.id.progressBar_cyclic);

        first.setAnimation(top_anumation);
        secound.setAnimation(top_anumation);
        third.setAnimation(top_anumation);
        four.setAnimation(top_anumation);
        five.setAnimation(top_anumation);
        six.setAnimation(top_anumation);
        seven.setAnimation(top_anumation);
        name.setAnimation(middel_animation);
        progressBar_cyclic.setAnimation(middel_animation);
        tag.setAnimation(buttom_animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
                finish();
            }

        },SPLASH_Time);

    }
}