package com.aequilibrium.assignment.transfarena.battle.callback;

import com.aequilibrium.assignment.transfarena.battle.model.BattleResult;

/**
 * Callback for result of battle calculation
 */
public interface BattleResultCallback {

    void onBattleResultCalculated(BattleResult battleResult);
}
