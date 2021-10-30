package com.swe.tableable.density;

public class Density {
    private String placeName;
    private double latitude;
    private double longitude;
    private int density;

    public Density(String placeName, double latitude, double longitude, int density) {
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.density = density;
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

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }
}
