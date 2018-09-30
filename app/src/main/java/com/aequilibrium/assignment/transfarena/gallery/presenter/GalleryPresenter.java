package com.aequilibrium.assignment.transfarena.gallery.presenter;

import com.aequilibrium.assignment.transfarena.gallery.ui.GalleryView;

import javax.inject.Inject;

public class GalleryPresenter {

    private GalleryView view;

    @Inject
    public GalleryPresenter() {

    }

    public void setView(GalleryView view) {
        this.view = view;
    }
}
