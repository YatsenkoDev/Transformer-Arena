package com.aequilibrium.assignment.transfarena.gallery;

import com.aequilibrium.assignment.transfarena.api.RestApiClient;
import com.aequilibrium.assignment.transfarena.gallery.callback.TokenReceivedCallback;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TokenService {

    private final RestApiClient restApiClient;
    private Disposable disposable;

    @Inject
    public TokenService(RestApiClient restApiClient) {
        this.restApiClient = restApiClient;
    }

    public void getToken(TokenReceivedCallback tokenReceivedCallback) {
        disposable = restApiClient.getToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> tokenReceivedCallback.onTokenReceived(result.string())
                        , error -> tokenReceivedCallback.onTokenReceived(null));
    }

    public void interrupt() {
        disposable.dispose();
    }
}
