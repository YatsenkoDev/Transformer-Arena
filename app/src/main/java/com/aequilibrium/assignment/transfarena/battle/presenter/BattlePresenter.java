package com.aequilibrium.assignment.transfarena.battle.presenter;

import com.aequilibrium.assignment.transfarena.battle.ui.BattleView;

import javax.inject.Inject;

public class BattlePresenter {

    private BattleView view;

    @Inject
    public BattlePresenter() {

    }

    public void setView(BattleView view) {
        this.view = view;
    }
}
