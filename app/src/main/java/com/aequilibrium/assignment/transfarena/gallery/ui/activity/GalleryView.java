package com.aequilibrium.assignment.transfarena.gallery.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

public interface GalleryView {

    void showLoading();

    void hideLoading();

    FragmentManager getSupportFragmentManager();

    void setViewPagerAdapter(PagerAdapter pagerAdapter);
}
