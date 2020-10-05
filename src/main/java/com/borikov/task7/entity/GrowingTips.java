package com.borikov.task7.entity;

public class GrowingTips {
    private int temperature;
    private boolean needLight;
    private int waterPerWeek;

    public GrowingTips() {
    }

    public GrowingTips(int temperature, boolean needLight, int waterPerWeek) {
        this.temperature = temperature;
        this.needLight = needLight;
        this.waterPerWeek = waterPerWeek;
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

    public int getWaterPerWeek() {
        return waterPerWeek;
    }

    public void setWaterPerWeek(int waterPerWeek) {
        this.waterPerWeek = waterPerWeek;
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
        return waterPerWeek == that.waterPerWeek;
    }

    @Override
    public int hashCode() {
        int result = temperature;
        result = 31 * result + (needLight ? 1 : 0);
        result = 31 * result + waterPerWeek;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GrowingTips{");
        sb.append("temperature=").append(temperature);
        sb.append(", needLight=").append(needLight);
        sb.append(", watterPerWeek=").append(waterPerWeek);
        sb.append('}');
        return sb.toString();
    }
}
