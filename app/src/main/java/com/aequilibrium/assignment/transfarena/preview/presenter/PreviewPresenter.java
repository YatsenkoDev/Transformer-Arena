package com.aequilibrium.assignment.transfarena.preview.presenter;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewView;

import javax.inject.Inject;

public class PreviewPresenter implements BasePresenter {

    private PreviewView view;

    @Inject
    public PreviewPresenter() {

    }

    public void setView(PreviewView view) {
        this.view = view;
    }

    @Override
    public void onViewDestroyed() {

    }
}
