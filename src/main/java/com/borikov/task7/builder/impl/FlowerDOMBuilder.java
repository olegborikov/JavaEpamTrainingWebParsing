package com.borikov.task7.builder.impl;

import com.borikov.task7.builder.AbstractFlowerBuilder;
import com.borikov.task7.builder.FlowerXmlTag;
import com.borikov.task7.entity.*;
import com.borikov.task7.exception.XMLFlowerParserException;
import com.borikov.task7.parser.DataParser;
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

    public FlowerDOMBuilder() throws XMLFlowerParserException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLFlowerParserException("Error in parser configuration", e);
        }
    }

    public void buildFlowers(String fileName) throws XMLFlowerParserException {
        try {
            Document document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList flowersList = root.getChildNodes();
            for (int i = 0; i < flowersList.getLength(); i++) {
                if (flowersList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element flowerElement = (Element) flowersList.item(i);
                    Flower flower = buildFlower(flowerElement);
                    flowers.add(flower);
                }
            }
        } catch (IOException | SAXException e) {
            throw new XMLFlowerParserException("Error while parsing", e);
        }
    }

    private Flower buildFlower(Element flowerElement) {
        Flower flower;
        if (flowerElement.getTagName().equals(FlowerXmlTag.DECORATIVE_FLOWER.getValue())) {
            flower = buildDecorativeFlower(flowerElement);
        } else {
            flower = buildWildFlower(flowerElement);
        }
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
        flower.setVisualParameters(buildVisualParameters(visualParametersElement));
        return flower;
    }

    private DecorativeFlower buildDecorativeFlower(Element flowerElement) {
        DecorativeFlower decorativeFlower = new DecorativeFlower();
        NodeList growingTipsElements =
                flowerElement.getElementsByTagName(FlowerXmlTag.GROWING_TIPS.getValue());
        Element growingTipsElement = (Element) growingTipsElements.item(0);
        decorativeFlower.setGrowingTips(buildGrowingTips(growingTipsElement));
        String date = getTextContent(flowerElement, FlowerXmlTag.DATE_OF_LANDING.getValue());
        Optional<Date> dateOfLanding = DataParser.parseToDate(date);
        if (dateOfLanding.isPresent()) {
            decorativeFlower.setDateOfLanding(dateOfLanding.get());
        }
        return decorativeFlower;
    }

    private WildFlower buildWildFlower(Element flowerElement) {
        WildFlower wildFlower = new WildFlower();
        String multiplying =
                getTextContent(flowerElement, FlowerXmlTag.MULTIPLYING.getValue());
        multiplying = DataParser.parseToEnumValue(multiplying);
        wildFlower.setMultiplyingType(MultiplyingType.valueOf(multiplying));
        String origin = getTextContent(flowerElement, FlowerXmlTag.ORIGIN.getValue());
        wildFlower.setOrigin(origin);
        return wildFlower;
    }

    private GrowingTips buildGrowingTips(Element growingTipsElement) {
        GrowingTips growingTips = new GrowingTips();
        String temperature = getTextContent(growingTipsElement,
                FlowerXmlTag.TEMPERATURE.getValue());
        String needLight = getTextContent(growingTipsElement,
                FlowerXmlTag.NEED_LIGHT.getValue());
        String waterPerWeek = getTextContent(growingTipsElement,
                FlowerXmlTag.WATER_PER_WEEK.getValue());
        growingTips.setNeedLight(Boolean.parseBoolean(needLight));
        growingTips.setTemperature(Integer.parseInt(temperature));
        growingTips.setWaterPerWeek(Integer.parseInt(waterPerWeek));
        return growingTips;
    }

    private VisualParameters buildVisualParameters(Element visualParametersElement) {
        VisualParameters visualParameters = new VisualParameters();
        String stemColor = getTextContent(visualParametersElement,
                FlowerXmlTag.STEM_COLOR.getValue());
        String leaveColor = getTextContent(visualParametersElement,
                FlowerXmlTag.LEAVE_COLOR.getValue());
        String averagePlantSize = getTextContent(visualParametersElement,
                FlowerXmlTag.AVERAGE_PLANT_SIZE.getValue());
        visualParameters.setLeaveColor(leaveColor);
        visualParameters.setStemColor(stemColor);
        visualParameters.setAveragePlantSize(Integer.parseInt(averagePlantSize));
        return visualParameters;
    }

    private static String getTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
