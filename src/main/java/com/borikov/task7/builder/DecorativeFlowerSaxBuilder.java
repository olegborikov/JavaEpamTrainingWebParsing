package com.borikov.task7.builder;

import com.borikov.task7.entity.DecorativeFlower;
import com.borikov.task7.builder.handler.DecorativeFlowerHandler;
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

public class DecorativeFlowerSaxBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private Set<DecorativeFlower> decorativeFlowers;
    private DecorativeFlowerHandler decorativeFlowerHandler = new DecorativeFlowerHandler();
    private XMLReader xmlReader;

    public DecorativeFlowerSaxBuilder() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            xmlReader = saxParser.getXMLReader();
        } catch (SAXException | ParserConfigurationException e) {
            LOGGER.log(Level.ERROR, "Error while parsing", e);
        }
        xmlReader.setContentHandler(decorativeFlowerHandler);
    }

    public Set<DecorativeFlower> getDecorativeFlowers() {
        return Collections.unmodifiableSet(decorativeFlowers);
    }

    public void buildSetDecorativeFlowers(String fileName) {
        try {
            xmlReader.parse(fileName);
        } catch (SAXException | IOException e) {
            LOGGER.log(Level.ERROR, "Error while parsing", e);
        }
        decorativeFlowers = decorativeFlowerHandler.getDecorativeFlowers();
    }
}
