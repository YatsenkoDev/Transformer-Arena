package com.aequilibrium.assignment.transfarena.preview.ui;

import android.support.v7.widget.RecyclerView;

public interface PreviewView {

    void showLoading();

    void hideLoading();

    void setParametersListAdapter(RecyclerView.Adapter adapter);
}
