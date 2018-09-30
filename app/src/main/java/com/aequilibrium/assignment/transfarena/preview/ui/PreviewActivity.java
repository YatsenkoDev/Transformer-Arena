package com.aequilibrium.assignment.transfarena.preview.ui;

import android.os.Bundle;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.TransfArenaApplication;
import com.aequilibrium.assignment.transfarena.base.ui.BaseActivity;
import com.aequilibrium.assignment.transfarena.preview.presenter.PreviewPresenter;

import javax.inject.Inject;

public class PreviewActivity extends BaseActivity implements PreviewView {

    @Inject
    PreviewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        presenter.setView(this);
    }

    @Override
    protected void inject() {
        ((TransfArenaApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected PreviewPresenter getPresenter() {
        return presenter;
    }
}
