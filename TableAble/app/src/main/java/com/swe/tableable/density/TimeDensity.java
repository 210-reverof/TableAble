package com.swe.tableable.density;

public class TimeDensity{
    private int storeNum, hour;
    private double density;

    public TimeDensity(int storeNum, int hour, double density) {
        this.storeNum = storeNum;
        this.hour = hour;
        this.density = density;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

}
