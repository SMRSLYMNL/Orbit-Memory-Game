package com.sscompany.memorygame;

import android.content.SharedPreferences;
import android.content.SharedPreferences.*;

public class Data {

    private final int[] levelDimensionX = new int[] {
            2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 5, 6, 6, 6,
            3, 3, 5, 5, 5, 5, 5, 5, 5,
            3, 4, 4, 4, 4, 5, 5, 6, 6
    };

    private final int[] levelDimensionY = new int[] {
            2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            3, 3, 3, 3, 3, 5, 5, 5, 5,
            2, 3, 3, 4, 4, 4, 4, 4, 4
    };

    private final int[] levelNumberOfBombs = new int[] {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            2, 2, 2, 2, 2, 2, 2, 2, 2
    };

    private final int[] levelNumberOfUniqueElements = new int[] {
            2, 2, 3, 3, 4, 6, 4, 6, 8, 5, 7, 10, 6, 9, 12,
            2, 4, 4, 5, 7, 6, 8, 10, 12,
            2, 3, 5, 4, 7, 5, 9, 6, 11
    };

    private final int[] levelNumberOfPairs = new int[] {
            2, 1, 3, 0, 2, 6, 0, 4, 8, 0, 4, 10, 0, 6, 12,
            0, 4, 1, 3, 7, 0, 4, 8, 12,
            2, 1, 5, 1, 7, 1, 9, 1, 11
    };

    private final int[] levelNumberOfFours = new int[] {
            0, 1, 0, 3, 2, 0, 4, 2, 0, 5, 3, 0, 6, 3, 0,
            2, 0, 3, 2, 0, 6, 4, 2, 0,
            0, 2, 0, 3, 0, 4, 0, 5, 0
    };

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

    public Data() {}

    public int getLevelDimensionX(int level)
    {
        return levelDimensionX[level];
    }

    public int getLevelDimensionY(int level)
    {
        return levelDimensionY[level];
    }

    public int getLevelNumberOfBombs(int level)
    {
        return levelNumberOfBombs[level];
    }

    public int getLevelNumberOfUniqueElements(int level)
    {
        return levelNumberOfUniqueElements[level];
    }

    public int getLevelNumberOfPairs(int level)
    {
        return levelNumberOfPairs[level];
    }

    public int getLevelNumberOfFours(int level)
    {
        return levelNumberOfFours[level];
    }

    public int[] getPictures(String type){
        if(type.equals("food"))
            return allPictures[0];

        if(type.equals("animal"))
            return allPictures[1];

        if(type.equals("car_logo"))
            return allPictures[2];

        if(type.equals("flag"))
            return allPictures[3];

        return new int[]{};
    }
}
