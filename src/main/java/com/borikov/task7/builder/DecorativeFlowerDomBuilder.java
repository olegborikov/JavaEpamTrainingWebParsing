package com.borikov.task7.builder;

import com.borikov.task7.entity.DecorativeFlower;
import com.borikov.task7.entity.SoilType;
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

public class DecorativeFlowerDomBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;
    private Set<DecorativeFlower> decorativeFlowers;
    private DocumentBuilder documentBuilder;

    public DecorativeFlowerDomBuilder() {
        decorativeFlowers = new HashSet<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.ERROR, "Error in parser configuration", e);
        }
    }

    public Set<DecorativeFlower> getDecorativeFlowers() {
        return Collections.unmodifiableSet(decorativeFlowers);
    }

    public void buildSetDecorativeFlowers(String fileName) {
        Document document;
        try {
            document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList decorativeFlowerList = root.getElementsByTagName(FlowerXmlTag.DECORATIVE_FLOWER.getValue());
            for (int i = 0; i < decorativeFlowerList.getLength(); i++) {
                Element decorativeFlowerElement = (Element) decorativeFlowerList.item(i);
                DecorativeFlower decorativeFlower = buildDecorativeFlowers(decorativeFlowerElement);
                decorativeFlowers.add(decorativeFlower);
            }
        } catch (IOException | SAXException e) {
            LOGGER.log(Level.ERROR, "Error while parsing", e);
        }
    }

    private DecorativeFlower buildDecorativeFlowers(Element decorativeFlowerElement) {
        DecorativeFlower decorativeFlower = new DecorativeFlower();
        String soilName = decorativeFlowerElement.getAttribute(FlowerXmlTag.SOIL.getValue());
        if (soilName != null && !soilName.isBlank()) {
            decorativeFlower.setSoilType(SoilType.valueOf(soilName.toUpperCase()));
        } else {
            decorativeFlower.setSoilType(SOIL_TYPE_DEFAULT);
        }
        decorativeFlower.setName(decorativeFlowerElement.getAttribute(FlowerXmlTag.NAME.getValue()));
        Element visualParametersElement = (Element) decorativeFlowerElement.getElementsByTagName(FlowerXmlTag.VISUAL_PARAMETERS.getValue()).item(0);
        String stemColor = getElementTextContent(visualParametersElement, FlowerXmlTag.STEM_COLOR.getValue());
        decorativeFlower.getVisualParameters().setStemColor(stemColor);
        String leaveColor = getElementTextContent(visualParametersElement, FlowerXmlTag.LEAVE_COLOR.getValue());
        decorativeFlower.getVisualParameters().setLeaveColor(leaveColor);
        String averagePlantSize = getElementTextContent(visualParametersElement, FlowerXmlTag.AVERAGE_PLANT_SIZE.getValue());
        decorativeFlower.getVisualParameters().setAveragePlantSize(Integer.parseInt(averagePlantSize));
        Element growingTipsElement = (Element) decorativeFlowerElement.getElementsByTagName(FlowerXmlTag.GROWING_TIPS.getValue()).item(0);
        String temperature = getElementTextContent(growingTipsElement, FlowerXmlTag.TEMPERATURE.getValue());
        decorativeFlower.getGrowingTips().setTemperature(Integer.parseInt(temperature));
        String needLight = getElementTextContent(growingTipsElement, FlowerXmlTag.NEED_LIGHT.getValue());
        decorativeFlower.getGrowingTips().setNeedLight(Boolean.parseBoolean(needLight));
        String waterPerWeek = getElementTextContent(growingTipsElement, FlowerXmlTag.WATER_PER_WEEK.getValue());
        decorativeFlower.getGrowingTips().setWaterPerWeek(Integer.parseInt(waterPerWeek));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfLanding = getElementTextContent(decorativeFlowerElement, FlowerXmlTag.DATE_OF_LANDING.getValue());
        try {
            decorativeFlower.setDateOfLanding(simpleDateFormat.parse(dateOfLanding));
        } catch (ParseException e) {
            LOGGER.log(Level.ERROR, "Error while parsing date", e);
        }
        return decorativeFlower;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
