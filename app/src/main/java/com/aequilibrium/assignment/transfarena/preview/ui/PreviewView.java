package com.aequilibrium.assignment.transfarena.preview.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

public interface PreviewView {

    void showLoading();

    void hideLoading();

    void setParametersListAdapter(RecyclerView.Adapter adapter);

    String getName();

    void showConnectionErrorMessage();

    void showNameRequiredError();

    void setResult(int resultCode, Intent data);

    void finish();
}
