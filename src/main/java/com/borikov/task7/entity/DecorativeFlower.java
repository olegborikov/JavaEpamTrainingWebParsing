package com.borikov.task7.entity;

import java.util.Date;

public class DecorativeFlower extends Flower {
    private GrowingTips growingTips;
    private Date dateOfLanding;

    public DecorativeFlower() {
        growingTips = new GrowingTips();
    }

    public DecorativeFlower(String name, SoilType soilType,
                            VisualParameters visualParameters,
                            GrowingTips growingTips, Date dateOfLanding) {
        super(name, soilType, visualParameters);
        this.growingTips = growingTips;
        this.dateOfLanding = dateOfLanding;
    }

    public GrowingTips getGrowingTips() {
        return growingTips;
    }

    public void setGrowingTips(GrowingTips growingTips) {
        this.growingTips = growingTips;
    }

    public Date getDateOfLanding() {
        return dateOfLanding;
    }

    public void setDateOfLanding(Date dateOfLanding) {
        this.dateOfLanding = dateOfLanding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DecorativeFlower that = (DecorativeFlower) o;
        if (growingTips != null ? !growingTips.equals(that.growingTips)
                : that.growingTips != null) {
            return false;
        }
        return dateOfLanding != null ? dateOfLanding.equals(that.dateOfLanding)
                : that.dateOfLanding == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (growingTips != null ? growingTips.hashCode() : 0);
        result = 31 * result + (dateOfLanding != null ? dateOfLanding.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DecorativeFlower{");
        sb.append(super.toString());
        sb.append("growingTips=").append(growingTips);
        sb.append(", dateOfLanding=").append(dateOfLanding);
        sb.append('}');
        return sb.toString();
    }
}
