package com.aequilibrium.assignment.transfarena.preview.service;

import com.aequilibrium.assignment.transfarena.api.RestApiClient;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.preview.callback.TransformerCreatedCallback;
import com.aequilibrium.assignment.transfarena.service.TokenService;
import com.aequilibrium.assignment.transfarena.utils.Constants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TransformerUpdatingService {

    private final RestApiClient restApiClient;
    private final TokenService tokenService;
    private Disposable disposable;

    @Inject
    public TransformerUpdatingService(RestApiClient restApiClient, TokenService tokenService) {
        this.restApiClient = restApiClient;
        this.tokenService = tokenService;
    }

    public void updateTransformer(TransformerCreatedCallback transformerCreatedCallback, Transformer transformer) {
        tokenService.getToken(token -> makeTransformerCall(token, transformerCreatedCallback, transformer));
    }

    private void makeTransformerCall(String token, TransformerCreatedCallback transformerCreatedCallback, Transformer transformer) {
        if (token == null) {
            transformerCreatedCallback.onTransformerSaved(null);
        }
        disposable = restApiClient.updateTransformer(token, Constants.JSON_CONTENT_TYPE, transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(transformerCreatedCallback::onTransformerSaved
                        , error -> transformerCreatedCallback.onTransformerSaved(null));
    }

    public void interrupt() {
        if (disposable != null) {
            disposable.dispose();
        }
        tokenService.interrupt();
    }
}
