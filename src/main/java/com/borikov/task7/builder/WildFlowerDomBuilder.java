package com.borikov.task7.builder;

import com.borikov.task7.entity.MultiplyingType;
import com.borikov.task7.entity.SoilType;
import com.borikov.task7.entity.WildFlower;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WildFlowerDomBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SoilType SOIL_TYPE_DEFAULT = SoilType.PODZOLIC;
    private Set<WildFlower> wildFlowers;
    private DocumentBuilder documentBuilder;

    public WildFlowerDomBuilder() {
        wildFlowers = new HashSet<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.ERROR, "Error in parser configuration", e);
        }
    }

    public Set<WildFlower> getWildFlowers() {
        return Collections.unmodifiableSet(wildFlowers);
    }

    public void buildSetWildFlowers(String fileName) {
        Document document;
        try {
            document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList wildFlowerList = root.getElementsByTagName(FlowerXmlTag.WILD_FLOWER.getValue());
            for (int i = 0; i < wildFlowerList.getLength(); i++) {
                Element wildFlowerElement = (Element) wildFlowerList.item(i);
                WildFlower wildFlower = buildWildFlowers(wildFlowerElement);
                wildFlowers.add(wildFlower);
            }
        } catch (IOException | SAXException e) {
            LOGGER.log(Level.ERROR, "Error while parsing", e);
        }
    }

    private WildFlower buildWildFlowers(Element wildFlowerElement) {
        WildFlower wildFlower = new WildFlower();
        String soilName = wildFlowerElement.getAttribute(FlowerXmlTag.SOIL.getValue());
        if (soilName != null && !soilName.isBlank()) {
            wildFlower.setSoilType(SoilType.valueOf(soilName.toUpperCase()));
        } else {
            wildFlower.setSoilType(SOIL_TYPE_DEFAULT);
        }
        wildFlower.setName(wildFlowerElement.getAttribute(FlowerXmlTag.NAME.getValue()));
        Element visualParametersElement = (Element) wildFlowerElement.getElementsByTagName(FlowerXmlTag.VISUAL_PARAMETERS.getValue()).item(0);
        String stemColor = getElementTextContent(visualParametersElement, FlowerXmlTag.STEM_COLOR.getValue());
        wildFlower.getVisualParameters().setStemColor(stemColor);
        String leaveColor = getElementTextContent(visualParametersElement, FlowerXmlTag.LEAVE_COLOR.getValue());
        wildFlower.getVisualParameters().setLeaveColor(leaveColor);
        String averagePlantSize = getElementTextContent(visualParametersElement, FlowerXmlTag.AVERAGE_PLANT_SIZE.getValue());
        wildFlower.getVisualParameters().setAveragePlantSize(Integer.parseInt(averagePlantSize));
        String multiplying = getElementTextContent(wildFlowerElement, FlowerXmlTag.MULTIPLYING.getValue());
        multiplying = multiplying.replace('-', '_');
        wildFlower.setMultiplyingType(MultiplyingType.valueOf(multiplying.toUpperCase()));
        String origin = getElementTextContent(wildFlowerElement, FlowerXmlTag.ORIGIN.getValue());
        wildFlower.setOrigin(origin);
        return wildFlower;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
