package com.aequilibrium.assignment.transfarena.preview.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.aequilibrium.assignment.transfarena.model.Transformer;

public interface PreviewView {

    void showLoading();

    void hideLoading();

    void setParametersListAdapter(RecyclerView.Adapter adapter);

    String getName();

    Transformer getTransformer();

    void selectAutobotsTeam(boolean select);

    void setupName(String name);

    void enableElements(boolean enable);

    void showConnectionErrorMessage();

    void showNameRequiredError();

    void setResult(int resultCode, Intent data);

    void finish();
}
