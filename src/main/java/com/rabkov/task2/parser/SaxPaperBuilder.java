package com.rabkov.task2.parser;

import com.rabkov.task2.builder.AbstractPaperBuilder;
import com.rabkov.task2.exception.PaperException;
import com.rabkov.task2.handler.PaperHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SaxPaperBuilder extends AbstractPaperBuilder {

    private PaperHandler handler;
    private XMLReader reader;

    public SaxPaperBuilder() throws PaperException {
        handler = new PaperHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser parser = factory.newSAXParser();
            reader = parser.getXMLReader();
            reader.setContentHandler(handler);
            reader.setErrorHandler(new PaperHandler());
        } catch (SAXException | ParserConfigurationException e) {
            throw new PaperException("Unable to configure SAX parser", e);
        }
    }

    @Override
    public void buildPapers(String xmlPath) throws PaperException {
        try {
            reader.parse(xmlPath);
        } catch (IOException e) {
            throw new PaperException("Unable to open XML file (" + xmlPath + ")", e);
        } catch (SAXException e) {
            throw new PaperException("Unable to parse XML file (" + xmlPath + ")", e);
        }

        papers = handler.getPapers();
    }
}


