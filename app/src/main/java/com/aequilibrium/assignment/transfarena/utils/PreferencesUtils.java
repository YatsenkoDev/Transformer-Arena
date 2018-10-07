package com.aequilibrium.assignment.transfarena.utils;

import android.content.SharedPreferences;

/**
 * Shared preferences managing utils
 */
public class PreferencesUtils {

    private static final String TOKEN_KEY = "TOKEN_KEY";

    private PreferencesUtils() {

    }

    /**
     * Saves token to preferences
     *
     * @param token       new token string
     * @param preferences default shared preferences
     */
    public static void saveToken(String token, SharedPreferences preferences) {
        preferences.edit().putString(TOKEN_KEY, token).apply();
    }

    /**
     * Reads saved token
     *
     * @param preferences default shared preferences
     * @return existing token
     */
    public static String getToken(SharedPreferences preferences) {
        return preferences.getString(TOKEN_KEY, null);
    }
}
