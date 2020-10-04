package com.borikov.task7.entity;

public class WildFlower extends Flower {
    private MultiplyingType multiplyingType;
    private String origin;

    public WildFlower() {
    }

    public WildFlower(String name, SoilType soilType,
                      VisualParameters visualParameters,
                      MultiplyingType multiplyingType, String origin) {
        super(name, soilType, visualParameters);
        this.multiplyingType = multiplyingType;
        this.origin = origin;
    }

    public MultiplyingType getMultiplyingType() {
        return multiplyingType;
    }

    public void setMultiplyingType(MultiplyingType multiplyingType) {
        this.multiplyingType = multiplyingType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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
        WildFlower that = (WildFlower) o;
        if (multiplyingType != that.multiplyingType) {
            return false;
        }
        return origin != null ? origin.equals(that.origin) : that.origin == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (multiplyingType != null ? multiplyingType.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WildFlower{");
        sb.append(super.toString());
        sb.append("multiplyingType=").append(multiplyingType);
        sb.append(", origin='").append(origin).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
