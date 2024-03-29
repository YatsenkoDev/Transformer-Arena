package com.aequilibrium.assignment.transfarena.preview.service;

import com.aequilibrium.assignment.transfarena.api.RestApiClient;
import com.aequilibrium.assignment.transfarena.preview.callback.TransformerDeletedCallback;
import com.aequilibrium.assignment.transfarena.service.TokenService;
import com.aequilibrium.assignment.transfarena.utils.Constants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * New transformer deleting service
 */
public class TransformerDeleteService {

    private final RestApiClient restApiClient;
    private final TokenService tokenService;
    private Disposable disposable;

    /**
     * Constructor
     *
     * @param restApiClient REST client with deleting api
     * @param tokenService  token managing service
     */
    @Inject
    public TransformerDeleteService(RestApiClient restApiClient, TokenService tokenService) {
        this.restApiClient = restApiClient;
        this.tokenService = tokenService;
    }

    /**
     * Deteles transformer
     *
     * @param transformerDeletedCallback callback for deleting result
     * @param id                         transformer's id
     */
    public void deleteTransformer(TransformerDeletedCallback transformerDeletedCallback, String id) {
        tokenService.getToken(token -> makeTransformerCall(token, transformerDeletedCallback, id));
    }

    private void makeTransformerCall(String token, TransformerDeletedCallback transformerDeletedCallback, String id) {
        if (token == null) {
            transformerDeletedCallback.onTransformerDeleted(false);
        }
        disposable = restApiClient.deleteTransformer(token, Constants.JSON_CONTENT_TYPE, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> transformerDeletedCallback.onTransformerDeleted(true)
                        , error -> transformerDeletedCallback.onTransformerDeleted(true));
    }

    /**
     * Interrupts deleting process
     */
    public void interrupt() {
        if (disposable != null) {
            disposable.dispose();
        }
        tokenService.interrupt();
    }
}
