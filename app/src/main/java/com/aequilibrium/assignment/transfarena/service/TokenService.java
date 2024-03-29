package com.aequilibrium.assignment.transfarena.service;

import android.content.SharedPreferences;

import com.aequilibrium.assignment.transfarena.api.RestApiClient;
import com.aequilibrium.assignment.transfarena.service.callback.TokenReceivedCallback;
import com.aequilibrium.assignment.transfarena.utils.Constants;
import com.aequilibrium.assignment.transfarena.utils.PreferencesUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Token managing service
 */
public class TokenService {

    private final RestApiClient restApiClient;
    private final SharedPreferences sharedPreferences;
    private Disposable disposable;

    /**
     * Constructor
     *
     * @param restApiClient     REST client with token api
     * @param sharedPreferences default shared preferences for token saving
     */
    @Inject
    public TokenService(RestApiClient restApiClient, SharedPreferences sharedPreferences) {
        this.restApiClient = restApiClient;
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * Downloads new or reads saved token
     *
     * @param tokenReceivedCallback callback for token value
     */
    public void getToken(TokenReceivedCallback tokenReceivedCallback) {
        if (PreferencesUtils.getToken(sharedPreferences) != null) {
            tokenReceivedCallback.onTokenReceived(PreferencesUtils.getToken(sharedPreferences));
        } else {
            disposable = restApiClient.getToken()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                                String token = Constants.TOKEN_PREFIX + result.string();
                                tokenReceivedCallback.onTokenReceived(token);
                                PreferencesUtils.saveToken(token, sharedPreferences);
                            }
                            , error -> tokenReceivedCallback.onTokenReceived(null));
        }
    }

    /**
     * Interrupts token downloading
     */
    public void interrupt() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
