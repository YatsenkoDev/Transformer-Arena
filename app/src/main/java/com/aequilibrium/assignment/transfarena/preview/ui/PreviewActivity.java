package com.aequilibrium.assignment.transfarena.preview.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.TransfArenaApplication;
import com.aequilibrium.assignment.transfarena.base.ui.BaseActivity;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.preview.callback.DeleteConfirmationCallback;
import com.aequilibrium.assignment.transfarena.preview.presenter.PreviewPresenter;
import com.aequilibrium.assignment.transfarena.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class PreviewActivity extends BaseActivity implements PreviewView {

    private static final float NORMAL_ALPHA = 1;
    private static final float TRANSPARENT_ALPHA = 0.15f;
    private static final String TRANSFORMER_KEY = "TRANSFORMER_KEY";
    private static final String IS_AUTOBOT_KEY = "IS_AUTOBOT_KEY";

    private Toast nameRequiredToast;
    private boolean elementsEnabled = true;
    private boolean editingModeEnabled;

    @Inject
    PreviewPresenter presenter;

    @BindView(R.id.name_input)
    EditText nameInput;
    @BindView(R.id.name_static)
    TextView nameStatic;
    @BindView(R.id.autobot_team)
    ImageView autobotTeam;
    @BindView(R.id.decipticon_team)
    ImageView decipticonTeam;
    @BindView(R.id.parameters_list)
    RecyclerView parametersList;
    @BindView(R.id.loading_bar)
    ProgressBar loadingBar;

    public static Intent buildAddNewIntent(Context context, boolean isAutobot) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(IS_AUTOBOT_KEY, isAutobot);
        return intent;
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
        checkForInitialTeam();
        nameRequiredToast = Toast.makeText(this, R.string.name_required, Toast.LENGTH_SHORT);
        presenter.setView(this);
        presenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(nameInput.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(getTransformer() != null && !editingModeEnabled ? R.menu.edit_menu : R.menu.save_menu, menu);
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
        return nameInput.getText().toString();
    }

    @Override
    public Transformer getTransformer() {
        return (Transformer) getIntent().getSerializableExtra(TRANSFORMER_KEY);
    }

    @Override
    public void selectAutobotsTeam(boolean select) {
        autobotTeam.setAlpha(select ? NORMAL_ALPHA : TRANSPARENT_ALPHA);
        decipticonTeam.setAlpha(select ? TRANSPARENT_ALPHA : NORMAL_ALPHA);
    }

    @Override
    public void setupName(String name) {
        this.nameStatic.setText(name);
        nameInput.setText(name);
    }

    @Override
    public void enableElements(boolean enable) {
        elementsEnabled = enable;
        nameStatic.setVisibility(enable ? View.INVISIBLE : View.VISIBLE);
        nameInput.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showNameRequiredError() {
        nameRequiredToast.show();
    }

    @Override
    public void showConfirmationDialog(DeleteConfirmationCallback deleteConfirmationCallback, String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton(android.R.string.no, null);
        builder.setPositiveButton(android.R.string.yes, (dialogInterface, i) -> deleteConfirmationCallback.onDeleteConfirmed());
        builder.setMessage(getString(R.string.delete_confirmation, name));
        builder.create().show();
    }

    @OnClick(R.id.autobot_team)
    public void autobotTeamClicked() {
        if (elementsEnabled) {
            presenter.autobotTeamSelected();
            selectAutobotsTeam(true);
        }
    }

    @OnClick(R.id.decipticon_team)
    public void decipticonTeamClicked() {
        if (elementsEnabled) {
            presenter.decipticonTeamSelected();
            selectAutobotsTeam(false);
        }
    }

    private void setupParametersList() {
        parametersList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        parametersList.setItemViewCacheSize(Constants.PARAMETERS.size()); //to prevent view recycling and value loosing
    }

    public void setEditingModeEnabled(boolean editingModeEnabled) {
        this.editingModeEnabled = editingModeEnabled;
        invalidateOptionsMenu();
    }

    private void checkForInitialTeam() {
        if (getIntent().getBooleanExtra(IS_AUTOBOT_KEY, true)) {
            presenter.autobotTeamSelected();
        } else {
            presenter.decipticonTeamSelected();
        }
    }
}
