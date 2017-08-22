package com.extremekod.hotelsny;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HotelsActivity extends AppCompatActivity {

    private static final String BASE_IMAGE_URL = "http://easyfun.hol.es/HotelsNY/foto/";

    private Context context;
    private ProgressBar progressBar;
    private DataAdapter adapter;

    private List<Hotel> hotelsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);

        initializeActivity();

        Controller.getInstance().getHotelsList()
                .subscribe(hotels -> {
                    updateHotelsList(hotels);
                    progressBar.setVisibility(View.GONE);
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.alpha_out);

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hotels, menu); // Добавляем меню, с помощью которого можно сортировать элементы списка
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_sortByDistance:
                Collections.sort(hotelsList, Hotel.distanceComparator);           // Сортируем отели по расстоянию до центра города
                updateRecyclerView();
                break;

            case R.id.action_sortBySuitesAvailability:
                Collections.sort(hotelsList, Hotel.suitesAvailabilityComparator); // Сортируем отели по количеству свободных номеров
                updateRecyclerView();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeActivity() {
        context = this;
        adapter = new DataAdapter(context, hotelsList);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Обрабатываем событие нажатия на определенный отель в списке
                Intent intent = new Intent(context, HotelDetailsActivity.class);
                intent.putExtra(Hotel.HOTEL_EXTRA, hotelsList.get(position));
                context.startActivity(intent);

                overridePendingTransition(R.anim.alpha_in, R.anim.zoom_in);
            }
        }));
    }

    private void updateHotelsList(List<Hotel> hotels) {
        if (hotels != null) {
            for (Hotel hotel : hotels) {
                hotel.setArraySuitesAvailability(hotel.getSuitesAvailability().split(":")); // Добавляем свободные номера в отдельный массив для более удобной работы с ними
                hotel.setImage(BASE_IMAGE_URL + hotel.getImage());                          // Добавляем полный путь к картинке
            }

            hotelsList = hotels;
            updateRecyclerView();
        }
    }

    private void updateRecyclerView() {
        adapter.setHotels(hotelsList);
        adapter.notifyDataSetChanged();
    }
}
