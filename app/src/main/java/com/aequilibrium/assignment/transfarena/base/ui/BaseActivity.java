package com.aequilibrium.assignment.transfarena.base.ui;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Base activity class with view binding and base methods
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Binds all the views and actions. Injects all dependencies
     *
     * @param layoutResID activity layout file
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        inject();
    }

    /**
     * Notifies presenter about view destroy
     */
    @Override
    protected void onDestroy() {
        getPresenter().onViewDestroyed();
        super.onDestroy();
    }

    /**
     * Display connection error in dialog
     */
    public void showConnectionErrorMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.connection_error);
        builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> finish());
        builder.create().show();
    }

    /**
     * Injects all dependencies
     */
    protected abstract void inject();

    /**
     * Getter
     *
     * @return presenter for current activity
     */
    protected abstract BasePresenter getPresenter();
}
