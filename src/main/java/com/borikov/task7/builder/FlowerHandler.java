package com.borikov.task7.builder;

import com.borikov.task7.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class FlowerHandler extends DefaultHandler {
    private Set<Flower> flowers;
    private Flower currentFlower;
    private FlowerXmlTag currentXmlTag;
    private EnumSet<FlowerXmlTag> tagsWithText;
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final char OLD_SYMBOL_REPLACE = '-';
    private static final char NEW_SYMBOL_REPLACE = '_';

    public FlowerHandler() {
        flowers = new HashSet<>();
        tagsWithText = EnumSet.range(FlowerXmlTag.TEMPERATURE, FlowerXmlTag.ORIGIN);
    }

    public Set<Flower> getFlowers() {
        return Collections.unmodifiableSet(flowers);
    }

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attrs) {
        if (FlowerXmlTag.DECORATIVE_FLOWER.getValue().equals(qName)
                || FlowerXmlTag.WILD_FLOWER.getValue().equals(qName)) {
            if (FlowerXmlTag.DECORATIVE_FLOWER.getValue().equals(qName)) {
                currentFlower = new DecorativeFlower();
            } else {
                currentFlower = new WildFlower();
            }
            String name = attrs.getValue(FlowerXmlTag.NAME.getValue());
            currentFlower.setName(name);
            String soil = attrs.getValue(FlowerXmlTag.SOIL.getValue());
            if (soil != null) {
                soil = soil.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
                currentFlower.setSoilType(SoilType.valueOf(soil.toUpperCase()));
            } else {
                currentFlower.setSoilType(SOIL_TYPE_DEFAULT);
            }
        } else {
            qName = qName.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
            FlowerXmlTag temp = FlowerXmlTag.valueOf(qName.toUpperCase());
            if (tagsWithText.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (FlowerXmlTag.DECORATIVE_FLOWER.getValue().equals(qName)
                || FlowerXmlTag.WILD_FLOWER.getValue().equals(qName)) {
            flowers.add(currentFlower);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (currentXmlTag != null && currentFlower != null) {
            switch (currentXmlTag) {
                case TEMPERATURE -> ((DecorativeFlower) currentFlower).getGrowingTips().setTemperature(Integer.parseInt(data));
                case NEED_LIGHT -> ((DecorativeFlower) currentFlower).getGrowingTips().setNeedLight(Boolean.parseBoolean(data));
                case WATER_PER_WEEK -> ((DecorativeFlower) currentFlower).getGrowingTips().setWaterPerWeek(Integer.parseInt(data));
                case DATE_OF_LANDING -> {
                    try {
                        ((DecorativeFlower) currentFlower).setDateOfLanding(simpleDateFormat.parse(data));
                    } catch (ParseException e) {
                        LOGGER.log(Level.ERROR, "Error while parsing date", e);
                    }
                }
                case STEM_COLOR -> currentFlower.getVisualParameters().setStemColor(data);
                case LEAVE_COLOR -> currentFlower.getVisualParameters().setLeaveColor(data);
                case AVERAGE_PLANT_SIZE -> currentFlower.getVisualParameters().setAveragePlantSize(Integer.parseInt(data));
                case MULTIPLYING -> {
                    data = data.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
                    ((WildFlower) currentFlower).setMultiplyingType(MultiplyingType.valueOf(data.toUpperCase()));
                }
                case ORIGIN -> ((WildFlower) currentFlower).setOrigin(data);
                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }
}
