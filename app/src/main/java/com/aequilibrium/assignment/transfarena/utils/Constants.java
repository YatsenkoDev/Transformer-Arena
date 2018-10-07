package com.aequilibrium.assignment.transfarena.utils;

import com.aequilibrium.assignment.transfarena.R;

import java.util.Arrays;
import java.util.List;

/**
 * All application constant values
 */
public class Constants {

    public static final String OPTIMUS_PRIME = "Optimus Prime";
    public static final String PREDAKING = "Predaking";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String AUTOBOTS_TEAM_KEY = "A";
    public static final String DECEPTICONS_TEAM_KEY = "D";
    public static final List<Integer> PARAMETERS = Arrays.asList(R.string.strength, R.string.intelligence, R.string.speed, R.string.endurance, R.string.rank, R.string.courage, R.string.firepower, R.string.skill);
    public static final String AUTOBOT_AVATAR = "file:///android_asset/autobot_%1$d.png";
    public static final String DECEPTICON_AVATAR = "file:///android_asset/decepticon_%1$d.png";

    private Constants() {

    }
}
