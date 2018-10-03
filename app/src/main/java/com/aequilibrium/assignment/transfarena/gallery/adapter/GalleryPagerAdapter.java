package com.aequilibrium.assignment.transfarena.gallery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aequilibrium.assignment.transfarena.gallery.ui.fragment.GalleryPageFragment;
import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.ArrayList;

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGES_COUNT = 2;
    private final ArrayList<Transformer> autobots;
    private final ArrayList<Transformer> decepticons;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<Transformer> autobots, ArrayList<Transformer> decepticons) {
        super(fm);
        this.autobots = autobots;
        this.decepticons = decepticons;
    }

    @Override
    public Fragment getItem(int position) {
        return GalleryPageFragment.getInstance(position == 0 ? autobots : decepticons);
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
