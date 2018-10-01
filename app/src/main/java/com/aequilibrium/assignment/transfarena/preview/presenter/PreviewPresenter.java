package com.aequilibrium.assignment.transfarena.preview.presenter;

import android.content.Context;
import android.content.Intent;

import com.aequilibrium.assignment.transfarena.base.presenter.BasePresenter;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.preview.adapter.ParameterListAdapter;
import com.aequilibrium.assignment.transfarena.preview.service.TransformerCreatingService;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewView;
import com.aequilibrium.assignment.transfarena.utils.Constants;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class PreviewPresenter implements BasePresenter {

    public static final String TRANSFORMER_KEY = "TRANSFORMER_KEY";
    private final Context context;
    private final TransformerCreatingService transformerCreatingService;
    private PreviewView view;
    private ParameterListAdapter adapter;
    private boolean isAutobot = true;

    @Inject
    public PreviewPresenter(Context context, TransformerCreatingService transformerCreatingService) {
        this.context = context;
        this.transformerCreatingService = transformerCreatingService;
    }

    public void setView(PreviewView view) {
        this.view = view;
    }

    public void start() {
        adapter = new ParameterListAdapter(context);
        view.setParametersListAdapter(adapter);
    }

    @Override
    public void onViewDestroyed() {
        transformerCreatingService.interrupt();
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
    }
}
