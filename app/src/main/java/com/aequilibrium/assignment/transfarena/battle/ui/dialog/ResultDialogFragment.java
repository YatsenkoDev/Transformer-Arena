package com.aequilibrium.assignment.transfarena.battle.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultDialogFragment extends DialogFragment {

    public static final String TAG = ResultDialogFragment.class.getSimpleName();

    @BindView(R.id.loading_bar)
    ProgressBar loadingBar;
    @BindView(R.id.battles_count)
    TextView battlesCountView;
    @BindView(R.id.winning_team)
    TextView winningTeamView;
    @BindView(R.id.survivors)
    TextView survivorsView;

    public static ResultDialogFragment getInstance() {
        return new ResultDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.battle_result)
                .setPositiveButton(android.R.string.cancel, null);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_battle_result, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    public void setupResults(int battlesCount, String winningTeam, String losingTeam
            , List<Transformer> winners, List<Transformer> survives) {
        loadingBar.setVisibility(View.GONE);
        battlesCountView.setText(getString(battlesCount == 1 ? R.string.battles_count_singular : R.string.battles_count_plural, battlesCount));
        winningTeamView.setText(getString(R.string.winning_team, winningTeam, StringUtils.getTransformersNames(winners)));
        survivorsView.setText(getString(survives.isEmpty() ? R.string.no_survivors : R.string.survived, losingTeam, StringUtils.getTransformersNames(survives)));
    }

    public void notifyAboutTotalDestroy() {
        showSimpleMessage(R.string.total_destory);
    }

    public void notifyAboutTie() {
        showSimpleMessage(R.string.tie);
    }

    private void showSimpleMessage(int stringRes) {
        loadingBar.setVisibility(View.GONE);
        battlesCountView.setText(stringRes);
        winningTeamView.setVisibility(View.GONE);
        survivorsView.setVisibility(View.GONE);
    }
}
