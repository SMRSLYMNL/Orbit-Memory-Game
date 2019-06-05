package com.sscompany.memorygame;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class TarzanModes extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarzan_modes);


    }

    public void mode2to3(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_2_to_3.class);
        startActivity(intent);
    }

    public void mode4to3(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_4_to_3.class);
        startActivity(intent);
    }

    public void mode4to4(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_4_to_4.class);
        startActivity(intent);
    }

    public void mode5to4(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_5_to_4.class);
        startActivity(intent);
    }

    public void mode6to4(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_6_to_4.class);
        startActivity(intent);
    }

    public void mode7to4(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_7_to_4.class);
        startActivity(intent);
    }

    public void mode6to5(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_6_to_5.class);
        startActivity(intent);
    }

    public void mode6to6(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_6_to_6.class);
        startActivity(intent);
    }

    public void mode8to5(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_8_to_5.class);
        startActivity(intent);
    }

    public void mode7to6(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_7_to_6.class);
        startActivity(intent);
    }

    public void mode8to6(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_8_to_6.class);
        startActivity(intent);
    }

}