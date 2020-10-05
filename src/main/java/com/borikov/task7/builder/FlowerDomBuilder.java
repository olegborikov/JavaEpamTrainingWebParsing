package com.borikov.task7.builder;

import com.borikov.task7.entity.*;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FlowerDomBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;
    private Set<Flower> flowers;
    private DocumentBuilder documentBuilder;

    public FlowerDomBuilder() {
        flowers = new HashSet<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.ERROR, "Error in parser configuration", e);
        }
    }

    public Set<Flower> getFlowers() {
        return Collections.unmodifiableSet(flowers);
    }

    public void buildSetFlowers(String fileName) {
        Document document;
        try {
            document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList decorativeFlowersList = root.getElementsByTagName(FlowerXmlTag.DECORATIVE_FLOWER.getValue());
            for (int i = 0; i < decorativeFlowersList.getLength(); i++) {
                Element flowerElement = (Element) decorativeFlowersList.item(i);
                Flower flower = buildFlowers(flowerElement, new DecorativeFlower());
                flowers.add(flower);
            }
            NodeList wildFlowersList = root.getElementsByTagName(FlowerXmlTag.WILD_FLOWER.getValue());
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
            soilName = soilName.replace('-', '_');
            flower.setSoilType(SoilType.valueOf(soilName.toUpperCase()));
        } else {
            flower.setSoilType(SOIL_TYPE_DEFAULT);
        }
        flower.setName(flowerElement.getAttribute(FlowerXmlTag.NAME.getValue()));
        Element visualParametersElement = (Element) flowerElement.getElementsByTagName(FlowerXmlTag.VISUAL_PARAMETERS.getValue()).item(0);
        String stemColor = getElementTextContent(visualParametersElement, FlowerXmlTag.STEM_COLOR.getValue());
        flower.getVisualParameters().setStemColor(stemColor);
        String leaveColor = getElementTextContent(visualParametersElement, FlowerXmlTag.LEAVE_COLOR.getValue());
        flower.getVisualParameters().setLeaveColor(leaveColor);
        String averagePlantSize = getElementTextContent(visualParametersElement, FlowerXmlTag.AVERAGE_PLANT_SIZE.getValue());
        flower.getVisualParameters().setAveragePlantSize(Integer.parseInt(averagePlantSize));
        if (flower instanceof DecorativeFlower) {
            Element growingTipsElement = (Element) flowerElement.getElementsByTagName(FlowerXmlTag.GROWING_TIPS.getValue()).item(0);
            String temperature = getElementTextContent(growingTipsElement, FlowerXmlTag.TEMPERATURE.getValue());
            ((DecorativeFlower) flower).getGrowingTips().setTemperature(Integer.parseInt(temperature));
            String needLight = getElementTextContent(growingTipsElement, FlowerXmlTag.NEED_LIGHT.getValue());
            ((DecorativeFlower) flower).getGrowingTips().setNeedLight(Boolean.parseBoolean(needLight));
            String waterPerWeek = getElementTextContent(growingTipsElement, FlowerXmlTag.WATER_PER_WEEK.getValue());
            ((DecorativeFlower) flower).getGrowingTips().setWaterPerWeek(Integer.parseInt(waterPerWeek));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfLanding = getElementTextContent(flowerElement, FlowerXmlTag.DATE_OF_LANDING.getValue());
            try {
                ((DecorativeFlower) flower).setDateOfLanding(simpleDateFormat.parse(dateOfLanding));
            } catch (ParseException e) {
                LOGGER.log(Level.ERROR, "Error while parsing date", e);
            }
        } else {
            String multiplying = getElementTextContent(flowerElement, FlowerXmlTag.MULTIPLYING.getValue());
            multiplying = multiplying.replace('-', '_');
            ((WildFlower) flower).setMultiplyingType(MultiplyingType.valueOf(multiplying.toUpperCase()));
            String origin = getElementTextContent(flowerElement, FlowerXmlTag.ORIGIN.getValue());
            ((WildFlower) flower).setOrigin(origin);
        }
        return flower;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
