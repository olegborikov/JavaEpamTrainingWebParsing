package com.borikov.task7.builder.impl;

import com.borikov.task7.builder.AbstractFlowerBuilder;
import com.borikov.task7.builder.FlowerXmlTag;
import com.borikov.task7.entity.*;
import com.borikov.task7.parser.DataParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class FlowerDOMBuilder extends AbstractFlowerBuilder {
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;
    private DocumentBuilder documentBuilder;
    private static final Logger LOGGER = LogManager.getLogger();

    public FlowerDOMBuilder() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.ERROR, "Error in parser configuration", e);
        }
    }

    public void buildSetFlowers(String fileName) {
        Document document;
        try {
            document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList decorativeFlowersList =
                    root.getElementsByTagName(FlowerXmlTag.DECORATIVE_FLOWER.getValue());
            for (int i = 0; i < decorativeFlowersList.getLength(); i++) {
                Element flowerElement = (Element) decorativeFlowersList.item(i);
                Flower flower = buildFlowers(flowerElement, new DecorativeFlower());
                flowers.add(flower);
            }
            NodeList wildFlowersList =
                    root.getElementsByTagName(FlowerXmlTag.WILD_FLOWER.getValue());
            for (int i = 0; i < wildFlowersList.getLength(); i++) {
                Element flowerElement = (Element) wildFlowersList.item(i);
                Flower flower = buildFlowers(flowerElement, new WildFlower());
                flowers.add(flower);
            }
        } catch (IOException | SAXException e) {
            LOGGER.log(Level.ERROR, "Error while parsing", e);
        }
    }

    private Flower buildFlowers(Element flowerElement, Flower flower) {
        String soilName = flowerElement.getAttribute(FlowerXmlTag.SOIL.getValue());
        if (soilName != null && !soilName.isBlank()) {
            soilName = DataParser.parseToEnumValue(soilName);
            flower.setSoilType(SoilType.valueOf(soilName));
        } else {
            flower.setSoilType(SOIL_TYPE_DEFAULT);
        }
        flower.setName(flowerElement.getAttribute(FlowerXmlTag.NAME.getValue()));
        NodeList visualParametersElements =
                flowerElement.getElementsByTagName(FlowerXmlTag.VISUAL_PARAMETERS.getValue());
        Element visualParametersElement = (Element) visualParametersElements.item(0);
        String stemColor = getTextContent(visualParametersElement, FlowerXmlTag.STEM_COLOR.getValue());
        String leaveColor = getTextContent(visualParametersElement, FlowerXmlTag.LEAVE_COLOR.getValue());
        String averagePlantSize = getTextContent(visualParametersElement, FlowerXmlTag.AVERAGE_PLANT_SIZE.getValue());
        VisualParameters visualParameters = flower.getVisualParameters();
        visualParameters.setLeaveColor(leaveColor);
        visualParameters.setStemColor(stemColor);
        visualParameters.setAveragePlantSize(Integer.parseInt(averagePlantSize));
        if (flower instanceof DecorativeFlower) {
            NodeList growingTipsElements =
                    flowerElement.getElementsByTagName(FlowerXmlTag.GROWING_TIPS.getValue());
            Element growingTipsElement = (Element) growingTipsElements.item(0);
            String temperature = getTextContent(growingTipsElement,
                    FlowerXmlTag.TEMPERATURE.getValue());
            String needLight = getTextContent(growingTipsElement,
                    FlowerXmlTag.NEED_LIGHT.getValue());
            String waterPerWeek = getTextContent(growingTipsElement,
                    FlowerXmlTag.WATER_PER_WEEK.getValue());
            GrowingTips growingTips = ((DecorativeFlower) flower).getGrowingTips();
            growingTips.setNeedLight(Boolean.parseBoolean(needLight));
            growingTips.setTemperature(Integer.parseInt(temperature));
            growingTips.setWaterPerWeek(Integer.parseInt(waterPerWeek));
            String date = getTextContent(flowerElement, FlowerXmlTag.DATE_OF_LANDING.getValue());
            Optional<Date> dateOfLanding = DataParser.parseToDate(date);
            if (dateOfLanding.isPresent()) {
                ((DecorativeFlower) flower).setDateOfLanding(dateOfLanding.get());
            }
        } else {
            String multiplying =
                    getTextContent(flowerElement, FlowerXmlTag.MULTIPLYING.getValue());
            multiplying = DataParser.parseToEnumValue(multiplying);
            ((WildFlower) flower).setMultiplyingType(MultiplyingType.valueOf(multiplying));
            String origin = getTextContent(flowerElement, FlowerXmlTag.ORIGIN.getValue());
            ((WildFlower) flower).setOrigin(origin);
        }
        return flower;
    }

    private static String getTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
