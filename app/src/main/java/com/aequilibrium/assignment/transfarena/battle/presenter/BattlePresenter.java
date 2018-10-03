package com.aequilibrium.assignment.transfarena.battle.presenter;

import android.content.Context;
import android.util.Pair;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.battle.adapter.BattleAdapter;
import com.aequilibrium.assignment.transfarena.battle.ui.activity.BattleView;
import com.aequilibrium.assignment.transfarena.model.BattleResult;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.utils.Constants;
import com.aequilibrium.assignment.transfarena.utils.MathUtils;
import com.aequilibrium.assignment.transfarena.utils.TeamUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BattlePresenter implements BasePresenter {

    private final Context context;
    private BattleView view;
    private List<Transformer> autobots;
    private List<Transformer> decepticons;
    private Disposable subscriber;

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
        if (subscriber != null) {
            subscriber.dispose();
        }
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
        subscriber = Single.fromCallable(this::calculateWinners)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(battleResult -> {
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

    private BattleResult calculateWinners() {
        List<Transformer> autobotsAfterFight = new ArrayList<>();
        List<Transformer> decepticonsAfterFight = new ArrayList<>();
        int counter = 0;
        int decepticonsElimenatedOponentsCounter = 0;
        int autobotsElimenatedOponentsCounter = 0;
        boolean totalDestroy = false;
        while (true) {
            if (counter == autobots.size()) {
                fillTheListWithRestTransformers(decepticons, decepticonsAfterFight, counter);
                break;
            } else if (counter == decepticons.size()) {
                fillTheListWithRestTransformers(autobots, autobotsAfterFight, counter);
                break;
            }
            Transformer autobot = autobots.get(counter);
            Transformer decepticon = decepticons.get(counter);
            counter++;
            if (checkIfIsFinalBattle(autobot, decepticon)) {
                totalDestroy = true;
                break;
            }
            if (isWinnerByName(autobot)) {
                autobotsAfterFight.add(autobot);
                autobotsElimenatedOponentsCounter++;
                continue;
            }
            if (isWinnerByName(decepticon)) {
                decepticonsAfterFight.add(decepticon);
                decepticonsElimenatedOponentsCounter++;
                continue;
            }
            if (isWinnerByCourageAndStrength(autobot, decepticon)) {
                autobotsAfterFight.add(autobot);
                autobotsElimenatedOponentsCounter++;
                continue;
            }
            if (isWinnerByCourageAndStrength(decepticon, autobot)) {
                decepticonsAfterFight.add(decepticon);
                decepticonsElimenatedOponentsCounter++;
                continue;
            }
            if (isWinnerBySkill(autobot, decepticon)) {
                autobotsAfterFight.add(autobot);
                autobotsElimenatedOponentsCounter++;
                continue;
            }
            if (isWinnerBySkill(decepticon, autobot)) {
                decepticonsAfterFight.add(decepticon);
                decepticonsElimenatedOponentsCounter++;
                continue;
            }
            if (isWinnerByOverallRating(autobot, decepticon)) {
                autobotsAfterFight.add(autobot);
                autobotsElimenatedOponentsCounter++;
                continue;
            }
            if (isWinnerByOverallRating(decepticon, autobot)) {
                decepticonsAfterFight.add(decepticon);
                decepticonsElimenatedOponentsCounter++;
            }
        }
        return new BattleResult(autobotsAfterFight, decepticonsAfterFight, decepticonsElimenatedOponentsCounter, autobotsElimenatedOponentsCounter, totalDestroy);
    }

    private boolean isWinnerByName(Transformer first) {
        return hasLeaderName(first.getName());
    }

    private boolean checkIfIsFinalBattle(Transformer first, Transformer second) {
        return hasLeaderName(first.getName()) && hasLeaderName(second.getName());
    }

    private void fillTheListWithRestTransformers(List<Transformer> initialList
            , List<Transformer> resultList, int counter) {
        if (initialList.size() > counter) {
            resultList.addAll(initialList.subList(counter, initialList.size()));
        }
    }

    private boolean isWinnerByCourageAndStrength(Transformer first, Transformer second) {
        return first.getCourage() - second.getCourage() >= 4 && first.getStrength() - second.getStrength() >= 3;

    }

    private boolean isWinnerBySkill(Transformer first, Transformer second) {
        return first.getSkill() - second.getSkill() >= 3;
    }

    private boolean isWinnerByOverallRating(Transformer first, Transformer second) {
        return MathUtils.calculateOverallRating(first) > MathUtils.calculateOverallRating(second);
    }

    private boolean hasLeaderName(String name) {
        return Constants.OPTIMUS_PRIME.equals(name) || Constants.PREDAKING.equals(name);
    }
}
