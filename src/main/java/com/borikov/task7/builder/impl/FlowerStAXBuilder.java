package com.borikov.task7.builder.impl;

import com.borikov.task7.builder.AbstractFlowerBuilder;
import com.borikov.task7.builder.FlowerXmlTag;
import com.borikov.task7.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FlowerStAXBuilder extends AbstractFlowerBuilder {
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;
    private XMLInputFactory inputFactory;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final char OLD_SYMBOL_REPLACE = '-';
    private static final char NEW_SYMBOL_REPLACE = '_';

    public FlowerStAXBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public void buildSetFlowers(String fileName) {
        try (FileInputStream fileInputStream = new FileInputStream(new File(fileName));) {
            XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(fileInputStream);
            while (xmlStreamReader.hasNext()) {
                int type = xmlStreamReader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    String name = xmlStreamReader.getLocalName();
                    name = name.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
                    if (FlowerXmlTag.valueOf(name.toUpperCase()) == FlowerXmlTag.DECORATIVE_FLOWER) {
                        Flower flower = buildFlower(xmlStreamReader, new DecorativeFlower());
                        flowers.add(flower);
                    }
                    if (FlowerXmlTag.valueOf(name.toUpperCase()) == FlowerXmlTag.WILD_FLOWER) {
                        Flower flower = buildFlower(xmlStreamReader, new WildFlower());
                        flowers.add(flower);
                    }
                }
            }
        } catch (XMLStreamException e) {
            LOGGER.log(Level.ERROR, "Error stax parsing", e);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.ERROR, "File {} not found", fileName, e);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Error while closing file {}", fileName, e);
        }
    }

    private Flower buildFlower(XMLStreamReader xmlStreamReader, Flower flower) throws XMLStreamException {
        flower.setName(xmlStreamReader.getAttributeValue(null, FlowerXmlTag.NAME.getValue()));
        String soil = (xmlStreamReader.getAttributeValue(null, FlowerXmlTag.SOIL.getValue()));
        if (soil != null) {
            soil = soil.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
            flower.setSoilType(SoilType.valueOf(soil.toUpperCase()));
        } else {
            flower.setSoilType(SOIL_TYPE_DEFAULT);
        }
        String name;
        while (xmlStreamReader.hasNext()) {
            int type = xmlStreamReader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = name.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
                    switch (FlowerXmlTag.valueOf(name.toUpperCase())) {
                        case VISUAL_PARAMETERS -> flower.setVisualParameters(getXmlVisualParameters(xmlStreamReader));
                        case GROWING_TIPS -> ((DecorativeFlower) flower).setGrowingTips(getXmlGrowingTips(xmlStreamReader));
                        case DATE_OF_LANDING -> {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                ((DecorativeFlower) flower).setDateOfLanding(simpleDateFormat.parse(getXmlText(xmlStreamReader)));
                            } catch (ParseException e) {
                                LOGGER.log(Level.ERROR, "Error while parsing date", e);
                            }
                        }
                        case MULTIPLYING -> {
                            String multiplying = getXmlText(xmlStreamReader);
                            multiplying = multiplying.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
                            ((WildFlower) flower).setMultiplyingType(MultiplyingType.valueOf(multiplying.toUpperCase()));
                        }
                        case ORIGIN -> ((WildFlower) flower).setOrigin(getXmlText(xmlStreamReader));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = name.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
                    if (FlowerXmlTag.valueOf(name.toUpperCase()) == FlowerXmlTag.DECORATIVE_FLOWER
                            || FlowerXmlTag.valueOf(name.toUpperCase()) == FlowerXmlTag.WILD_FLOWER) {
                        return flower;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag decorative-flower or wild-flower");
    }

    private VisualParameters getXmlVisualParameters(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        VisualParameters visualParameters = new VisualParameters();
        int type;
        String name;
        while (xmlStreamReader.hasNext()) {
            type = xmlStreamReader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = name.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
                    switch (FlowerXmlTag.valueOf(name.toUpperCase())) {
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
                    name = name.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
                    if (FlowerXmlTag.valueOf(name.toUpperCase()) == FlowerXmlTag.VISUAL_PARAMETERS) {
                        return visualParameters;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag visual-parameters");
    }

    private GrowingTips getXmlGrowingTips(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        GrowingTips growingTips = new GrowingTips();
        int type;
        String name;
        while (xmlStreamReader.hasNext()) {
            type = xmlStreamReader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = name.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
                    switch (FlowerXmlTag.valueOf(name.toUpperCase())) {
                        case TEMPERATURE -> growingTips.setTemperature(Integer.parseInt(getXmlText(xmlStreamReader)));
                        case NEED_LIGHT -> growingTips.setNeedLight(Boolean.parseBoolean(getXmlText(xmlStreamReader)));
                        case WATER_PER_WEEK -> growingTips.setWaterPerWeek(Integer.parseInt(getXmlText(xmlStreamReader)));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = xmlStreamReader.getLocalName();
                    name = name.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
                    if (FlowerXmlTag.valueOf(name.toUpperCase()) == FlowerXmlTag.GROWING_TIPS) {
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
