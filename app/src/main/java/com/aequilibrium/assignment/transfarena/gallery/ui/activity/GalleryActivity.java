package com.aequilibrium.assignment.transfarena.gallery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.TransfArenaApplication;
import com.aequilibrium.assignment.transfarena.base.ui.BaseActivity;
import com.aequilibrium.assignment.transfarena.gallery.presenter.GalleryPresenter;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class GalleryActivity extends BaseActivity implements GalleryView {

    @Inject
    GalleryPresenter presenter;

    @BindView(R.id.loading_bar)
    ProgressBar loadingBar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        presenter.setView(this);
        presenter.start();
    }

    @Override
    protected void inject() {
        ((TransfArenaApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected GalleryPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showLoading() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingBar.setVisibility(View.GONE);
        setupTabs();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setViewPagerAdapter(PagerAdapter pagerAdapter) {
        pager.setAdapter(pagerAdapter);
    }

    @OnClick(R.id.add_button)
    public void addNewClick() {
        presenter.onAddNewClicked();
    }

    private void setupTabs() {
        tabs.setupWithViewPager(pager);
        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(R.drawable.symbol_autobot);
        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(R.drawable.symbol_decept);
    }
}
