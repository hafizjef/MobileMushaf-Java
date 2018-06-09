package com.hafizjef.tasmuq.data;

import com.hafizjef.tasmuq.model.DetailResponse;
import com.hafizjef.tasmuq.model.ImageModel;
import com.hafizjef.tasmuq.model.MushafResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MushafApiService {
    @GET("user/{email}")
    Observable<MushafResponse> getMushaf(@Path("email") String email);

    @GET("details/{uuid}")
    Observable<DetailResponse> getDetails(@Path("uuid") String uuid);

    @POST("/")
    Observable<MushafResponse> postReport(@Body ImageModel Report);
}
