package com.borikov.task7.builder;

public enum FlowerXmlTag {
    GREENHOUSE("greenhouse"),
    DECORATIVE_FLOWER("decorative-flower"),
    WILD_FLOWER("wild-flower"),
    NAME("name"),
    SOIL("soil"),
    VISUAL_PARAMETERS("visual-parameters"),
    GROWING_TIPS("growing-tips"),
    TEMPERATURE("temperature"),
    NEED_LIGHT("need-light"),
    WATER_PER_WEEK("water-per-week"),
    DATE_OF_LANDING("date-of-landing"),
    STEM_COLOR("stem-color"),
    LEAVE_COLOR("leave-color"),
    AVERAGE_PLANT_SIZE("average-plant-size"),
    MULTIPLYING("multiplying"),
    ORIGIN("origin");

    private final String value;

    FlowerXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
