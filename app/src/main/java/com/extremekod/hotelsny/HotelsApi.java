package com.extremekod.hotelsny;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HotelsApi {
    // Получаем с сайта список всех доступных отелей
    @GET("hotels.json")
    Call<List<Hotel>> getHotelsList();

    // Получаем дополнительные данные по определенному отелю
    @GET("add_info/{id}.json")
    Call<Hotel> getAdditionalHotelInfo(@Path("id") int version);
}
