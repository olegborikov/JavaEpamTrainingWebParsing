package com.borikov.task7.entity;

public abstract class Flower {
    private String name;
    private SoilType soilType;
    private VisualParameters visualParameters;
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;

    public Flower(String name, SoilType soilType, VisualParameters visualParameters) {
        this.name = name;
        this.soilType = soilType;
        this.visualParameters = visualParameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SoilType getSoilType() {
        return soilType;
    }

    public void setSoilType(SoilType soilType) {
        this.soilType = soilType;
    }

    public VisualParameters getVisualParameters() {
        return visualParameters;
    }

    public void setVisualParameters(VisualParameters visualParameters) {
        this.visualParameters = visualParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Flower flower = (Flower) o;
        if (name != null ? !name.equals(flower.name) : flower.name != null) {
            return false;
        }
        if (soilType != flower.soilType) {
            return false;
        }
        return visualParameters != null ? visualParameters.equals(flower.visualParameters)
                : flower.visualParameters == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (soilType != null ? soilType.hashCode() : 0);
        result = 31 * result + (visualParameters != null ? visualParameters.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("name='").append(name).append('\'');
        sb.append(", soilType=").append(soilType);
        sb.append(", visualParameters=").append(visualParameters);
        return sb.toString();
    }
}
