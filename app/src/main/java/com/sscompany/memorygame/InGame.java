package com.sscompany.memorygame;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class InGame extends Activity
{
    private static long WAITING_TIME_IN_MILLIS = 3000;
    private static long START_TIME_IN_MILLIS = 30000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis;

    private TextView moves;

    private boolean gameOrNot = false;

    private ImageView pauseButton;
    private ImageView restartButton;
    private ImageView exitButton;

    private static AlertDialog alertDialog;

    private int count;
    private int k;
    private int opened = -1;
    private int countPair = 0;
    private int numberOfMoves = 0;

    private int columnCount;
    private int rowCount;
    private String type;

    private int size;
    private int sideOfSquare;
    private int marginOfSquare;
    private int bombPosition;

    private float imageRadius;


    private int[] found;
    private int[] positions;

    private ImageView childs[];

    private final int[][] allPictures = new int[][]
            {
                    {
                            R.drawable.apple,
                            R.drawable.pear,
                            R.drawable.pomegranate,
                            R.drawable.banana,
                            R.drawable.orange,
                            R.drawable.strawberry,
                            R.drawable.cherry,
                            R.drawable.kiwi,
                            R.drawable.grape,
                            R.drawable.plum,
                            R.drawable.apricot,
                            R.drawable.lime,
                            R.drawable.bomb
                    },
                    {
                            R.drawable.lion,
                            R.drawable.camel,
                            R.drawable.cow,
                            R.drawable.tiger,
                            R.drawable.bear,
                            R.drawable.elephant,
                            R.drawable.rhinoceros,
                            R.drawable.zebra,
                            R.drawable.lemur,
                            R.drawable.deer,
                            R.drawable.sheep,
                            R.drawable.wolf,
                            R.drawable.bomb
                    },
                    {
                            R.drawable.bmw,
                            R.drawable.cadillac,
                            R.drawable.lamborghini,
                            R.drawable.volkswagen,
                            R.drawable.ford,
                            R.drawable.mazda,
                            R.drawable.nissan,
                            R.drawable.ferrari,
                            R.drawable.porsche,
                            R.drawable.seat,
                            R.drawable.chevrolet,
                            R.drawable.mercedes,
                            R.drawable.bomb
                    },
                    {
                            R.drawable.azerbaijan,
                            R.drawable.turkey,
                            R.drawable.russia,
                            R.drawable.united_states,
                            R.drawable.japan,
                            R.drawable.germany,
                            R.drawable.canada,
                            R.drawable.united_kingdom,
                            R.drawable.china,
                            R.drawable.spain,
                            R.drawable.brazil,
                            R.drawable.argentina,
                            R.drawable.bomb
                    }
            };

    private int[] pictures = new int[]{};

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////MAIN FUNCTION/////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_game);

        //Getting Ancestor

        Intent ancestorIntent = getIntent();
        System.out.println(ancestorIntent + "   Ancestor Intent");

        columnCount = ancestorIntent.getIntExtra("column", 1);
        rowCount = ancestorIntent.getIntExtra("row", 1);
        type = ancestorIntent.getStringExtra("type");

        WAITING_TIME_IN_MILLIS = (long)((double)((columnCount + rowCount)/2) * 1000);
        START_TIME_IN_MILLIS = columnCount * rowCount * 3000;

        mTimeLeftInMillis = WAITING_TIME_IN_MILLIS;

        if(type.equals("food"))
            pictures = allPictures[0];
        else if(type.equals("animal"))
            pictures = allPictures[1];
        else if(type.equals("car_logo"))
            pictures = allPictures[2];
        else if(type.equals("flag"))
            pictures = allPictures[3];


        System.out.println("Row: " + rowCount + " Column: " + columnCount);

        //Initializations
        size = columnCount * rowCount;

        found = new int[size];
        positions = new int[size];

        childs = new ImageView[size];

        alertDialog = null;

        opened = -1;
        countPair = 0;
        numberOfMoves = 0;

        mTextViewCountDown = findViewById(R.id.timer);
        moves = findViewById(R.id.moves);

        gameOrNot = false;

        pauseButton = findViewById(R.id.button_pause);
        restartButton= findViewById(R.id.button_restart);
        exitButton = findViewById(R.id.button_exit);


        //Setting Clickable to Toolbar Buttons

        pauseButton.setClickable(false);
        restartButton.setClickable(false);
        exitButton.setClickable(false);


        //Getting Height of Some Components

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        int actionBarHeight = 0;
        final TypedArray styledAttributes = InGame.this.getTheme().obtainStyledAttributes(
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

        int height = displayMetrics.heightPixels - actionBarHeight - statusBarHeight - navigationBarHeight;
        int width = displayMetrics.widthPixels;


        //Getting ImageViews' Size

        marginOfSquare = width / 200;

        int heightWithoutMargins = height - (width / 100 * 6) - ((rowCount - 1) * 2 + 1) * marginOfSquare;
        int widthWithoutMargins = width - (width / 100 * 6) - ((rowCount - 1) * 2 + 1) * marginOfSquare;

        sideOfSquare = Math.min(heightWithoutMargins / rowCount, widthWithoutMargins / columnCount);

        //Initializing imageRadius
        imageRadius = sideOfSquare / 2;

        //marginOfSquare = dpToPx(marginOfSquare, this);

        //Setting Image Positions

        for(int i = 0; i < size; i++)
        {
            positions[i] = -1;
            found[i] = 0;
        }

        int random;

        if(size % 2 == 1)
        {
            countPair = 1;
            random = (int)(Math.random() * size);
            positions[random] = 12;

            bombPosition = random;

            for(int i = 0; i < size - 1; i++)
            {
                random = (int)(Math.random() * size);
                while(positions[random] != -1)
                {
                    random = (int)(Math.random() * size);
                }
                positions[random] = i / 2;
            }
        }
        else
        {
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

        //Setting GridLayout Parameters and Adding ImageViews
        GridLayout gridLayout = findViewById(R.id.grid);

        gridLayout.setColumnCount(columnCount);
        gridLayout.setRowCount(rowCount);
        gridLayout.setY((int)(actionBarHeight / 2));

        LinearLayout.LayoutParams lpForGridLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpForGridLayout.setMargins(width / 100 * 3, width / 100 * 3, width / 100 * 3, width / 100 * 3);

        for(int i = 0; i < size; i++)
        {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sideOfSquare, sideOfSquare);
            lp.setMargins(marginOfSquare, marginOfSquare, marginOfSquare, marginOfSquare);


            //Setting ImageViews
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(sideOfSquare,sideOfSquare));

            image.setLayoutParams(lp);

            image.setMaxWidth(sideOfSquare);
            image.setMinimumWidth(sideOfSquare);
            image.setMaxHeight(sideOfSquare);
            image.setMinimumHeight(sideOfSquare);


            image.setOnClickListener(imageClicked);
            image.setClickable(false);

            Bitmap batmapBitmap = BitmapFactory.decodeResource(getResources(), pictures[positions[i]]);
            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);

            circularBitmapDrawable.setCornerRadius(imageRadius);
            image.setImageDrawable(circularBitmapDrawable);

            gridLayout.addView(image);

            childs[i] = (ImageView) gridLayout.getChildAt(i);
        }


        //Setting and Starting Timer

        updateCountDownText();
        startTimer();

        if(columnCount * rowCount % 2 == 1)
        {
            pauseTimer();

            for(int i = 0; i < size; i++)
            {

                //Drawable drawable = getResources().getDrawable();

                Bitmap batmapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unfound);
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);

                circularBitmapDrawable.setCornerRadius(imageRadius);

                childs[i].setImageDrawable(circularBitmapDrawable);
            }

            if(alertDialog != null)
                alertDialog.dismiss();

            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(InGame.this, R.style.myDialog));

            builder.setTitle("Be Careful!!!")
                    .setMessage("Do Not Open Bomb Icon!!!\nIt will explode!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            for(int i = 0; i < size; i++)
                            {
                                final ObjectAnimator flip = ObjectAnimator.ofFloat(childs[i], "rotationY", 0f, 90f);
                                flip.setDuration(100);
                                flip.start();
                            }

                            new CountDownTimer(100, 1000) {

                                public void onTick(long millisUntilFinished)
                                {

                                }

                                public void onFinish()
                                {

                                    for(int i = 0; i < size; i++)
                                    {
                                        Bitmap batmapBitmap = BitmapFactory.decodeResource(getResources(), pictures[positions[i]]);
                                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);

                                        drawable.setCornerRadius(imageRadius);
                                        childs[i].setImageDrawable(drawable);

                                        final ObjectAnimator flip = ObjectAnimator.ofFloat(childs[i], "rotationY", 270f, 360f);
                                        flip.setDuration(100);
                                        flip.start();
                                    }

                                    new CountDownTimer(100, 1000) {

                                        public void onTick(long millisUntilFinished)
                                        {

                                        }

                                        public void onFinish()
                                        {
                                            startTimer();
                                        }

                                    }.start();

                                }

                            }.start();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false);

            alertDialog = builder.create();
            alertDialog.show();


        }
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public View.OnClickListener imageClicked  = new View.OnClickListener()
    {
        @Override
        public void onClick(final View view)
        {
            final ImageView bombImage = childs[bombPosition];

            if(view == bombImage && size % 2 == 1)
            {
                view.setClickable(false);
                for(int i = 0; i < size; i++)
                {
                    childs[i].setClickable(false);
                }

                final ObjectAnimator flip = ObjectAnimator.ofFloat(view, "rotationY", 0f, 90f);
                flip.setDuration(150);
                flip.start();

                new CountDownTimer(150, 1000) {

                    public void onTick(long millisUntilFinished)
                    {

                    }

                    public void onFinish()
                    {
                        Bitmap batmapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boom);
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);

                        drawable.setCornerRadius(imageRadius);
                        ((ImageView)view).setImageDrawable(drawable);

                        final ObjectAnimator flip = ObjectAnimator.ofFloat(view, "rotationY", 270f, 360f);
                        flip.setDuration(150);
                        flip.start();

                        new CountDownTimer(150, 1000) {

                            public void onTick(long millisUntilFinished)
                            {

                            }

                            public void onFinish()
                            {

                                new CountDownTimer(1000, 1000) {

                                    public void onTick(long millisUntilFinished)
                                    {

                                    }

                                    public void onFinish()
                                    {
                                        if(alertDialog != null)
                                            alertDialog.dismiss();

                                        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(InGame.this, R.style.myDialog));

                                        builder.setTitle("Game Over")
                                                .setMessage("Do you want to try again?")

                                                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which)
                                                    {
                                                        Intent intent = new Intent(getApplicationContext(), InGame.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                                        if(rowCount == 3 && columnCount == 2)
                                                        {
                                                            intent.putExtra("column", 2);
                                                            intent.putExtra("row", 3);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));
                                                        }
                                                        else if(rowCount == 4 && columnCount == 3)
                                                        {
                                                            intent.putExtra("column", 3);
                                                            intent.putExtra("row", 4);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));
                                                        }
                                                        else if(rowCount == 4 && columnCount == 4)
                                                        {
                                                            intent.putExtra("column", 4);
                                                            intent.putExtra("row", 4);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));
                                                        }
                                                        else if(rowCount == 5 && columnCount == 4)
                                                        {
                                                            intent.putExtra("column", 4);
                                                            intent.putExtra("row", 5);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));
                                                        }
                                                        else if(rowCount == 6 && columnCount == 4)
                                                        {
                                                            intent.putExtra("column", 4);
                                                            intent.putExtra("row", 6);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));
                                                        }
                                                        else if(rowCount == 3 && columnCount == 3)
                                                        {
                                                            intent.putExtra("column", 3);
                                                            intent.putExtra("row", 3);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));
                                                        }
                                                        else if(rowCount == 5 && columnCount == 3)
                                                        {
                                                            intent.putExtra("column", 3);
                                                            intent.putExtra("row", 5);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));
                                                        }
                                                        else if(rowCount == 5 && columnCount == 5)
                                                        {
                                                            intent.putExtra("column", 5);
                                                            intent.putExtra("row", 5);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));
                                                        }

                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which)
                                                    {
                                                        Intent intent = new Intent(getApplicationContext(), Modes.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                                        startActivity(intent);
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setCancelable(false);

                                        alertDialog = builder.create();
                                        alertDialog.show();
                                    }

                                }.start();
                            }

                        }.start();


                    }

                }.start();
            }

            else
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
                        Bitmap batmapBitmap = BitmapFactory.decodeResource(getResources(), pictures[positions[i]]);
                        final RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);

                        drawable.setCornerRadius(imageRadius);

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
                    numberOfMoves++;
                    moves.setText("Moves: " + numberOfMoves);

                    if(positions[k] == positions[opened])
                    {
                        countPair += 2;

                        childs[k].setClickable(false);
                        childs[opened].setClickable(false);

                        slideToTop(childs[k]);
                        slideToTop(childs[opened]);

                        found[k] = 1;
                        found[opened] = 1;

                        if(countPair == size) {
                            pauseTimer();

                            for(int i = 0; i < size; i ++)
                            {
                                childs[i].setClickable(false);
                            }

                            if(size % 2 == 1)
                            {
                                final ImageView imageBomb = childs[bombPosition];

                                ObjectAnimator flip1 = ObjectAnimator.ofFloat(imageBomb, "rotationY", 0f, 90f);
                                flip1.setDuration(150);
                                flip1.start();

                                new CountDownTimer(150, 1000)
                                {
                                    public void onTick(long millisUntilFinished)
                                    {

                                    }

                                    public void onFinish()
                                    {

                                        Bitmap batmapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
                                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);

                                        drawable.setCornerRadius(imageRadius);
                                        imageBomb.setImageDrawable(drawable);

                                        ObjectAnimator flip1 = ObjectAnimator.ofFloat(imageBomb, "rotationY", 270f, 360f);
                                        flip1.setDuration(150);
                                        flip1.start();

                                        new CountDownTimer(650, 1000)
                                        {
                                            public void onTick(long millisUntilFinished) {}

                                            public void onFinish()
                                            {

                                                if (alertDialog != null)
                                                    alertDialog.dismiss();

                                                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(InGame.this, R.style.myDialog));

                                                if (rowCount == 5 && columnCount == 5)
                                                {
                                                    builder.setTitle("CONGRATULATIONS!!!")
                                                            .setMessage("You finished the level in " + numberOfMoves + " moves!\n")
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which)
                                                                {
                                                                    Intent intent = new Intent(getApplicationContext(), Modes.class);
                                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    intent.putExtra("type", getIntent().getStringExtra("type"));

                                                                    startActivity(intent);
                                                                }
                                                            })
                                                            .setIcon(R.drawable.congratulations_icon)
                                                            .setCancelable(true);

                                                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                        @Override
                                                        public void onCancel(DialogInterface dialog)
                                                        {
                                                            Intent intent = new Intent(getApplicationContext(), Modes.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));

                                                            startActivity(intent);
                                                        }
                                                    });

                                                    alertDialog = builder.create();
                                                    alertDialog.show();
                                                }

                                                else
                                                {
                                                    builder.setTitle("CONGRATULATIONS!!!")
                                                            .setMessage("You finished the level in " + numberOfMoves + " moves!\nDo you want to continue to the next level?")
                                                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which)
                                                                {
                                                                    Intent intent = new Intent(getApplicationContext(), InGame.class);
                                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                                                    if(rowCount == 3 && columnCount == 2)
                                                                    {
                                                                        intent.putExtra("column", 3);
                                                                        intent.putExtra("row", 4);
                                                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                                                    }
                                                                    else if(rowCount == 4 && columnCount == 3)
                                                                    {
                                                                        intent.putExtra("column", 4);
                                                                        intent.putExtra("row", 4);
                                                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                                                    }
                                                                    else if(rowCount == 4 && columnCount == 4)
                                                                    {
                                                                        intent.putExtra("column", 4);
                                                                        intent.putExtra("row", 5);
                                                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                                                    }
                                                                    else if(rowCount == 5 && columnCount == 4)
                                                                    {
                                                                        intent.putExtra("column", 4);
                                                                        intent.putExtra("row", 6);
                                                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                                                    }
                                                                    else if(rowCount == 6 && columnCount == 4)
                                                                    {
                                                                        intent.putExtra("column", 3);
                                                                        intent.putExtra("row", 3);
                                                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                                                    }
                                                                    else if(rowCount == 3 && columnCount == 3)
                                                                    {
                                                                        intent.putExtra("column", 3);
                                                                        intent.putExtra("row", 5);
                                                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                                                    }
                                                                    else if(rowCount == 5 && columnCount == 3)
                                                                    {
                                                                        intent.putExtra("column", 5);
                                                                        intent.putExtra("row", 5);
                                                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                                                    }

                                                                    startActivity(intent);
                                                                }
                                                            })
                                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent intent = new Intent(getApplicationContext(), Modes.class);
                                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    intent.putExtra("type", getIntent().getStringExtra("type"));
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            })
                                                            .setIcon(R.drawable.congratulations_icon)
                                                            .setCancelable(true);

                                                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                        @Override
                                                        public void onCancel(DialogInterface dialog)
                                                        {
                                                            Intent intent = new Intent(getApplicationContext(), Modes.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));

                                                            startActivity(intent);
                                                        }
                                                    });

                                                }

                                                alertDialog = builder.create();
                                                alertDialog.show();
                                            }

                                        }.start();
                                    }
                                }.start();
                            }

                            else
                            {
                                new CountDownTimer(1000, 1000)
                                {
                                    public void onTick(long millisUntilFinished)
                                    {

                                    }

                                    public void onFinish()
                                    {
                                        if (alertDialog != null)
                                            alertDialog.dismiss();

                                        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(InGame.this, R.style.myDialog));

                                        if (rowCount == 5 && columnCount == 5)
                                        {
                                            builder.setTitle("CONGRATULATIONS!!!")
                                                    .setMessage("You finished the level in " + numberOfMoves + " moves!\n")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which)
                                                        {
                                                            Intent intent = new Intent(getApplicationContext(), Modes.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));

                                                            startActivity(intent);
                                                        }
                                                    })
                                                    .setIcon(R.drawable.congratulations_icon)
                                                    .setCancelable(false);

                                            alertDialog = builder.create();
                                            alertDialog.show();
                                        }

                                        else
                                        {
                                            builder.setTitle("CONGRATULATIONS!!!")
                                                    .setMessage("You finished the level in " + numberOfMoves + " moves!\nDo you want to continue to the next level?")
                                                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which)
                                                        {
                                                            Intent intent = new Intent(getApplicationContext(), InGame.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                                            if(rowCount == 3 && columnCount == 2)
                                                            {
                                                                intent.putExtra("column", 3);
                                                                intent.putExtra("row", 4);
                                                                intent.putExtra("type", getIntent().getStringExtra("type"));
                                                            }
                                                            else if(rowCount == 4 && columnCount == 3)
                                                            {
                                                                intent.putExtra("column", 4);
                                                                intent.putExtra("row", 4);
                                                                intent.putExtra("type", getIntent().getStringExtra("type"));
                                                            }
                                                            else if(rowCount == 4 && columnCount == 4)
                                                            {
                                                                intent.putExtra("column", 4);
                                                                intent.putExtra("row", 5);
                                                                intent.putExtra("type", getIntent().getStringExtra("type"));
                                                            }
                                                            else if(rowCount == 5 && columnCount == 4)
                                                            {
                                                                intent.putExtra("column", 4);
                                                                intent.putExtra("row", 6);
                                                                intent.putExtra("type", getIntent().getStringExtra("type"));
                                                            }
                                                            else if(rowCount == 6 && columnCount == 4)
                                                            {
                                                                intent.putExtra("column", 3);
                                                                intent.putExtra("row", 3);
                                                                intent.putExtra("type", getIntent().getStringExtra("type"));
                                                            }
                                                            else if(rowCount == 3 && columnCount == 3)
                                                            {
                                                                intent.putExtra("column", 3);
                                                                intent.putExtra("row", 5);
                                                                intent.putExtra("type", getIntent().getStringExtra("type"));
                                                            }
                                                            else if(rowCount == 5 && columnCount == 3)
                                                            {
                                                                intent.putExtra("column", 5);
                                                                intent.putExtra("row", 5);
                                                                intent.putExtra("type", getIntent().getStringExtra("type"));
                                                            }

                                                            startActivity(intent);
                                                        }
                                                    })
                                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent intent = new Intent(getApplicationContext(), Modes.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            intent.putExtra("type", getIntent().getStringExtra("type"));
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    })
                                                    .setIcon(R.drawable.congratulations_icon)
                                                    .setCancelable(false);

                                        }

                                        alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }.start();
                            }

                        }

                        for(int i = 0; i < size; i++)
                        {
                            if(found[i] == 0)
                                childs[i].setClickable(true);
                        }
                    }
                    if(positions[k] != positions[opened])
                    {
                        childs[k].setClickable(false);
                        childs[opened].setClickable(false);

                        new CountDownTimer(1000, 1000)
                        {
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

                                new CountDownTimer(150, 1000)
                                {
                                    public void onTick(long millisUntilFinished)
                                    {

                                    }

                                    public void onFinish()
                                    {
                                        //Drawable drawable = getResources().getDrawable(R.drawable.unfound);

                                        Bitmap batmapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unfound);
                                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);

                                        drawable.setCornerRadius(imageRadius);;
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
                                            if(found[i] == 0)
                                                childs[i].setClickable(true);
                                        }

                                        new CountDownTimer(150, 1000)
                                        {
                                            public void onTick(long millisUntilFinished) {}

                                            public void onFinish() {}

                                        }.start();
                                    }
                                }.start();
                            }
                        }.start();
                    }
                }
            }

        }
    };

    public static int dpToPx(int dp, Context context)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void slideToTop(final View view)
    {

        new CountDownTimer(700, 1000)
        {
            public void onTick(long millisUntilFinished)
            {

            }

            public void onFinish()
            {
                ObjectAnimator flip = ObjectAnimator.ofFloat(view, "rotationY", 0f, 90f);
                flip.setDuration(100);
                flip.start();

                new CountDownTimer(100, 1000)
                {
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

    @Override
    protected void onPause() {
        super.onPause();
        pause(this.findViewById(android.R.id.content).getRootView());
    }

    public void restart(View v)
    {
        pauseTimer();

        if(alertDialog != null)
            alertDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(InGame.this, R.style.myDialog));

        builder.setTitle("Restart Game")
                .setMessage("Are you sure you want to restart the game?")
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(getApplicationContext(), InGame.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        if(rowCount == 3 && columnCount == 2)
                        {
                            intent.putExtra("column", 2);
                            intent.putExtra("row", 3);
                            intent.putExtra("type", getIntent().getStringExtra("type"));
                        }
                        else if(rowCount == 4 && columnCount == 3)
                        {
                            intent.putExtra("column", 3);
                            intent.putExtra("row", 4);
                            intent.putExtra("type", getIntent().getStringExtra("type"));
                        }
                        else if(rowCount == 4 && columnCount == 4)
                        {
                            intent.putExtra("column", 4);
                            intent.putExtra("row", 4);
                            intent.putExtra("type", getIntent().getStringExtra("type"));
                        }
                        else if(rowCount == 5 && columnCount == 4)
                        {
                            intent.putExtra("column", 4);
                            intent.putExtra("row", 5);
                            intent.putExtra("type", getIntent().getStringExtra("type"));
                        }
                        else if(rowCount == 6 && columnCount == 4)
                        {
                            intent.putExtra("column", 4);
                            intent.putExtra("row", 6);
                            intent.putExtra("type", getIntent().getStringExtra("type"));
                        }
                        else if(rowCount == 3 && columnCount == 3)
                        {
                            intent.putExtra("column", 3);
                            intent.putExtra("row", 3);
                            intent.putExtra("type", getIntent().getStringExtra("type"));
                        }
                        else if(rowCount == 5 && columnCount == 3)
                        {
                            intent.putExtra("column", 3);
                            intent.putExtra("row", 5);
                            intent.putExtra("type", getIntent().getStringExtra("type"));
                        }
                        else if(rowCount == 5 && columnCount == 5)
                        {
                            intent.putExtra("column", 5);
                            intent.putExtra("row", 5);
                            intent.putExtra("type", getIntent().getStringExtra("type"));
                        }

                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startTimer();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateCountDownText()
    {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void pauseTimer()
    {
        mCountDownTimer.cancel();
    }

    public void pause(View v)
    {
        pauseTimer();

        if(alertDialog != null)
            alertDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(InGame.this, R.style.myDialog));

        builder.setTitle("Resume Game")
                .setMessage("Do you want to resume the game?")
                .setPositiveButton("Resume", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startTimer();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void startTimer()
    {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish()
            {
                if(!gameOrNot)
                {
                    for(int i = 0; i < size; i++)
                    {
                        final ObjectAnimator flip = ObjectAnimator.ofFloat(childs[i], "rotationY", 0f, 90f);
                        flip.setDuration(150);
                        flip.start();
                    }

                    new CountDownTimer(150, 1000) {

                        public void onTick(long millisUntilFinished)
                        {

                        }

                        public void onFinish()
                        {
                            for(int i = 0; i < size; i++)
                            {
                                //Drawable drawable = getResources().getDrawable(R.drawable.unfound);

                                Bitmap batmapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unfound);
                                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);

                                drawable.setCornerRadius(imageRadius);

                                childs[i].setImageDrawable(drawable);
                            }

                            for(int i = 0; i < size; i++)
                            {
                                final ObjectAnimator flip = ObjectAnimator.ofFloat(childs[i], "rotationY", 270f, 360f);
                                flip.setDuration(150);
                                flip.start();
                            }

                            new CountDownTimer(150, 1000) {

                                public void onTick(long millisUntilFinished)
                                {

                                }

                                public void onFinish()
                                {
                                    for(int i = 0; i < size; i++)
                                    {
                                        childs[i].setClickable(true);
                                    }
                                }

                            }.start();

                        }

                    }.start();

                    mTimeLeftInMillis = START_TIME_IN_MILLIS;
                    gameOrNot = true;

                    pauseButton.setClickable(true);
                    restartButton.setClickable(true);
                    exitButton.setClickable(true);

                    startTimer();
                }
                else
                {
                    if(alertDialog != null)
                        alertDialog.dismiss();

                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(InGame.this, R.style.myDialog));

                    builder.setTitle("Game Over")
                            .setMessage("Do you want to try again?")

                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Intent intent = new Intent(getApplicationContext(), InGame.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    if(rowCount == 3 && columnCount == 2)
                                    {
                                        intent.putExtra("column", 2);
                                        intent.putExtra("row", 3);
                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                    }
                                    else if(rowCount == 4 && columnCount == 3)
                                    {
                                        intent.putExtra("column", 3);
                                        intent.putExtra("row", 4);
                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                    }
                                    else if(rowCount == 4 && columnCount == 4)
                                    {
                                        intent.putExtra("column", 4);
                                        intent.putExtra("row", 4);
                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                    }
                                    else if(rowCount == 5 && columnCount == 4)
                                    {
                                        intent.putExtra("column", 4);
                                        intent.putExtra("row", 5);
                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                    }
                                    else if(rowCount == 6 && columnCount == 4)
                                    {
                                        intent.putExtra("column", 4);
                                        intent.putExtra("row", 6);
                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                    }
                                    else if(rowCount == 6 && columnCount == 5)
                                    {
                                        intent.putExtra("column", 5);
                                        intent.putExtra("row", 6);
                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                    }
                                    else if(rowCount == 6 && columnCount == 6)
                                    {
                                        intent.putExtra("column", 6);
                                        intent.putExtra("row", 6);
                                        intent.putExtra("type", getIntent().getStringExtra("type"));
                                    }

                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Intent intent = new Intent(getApplicationContext(), Modes.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("type", getIntent().getStringExtra("type"));
                                    startActivity(intent);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false);

                    alertDialog = builder.create();
                    alertDialog.show();
                }

            }

        }.start();

    }

    public void exit(View v)
    {
        pauseTimer();

        if(alertDialog != null)
            alertDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(InGame.this, R.style.myDialog));

        builder.setTitle("Exit Game")
                .setMessage("Are you sure you want to exit the game?")

                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(getApplicationContext(), Modes.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("type", getIntent().getStringExtra("type"));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startTimer();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onUserLeaveHint()
    {
        if(alertDialog != null)
            alertDialog.dismiss();

        pause(this.findViewById(android.R.id.content).getRootView());
    }

    @Override
    public void onBackPressed()
    {
        if(alertDialog != null)
            alertDialog.dismiss();

        exit(this.findViewById(android.R.id.content).getRootView());
    }
}