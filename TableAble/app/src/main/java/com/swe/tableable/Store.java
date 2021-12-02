package com.swe.tableable;

import java.io.Serializable;

public class Store implements Serializable {
    private int storeNum;
    private String storeName;
    private Double latitude;
    private Double longitude;
    private String address;
    private int table1Cnt;
    private int table1Sit;
    private int table2Cnt;
    private int table2Sit;
    private int table4Cnt;
    private int table4Sit;
    private double sitAvg;

    public Store(int storeNum, String storeName, Double latitude, Double longitude, String address, int table1Cnt, int table1Sit, int table2Cnt, int table2Sit, int table4Cnt, int table4Sit, double sitAvg) {
        this.storeNum = storeNum;
        this.storeName = storeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.table1Cnt = table1Cnt;
        this.table1Sit = table1Sit;
        this.table2Cnt = table2Cnt;
        this.table2Sit = table2Sit;
        this.table4Cnt = table4Cnt;
        this.table4Sit = table4Sit;
        this.sitAvg = sitAvg;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTable1Cnt() {
        return table1Cnt;
    }

    public void setTable1Cnt(int table1Cnt) {
        this.table1Cnt = table1Cnt;
    }

    public int getTable1Sit() {
        return table1Sit;
    }

    public void setTable1Sit(int table1Sit) {
        this.table1Sit = table1Sit;
    }

    public int getTable2Cnt() {
        return table2Cnt;
    }

    public void setTable2Cnt(int table2Cnt) {
        this.table2Cnt = table2Cnt;
    }

    public int getTable2Sit() {
        return table2Sit;
    }

    public void setTable2Sit(int table2Sit) {
        this.table2Sit = table2Sit;
    }

    public int getTable4Cnt() {
        return table4Cnt;
    }

    public void setTable4Cnt(int table4Cnt) {
        this.table4Cnt = table4Cnt;
    }

    public int getTable4Sit() {
        return table4Sit;
    }

    public void setTable4Sit(int table4Sit) {
        this.table4Sit = table4Sit;
    }

    public double getSitAvg() {
        return sitAvg;
    }

    public void setSitAvg(double sitAvg) {
        this.sitAvg = sitAvg;
    }
}
