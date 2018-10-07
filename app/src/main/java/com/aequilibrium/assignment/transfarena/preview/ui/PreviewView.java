package com.aequilibrium.assignment.transfarena.preview.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.preview.callback.DeleteConfirmationCallback;

/**
 * View methods for preview presenter
 *
 * @see com.aequilibrium.assignment.transfarena.preview.presenter.PreviewPresenter
 */
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

    void setEditingModeEnabled(boolean editingModeEnabled);

    void showConfirmationDialog(DeleteConfirmationCallback deleteConfirmationCallback, String name);

    void setResult(int resultCode, Intent data);

    void finish();
}
