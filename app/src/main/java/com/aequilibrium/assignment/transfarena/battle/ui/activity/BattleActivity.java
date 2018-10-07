package com.aequilibrium.assignment.transfarena.battle.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.TransfArenaApplication;
import com.aequilibrium.assignment.transfarena.base.ui.BaseActivity;
import com.aequilibrium.assignment.transfarena.battle.presenter.BattlePresenter;
import com.aequilibrium.assignment.transfarena.battle.ui.dialog.ResultDialogFragment;
import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Battle arena view
 * Responsible for teams and battle result display
 */
public class BattleActivity extends BaseActivity implements BattleView {

    private static final String AUTOBOTS_TEAM_KEY = "AUTOBOTS_TEAM_KEY";
    private static final String DECEPTICONS_TEAM_KEY = "DECEPTICONS_TEAM_KEY";
    @Inject
    BattlePresenter presenter;
    @BindView(R.id.battle_list)
    RecyclerView battleList;
    private ResultDialogFragment resultDialog;
    private Toast emptyTeamError;

    /**
     * Intent for activity start
     *
     * @param context    application context
     * @param autobots   team 1
     * @param deciptions team 2
     * @return BattleActivity intent
     */
    public static Intent buildIntent(Context context, ArrayList<Transformer> autobots, ArrayList<Transformer> deciptions) {
        Intent intent = new Intent(context, BattleActivity.class);
        intent.putExtra(AUTOBOTS_TEAM_KEY, autobots);
        intent.putExtra(DECEPTICONS_TEAM_KEY, deciptions);
        return intent;
    }

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        setupList();
        emptyTeamError = Toast.makeText(this, getString(R.string.empty_team_error), Toast.LENGTH_SHORT);
        presenter.setView(this);
        presenter.setAutobots((List<Transformer>) getIntent().getSerializableExtra(AUTOBOTS_TEAM_KEY));
        presenter.setDecipticonss((List<Transformer>) getIntent().getSerializableExtra(DECEPTICONS_TEAM_KEY));
        presenter.start();
    }

    @Override
    protected void inject() {
        ((TransfArenaApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected BattlePresenter getPresenter() {
        return presenter;
    }

    /**
     * Sets adapter for teams display recyclerview
     *
     * @param adapter RecyclerView adapter
     * @see com.aequilibrium.assignment.transfarena.battle.adapter.BattleAdapter
     */
    @Override
    public void setBattleListAdapter(RecyclerView.Adapter adapter) {
        battleList.setAdapter(adapter);
    }

    /**
     * Displays battle result dialog with progress bar
     */
    @Override
    public void showResultDialog() {
        resultDialog = ResultDialogFragment.getInstance();
        resultDialog.show(getFragmentManager(), ResultDialogFragment.TAG);
    }

    /**
     * Inserts battle result in result dialog
     *
     * @param battlesCount amount of battles
     * @param winningTeam  name of winning team
     * @param losingTeam   name of loosing team
     * @param winners      list of winning transformers
     * @param survives     list of survived transformers
     */
    @Override
    public void setupResults(int battlesCount, String winningTeam, String losingTeam, List<Transformer> winners, List<Transformer> survives) {
        if (resultDialog != null && resultDialog.getDialog().isShowing()) {
            resultDialog.setupResults(battlesCount, winningTeam, losingTeam, winners, survives);
        }
    }

    /**
     * Shows "Total Destroy" as a result of the battle in result dialog
     */
    @Override
    public void notifyAboutTotalDestroy() {
        if (resultDialog != null && resultDialog.getDialog().isShowing()) {
            resultDialog.notifyAboutTotalDestroy();
        }
    }

    /**
     * Displays error of empty team
     */
    @Override
    public void showEmptyTeamError() {
        emptyTeamError.show();
    }

    /**
     * Shows "Tie" as a result of the battle in result dialog
     */
    @Override
    public void notifyAboutTie() {
        if (resultDialog != null && resultDialog.getDialog().isShowing()) {
            resultDialog.notifyAboutTie();
        }
    }

    /**
     * Begin battle button click
     */
    @OnClick(R.id.begin_battle)
    public void beginBattleClick() {
        presenter.onBeginBattleClicked();
    }

    private void setupList() {
        battleList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
