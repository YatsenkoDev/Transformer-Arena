package com.aequilibrium.assignment.transfarena.preview.presenter;

import com.aequilibrium.assignment.transfarena.preview.ui.PreviewView;

import javax.inject.Inject;

public class PreviewPresenter {

    private PreviewView view;

    @Inject
    public PreviewPresenter() {

    }

    public void setView(PreviewView view) {
        this.view = view;
    }
}
