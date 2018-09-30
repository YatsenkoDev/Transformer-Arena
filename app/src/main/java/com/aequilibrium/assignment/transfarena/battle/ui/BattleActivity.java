package com.aequilibrium.assignment.transfarena.battle.ui;

import android.os.Bundle;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.TransfArenaApplication;
import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.base.ui.BaseActivity;
import com.aequilibrium.assignment.transfarena.battle.presenter.BattlePresenter;

import javax.inject.Inject;

public class BattleActivity extends BaseActivity implements BattleView {

    @Inject
    BattlePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        presenter.setView(this);
    }

    @Override
    protected void inject() {
        ((TransfArenaApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected BattlePresenter getPresenter() {
        return presenter;
    }
}
