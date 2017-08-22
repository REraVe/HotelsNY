
package com.extremekod.hotelsny;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

public class Hotel implements Serializable {

    public static final String HOTEL_EXTRA = "com.alexey.galich.testtaskhotels";
    private static final int METERS_IN_KM = 1000;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("stars")
    @Expose
    private Float stars;

    @SerializedName("distance")
    @Expose
    private Float distance;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("suites_availability")
    @Expose
    private String suitesAvailability;

    @SerializedName("lat")
    @Expose
    private Float lat;

    @SerializedName("lon")
    @Expose
    private Float lon;

    private String[] arraySuitesAvailability; // Массив свободных номеров

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getStars() {
        return stars;
    }

    public void setStars(Float stars) {
        this.stars = stars;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSuitesAvailability() {
        return suitesAvailability;
    }

    public void setSuitesAvailability(String suitesAvailability) {
        this.suitesAvailability = suitesAvailability;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String[] getArraySuitesAvailability() {
        return arraySuitesAvailability;
    }

    public void setArraySuitesAvailability(String[] arraySuitesAvailability) {
        this.arraySuitesAvailability = arraySuitesAvailability;
    }

    // Для сравнения отелей по расстоянию до центра города
    public static Comparator<Hotel> distanceComparator = new Comparator<Hotel>() {
        @Override
        public int compare(Hotel hotel1, Hotel hotel2) {
            // Переводим километры в метры, что бы избежать округления при преобразовании float в int
            return (int) ((hotel1.getDistance() - hotel2.getDistance()) * METERS_IN_KM);
        }
    };

    // Для сравнения отелей по количеству свободных номеров
    public static Comparator<Hotel> suitesAvailabilityComparator = new Comparator<Hotel>() {
        @Override
        public int compare(Hotel hotel1, Hotel hotel2) {
            return hotel1.getArraySuitesAvailability().length - hotel2.getArraySuitesAvailability().length;
        }
    };

}
