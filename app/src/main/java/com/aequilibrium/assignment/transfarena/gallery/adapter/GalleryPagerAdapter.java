package com.aequilibrium.assignment.transfarena.gallery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aequilibrium.assignment.transfarena.gallery.ui.fragment.GalleryPageFragment;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGES_COUNT = 2;
    private final ArrayList<Transformer> autobots = new ArrayList<>();
    private final ArrayList<Transformer> decepticons = new ArrayList<>();

    public GalleryPagerAdapter(FragmentManager fm, List<Transformer> transformers) {
        super(fm);
        generateTeams(transformers);
    }

    @Override
    public Fragment getItem(int position) {
        return GalleryPageFragment.getInstance(position == 0 ? autobots : decepticons);
    }

    private void generateTeams(List<Transformer> transformers) {
        for (Transformer transformer : transformers) {
            if (Constants.AUTOBOTS_TEAM_KEY.equals(transformer.getTeam())) {
                autobots.add(transformer);
            } else if (Constants.DECEPTICONS_TEAM_KEY.equals(transformer.getTeam())) {
                decepticons.add(transformer);
            }
        }
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
