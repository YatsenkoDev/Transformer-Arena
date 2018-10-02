package com.aequilibrium.assignment.transfarena.gallery.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

public interface GalleryView {

    void showLoading();

    void hideLoading();

    FragmentManager getSupportFragmentManager();

    void startActivityForResult(Intent intent, int requestCode);

    void setViewPagerAdapter(PagerAdapter pagerAdapter);

    void showConnectionErrorMessage();

    int getCurrentPage();
}
