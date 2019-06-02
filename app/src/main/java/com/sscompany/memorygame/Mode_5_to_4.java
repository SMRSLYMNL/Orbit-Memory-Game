package com.sscompany.memorygame;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class Mode_5_to_4 extends AppCompatActivity {

    int count;
    int k;
    int opened = -1;
    final int size = 20;
    int[] found = new int[size];
    int[] positions = new int[size];
    private int countPair = 0;
    int currentPos = -1;
    ImageView childs[] = new ImageView[size];
    final int[] pictures = new int[]
            {
                    R.drawable.jane_found_1,
                    R.drawable.baby_found_1,
                    R.drawable.tarzan_found_1,
                    R.drawable.kala_found_1,
                    R.drawable.clayton_found_1,
                    R.drawable.archimedes_found_1,
                    R.drawable.kerchak_found_1,
                    R.drawable.sabor_found_1,
                    R.drawable.tantor_found_1,
                    R.drawable.trek_found_1
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_5_to_4);

        GridLayout gridLayout = findViewById(R.id.grid);

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        int actionBarHeight = 0;
        final TypedArray styledAttributes = Mode_5_to_4.this.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize }
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels - actionBarHeight - statusBarHeight;
        int width = displayMetrics.widthPixels;

        /*Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        int density  = getResources().getDisplayMetrics().densityDpi;
        int dpHeight = outMetrics.heightPixels / density;
        int dpWidth  = outMetrics.widthPixels / density;*/

        height -= dpToPx(20, Mode_5_to_4.this);
        width -= dpToPx(20, Mode_5_to_4.this);

        height -= dpToPx(20, Mode_5_to_4.this);
        width -= dpToPx(16, Mode_5_to_4.this);

        int sideOfSquare = Math.min(height / 5, width / 4);

        for(int i = 0; i < gridLayout.getChildCount(); i++)
        {
            ImageView image = (ImageView) gridLayout.getChildAt(i);
            childs[i] = image;
            image.setMaxWidth(sideOfSquare);
            image.setMinimumWidth(sideOfSquare);

            image.setMaxHeight(sideOfSquare);
            image.setMinimumHeight(sideOfSquare);
        }

        for(int i = 0; i < size; i++)
        {
            positions[i] = -1;
            found[i] = 0;
        }
        int random;
        for(int i = 0; i < size; i++)
        {
            random = (int)(Math.random() * size);
            while(positions[random] != -1)
            {
                random = (int)(Math.random() * size);
            }
            positions[random] = i / 2;
        }


    }

    public void imageClicked(final View view)
    {
        count++;
        ((ImageView)view).setClickable(false);
        if(count % 2 == 0)
        {
            for(int i = 0; i < size; i++)
            {
                childs[i].setClickable(false);
            }
        }

        for(int i = 0; i < size; i++)
        {
            final ImageView image = childs[i];
            if(image == view)
            {
                final ObjectAnimator flip = ObjectAnimator.ofFloat(image, "rotationY", 0f, 90f);
                flip.setDuration(150);
                flip.start();
                final Drawable drawable = getResources().getDrawable(pictures[positions[i]]);
                new CountDownTimer(150, 1000) {

                    public void onTick(long millisUntilFinished)
                    {

                    }

                    public void onFinish()
                    {
                        image.setImageDrawable(drawable);
                        ObjectAnimator flip1 = ObjectAnimator.ofFloat(image, "rotationY", 270f, 360f);
                        flip1.setDuration(150);
                        flip1.start();
                    }

                }.start();

                k = i;
            }
        }


        if(count % 2 == 1)
        {
            opened = k;
        }
        else if(count % 2 == 0)
        {
            if(positions[k] == positions[opened])
            {
                countPair += 2;
                ImageView image = childs[k];
                image.setClickable(false);
                ImageView image1 = childs[opened];
                image1.setClickable(false);

                slideToTop(image);
                slideToTop(image1);

                found[k] = 1;
                found[opened] = 1;

                if(countPair == size)
                {
                    Toast.makeText(Mode_5_to_4.this, "CONGRATULATIONS!!!",Toast.LENGTH_SHORT).show();

                    new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished)
                        {

                        }

                        public void onFinish()
                        {
                            Intent intent = new Intent(getApplicationContext(), TarzanModes.class);
                            startActivity(intent);
                        }

                    }.start();
                }

                for(int i = 0; i < size; i++)
                {
                    image = childs[i];
                    if(found[i] == 0)
                        image.setClickable(true);
                }
            }
            if(positions[k] != positions[opened])
            {
                ImageView imageg = childs[k];
                ImageView imageo = childs[opened];

                imageg.setClickable(false);
                imageo.setClickable(false);

                new CountDownTimer(1000, 1000) {

                    public void onTick(long millisUntilFinished)
                    {

                    }

                    public void onFinish()
                    {
                        final ImageView imagek = childs[k];

                        ObjectAnimator flip = ObjectAnimator.ofFloat(imagek, "rotationY", 0f, 90f);
                        flip.setDuration(150);
                        flip.start();

                        final ImageView image1 = childs[opened];
                        ObjectAnimator flip1 = ObjectAnimator.ofFloat(image1, "rotationY", 0f, 90f);
                        flip1.setDuration(150);
                        flip1.start();

                        new CountDownTimer(150, 1000) {

                            public void onTick(long millisUntilFinished)
                            {

                            }

                            public void onFinish()
                            {
                                Drawable drawable = getResources().getDrawable(R.drawable.tarzan_logo);
                                imagek.setImageDrawable(drawable);
                                ObjectAnimator flip1 = ObjectAnimator.ofFloat(imagek, "rotationY", 270f, 360f);
                                flip1.setDuration(150);
                                flip1.start();

                                image1.setImageDrawable(drawable);
                                ObjectAnimator flip2 = ObjectAnimator.ofFloat(image1, "rotationY", 270f, 360f);
                                flip2.setDuration(150);
                                flip2.start();

                                for(int i = 0; i < size; i++)
                                {
                                    final ImageView image = childs[i];
                                    if(found[i] == 0)
                                        image.setClickable(true);
                                }

                                new CountDownTimer(150, 1000) {

                                    public void onTick(long millisUntilFinished)
                                    {

                                    }

                                    public void onFinish()
                                    {

                                    }

                                }.start();
                            }

                        }.start();


                    }

                }.start();

            }
        }

    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void slideToTop(final View view){

        new CountDownTimer(700, 1000) {

            public void onTick(long millisUntilFinished)
            {

            }

            public void onFinish()
            {
                ObjectAnimator flip = ObjectAnimator.ofFloat(view, "rotationY", 0f, 90f);
                flip.setDuration(100);
                flip.start();

                new CountDownTimer(100, 1000) {

                    public void onTick(long millisUntilFinished)
                    {

                    }

                    public void onFinish()
                    {
                        view.setVisibility(View.INVISIBLE);
                    }

                }.start();
            }

        }.start();

    }

}
