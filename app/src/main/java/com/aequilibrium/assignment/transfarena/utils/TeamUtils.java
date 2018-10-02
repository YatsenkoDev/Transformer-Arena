package com.aequilibrium.assignment.transfarena.utils;

import android.util.Pair;

import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.ArrayList;
import java.util.List;

public class TeamUtils {

    private TeamUtils() {

    }

    public static Pair<ArrayList<Transformer>, ArrayList<Transformer>> separateByTeam(List<Transformer> transformers) {
        ArrayList<Transformer> autobots = new ArrayList<>();
        ArrayList<Transformer> decepticons = new ArrayList<>();
        for (Transformer transformer : transformers) {
            if (Constants.AUTOBOTS_TEAM_KEY.equals(transformer.getTeam())) {
                autobots.add(transformer);
            } else if (Constants.DECEPTICONS_TEAM_KEY.equals(transformer.getTeam())) {
                decepticons.add(transformer);
            }
        }
        return new Pair<>(autobots, decepticons);
    }

    public static String getTransformerAvatar(Transformer transformer) {
        return String.format(Constants.AUTOBOTS_TEAM_KEY.equals(transformer.getTeam()) ? Constants.AUTOBOT_AVATAR : Constants.DECEPTICON_AVATAR, transformer.getRank());
    }
}
