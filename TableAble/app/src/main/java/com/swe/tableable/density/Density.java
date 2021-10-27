package com.swe.tableable.density;

public class Density {
    private String placeName;
    private double distance;
    private int density;

    public Density(String placeName, double distance, int density) {
        this.placeName = placeName;
        this.distance = distance;
        this.density = density;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }
}
