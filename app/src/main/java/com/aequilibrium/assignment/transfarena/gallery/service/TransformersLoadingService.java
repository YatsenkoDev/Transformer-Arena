package com.aequilibrium.assignment.transfarena.gallery.service;

import com.aequilibrium.assignment.transfarena.api.RestApiClient;
import com.aequilibrium.assignment.transfarena.gallery.callback.TransformersLoadedCallback;
import com.aequilibrium.assignment.transfarena.service.TokenService;
import com.aequilibrium.assignment.transfarena.utils.Constants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TransformersLoadingService {

    private final RestApiClient restApiClient;
    private final TokenService tokenService;
    private Disposable disposable;

    @Inject
    public TransformersLoadingService(RestApiClient restApiClient, TokenService tokenService) {
        this.restApiClient = restApiClient;
        this.tokenService = tokenService;
    }

    public void loadTransformers(TransformersLoadedCallback transformersLoadedCallback) {
        tokenService.getToken(token -> makeTransformersCall(token, transformersLoadedCallback));
    }

    private void makeTransformersCall(String token, TransformersLoadedCallback transformersLoadedCallback) {
        if (token == null) {
            transformersLoadedCallback.onTransformersLoaded(null);
        }
        disposable = restApiClient.getAllTransformers(token, Constants.JSON_CONTENT_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> transformersLoadedCallback.onTransformersLoaded(response.getTransformers())
                        , error -> transformersLoadedCallback.onTransformersLoaded(null));
    }

    public void interrupt() {
        if (disposable != null) {
            disposable.dispose();
        }
        tokenService.interrupt();
    }
}
