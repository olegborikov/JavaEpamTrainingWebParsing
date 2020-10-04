package com.borikov.task7.entity;

public class WildFlower extends Flower {
    private String origin;
    private MultiplyingType multiplyingType;

    public WildFlower(String name, SoilType soilType,
                      VisualParameters visualParameters,
                      String origin, MultiplyingType multiplyingType) {
        super(name, soilType, visualParameters);
        this.origin = origin;
        this.multiplyingType = multiplyingType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public MultiplyingType getMultiplyingType() {
        return multiplyingType;
    }

    public void setMultiplyingType(MultiplyingType multiplyingType) {
        this.multiplyingType = multiplyingType;
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
        if (origin != null ? !origin.equals(that.origin) : that.origin != null) {
            return false;
        }
        return multiplyingType == that.multiplyingType;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (multiplyingType != null ? multiplyingType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WildFlower{");
        sb.append(super.toString());
        sb.append("origin='").append(origin).append('\'');
        sb.append(", multiplyingType=").append(multiplyingType);
        sb.append('}');
        return sb.toString();
    }
}
