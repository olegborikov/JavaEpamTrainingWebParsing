package com.borikov.task7.builder.impl;

import com.borikov.task7.builder.AbstractFlowerBuilder;
import com.borikov.task7.builder.FlowerHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class FlowerSAXBuilder extends AbstractFlowerBuilder {
    private FlowerHandler flowerHandler = new FlowerHandler();
    private XMLReader xmlReader;
    private static final Logger LOGGER = LogManager.getLogger();

    public FlowerSAXBuilder() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            xmlReader = saxParser.getXMLReader();
        } catch (SAXException | ParserConfigurationException e) {
            LOGGER.log(Level.ERROR, "Error while parsing", e);
        }
        xmlReader.setContentHandler(flowerHandler);
    }

    public void buildSetFlowers(String fileName) {
        try {
            xmlReader.parse(fileName);
        } catch (SAXException | IOException e) {
            LOGGER.log(Level.ERROR, "Error while parsing", e);
        }
        flowers = flowerHandler.getFlowers();
    }
}
