package com.borikov.task7.builder.handler;

import com.borikov.task7.builder.FlowerXmlTag;
import com.borikov.task7.entity.DecorativeFlower;
import com.borikov.task7.entity.SoilType;
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

public class DecorativeFlowerHandler extends DefaultHandler {
    private Set<DecorativeFlower> decorativeFlowers;
    private DecorativeFlower currentDecorativeFlower;
    private FlowerXmlTag currentXmlTag;
    private EnumSet<FlowerXmlTag> tagsWithText;
    private static final String ELEMENT_DECORATIVE_FLOWER = "decorative-flower";
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;
    private static final Logger LOGGER = LogManager.getLogger();

    public DecorativeFlowerHandler() {
        decorativeFlowers = new HashSet<>();
        tagsWithText = EnumSet.range(FlowerXmlTag.TEMPERATURE, FlowerXmlTag.AVERAGE_PLANT_SIZE);
    }

    public Set<DecorativeFlower> getDecorativeFlowers() {
        return Collections.unmodifiableSet(decorativeFlowers);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (ELEMENT_DECORATIVE_FLOWER.equals(qName)) {
            currentDecorativeFlower = new DecorativeFlower();
            String name = attrs.getValue(FlowerXmlTag.NAME.getValue());
            currentDecorativeFlower.setName(name);
            if (attrs.getLength() == 2) {
                String soil = attrs.getValue(FlowerXmlTag.SOIL.getValue());
                soil = soil.replace('-', '_');
                currentDecorativeFlower.setSoilType(SoilType.valueOf(soil.toUpperCase()));
            } else {
                currentDecorativeFlower.setSoilType(SOIL_TYPE_DEFAULT);
            }
        } else {
            qName = qName.replace('-', '_');
            FlowerXmlTag temp = FlowerXmlTag.valueOf(qName.toUpperCase());
            if (tagsWithText.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (ELEMENT_DECORATIVE_FLOWER.equals(qName)) {
            decorativeFlowers.add(currentDecorativeFlower);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (currentXmlTag != null && currentDecorativeFlower != null) {
            switch (currentXmlTag) {
                case TEMPERATURE -> currentDecorativeFlower.getGrowingTips().setTemperature(Integer.parseInt(data));
                case NEED_LIGHT -> currentDecorativeFlower.getGrowingTips().setNeedLight(Boolean.parseBoolean(data));
                case WATER_PER_WEEK -> currentDecorativeFlower.getGrowingTips().setWaterPerWeek(Integer.parseInt(data));
                case DATE_OF_LANDING -> {
                    try {
                        currentDecorativeFlower.setDateOfLanding(simpleDateFormat.parse(data));
                    } catch (ParseException e) {
                        LOGGER.log(Level.ERROR, "Error while parsing date", e);
                    }
                }
                case STEM_COLOR -> currentDecorativeFlower.getVisualParameters().setStemColor(data);
                case LEAVE_COLOR -> currentDecorativeFlower.getVisualParameters().setLeaveColor(data);
                case AVERAGE_PLANT_SIZE -> currentDecorativeFlower.getVisualParameters().setAveragePlantSize(Integer.parseInt(data));
                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }
}
