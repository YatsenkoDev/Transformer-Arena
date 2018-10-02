package com.aequilibrium.assignment.transfarena.battle.presenter;

import android.content.Context;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.battle.adapter.BattleAdapter;
import com.aequilibrium.assignment.transfarena.battle.ui.BattleView;
import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class BattlePresenter implements BasePresenter {

    private final Context context;
    private BattleView view;
    private List<Transformer> autobots;
    private List<Transformer> decepticons;

    @Inject
    public BattlePresenter(Context context) {
        this.context = context;
    }

    public void setView(BattleView view) {
        this.view = view;
    }

    public void start() {
        Collections.sort(this.autobots);
        Collections.sort(this.decepticons);
        view.setBattleListAdapter(new BattleAdapter(context, autobots, decepticons));
    }

    @Override
    public void onViewDestroyed() {

    }

    public void setAutobots(List<Transformer> autobots) {
        this.autobots = autobots;
    }

    public void setDecipticonss(List<Transformer> decepticons) {
        this.decepticons = decepticons;
    }
}
