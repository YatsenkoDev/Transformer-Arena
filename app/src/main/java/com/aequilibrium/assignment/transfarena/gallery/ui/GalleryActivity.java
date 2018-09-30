package com.aequilibrium.assignment.transfarena.gallery.ui;

import android.os.Bundle;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.TransfArenaApplication;
import com.aequilibrium.assignment.transfarena.base.ui.BaseActivity;
import com.aequilibrium.assignment.transfarena.gallery.presenter.GalleryPresenter;

import javax.inject.Inject;

public class GalleryActivity extends BaseActivity implements GalleryView {

    @Inject
    GalleryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        presenter.setView(this);
    }

    @Override
    protected void inject() {
        ((TransfArenaApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected GalleryPresenter getPresenter() {
        return presenter;
    }
}
