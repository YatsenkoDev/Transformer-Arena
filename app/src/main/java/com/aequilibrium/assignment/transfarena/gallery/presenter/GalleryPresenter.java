package com.aequilibrium.assignment.transfarena.gallery.presenter;

import android.util.Log;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.gallery.TokenService;
import com.aequilibrium.assignment.transfarena.gallery.ui.GalleryView;

import javax.inject.Inject;

public class GalleryPresenter implements BasePresenter {

    private final TokenService tokenService;
    private GalleryView view;

    @Inject
    public GalleryPresenter(TokenService tokenService) {
        this.tokenService = tokenService;
        tokenService.getToken(token -> Log.d("GalleryPresenter", "Token - " + token));
    }

    public void setView(GalleryView view) {
        this.view = view;
    }

    @Override
    public void onViewDestroyed() {
        tokenService.interrupt();
    }
}
