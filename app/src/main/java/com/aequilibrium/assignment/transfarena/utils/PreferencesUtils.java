package com.aequilibrium.assignment.transfarena.utils;

import android.content.SharedPreferences;

public class PreferencesUtils {

    private static final String TOKEN_KEY = "TOKEN_KEY";

    private PreferencesUtils() {

    }

    public static void saveToken(String token, SharedPreferences preferences) {
        preferences.edit().putString(TOKEN_KEY, token).apply();
    }

    public static String getToken(SharedPreferences preferences) {
        return preferences.getString(TOKEN_KEY, null);
    }
}
