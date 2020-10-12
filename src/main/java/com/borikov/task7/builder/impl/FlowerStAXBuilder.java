package com.borikov.task7.builder.impl;

import com.borikov.task7.builder.AbstractFlowerBuilder;
import com.borikov.task7.builder.FlowerXmlTag;
import com.borikov.task7.entity.*;
import com.borikov.task7.exception.XMLFlowerParserException;
import com.borikov.task7.parser.DataParser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class FlowerStAXBuilder extends AbstractFlowerBuilder {
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;
    private XMLInputFactory inputFactory;

    public FlowerStAXBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public void buildSetFlowers(String fileName) throws XMLFlowerParserException {
        try (FileInputStream fileInputStream = new FileInputStream(new File(fileName));) {
            XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(fileInputStream);
            while (xmlStreamReader.hasNext()) {
                int type = xmlStreamReader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    String name = xmlStreamReader.getLocalName();
                    name = DataParser.parseToEnumValue(name);
                    if (FlowerXmlTag.valueOf(name) == FlowerXmlTag.DECORATIVE_FLOWER
                            || FlowerXmlTag.valueOf(name) == FlowerXmlTag.WILD_FLOWER) {
                        Flower flower = buildFlower(xmlStreamReader);
                        flowers.add(flower);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new XMLFlowerParserException("Error while paring", e);
        }
    }

    private Flower buildFlower(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        Flower flower;
        if (xmlStreamReader.getLocalName().equals(FlowerXmlTag.DECORATIVE_FLOWER.getValue())) {
            flower = new DecorativeFlower();
        } else {
            flower = new WildFlower();
        }
        flower.setName(xmlStreamReader.getAttributeValue(null, FlowerXmlTag.NAME.getValue()));
        String soil = (xmlStreamReader.getAttributeValue(null, FlowerXmlTag.SOIL.getValue()));
        if (soil != null) {
            soil = DataParser.parseToEnumValue(soil);
            flower.setSoilType(SoilType.valueOf(soil));
        } else {
            flower.setSoilType(SOIL_TYPE_DEFAULT);
        }
        String name;
        while (xmlStreamReader.hasNext()) {
            int type = xmlStreamReader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = DataParser.parseToEnumValue(name);
                    switch (FlowerXmlTag.valueOf(name)) {
                        case VISUAL_PARAMETERS -> {
                            VisualParameters visualParameters =
                                    getXmlVisualParameters(xmlStreamReader);
                            flower.setVisualParameters(visualParameters);
                        }
                        case GROWING_TIPS -> {
                            GrowingTips growingTips = getXmlGrowingTips(xmlStreamReader);
                            ((DecorativeFlower) flower).setGrowingTips(growingTips);
                        }
                        case DATE_OF_LANDING -> {
                            Optional<Date> date =
                                    DataParser.parseToDate(getXmlText(xmlStreamReader));
                            if (date.isPresent()) {
                                ((DecorativeFlower) flower).setDateOfLanding(date.get());
                            }
                        }
                        case MULTIPLYING -> {
                            String multiplying = getXmlText(xmlStreamReader);
                            multiplying = DataParser.parseToEnumValue(multiplying);
                            ((WildFlower) flower).setMultiplyingType(MultiplyingType.valueOf(multiplying));
                        }
                        case ORIGIN -> ((WildFlower) flower).setOrigin(getXmlText(xmlStreamReader));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = DataParser.parseToEnumValue(name);
                    if (FlowerXmlTag.valueOf(name) == FlowerXmlTag.DECORATIVE_FLOWER
                            || FlowerXmlTag.valueOf(name) == FlowerXmlTag.WILD_FLOWER) {
                        return flower;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag decorative-flower or wild-flower");
    }

    private VisualParameters getXmlVisualParameters(XMLStreamReader xmlStreamReader)
            throws XMLStreamException {
        VisualParameters visualParameters = new VisualParameters();
        int type;
        String name;
        while (xmlStreamReader.hasNext()) {
            type = xmlStreamReader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = DataParser.parseToEnumValue(name);
                    switch (FlowerXmlTag.valueOf(name)) {
                        case STEM_COLOR -> visualParameters.setStemColor(getXmlText(xmlStreamReader));
                        case LEAVE_COLOR -> visualParameters.setLeaveColor(getXmlText(xmlStreamReader));
                        case AVERAGE_PLANT_SIZE -> {
                            String averagePlantSize = getXmlText(xmlStreamReader);
                            visualParameters.setAveragePlantSize(Integer.parseInt(averagePlantSize));
                        }
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = DataParser.parseToEnumValue(name);
                    if (FlowerXmlTag.valueOf(name) == FlowerXmlTag.VISUAL_PARAMETERS) {
                        return visualParameters;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag visual-parameters");
    }

    private GrowingTips getXmlGrowingTips(XMLStreamReader xmlStreamReader)
            throws XMLStreamException {
        GrowingTips growingTips = new GrowingTips();
        int type;
        String name;
        while (xmlStreamReader.hasNext()) {
            type = xmlStreamReader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = DataParser.parseToEnumValue(name);
                    switch (FlowerXmlTag.valueOf(name)) {
                        case TEMPERATURE -> {
                            String temperature = getXmlText(xmlStreamReader);
                            growingTips.setTemperature(Integer.parseInt(temperature));
                        }
                        case NEED_LIGHT -> {
                            String needLight = getXmlText(xmlStreamReader);
                            growingTips.setNeedLight(Boolean.parseBoolean(needLight));
                        }
                        case WATER_PER_WEEK -> {
                            String waterPerWeek = getXmlText(xmlStreamReader);
                            growingTips.setWaterPerWeek(Integer.parseInt(waterPerWeek));
                        }
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = DataParser.parseToEnumValue(name);
                    if (FlowerXmlTag.valueOf(name) == FlowerXmlTag.GROWING_TIPS) {
                        return growingTips;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag growing-tips");
    }

    private String getXmlText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
