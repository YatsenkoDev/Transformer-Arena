package com.aequilibrium.assignment.transfarena.api;

import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.model.TransformersResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestApiClient {

    @GET("allspark")
    Observable<ResponseBody> getToken();

    @GET("transformers")
    Observable<TransformersResponse> getAllTransformers(@Header("Authorization") String token, @Header("Content-Typ") String contentType);

    @POST("transformers")
    Observable<Transformer> createTransformer(@Header("Authorization") String token, @Header("Content-Typ") String contentType, @Body Transformer transformer);

}
