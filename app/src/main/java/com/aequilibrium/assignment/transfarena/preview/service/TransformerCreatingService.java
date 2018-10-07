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

/**
 * New transformer saving service
 */
public class TransformerCreatingService {

    private final RestApiClient restApiClient;
    private final TokenService tokenService;
    private Disposable disposable;

    /**
     * Constructor
     *
     * @param restApiClient REST client with creation api
     * @param tokenService  token managing service
     */
    @Inject
    public TransformerCreatingService(RestApiClient restApiClient, TokenService tokenService) {
        this.restApiClient = restApiClient;
        this.tokenService = tokenService;
    }

    /**
     * Saves new transformer
     *
     * @param transformerCreatedCallback callback of saving result
     * @param transformer                object to save
     */
    public void createTransformer(TransformerCreatedCallback transformerCreatedCallback, Transformer transformer) {
        tokenService.getToken(token -> makeTransformerCall(token, transformerCreatedCallback, transformer));
    }

    private void makeTransformerCall(String token, TransformerCreatedCallback transformerCreatedCallback, Transformer transformer) {
        if (token == null) {
            transformerCreatedCallback.onTransformerSaved(null);
        }
        disposable = restApiClient.createTransformer(token, Constants.JSON_CONTENT_TYPE, transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(transformerCreatedCallback::onTransformerSaved
                        , error -> transformerCreatedCallback.onTransformerSaved(null));
    }

    /**
     * Interrupts saving process
     */
    public void interrupt() {
        if (disposable != null) {
            disposable.dispose();
        }
        tokenService.interrupt();
    }
}
