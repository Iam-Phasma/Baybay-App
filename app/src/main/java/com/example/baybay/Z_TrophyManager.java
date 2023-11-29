package com.example.baybay;

import android.content.Context;
import android.content.SharedPreferences;

public class Z_TrophyManager {
    public static Z_TrophyManager instance;
    public static int trophies = 0;
    private static SharedPreferences sharedPreferences;
    private Context context;

    // Private constructor to prevent direct instantiation
    private Z_TrophyManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        trophies = sharedPreferences.getInt("trophies", 0);
    }

    public static Z_TrophyManager getInstance(Context context) {
        if (instance == null) {
            instance = new Z_TrophyManager(context);
        }
        return instance;
    }

    public static int getTrophies() {
        return trophies;
    }

    public static void setTrophies(int newTrophies) {
        trophies = newTrophies;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("trophies", trophies);
        editor.apply();
    }

    // Add other methods for managing trophies as needed
}
