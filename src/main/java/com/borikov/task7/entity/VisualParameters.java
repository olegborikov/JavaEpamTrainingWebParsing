package com.borikov.task7.entity;

public class VisualParameters {
    private String stemColor;
    private String leaveColor;
    private int averagePlantSize;

    public VisualParameters(String stemColor, String leaveColor,
                            int averagePlantSize) {
        this.stemColor = stemColor;
        this.leaveColor = leaveColor;
        this.averagePlantSize = averagePlantSize;
    }

    public String getStemColor() {
        return stemColor;
    }

    public void setStemColor(String stemColor) {
        this.stemColor = stemColor;
    }

    public String getLeaveColor() {
        return leaveColor;
    }

    public void setLeaveColor(String leaveColor) {
        this.leaveColor = leaveColor;
    }

    public int getAveragePlantSize() {
        return averagePlantSize;
    }

    public void setAveragePlantSize(int averagePlantSize) {
        this.averagePlantSize = averagePlantSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VisualParameters that = (VisualParameters) o;
        if (averagePlantSize != that.averagePlantSize) {
            return false;
        }
        if (stemColor != null ? !stemColor.equals(that.stemColor)
                : that.stemColor != null) {
            return false;
        }
        return leaveColor != null ? leaveColor.equals(that.leaveColor)
                : that.leaveColor == null;
    }

    @Override
    public int hashCode() {
        int result = stemColor != null ? stemColor.hashCode() : 0;
        result = 31 * result + (leaveColor != null ? leaveColor.hashCode() : 0);
        result = 31 * result + averagePlantSize;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VisualParameters{");
        sb.append("stemColor='").append(stemColor).append('\'');
        sb.append(", leaveColor='").append(leaveColor).append('\'');
        sb.append(", averagePlantSize=").append(averagePlantSize);
        sb.append('}');
        return sb.toString();
    }
}
