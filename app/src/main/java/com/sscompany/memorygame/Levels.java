package com.sscompany.memorygame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import static android.view.Gravity.CENTER;

public class Levels extends AppCompatActivity
{

    private Context mContext;
    private int width;
    private int height;

    private ArrayList<ImageView> levels;

    private SharedPreferences levelData;
    private SharedPreferences.Editor levelDataEditor;

    private GridLayout gridNoBomb;
    private GridLayout gridOneBomb;
    private GridLayout gridTwoBombs;

    private String type;
    private String levelDataKey;

    private final ArrayList<String> defaultLevelArray = new ArrayList<String>(Arrays.asList(
            "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "0", "0", "0", "0", "0", "0"));

    private ArrayList<String> levelArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);

        mContext = Levels.this;
        getScreenSizes();
        levels = new ArrayList<>();

        //Getting intentExtras
        type = getIntent().getStringExtra("type");

        //Shared Preferences
        levelDataKey = type + "_availability";

        levelData = mContext.getSharedPreferences(levelDataKey, 0);
        levelDataEditor = levelData.edit();

        //Initializing levelArray
        levelArray = getLevels(levelDataKey);

        ScrollView scrollView = findViewById(R.id.scroll_view);

        LinearLayout.LayoutParams lpForGridLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpForGridLayout.setMargins(width / 100 * 7, width / 100 * 7, width / 100 * 7, width / 100 * 7);

        gridNoBomb = new GridLayout(mContext);
        gridNoBomb.setRowCount(5);
        gridNoBomb.setColumnCount(3);

        gridNoBomb.setLayoutParams(lpForGridLayout);

        int sizeOfLevel = width / 100 * 68 / 3;
        int marginOfLevel = width / 100 * 3;

        for(int i = 0; i < 33; i++)
        {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sizeOfLevel, sizeOfLevel);
            lp.setMargins(marginOfLevel, marginOfLevel, marginOfLevel, marginOfLevel);

            ImageView level = new ImageView(mContext);

            if(i < 15)
            {
                if(levelArray.get(i).equals("0"))
                    level.setImageResource(R.drawable.locked_level);

                else if(levelArray.get(i).equals("1"))
                    level.setImageResource(R.drawable.new_level);

                else if(levelArray.get(i).equals("2"))
                    level.setImageResource(R.drawable.completed_level);
            }
            else if(i < 24)
            {
                if(levelArray.get(i).equals("0"))
                    level.setImageResource(R.drawable.locked_level_2);

                else if(levelArray.get(i).equals("1"))
                    level.setImageResource(R.drawable.new_level_2);

                else if(levelArray.get(i).equals("2"))
                    level.setImageResource(R.drawable.completed_level_2);
            }
            else
            {
                if(levelArray.get(i).equals("0"))
                    level.setImageResource(R.drawable.locked_level_3);

                else if(levelArray.get(i).equals("1"))
                    level.setImageResource(R.drawable.new_level_3);

                else if(levelArray.get(i).equals("2"))
                    level.setImageResource(R.drawable.completed_level_3);
            }

            level.setLayoutParams(lp);

            level.setMinimumWidth(sizeOfLevel);
            level.setMinimumHeight(sizeOfLevel);
            level.setMaxWidth(sizeOfLevel);
            level.setMaxHeight(sizeOfLevel);

            level.setOnClickListener(clickListener);

            levels.add(level);

            gridNoBomb.addView(level);
        }

//        gridOneBomb = new GridLayout(mContext);
//        gridOneBomb.setRowCount(3);
//        gridOneBomb.setColumnCount(3);
//
//        gridOneBomb.setLayoutParams(lpForGridLayout);
//
//        for(int i = 15; i < 24; i++)
//        {
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sizeOfLevel, sizeOfLevel);
//            lp.setMargins(marginOfLevel, marginOfLevel, marginOfLevel, marginOfLevel);
//
//            ImageView level = new ImageView(mContext);
//
//            if(levelArray.get(i).equals("0"))
//                level.setImageResource(R.drawable.locked_level_2);
//
//            else if(levelArray.get(i).equals("1"))
//                level.setImageResource(R.drawable.new_level_2);
//
//            else if(levelArray.get(i).equals("2"))
//                level.setImageResource(R.drawable.completed_level_2);
//
//            level.setLayoutParams(lp);
//
//            level.setMinimumWidth(sizeOfLevel);
//            level.setMinimumHeight(sizeOfLevel);
//            level.setMaxWidth(sizeOfLevel);
//            level.setMaxHeight(sizeOfLevel);
//
//            level.setOnClickListener(clickListenerOneBomb);
//
//            levels.add(level);
//
//            gridOneBomb.addView(level);
//        }
//
//        gridTwoBombs = new GridLayout(mContext);
//        gridTwoBombs.setRowCount(3);
//        gridTwoBombs.setColumnCount(3);
//
//        gridTwoBombs.setLayoutParams(lpForGridLayout);
//
//        for(int i = 24; i < 33; i++)
//        {
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sizeOfLevel, sizeOfLevel);
//            lp.setMargins(marginOfLevel, marginOfLevel, marginOfLevel, marginOfLevel);
//
//            ImageView level = new ImageView(mContext);
//
//            if(levelArray.get(i).equals("0"))
//                level.setImageResource(R.drawable.locked_level_3);
//
//            else if(levelArray.get(i).equals("1"))
//                level.setImageResource(R.drawable.new_level_3);
//
//            else if(levelArray.get(i).equals("2"))
//                level.setImageResource(R.drawable.completed_level_3);
//
//            level.setLayoutParams(lp);
//
//            level.setMinimumWidth(sizeOfLevel);
//            level.setMinimumHeight(sizeOfLevel);
//            level.setMaxWidth(sizeOfLevel);
//            level.setMaxHeight(sizeOfLevel);
//
//            level.setOnClickListener(clickListenerTwoBombs);
//
//            levels.add(level);
//
//            gridTwoBombs.addView(level);
//        }


        LinearLayout linearLayoutForGrids = new LinearLayout(mContext);
        linearLayoutForGrids.setOrientation(LinearLayout.VERTICAL);

        //HEADER NO BOMB
        linearLayoutForGrids.addView(gridNoBomb);
//        //HEADER ONE BOMB
//        linearLayoutForGrids.addView(gridOneBomb);
//        //HEADER TWO BOMBS
//        linearLayoutForGrids.addView(gridTwoBombs);

        scrollView.addView(linearLayoutForGrids);
        //scrollView.addView(gridNoBomb);

    }

    private void getScreenSizes()
    {
        //Getting Height of Some Components

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        int actionBarHeight = 0;
        final TypedArray styledAttributes = mContext.getTheme().obtainStyledAttributes(
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


        //Getting Usable Screen Size

        height = displayMetrics.heightPixels - actionBarHeight - statusBarHeight - navigationBarHeight;
        width = displayMetrics.widthPixels;
    }


    public void saveLevels(ArrayList<String> list, String key){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        levelDataEditor.putString("type", json);
        levelDataEditor.apply();
    }

    public ArrayList<String> getLevels(String key){
        Gson gson = new Gson();
        String defaultList = new Gson().toJson(defaultLevelArray);
        String json = levelData.getString(key, defaultList);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("Index : " + gridNoBomb.indexOfChild(v));
        }
    };

    View.OnClickListener clickListenerOneBomb = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("Index : " + gridOneBomb.indexOfChild(v));
        }
    };

    View.OnClickListener clickListenerTwoBombs = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("Index : " + gridTwoBombs.indexOfChild(v));
        }
    };
}
