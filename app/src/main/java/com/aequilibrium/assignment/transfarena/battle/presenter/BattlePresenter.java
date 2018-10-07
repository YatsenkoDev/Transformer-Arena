package com.aequilibrium.assignment.transfarena.battle.presenter;

import android.content.Context;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.battle.adapter.BattleAdapter;
import com.aequilibrium.assignment.transfarena.battle.service.BattleService;
import com.aequilibrium.assignment.transfarena.battle.ui.activity.BattleView;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.utils.TeamUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Presenter of the battle screen
 * Responsible for winner's team determination and battle process
 */
public class BattlePresenter implements BasePresenter {

    private final Context context;
    private final BattleService battleService;
    private BattleView view;
    private List<Transformer> autobots;
    private List<Transformer> decepticons;

    /**
     * Contructor
     *
     * @param context       Application's context
     * @param battleService service for battle calculation
     */
    @Inject
    public BattlePresenter(Context context, BattleService battleService) {
        this.context = context;
        this.battleService = battleService;
    }

    /**
     * Setter
     *
     * @param view Battle info display view
     */
    public void setView(BattleView view) {
        this.view = view;
    }

    /**
     * Initiates battle info
     */
    public void start() {
        Collections.sort(this.autobots);
        Collections.sort(this.decepticons);
        view.setBattleListAdapter(new BattleAdapter(context, autobots, decepticons));
    }

    /**
     * Notifies about view destroy to interrupt battle calculation
     */
    @Override
    public void onViewDestroyed() {
        battleService.interrupt();
    }

    /**
     * Setter
     *
     * @param autobots team 1
     */
    public void setAutobots(List<Transformer> autobots) {
        this.autobots = autobots;
    }

    /**
     * Setter
     *
     * @param decepticons team 2
     */
    public void setDecipticonss(List<Transformer> decepticons) {
        this.decepticons = decepticons;
    }

    /**
     * Starts winner calculation
     */
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
