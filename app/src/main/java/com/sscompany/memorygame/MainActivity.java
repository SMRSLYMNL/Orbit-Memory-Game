package com.sscompany.memorygame;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bitmap bitmap;

        Button fruits = findViewById(R.id.fruits);
        Button animals = findViewById(R.id.animals);
        Button carLogos = findViewById(R.id.car_logos);
        Button flags = findViewById(R.id.flags);


        //Getting Height of Some Components

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        int actionBarHeight = 0;
        final TypedArray styledAttributes = MainActivity.this.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize }
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        int navigationBarHeight = 0;
        Resources resources = getApplicationContext().getResources();
        resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }


        //Getting Screen Size

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels - actionBarHeight - statusBarHeight - navigationBarHeight;
        int width = displayMetrics.widthPixels;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width * 92 / 100, height / 5);
        lp.setMargins(height / 25, height / 25, height / 25, height / 25);

        fruits.setMaxWidth(width * 92 / 100);
        fruits.setMinimumWidth(width * 92 / 100);
        fruits.setMaxHeight(height / 5);
        fruits.setMinimumHeight(height / 5);

        fruits.setLayoutParams(lp);

        animals.setMaxWidth(width * 92 / 100);
        animals.setMinimumWidth(width * 92 / 100);
        animals.setMaxHeight(height / 5);
        animals.setMinimumHeight(height / 5);

        animals.setLayoutParams(lp);

        carLogos.setMaxWidth(width * 92 / 100);
        carLogos.setMinimumWidth(width * 92 / 100);
        carLogos.setMaxHeight(height / 5);
        carLogos.setMinimumHeight(height / 5);

        carLogos.setLayoutParams(lp);

        flags.setMaxWidth(width * 92 / 100);
        flags.setMinimumWidth(width * 92 / 100);
        flags.setMaxHeight(height / 5);
        flags.setMinimumHeight(height / 5);

        flags.setLayoutParams(lp);

    }

    public void fruits(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Levels.class);

        intent.putExtra("type", "food");

        startActivity(intent);
    }

    public void animal(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Levels.class);
        intent.putExtra("type", "animal");

        startActivity(intent);
    }

    public void flag(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Levels.class);
        intent.putExtra("type", "flag");

        startActivity(intent);
    }

    public void car_logo(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Levels.class);
        intent.putExtra("type", "car_logo");

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}