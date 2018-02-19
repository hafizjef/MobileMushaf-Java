package com.hafizjef.tasmuq.data;

import com.hafizjef.tasmuq.model.DetailResponse;
import com.hafizjef.tasmuq.model.MushafResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MushafApiService {
    @GET("user/{email}")
    Observable<MushafResponse> getMushaf(@Path("email") String email);

    @GET("details/{uuid}")
    Observable<DetailResponse> getDetails(@Path("uuid") String uuid);
}
