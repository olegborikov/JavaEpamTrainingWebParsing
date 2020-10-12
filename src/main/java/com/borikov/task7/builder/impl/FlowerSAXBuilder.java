package com.borikov.task7.builder.impl;

import com.borikov.task7.builder.AbstractFlowerBuilder;
import com.borikov.task7.builder.impl.handler.FlowerHandler;
import com.borikov.task7.exception.XMLFlowerParserException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class FlowerSAXBuilder extends AbstractFlowerBuilder {
    private final FlowerHandler flowerHandler = new FlowerHandler();
    private XMLReader xmlReader;

    public FlowerSAXBuilder() throws XMLFlowerParserException {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(flowerHandler);
        } catch (SAXException | ParserConfigurationException e) {
            throw new XMLFlowerParserException("Error in parser configuration", e);
        }
    }

    public void buildSetFlowers(String fileName) throws XMLFlowerParserException {
        try {
            xmlReader.parse(fileName);
            flowers = flowerHandler.getFlowers();
        } catch (SAXException | IOException e) {
            throw new XMLFlowerParserException("Error while parsing", e);
        }
    }
}
