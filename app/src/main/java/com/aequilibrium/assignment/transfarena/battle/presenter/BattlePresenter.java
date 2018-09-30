package com.aequilibrium.assignment.transfarena.battle.presenter;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.battle.ui.BattleView;

import javax.inject.Inject;

public class BattlePresenter implements BasePresenter {

    private BattleView view;

    @Inject
    public BattlePresenter() {

    }

    public void setView(BattleView view) {
        this.view = view;
    }

    @Override
    public void onViewDestroyed() {

    }
}
