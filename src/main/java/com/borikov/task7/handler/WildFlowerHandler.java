package com.borikov.task7.handler;

import com.borikov.task7.entity.MultiplyingType;
import com.borikov.task7.entity.SoilType;
import com.borikov.task7.entity.WildFlower;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class WildFlowerHandler extends DefaultHandler {
    private Set<WildFlower> wildFlowers;
    private WildFlower currentWildFlower;
    private FlowerXmlTag currentXmlTag;
    private EnumSet<FlowerXmlTag> tagsWithText;
    private static final String ELEMENT_WILD_FLOWER = "wild-flower";
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;

    public WildFlowerHandler() {
        wildFlowers = new HashSet<>();
        tagsWithText = EnumSet.range(FlowerXmlTag.STEM_COLOR, FlowerXmlTag.ORIGIN);
    }

    public Set<WildFlower> getWildFlowers() {
        return Collections.unmodifiableSet(wildFlowers);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (ELEMENT_WILD_FLOWER.equals(qName)) {
            currentWildFlower = new WildFlower();
            String name = attrs.getValue(FlowerXmlTag.NAME.getValue());
            currentWildFlower.setName(name);
            if (attrs.getLength() == 2) {
                String soil = attrs.getValue(FlowerXmlTag.SOIL.getValue());
                soil = soil.replace('-', '_');
                currentWildFlower.setSoilType(SoilType.valueOf(soil.toUpperCase()));
            } else {
                currentWildFlower.setSoilType(SOIL_TYPE_DEFAULT);
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
        if (ELEMENT_WILD_FLOWER.equals(qName)) {
            wildFlowers.add(currentWildFlower);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).trim();
        if (currentXmlTag != null && currentWildFlower != null) {
            switch (currentXmlTag) {
                case STEM_COLOR -> currentWildFlower.getVisualParameters().setStemColor(data);
                case LEAVE_COLOR -> currentWildFlower.getVisualParameters().setLeaveColor(data);
                case AVERAGE_PLANT_SIZE -> currentWildFlower.getVisualParameters().setAveragePlantSize(Integer.parseInt(data));
                case MULTIPLYING -> {
                    data = data.replace('-', '_');
                    currentWildFlower.setMultiplyingType(MultiplyingType.valueOf(data.toUpperCase()));
                }
                case ORIGIN -> currentWildFlower.setOrigin(data);
                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }
}
