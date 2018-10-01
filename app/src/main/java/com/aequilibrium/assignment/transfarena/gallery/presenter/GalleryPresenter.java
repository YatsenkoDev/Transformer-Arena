package com.aequilibrium.assignment.transfarena.gallery.presenter;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.gallery.adapter.GalleryPagerAdapter;
import com.aequilibrium.assignment.transfarena.gallery.service.TransformersLoadingService;
import com.aequilibrium.assignment.transfarena.gallery.ui.activity.GalleryView;
import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.List;

import javax.inject.Inject;

public class GalleryPresenter implements BasePresenter {

    private final TransformersLoadingService transformersLoadingService;
    private GalleryView view;
    private List<Transformer> transformers;

    @Inject
    public GalleryPresenter(TransformersLoadingService transformersLoadingService) {
        this.transformersLoadingService = transformersLoadingService;
    }

    public void setView(GalleryView view) {
        this.view = view;
    }

    public void start() {
        view.showLoading();
        loadTransformersToList();
    }

    private void loadTransformersToList() {
        transformersLoadingService.loadTransformers(this::setupTransformersList);

    }

    private void setupTransformersList(List<Transformer> transformers) {
        this.transformers = transformers;
        view.setViewPagerAdapter(new GalleryPagerAdapter(view.getSupportFragmentManager(), transformers));
        view.hideLoading();
    }

    @Override
    public void onViewDestroyed() {
        transformersLoadingService.interrupt();
    }
}
