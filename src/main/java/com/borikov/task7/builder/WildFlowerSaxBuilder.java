package com.borikov.task7.builder;

import com.borikov.task7.entity.WildFlower;
import com.borikov.task7.handler.WildFlowerHandler;
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

public class WildFlowerSaxBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private Set<WildFlower> wildFlowers;
    private WildFlowerHandler wildFlowerHandler = new WildFlowerHandler();
    private XMLReader xmlReader;

    public WildFlowerSaxBuilder() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            xmlReader = saxParser.getXMLReader();
        } catch (SAXException | ParserConfigurationException e) {
            LOGGER.log(Level.ERROR, "Error while parsing", e);
        }
        xmlReader.setContentHandler(wildFlowerHandler);
    }

    public Set<WildFlower> getWildFlowers() {
        return Collections.unmodifiableSet(wildFlowers);
    }

    public void buildSetWildFlowers(String fileName) {
        try {
            xmlReader.parse(fileName);
        } catch (SAXException | IOException e) {
            LOGGER.log(Level.ERROR, "Error while parsing", e);
        }
        wildFlowers = wildFlowerHandler.getWildFlowers();
    }
}
