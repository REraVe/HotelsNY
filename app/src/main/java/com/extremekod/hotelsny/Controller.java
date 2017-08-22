package com.extremekod.hotelsny;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

    private static final String BASE_URL = "http://easyfun.hol.es/HotelsNY/";

    private HotelsApi hotelsApi;

    private Controller(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        hotelsApi = retrofit.create(HotelsApi.class);
    }

    private static class SingletonHelper{
        private static final Controller INSTANCE = new Controller();
    }

    public static Controller getInstance(){
        return SingletonHelper.INSTANCE;
    }

    public Observable<List<Hotel>> getHotelsList() {
        return Observable.create(e -> {
            Call<List<Hotel>> call = hotelsApi.getHotelsList();
            call.enqueue(new Callback<List<Hotel>>() {

                @Override
                public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                    if (response.isSuccessful()) {
                        e.onNext(response.body());
                    }
                    else {
                        e.onError(new Throwable());
                        Log.d("MyLog", "Код ошибки: " + response.code() + " Сообщение: " + response.message());
                    }
                    e.onComplete();
                }

                @Override
                public void onFailure(Call<List<Hotel>> call, Throwable t) {
                    t.printStackTrace();
                    e.onError(t);
                }
            });
        });
    }

    public Observable<Hotel> getAdditionalHotelInfo(Hotel hotel) {
        return Observable.create(e -> {
            Call<Hotel> call = hotelsApi.getAdditionalHotelInfo(hotel.getId());
            call.enqueue(new Callback<Hotel>() {

                @Override
                public void onResponse(Call<Hotel> call, Response<Hotel> response) {
                    if (response.isSuccessful()) {
                        e.onNext(response.body());
                    }
                    else {
                        e.onError(new Throwable());
                        Log.d("MyLog", "Код ошибки: " + response.code() + " Сообщение: " + response.message());
                    }
                    e.onComplete();
                }

                @Override
                public void onFailure(Call<Hotel> call, Throwable t) {
                    t.printStackTrace();
                    e.onError(t);
                }
            });
        });
    }

}
