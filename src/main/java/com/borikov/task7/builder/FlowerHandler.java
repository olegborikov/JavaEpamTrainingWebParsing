package com.borikov.task7.builder;

import com.borikov.task7.entity.*;
import com.borikov.task7.parser.DataParser;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

public class FlowerHandler extends DefaultHandler {
    private Set<Flower> flowers;
    private Flower currentFlower;
    private FlowerXmlTag currentXmlTag;
    private EnumSet<FlowerXmlTag> tagsWithText;
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;

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
                soil = DataParser.parseToEnumValue(soil);
                currentFlower.setSoilType(SoilType.valueOf(soil));
            } else {
                currentFlower.setSoilType(SOIL_TYPE_DEFAULT);
            }
        } else {
            qName = DataParser.parseToEnumValue(qName);
            FlowerXmlTag temp = FlowerXmlTag.valueOf(qName);
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
        if (currentXmlTag != null && currentFlower != null) {
            switch (currentXmlTag) {
                case TEMPERATURE -> {
                    GrowingTips growingTips = ((DecorativeFlower) currentFlower).getGrowingTips();
                    growingTips.setTemperature(Integer.parseInt(data));
                }
                case NEED_LIGHT -> {
                    GrowingTips growingTips = ((DecorativeFlower) currentFlower).getGrowingTips();
                    growingTips.setNeedLight(Boolean.parseBoolean(data));
                }
                case WATER_PER_WEEK -> {
                    GrowingTips growingTips = ((DecorativeFlower) currentFlower).getGrowingTips();
                    growingTips.setWaterPerWeek(Integer.parseInt(data));
                }
                case DATE_OF_LANDING -> {
                    Optional<Date> date = DataParser.parseToDate(data);
                    if (date.isPresent()) {
                        ((DecorativeFlower) currentFlower).setDateOfLanding(date.get());
                    }
                }
                case STEM_COLOR -> {
                    VisualParameters visualParameters = currentFlower.getVisualParameters();
                    visualParameters.setStemColor(data);
                }
                case LEAVE_COLOR -> {
                    VisualParameters visualParameters = currentFlower.getVisualParameters();
                    visualParameters.setLeaveColor(data);
                }
                case AVERAGE_PLANT_SIZE -> {
                    VisualParameters visualParameters = currentFlower.getVisualParameters();
                    visualParameters.setAveragePlantSize(Integer.parseInt(data));
                }
                case MULTIPLYING -> {
                    data = DataParser.parseToEnumValue(data);
                    MultiplyingType multiplyingType = MultiplyingType.valueOf(data);
                    ((WildFlower) currentFlower).setMultiplyingType(multiplyingType);
                }
                case ORIGIN -> ((WildFlower) currentFlower).setOrigin(data);
                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }
}
