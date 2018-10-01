package com.aequilibrium.assignment.transfarena.gallery.presenter;

import android.content.Context;
import android.content.Intent;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.gallery.adapter.GalleryPagerAdapter;
import com.aequilibrium.assignment.transfarena.gallery.service.TransformersLoadingService;
import com.aequilibrium.assignment.transfarena.gallery.ui.activity.GalleryView;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.preview.presenter.PreviewPresenter;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewActivity;

import java.util.List;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

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
        if (transformers != null) {
            this.transformers = transformers;
            view.setViewPagerAdapter(new GalleryPagerAdapter(view.getSupportFragmentManager(), transformers));
            view.hideLoading();
        } else {
            view.showConnectionErrorMessage();
        }
    }

    public void onAddNewClicked() {
        view.startActivityForResult(PreviewActivity.buildAddNewIntent(context), ADD_NEW_KEY_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADD_NEW_KEY_CODE) {
            Transformer transformer = (Transformer) data.getSerializableExtra(PreviewPresenter.TRANSFORMER_KEY);
            if (transformer != null) {
                transformers.add(transformer);
                setupTransformersList(transformers);
            }
        }
    }
}
