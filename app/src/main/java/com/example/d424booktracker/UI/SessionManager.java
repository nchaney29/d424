package com.example.d424booktracker.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.se.omapi.Session;

public class SessionManager {
    private static final String USERNAME = "UserSession";
    private static final String USER_ID = "CURRENT_USER_ID";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(USERNAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void createSession(int userId) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putInt(USER_ID, userId);
        editor.commit();
    }

    public int getCurrentId() {
        return preferences.getInt(USER_ID, -1);
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
