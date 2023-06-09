package com.duyle.lab8;

import java.io.Serializable;

public class SachModel implements Serializable {

    private String tenSach;
    private int year;
    private String tacGia;

    public SachModel(String tenSach, int year, String tacGia) {
        this.tenSach = tenSach;
        this.year = year;
        this.tacGia = tacGia;
    }


    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }
}
