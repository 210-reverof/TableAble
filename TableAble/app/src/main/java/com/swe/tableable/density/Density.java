package com.swe.tableable.density;

import java.io.Serializable;

public class Density implements Serializable {
    private int placeNum;
    private String placeName;
    private double latitude;
    private double longitude;
    private double density;

    public Density(int placeNum, String placeName, double latitude, double longitude, double density) {
        this.placeNum = placeNum;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.density = density;
    }

    public int getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(int placeNum) {
        this.placeNum = placeNum;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }
}
