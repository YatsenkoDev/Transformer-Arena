package com.aequilibrium.assignment.transfarena.base.ui;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        inject();
    }

    @Override
    protected void onDestroy() {
        getPresenter().onViewDestroyed();
        super.onDestroy();
    }

    public void showConnectionErrorMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.connection_error);
        builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> finish());
        builder.create().show();
    }

    protected abstract void inject();

    protected abstract BasePresenter getPresenter();
}
