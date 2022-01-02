package com.example.feleves_projekt.Services;

import com.example.feleves_projekt.Models.Reservations;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReservationsServices {
/*
    implemented, not used
    @GET("reservations/{id}")
    @Headers({"Accept: application/json",
            "x-api-key: ValidApiKulcs"})
    public Call<Reservations> getReservation(@Path("id") Long id);
*/

    @GET("reservations/")
    @Headers({"Accept: application/json",
              "x-api-key: ValidApiKulcs"})
    Call<List<Reservations>> getReservationsByUser(@Query("userId") Long id);

/*
    implemented, not used
    @POST("reservations/")
    @Headers({"Accept: application/json",
            "x-api-key: ValidApiKulcs"})
    public Call<Reservations> addReservation(@Body Reservations data);
*/

    @PUT("reservations/{id}")
    @Headers({"Accept: application/json",
            "x-api-key: ValidApiKulcs"})
    Call<Reservations> updateReservation(@Path("id") Long id, @Body Reservations data);

/*
    implemented, not used
    @DELETE("reservations/{id}")
    @Headers({"Accept: application/json",
            "x-api-key: ValidApiKulcs"})
    public Call<Reservations> deleteReservation(@Path("id") Long id);
*/

}
