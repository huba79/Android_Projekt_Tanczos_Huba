package com.example.feleves_projekt.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkServices {

    private static final String BASE_URL = "http://192.168.50.199:8080/horgasztavam/";
    // emu: "http://10.0.2.2:8080/horgasztavam/"
    // home network: "http://192.168.50.199:8080/horgasztavam/"
    private static OkHttpClient client;

    public static Retrofit getApiServiceInstance() {
        //request logger hogy lassam a request headereket a logban. Szukseg volt ra.
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        /* Gson builder felepites hogy deszerializalni lehessen a string-kent megkapott OffsetDateTime-t */
        /*megoldast kell talalni a szerializalasra is*/
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, (JsonDeserializer<OffsetDateTime>)
                        (json, type, context) -> OffsetDateTime.parse(json.getAsString()))
                //szerializalasra
                .registerTypeAdapter(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
                    @Override
                    public JsonElement serialize(OffsetDateTime offsetDateTime, Type type, JsonSerializationContext context) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        return new JsonPrimitive(formatter.format(offsetDateTime) );
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .serializeNulls()
                .create();


        return new Retrofit
                .Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
