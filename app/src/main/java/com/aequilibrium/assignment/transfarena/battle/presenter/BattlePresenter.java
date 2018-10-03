package com.aequilibrium.assignment.transfarena.battle.presenter;

import android.content.Context;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.battle.service.BattleService;
import com.aequilibrium.assignment.transfarena.battle.adapter.BattleAdapter;
import com.aequilibrium.assignment.transfarena.battle.ui.activity.BattleView;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.utils.TeamUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class BattlePresenter implements BasePresenter {

    private final Context context;
    private final BattleService battleService;
    private BattleView view;
    private List<Transformer> autobots;
    private List<Transformer> decepticons;

    @Inject
    public BattlePresenter(Context context, BattleService battleService) {
        this.context = context;
        this.battleService = battleService;
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
        battleService.interrupt();
    }

    public void setAutobots(List<Transformer> autobots) {
        this.autobots = autobots;
    }

    public void setDecipticonss(List<Transformer> decepticons) {
        this.decepticons = decepticons;
    }

    public void onBeginBattleClicked() {
        if (autobots.isEmpty() || decepticons.isEmpty()) {
            view.showEmptyTeamError();
        } else {
            view.showResultDialog();
            startWinnerCalculations();
        }
    }

    private void startWinnerCalculations() {
        battleService.calculateBattleResult(autobots, decepticons, battleResult -> {
            if (battleResult.isTotalDestroy()) {
                view.notifyAboutTotalDestroy();
            } else if (battleResult.getAutobotsElimenatedOponentsCounter() == battleResult.getDecepticonsElimenatedOponentsCounter()) {
                view.notifyAboutTie();
            } else {
                view.setupResults(getPossibleBattlesCount()
                        , TeamUtils.getTeamNameByBattleResult(context, battleResult, true)
                        , TeamUtils.getTeamNameByBattleResult(context, battleResult, false)
                        , TeamUtils.getWinningTem(battleResult)
                        , TeamUtils.getLoosingTem(battleResult));
            }
        });
    }

    private int getPossibleBattlesCount() {
        return autobots.size() < decepticons.size() ? autobots.size() : decepticons.size();
    }


}
