package com.aequilibrium.assignment.transfarena.preview.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.TransfArenaApplication;
import com.aequilibrium.assignment.transfarena.base.ui.BaseActivity;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.preview.presenter.PreviewPresenter;
import com.aequilibrium.assignment.transfarena.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class PreviewActivity extends BaseActivity implements PreviewView {

    private static final float NORMAL_ALPHA = 1;
    private static final float TRANSPARENT_ALPHA = 0.15f;
    private static final String TRANSFORMER_KEY = "TRANSFORMER_KEY";

    private Toast nameRequiredToast;

    @Inject
    PreviewPresenter presenter;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.autobot_team)
    ImageView autobotTeam;
    @BindView(R.id.decipticon_team)
    ImageView decipticonTeam;
    @BindView(R.id.parameters_list)
    RecyclerView parametersList;
    @BindView(R.id.loading_bar)
    ProgressBar loadingBar;

    public static Intent buildAddNewIntent(Context context) {
        return new Intent(context, PreviewActivity.class);
    }

    public static Intent buildAddNewIntent(Context context, Transformer transformer) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(TRANSFORMER_KEY, transformer);
        return intent;
    }

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        setupParametersList();
        nameRequiredToast = Toast.makeText(this, R.string.name_required, Toast.LENGTH_SHORT);
        presenter.setView(this);
        presenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(name.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(getIntent().getSerializableExtra(TRANSFORMER_KEY) != null ? R.menu.edit_menu : R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: {
                presenter.onSaveClicked();
                break;
            }
            case R.id.edit: {
                presenter.onEditClicked();
                break;
            }
            case R.id.delete: {
                presenter.onDeleteClicked();
                break;
            }
        }
        return true;
    }

    @Override
    protected void inject() {
        ((TransfArenaApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected PreviewPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showLoading() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void setParametersListAdapter(RecyclerView.Adapter adapter) {
        parametersList.setAdapter(adapter);
    }

    @Override
    public String getName() {
        return name.getText().toString();
    }

    @Override
    public void showNameRequiredError() {
        nameRequiredToast.show();
    }

    @OnClick(R.id.autobot_team)
    public void autobotTeamClicked() {
        autobotTeam.setAlpha(NORMAL_ALPHA);
        decipticonTeam.setAlpha(TRANSPARENT_ALPHA);
        presenter.autobotTeamClicked();
    }

    @OnClick(R.id.decipticon_team)
    public void decipticonTeamClicked() {
        autobotTeam.setAlpha(TRANSPARENT_ALPHA);
        decipticonTeam.setAlpha(NORMAL_ALPHA);
        presenter.decipticonTeamClicked();
    }

    private void setupParametersList() {
        parametersList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        parametersList.setItemViewCacheSize(Constants.PARAMETERS.size()); //to prevent view recycling and value loosing
    }
}
