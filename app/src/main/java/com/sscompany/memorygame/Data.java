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
}
