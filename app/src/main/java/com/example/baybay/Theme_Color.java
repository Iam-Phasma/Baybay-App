package com.example.baybay;

import android.content.Context;
import android.content.SharedPreferences;

public class Theme_Color {

    public static String colorPick = "Latte";
    public static String defaultColor = "";

    public static void init(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        colorPick = preferences.getString("COLOR_PICK_KEY", "Latte");

        if ("Latte".equals(colorPick)) {
            defaultColor = "#FCF4E7";
        } else if ("Alice".equals(colorPick)) {
            defaultColor = "#f2f3f4";
        }
    }

    public static String getDefaultColor(){
        return defaultColor;
    }

    public static String getColorPick() {
        return colorPick;
    }
}
