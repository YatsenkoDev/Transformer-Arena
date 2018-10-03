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

public class BattleActivity extends BaseActivity implements BattleView {

    private static final String AUTOBOTS_TEAM_KEY = "AUTOBOTS_TEAM_KEY";
    private static final String DECEPTICONS_TEAM_KEY = "DECEPTICONS_TEAM_KEY";
    @Inject
    BattlePresenter presenter;
    @BindView(R.id.battle_list)
    RecyclerView battleList;
    private ResultDialogFragment resultDialog;
    private Toast emptyTeamError;

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


    @Override
    public void setBattleListAdapter(RecyclerView.Adapter adapter) {
        battleList.setAdapter(adapter);
    }

    @Override
    public void showResultDialog() {
        resultDialog = ResultDialogFragment.getInstance();
        resultDialog.show(getFragmentManager(), ResultDialogFragment.TAG);
    }

    @Override
    public void setupResults(int battlesCount, String winningTeam, String losingTeam, List<Transformer> winners, List<Transformer> survives) {
        if (resultDialog != null && resultDialog.getDialog().isShowing()) {
            resultDialog.setupResults(battlesCount, winningTeam, losingTeam, winners, survives);
        }
    }

    @Override
    public void notifyAboutTotalDestroy() {
        if (resultDialog != null && resultDialog.getDialog().isShowing()) {
            resultDialog.notifyAboutTotalDestroy();
        }
    }

    @Override
    public void showEmptyTeamError() {
        emptyTeamError.show();
    }

    @Override
    public void notifyAboutTie() {
        if (resultDialog != null && resultDialog.getDialog().isShowing()) {
            resultDialog.notifyAboutTie();
        }
    }

    @OnClick(R.id.begin_battle)
    public void beginBattleClick() {
        presenter.onBeginBattleClicked();
    }

    private void setupList() {
        battleList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
