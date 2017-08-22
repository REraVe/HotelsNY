package com.extremekod.hotelsny;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HotelDetailsActivity extends AppCompatActivity {

    private ImageView ivPhoto;
    private RatingBar rbStars;
    private TextView tvName, tvAddress, tvDistance, tvSuites;
    private ImageButton btnBack;

    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);

        initializeActivity();

        loadInformation();
    }

    @Override
    protected void onDestroy() {
        // Обнуляем ImageView, что бы не было утечек памяти
        ivPhoto.setImageBitmap(null);
        btnBack.setImageBitmap(null);

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.alpha_out);

        super.onBackPressed();
    }

    private void initializeActivity() {
        ivPhoto    = (ImageView) findViewById(R.id.ivPhoto);
        rbStars    = (RatingBar) findViewById(R.id.rbStars);
        tvName     = (TextView) findViewById(R.id.tvName);
        tvAddress  = (TextView) findViewById(R.id.tvAddress);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvSuites   = (TextView) findViewById(R.id.tvSuites);
        btnBack    = (ImageButton) findViewById(R.id.btnBack);
    }

    private void loadInformation() {
        hotel = (Hotel) getIntent().getSerializableExtra(Hotel.HOTEL_EXTRA);

        String distance       = Float.toString(hotel.getDistance()) + " км";
        String suitesQuantity = hotel.getSuitesAvailability().replace(":", "; ");

        Controller.getInstance().getAdditionalHotelInfo(hotel)
                .subscribe(hotelResponse -> {
                    if (hotelResponse != null) {
                        // Добавляем дополнительные данные в созданые ранее объекты
                        hotel.setLat(hotelResponse.getLat());
                        hotel.setLon(hotelResponse.getLon());
                    }
                });

        Picasso.with(this)
                .load(hotel.getImage())
                .transform(new ImageSizeTransformation())
                .placeholder(R.drawable.pic_no_hotel)
                .error(R.drawable.pic_no_hotel)
                .into(ivPhoto);

        rbStars   .setRating(hotel.getStars());
        tvName    .setText(hotel.getName());
        tvAddress .setText(hotel.getAddress());
        tvDistance.setText(distance);
        tvSuites  .setText(suitesQuantity);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.btnMap:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:" + hotel.getLat() + "," + hotel.getLon()));
                startActivity(intent);
                break;
        }
    }

}
