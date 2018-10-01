package com.aequilibrium.assignment.transfarena.gallery.presenter;

import android.content.Context;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.gallery.adapter.GalleryPagerAdapter;
import com.aequilibrium.assignment.transfarena.gallery.service.TransformersLoadingService;
import com.aequilibrium.assignment.transfarena.gallery.ui.activity.GalleryView;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewActivity;

import java.util.List;

import javax.inject.Inject;

public class GalleryPresenter implements BasePresenter {

    private static final int ADD_NEW_KEY_CODE = 0;
    private final Context context;
    private final TransformersLoadingService transformersLoadingService;
    private GalleryView view;
    private List<Transformer> transformers;

    @Inject
    public GalleryPresenter(Context context, TransformersLoadingService transformersLoadingService) {
        this.context = context;
        this.transformersLoadingService = transformersLoadingService;
    }

    public void setView(GalleryView view) {
        this.view = view;
    }

    public void start() {
        view.showLoading();
        loadTransformersToList();
    }

    @Override
    public void onViewDestroyed() {
        transformersLoadingService.interrupt();
    }

    private void loadTransformersToList() {
        transformersLoadingService.loadTransformers(this::setupTransformersList);

    }

    private void setupTransformersList(List<Transformer> transformers) {
        this.transformers = transformers;
        view.setViewPagerAdapter(new GalleryPagerAdapter(view.getSupportFragmentManager(), transformers));
        view.hideLoading();
    }

    public void onAddNewClicked() {
        view.startActivityForResult(PreviewActivity.buildAddNewIntent(context), ADD_NEW_KEY_CODE);
    }
}
