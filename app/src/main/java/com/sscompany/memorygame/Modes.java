package com.sscompany.memorygame;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class Modes extends AppCompatActivity
{

    private static final String TAG = "Modes";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modes);

        Button b2to3 = findViewById(R.id.b2to3);
        Button b4to3 = findViewById(R.id.b4to3);
        Button b4to4 = findViewById(R.id.b4to4);
        Button b5to4 = findViewById(R.id.b5to4);
        Button b6to4 = findViewById(R.id.b6to4);
        Button b3to3 = findViewById(R.id.b3to3);
        Button b3to5 = findViewById(R.id.b3to5);
        Button b5to5 = findViewById(R.id.b5to5);


        //Getting Height of Some Components

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        int actionBarHeight = 0;
        final TypedArray styledAttributes = Modes.this.getTheme().obtainStyledAttributes(
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


        b2to3.setMaxWidth(width * 40 / 100);
        b2to3.setMinimumWidth(width * 40 / 100);
        b2to3.setMaxHeight(height / 9);
        b2to3.setMinimumHeight(height / 9);

        b4to3.setMaxWidth(width * 40 / 100);
        b4to3.setMinimumWidth(width * 40 / 100);
        b4to3.setMaxHeight(height / 9);
        b4to3.setMinimumHeight(height / 9);

        b4to4.setMaxWidth(width * 40 / 100);
        b4to4.setMinimumWidth(width * 40 / 100);
        b4to4.setMaxHeight(height / 9);
        b4to4.setMinimumHeight(height / 9);

        b5to4.setMaxWidth(width * 40 / 100);
        b5to4.setMinimumWidth(width * 40 / 100);
        b5to4.setMaxHeight(height / 9);
        b5to4.setMinimumHeight(height / 9);

        b6to4.setMaxWidth(width * 40 / 100);
        b6to4.setMinimumWidth(width * 40 / 100);
        b6to4.setMaxHeight(height / 9);
        b6to4.setMinimumHeight(height / 9);

        b3to3.setMaxWidth(width * 40 / 100);
        b3to3.setMinimumWidth(width * 40 / 100);
        b3to3.setMaxHeight(height / 9);
        b3to3.setMinimumHeight(height / 9);

        b3to5.setMaxWidth(width * 40 / 100);
        b3to5.setMinimumWidth(width * 40 / 100);
        b3to5.setMaxHeight(height / 9);
        b3to5.setMinimumHeight(height / 9);

        b5to5.setMaxWidth(width * 40 / 100);
        b5to5.setMinimumWidth(width * 40 / 100);
        b5to5.setMaxHeight(height / 9);
        b5to5.setMinimumHeight(height / 9);



    }

    public void back(View view)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void mode2to3(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("row", 3);
        intent.putExtra("column", 2);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    public void mode4to3(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("column", 3);
        intent.putExtra("row", 4);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    public void mode4to4(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("column", 4);
        intent.putExtra("row", 4);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    public void mode5to4(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("column", 4);
        intent.putExtra("row", 5);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    public void mode6to4(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("column", 4);
        intent.putExtra("row", 6);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    public void mode3to3(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("column", 3);
        intent.putExtra("row", 3);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    public void mode3to5(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("column", 3);
        intent.putExtra("row", 5);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    public void mode5to5(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("column", 5);
        intent.putExtra("row", 5);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    public void mode7to4(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("column", 4);
        intent.putExtra("row", 7);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    public void mode6to5(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("column", 5);
        intent.putExtra("row", 6);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    public void mode6to6(View view)
    {
        Intent intent = new Intent(getApplicationContext(), InGame.class);

        intent.putExtra("column", 6);
        intent.putExtra("row", 6);

        intent.putExtra("type", getIntent().getStringExtra("type"));

        startActivity(intent);
        finish();
    }

    /*


    public void mode8to5(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_8_to_5.class);
        startActivity(intent);
        finish();
    }

    public void mode7to6(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_7_to_6.class);
        startActivity(intent);
        finish();
    }

    public void mode8to6(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Mode_8_to_6.class);
        startActivity(intent);
        finish();
    }

    */
}