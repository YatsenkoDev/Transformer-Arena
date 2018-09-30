package com.aequilibrium.assignment.transfarena.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface RestApiClient {

    @GET("allspark")
    Observable<ResponseBody> getToken();

}
