package com.aequilibrium.assignment.transfarena.preview.presenter;

import android.content.Context;
import android.content.Intent;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.preview.adapter.ParameterListAdapter;
import com.aequilibrium.assignment.transfarena.preview.service.TransformerCreatingService;
import com.aequilibrium.assignment.transfarena.preview.service.TransformerDeleteService;
import com.aequilibrium.assignment.transfarena.preview.service.TransformerUpdatingService;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewView;
import com.aequilibrium.assignment.transfarena.utils.Constants;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class PreviewPresenter implements BasePresenter {

    public static final String TRANSFORMER_KEY = "TRANSFORMER_KEY";
    public static final String TRANSFORMER_ID_KEY = "TRANSFORMER_ID_KEY";
    public static final String TRANSFORMER_UPDATE_KEY = "TRANSFORMER_UPDATE_KEY";
    private final Context context;
    private final TransformerCreatingService transformerCreatingService;
    private final TransformerUpdatingService transformerUpdatingService;
    private final TransformerDeleteService transformerDeleteService;
    private PreviewView view;
    private ParameterListAdapter adapter;
    private boolean isAutobot = true;
    private boolean editingModeEnabled;

    @Inject
    public PreviewPresenter(Context context, TransformerCreatingService transformerCreatingService, TransformerUpdatingService transformerUpdatingService, TransformerDeleteService transformerDeleteService) {
        this.context = context;
        this.transformerCreatingService = transformerCreatingService;
        this.transformerUpdatingService = transformerUpdatingService;
        this.transformerDeleteService = transformerDeleteService;
    }

    public void setView(PreviewView view) {
        this.view = view;
    }

    public void start() {
        adapter = new ParameterListAdapter(context, view.getTransformer());
        isAutobot = view.getTransformer() == null || isAutobot(view.getTransformer().getTeam());
        view.selectAutobotsTeam(isAutobot);
        if (view.getTransformer() != null) {
            view.setupName(view.getTransformer().getName());
            view.enableElements(false);
            adapter.setElementsEnabled(false);
        }
        view.setParametersListAdapter(adapter);
    }

    @Override
    public void onViewDestroyed() {
        transformerCreatingService.interrupt();
        transformerDeleteService.interrupt();
        transformerUpdatingService.interrupt();
    }

    public void autobotTeamClicked() {
        isAutobot = true;
    }

    public void decipticonTeamClicked() {
        isAutobot = false;
    }

    public void onSaveClicked() {
        if (view.getName().isEmpty()) {
            view.showNameRequiredError();
        } else {
            view.showLoading();
            if (editingModeEnabled) {
                transformerUpdatingService.updateTransformer(this::transformerCreated, getTransformerFromParams(true));
            } else {
                transformerCreatingService.createTransformer(this::transformerCreated, getTransformerFromParams(false));
            }
        }
    }

    public void onDeleteClicked() {
        view.showConfirmationDialog(this::onDeleteConfirmed, view.getTransformer().getName());
    }

    public void onEditClicked() {
        editingModeEnabled = true;
        view.enableElements(true);
        adapter.setElementsEnabled(true);
        adapter.notifyDataSetChanged();
        view.setEditingModeEnabled(true);
    }

    private Transformer getTransformerFromParams(boolean withId) {
        return new Transformer(withId && view.getTransformer() != null ? view.getTransformer().getId() : null
                , view.getName()
                , adapter.getParametersValues().get(0)
                , adapter.getParametersValues().get(1)
                , adapter.getParametersValues().get(2)
                , adapter.getParametersValues().get(3)
                , adapter.getParametersValues().get(4)
                , adapter.getParametersValues().get(5)
                , adapter.getParametersValues().get(6)
                , adapter.getParametersValues().get(7)
                , getTeam());
    }

    private String getTeam() {
        return isAutobot ? Constants.AUTOBOTS_TEAM_KEY : Constants.DECEPTICONS_TEAM_KEY;
    }

    private boolean isAutobot(String team) {
        return !Constants.DECEPTICONS_TEAM_KEY.equals(team);
    }

    private void transformerCreated(Transformer transformer) {
        if (transformer != null) {
            view.hideLoading();
            Intent intent = new Intent();
            intent.putExtra(TRANSFORMER_KEY, transformer);
            intent.putExtra(TRANSFORMER_ID_KEY, transformer.getId());
            intent.putExtra(TRANSFORMER_UPDATE_KEY, editingModeEnabled);
            view.setResult(RESULT_OK, intent);
            view.finish();
        } else {
            view.showConnectionErrorMessage();
        }
    }

    private void onDeleteConfirmed() {
        view.showLoading();
        transformerDeleteService.deleteTransformer(this::onDeleted, view.getTransformer().getId());
    }

    private void onDeleted(boolean success) {
        if (success) {
            view.hideLoading();
            Intent intent = new Intent();
            intent.putExtra(TRANSFORMER_ID_KEY, view.getTransformer().getId());
            view.setResult(RESULT_OK, intent);
            view.finish();
        } else {
            view.showConnectionErrorMessage();
        }
    }
}
