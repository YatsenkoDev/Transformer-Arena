package com.aequilibrium.assignment.transfarena.battle.model;

import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.List;

/**
 * Model of result for battle calculation
 */
public class BattleResult {

    private final List<Transformer> autobots;
    private final List<Transformer> decepticons;
    private final int autobotsElimenatedOponentsCounter;
    private final int decepticonsElimenatedOponentsCounter;
    private final boolean totalDestroy;

    public BattleResult(List<Transformer> autobots, List<Transformer> decepticons
            , int decepticonsElimenatedOponentsCounter, int autobotsElimenatedOponentsCounter, boolean totalDestroy) {
        this.autobots = autobots;
        this.decepticons = decepticons;
        this.decepticonsElimenatedOponentsCounter = decepticonsElimenatedOponentsCounter;
        this.autobotsElimenatedOponentsCounter = autobotsElimenatedOponentsCounter;
        this.totalDestroy = totalDestroy;
    }

    public List<Transformer> getAutobots() {
        return autobots;
    }

    public List<Transformer> getDecepticons() {
        return decepticons;
    }

    public int getAutobotsElimenatedOponentsCounter() {
        return autobotsElimenatedOponentsCounter;
    }

    public int getDecepticonsElimenatedOponentsCounter() {
        return decepticonsElimenatedOponentsCounter;
    }

    public boolean isTotalDestroy() {
        return totalDestroy;
    }
}
