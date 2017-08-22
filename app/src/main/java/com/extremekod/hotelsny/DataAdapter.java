package com.extremekod.hotelsny;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<Hotel> hotels;
    private LayoutInflater inflater;
    private Context context;

    public DataAdapter(Context context, List<Hotel> hotels) {
        this.hotels   = hotels;
        this.inflater = LayoutInflater.from(context);
        this.context  = context;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);

        String distance       = Float.toString(hotel.getDistance()) + " км";
        String suitesQuantity = Integer.toString(hotel.getArraySuitesAvailability().length);

        // Загружаем картинку отеля (без красной линии по периметру)
        Picasso.with(context)
                .load(hotel.getImage())
                .transform(new ImageSizeTransformation())
                .placeholder(R.drawable.pic_no_hotel)
                .error(R.drawable.pic_no_hotel)
                .into(holder.ivPhoto);

        holder.rbStars   .setRating(hotel.getStars());
        holder.tvName    .setText(hotel.getName());
        holder.tvAddress .setText(hotel.getAddress());
        holder.tvDistance.setText(distance);
        holder.tvSuites  .setText(suitesQuantity);
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivPhoto;
        final RatingBar rbStars;
        final TextView tvName, tvAddress, tvDistance, tvSuites;

        ViewHolder(View view){
            super(view);

            ivPhoto    = (ImageView) view.findViewById(R.id.ivPhoto);
            rbStars    = (RatingBar) view.findViewById(R.id.rbStars);
            tvName     = (TextView) view.findViewById(R.id.tvName);
            tvAddress  = (TextView) view.findViewById(R.id.tvAddress);
            tvDistance = (TextView) view.findViewById(R.id.tvDistance);
            tvSuites   = (TextView) view.findViewById(R.id.tvSuitesQuantity);
        }
    }
}