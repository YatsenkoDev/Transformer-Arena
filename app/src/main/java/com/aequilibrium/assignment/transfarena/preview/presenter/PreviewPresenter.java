package com.aequilibrium.assignment.transfarena.preview.presenter;

import android.content.Context;
import android.content.Intent;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.preview.adapter.ParameterListAdapter;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewView;

import javax.inject.Inject;

public class PreviewPresenter implements BasePresenter {

    private final Context context;
    private PreviewView view;
    private ParameterListAdapter adapter;

    @Inject
    public PreviewPresenter(Context context) {

        this.context = context;
    }

    public void setView(PreviewView view) {
        this.view = view;
    }

    public void start() {
        adapter = new ParameterListAdapter(context);
        view.setParametersListAdapter(adapter);
    }

    @Override
    public void onViewDestroyed() {

    }

    public void autobotTeamClicked() {

    }

    public void decipticonTeamClicked() {

    }

    public void onSaveClicked() {
    }

    public void onEditClicked() {
    }

    public void onDeleteClicked() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
