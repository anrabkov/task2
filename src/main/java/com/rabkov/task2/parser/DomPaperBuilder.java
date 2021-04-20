package com.rabkov.task2.parser;

import com.rabkov.task2.builder.AbstractPaperBuilder;
import com.rabkov.task2.builder.PaperXmlAttribute;
import com.rabkov.task2.builder.PaperXmlTag;
import com.rabkov.task2.entity.*;
import com.rabkov.task2.exception.PaperException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.MonthDay;

public class DomPaperBuilder extends AbstractPaperBuilder {

    private final DocumentBuilder documentBuilder;

    public DomPaperBuilder() throws PaperException {
        super();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            String errorMsg = "Unable to configure parser";
            throw new PaperException(errorMsg, e);
        }
    }

    @Override
    public void buildPapers(String xmlPath) throws PaperException {
        Document document;

        try {
            document = documentBuilder.parse(xmlPath);
            Element root = document.getDocumentElement();

            String glossyPaperTag = PaperXmlTag.GLOSSY_PAPER.getValue();
            String notGlossyPaperTag = PaperXmlTag.NOT_GLOSSY_PAPER.getValue();
            NodeList glossyPapers = root.getElementsByTagName(glossyPaperTag);
            NodeList notGlossyPapers = root.getElementsByTagName(notGlossyPaperTag);

            for (int i = 0; i < glossyPapers.getLength(); i++) {
                Element element = (Element) glossyPapers.item(i);
                AbstractPaper paper = new GlossyPaper();
                buildEntity(element, paper);
                papers.add(paper);
            }

            for (int i = 0; i < notGlossyPapers.getLength(); i++) {
                Element element = (Element) notGlossyPapers.item(i);
                AbstractPaper paper = new NotGlossyPaper();
                buildEntity(element, paper);
                papers.add(paper);
            }
        } catch (SAXException e) {
            String errorMsg = "Unable to parse XML file (" + xmlPath + ")";
            throw new PaperException(errorMsg, e);
        } catch (IOException e) {
            String errorMsg = "Unable to open XML file (" + xmlPath + ")";
            throw new PaperException(errorMsg, e);
        }
    }

    private void buildEntity(Element element, AbstractPaper paper) {
        String idAttribute = PaperXmlAttribute.ID.getValue();
        String noteAttribute = PaperXmlAttribute.NOTE.getValue();
        String titleTag = PaperXmlTag.TITLE.getValue();
        String numberOfPagesTag = PaperXmlTag.NUMBER_OF_PAGES.getValue();
        String priceTag = PaperXmlTag.PRICE.getValue();
        String monthlyTag = PaperXmlTag.MONTHLY.getValue();
        String colorTag = PaperXmlTag.COLOR.getValue();
        String publicationDateTag = PaperXmlTag.PUBLICATION_DATE.getValue();

        String id = element.getAttribute(idAttribute);
        String note = element.getAttribute(noteAttribute);
        note = (note == null || note.equals("")) ? AbstractPaper.DEFAULT_NOTE : note;
        String title = getElementTextContent(element, titleTag);
        int numberOfPages = Integer.parseInt(getElementTextContent(element, numberOfPagesTag));
        int price = Integer.parseInt(getElementTextContent(element, priceTag));
        boolean monthly = Boolean.parseBoolean(getElementTextContent(element, monthlyTag));
        boolean color = Boolean.parseBoolean(getElementTextContent(element, colorTag));
        MonthDay publicationDate = MonthDay.parse(getElementTextContent(element, publicationDateTag));

        paper.setId(id);
        paper.setNote(note);
        paper.setTitle(title);
        paper.setNumberOfPages(numberOfPages);
        paper.setPrice(price);
        paper.setMonthly(monthly);
        paper.setColor(color);
        paper.setPublicationDate(publicationDate);


        if (paper instanceof GlossyPaper) {
            GlossyPaper glossyPaper = (GlossyPaper) paper;
            String glossyPaperTypeTag = PaperXmlTag.GLOSSY_PAPER_TYPE.getValue();
            GlossyPaperType glossyPaperType = GlossyPaperType.valueOf(getElementTextContent(element,
                    glossyPaperTypeTag));
            glossyPaper.setGlossyPaperType(glossyPaperType);
        } else {
            NotGlossyPaper notGlossyPaper = (NotGlossyPaper) paper;
            String notGlossyPaperTypeTag = PaperXmlTag.NOT_GLOSSY_PAPER_TYPE.getValue();
            NotGlossyPaperType notGlossyPaperType = NotGlossyPaperType.valueOf(getElementTextContent(element,
                    notGlossyPaperTypeTag));
            notGlossyPaper.setNotGlossyPaperType(notGlossyPaperType);
        }
    }

    private String getElementTextContent(Element element, String name) {
        NodeList nodeList = element.getElementsByTagName(name);
        Node node = nodeList.item(0);

        return node.getTextContent();
    }
}


