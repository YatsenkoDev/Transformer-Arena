package com.aequilibrium.assignment.transfarena.battle.service;

import android.support.annotation.NonNull;

import com.aequilibrium.assignment.transfarena.battle.callback.BattleResultCallback;
import com.aequilibrium.assignment.transfarena.battle.model.BattleResult;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.utils.Constants;
import com.aequilibrium.assignment.transfarena.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Battle calculation service
 */
public class BattleService {

    private Disposable disposable;

    @Inject
    public BattleService() {

    }

    /**
     * Calculates battle result asynchronously
     *
     * @param autobots             team 1
     * @param decepticons          team 2
     * @param battleResultCallback callback for result
     */
    public void calculateBattleResult(List<Transformer> autobots, List<Transformer> decepticons, BattleResultCallback battleResultCallback) {
        disposable = Single.fromCallable(() -> calculateWinners(autobots, decepticons))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(battleResultCallback::onBattleResultCalculated, error -> battleResultCallback.onBattleResultCalculated(null));
    }

    private BattleResult calculateWinners(@NonNull List<Transformer> autobots, @NonNull List<Transformer> decepticons) {
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

    /**
     * Interrupts battle calculation process
     */
    public void interrupt() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
