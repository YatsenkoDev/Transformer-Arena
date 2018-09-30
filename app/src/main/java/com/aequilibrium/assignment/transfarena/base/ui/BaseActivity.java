package com.aequilibrium.assignment.transfarena.base.ui;

import android.support.v7.app.AppCompatActivity;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        inject();
    }

    protected abstract void inject();

    @Override
    protected void onDestroy() {
        getPresenter().onViewDestroyed();
        super.onDestroy();
    }

    protected abstract BasePresenter getPresenter();
}
