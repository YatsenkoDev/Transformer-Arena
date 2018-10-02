package com.aequilibrium.assignment.transfarena.gallery.presenter;

import android.content.Context;
import android.content.Intent;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.bus.RxBus;
import com.aequilibrium.assignment.transfarena.bus.event.TransformerSelectedEvent;
import com.aequilibrium.assignment.transfarena.gallery.adapter.GalleryPagerAdapter;
import com.aequilibrium.assignment.transfarena.gallery.service.TransformersLoadingService;
import com.aequilibrium.assignment.transfarena.gallery.ui.activity.GalleryView;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.preview.presenter.PreviewPresenter;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewActivity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;

public class GalleryPresenter implements BasePresenter {

    private static final int ADD_NEW_KEY_CODE = 0;
    private static final int SELECT_KEY_CODE = 1;
    private final Context context;
    private final TransformersLoadingService transformersLoadingService;
    private final RxBus rxBus;
    private GalleryView view;
    private List<Transformer> transformers;
    private Disposable transformerSelectedSubscriber;

    @Inject
    public GalleryPresenter(Context context, TransformersLoadingService transformersLoadingService, RxBus rxBus) {
        this.context = context;
        this.transformersLoadingService = transformersLoadingService;
        this.rxBus = rxBus;
    }

    public void setView(GalleryView view) {
        this.view = view;
    }

    public void start() {
        view.showLoading();
        loadTransformersToList();
        transformerSelectedSubscriber = rxBus.register(TransformerSelectedEvent.class, this::onTransformerSelected);
    }

    @Override
    public void onViewDestroyed() {
        transformersLoadingService.interrupt();
        transformerSelectedSubscriber.dispose();
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

    private void onTransformerSelected(TransformerSelectedEvent transformerSelectedEvent) {
        view.startActivityForResult(PreviewActivity.buildAddNewIntent(context, transformerSelectedEvent.getTransformer()), SELECT_KEY_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Transformer transformer = (Transformer) data.getSerializableExtra(PreviewPresenter.TRANSFORMER_KEY);
            switch (requestCode) {
                case ADD_NEW_KEY_CODE: {
                    if (transformer != null) {
                        transformers.add(transformer);
                    }
                    break;
                }
                case SELECT_KEY_CODE: {
                    for (int i = 0; i < transformers.size(); i++) {
                        if (transformers.get(i).getId().equals(data.getStringExtra(PreviewPresenter.TRANSFORMER_ID_KEY))) {
                            if (data.getBooleanExtra(PreviewPresenter.TRANSFORMER_UPDATE_KEY, false)) {
                                transformers.set(i, transformer);
                            } else {
                                transformers.remove(i);
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            setupTransformersList(transformers);
        }
    }
}
