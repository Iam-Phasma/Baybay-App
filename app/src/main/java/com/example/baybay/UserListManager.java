package com.example.baybay;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.baybay.Gameplay;
import com.example.baybay.recyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserListManager {
    private static ArrayList<Gameplay> usersList;
    private static recyclerAdapter adapter;

    public static ArrayList<Gameplay> getUserList(Context context) {
        if (usersList == null) {
            // Initialize the user list from SharedPreferences
            usersList = loadUserList(context);
        }
        return usersList;
    }

    public static void saveUserList(Context context, ArrayList<Gameplay> userList) {
        // Save the updated ArrayList to SharedPreferences
        usersList = userList;
        saveUserListToSharedPreferences(context, userList);
    }

    private static void saveUserListToSharedPreferences(Context context, ArrayList<Gameplay> userList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convert the ArrayList to a JSON string
        Gson gson = new Gson();
        String userListJson = gson.toJson(userList);

        // Save the JSON string to SharedPreferences
        editor.putString("user_list", userListJson);
        editor.apply();
    }

    private static ArrayList<Gameplay> loadUserList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userListJson = sharedPreferences.getString("user_list", "");

        // Convert the JSON string back to ArrayList
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Gameplay>>(){}.getType();
        return gson.fromJson(userListJson, type);
    }

    public static void setAdapter(recyclerAdapter adapter) {
        UserListManager.adapter = adapter;
    }

    public static void updateRecyclerView() {
        if (adapter != null) {
            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged();
        }
    }
}
