package com.aequilibrium.assignment.transfarena.preview.presenter;

import android.content.Context;
import android.content.Intent;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.preview.adapter.ParameterListAdapter;
import com.aequilibrium.assignment.transfarena.preview.service.TransformerCreatingService;
import com.aequilibrium.assignment.transfarena.preview.service.TransformerDeleteService;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewView;
import com.aequilibrium.assignment.transfarena.utils.Constants;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class PreviewPresenter implements BasePresenter {

    public static final String TRANSFORMER_KEY = "TRANSFORMER_KEY";
    public static final String TRANSFORMER_ID_KEY = "TRANSFORMER_ID_KEY";
    private final Context context;
    private final TransformerCreatingService transformerCreatingService;
    private final TransformerDeleteService transformerDeleteService;
    private PreviewView view;
    private ParameterListAdapter adapter;
    private boolean isAutobot = true;

    @Inject
    public PreviewPresenter(Context context, TransformerCreatingService transformerCreatingService, TransformerDeleteService transformerDeleteService) {
        this.context = context;
        this.transformerCreatingService = transformerCreatingService;
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
            transformerCreatingService.createTransformer(this::transformerCreated, getTransformerFromParams());
        }
    }

    private Transformer getTransformerFromParams() {
        return new Transformer(view.getName()
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
            view.setResult(RESULT_OK, intent);
            view.finish();
        } else {
            view.showConnectionErrorMessage();
        }
    }

    public void onEditClicked() {
    }

    public void onDeleteClicked() {
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
