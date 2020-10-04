package com.borikov.task7.entity;

public class GrowingTips {
    private int temperature;
    private boolean needLight;
    private int watterPerWeek;

    public GrowingTips(int temperature, boolean needLight, int watterPerWeek) {
        this.temperature = temperature;
        this.needLight = needLight;
        this.watterPerWeek = watterPerWeek;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public boolean isNeedLight() {
        return needLight;
    }

    public void setNeedLight(boolean needLight) {
        this.needLight = needLight;
    }

    public int getWatterPerWeek() {
        return watterPerWeek;
    }

    public void setWatterPerWeek(int watterPerWeek) {
        this.watterPerWeek = watterPerWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GrowingTips that = (GrowingTips) o;
        if (temperature != that.temperature) {
            return false;
        }
        if (needLight != that.needLight) {
            return false;
        }
        return watterPerWeek == that.watterPerWeek;
    }

    @Override
    public int hashCode() {
        int result = temperature;
        result = 31 * result + (needLight ? 1 : 0);
        result = 31 * result + watterPerWeek;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GrowingTips{");
        sb.append("temperature=").append(temperature);
        sb.append(", needLight=").append(needLight);
        sb.append(", watterPerWeek=").append(watterPerWeek);
        sb.append('}');
        return sb.toString();
    }
}
