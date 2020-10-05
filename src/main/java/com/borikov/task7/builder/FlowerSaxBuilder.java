package com.borikov.task7.builder;

import com.borikov.task7.entity.Flower;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class FlowerSaxBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private Set<Flower> flowers;
    private FlowerHandler flowerHandler = new FlowerHandler();
    private XMLReader xmlReader;

    public FlowerSaxBuilder() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            xmlReader = saxParser.getXMLReader();
        } catch (SAXException | ParserConfigurationException e) {
            LOGGER.log(Level.ERROR, "Error while parsing", e);
        }
        xmlReader.setContentHandler(flowerHandler);
    }

    public Set<Flower> getFlowers() {
        return Collections.unmodifiableSet(flowers);
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
