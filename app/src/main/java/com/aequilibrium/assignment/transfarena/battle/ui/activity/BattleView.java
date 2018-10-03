package com.aequilibrium.assignment.transfarena.battle.ui.activity;

import android.support.v7.widget.RecyclerView;

import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.List;

public interface BattleView {

    void setBattleListAdapter(RecyclerView.Adapter adapter);

    void showResultDialog();

    void setupResults(int battlesCount, String winningTeam, String losingTeam
            , List<Transformer> winners, List<Transformer> survives);

    void notifyAboutTotalDestroy();

    void showEmptyTeamError();

    void notifyAboutTie();
}
