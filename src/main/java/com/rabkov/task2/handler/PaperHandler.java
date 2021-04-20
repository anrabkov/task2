package com.rabkov.task2.handler;

import com.rabkov.task2.builder.PaperXmlTag;
import com.rabkov.task2.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.MonthDay;
import java.util.*;

public class PaperHandler extends DefaultHandler {

    private final char HYPHEN = '-';
    private final char UNDERSCORE = '_';

    public Set<AbstractPaper> papers;
    private AbstractPaper currentPaper;
    private PaperXmlTag currentXmlTag;
    private EnumSet<PaperXmlTag> tagsWithTextContent;

    public PaperHandler() {
        papers = new HashSet<>();
        tagsWithTextContent = EnumSet.range(PaperXmlTag.TITLE, PaperXmlTag.NOT_GLOSSY_PAPER_TYPE);

    }

    public Set<AbstractPaper> getPapers() {
        return papers;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String glossyPaperTag = PaperXmlTag.GLOSSY_PAPER.getValue();
        String notGlossyPaperTag = PaperXmlTag.NOT_GLOSSY_PAPER.getValue();

        if (glossyPaperTag.equals(qName) || notGlossyPaperTag.equals(qName)) {
            currentPaper = glossyPaperTag.equals(qName) ? new GlossyPaper() : new NotGlossyPaper();

            String paperId = attributes.getValue(0);
            String note = attributes.getValue(1);

            currentPaper.setId(paperId);
            currentPaper.setNote(note == null ? AbstractPaper.DEFAULT_NOTE : note);
        } else {
            String constantName = toConstantName(qName);
            PaperXmlTag tag = PaperXmlTag.valueOf(constantName);
            if (tagsWithTextContent.contains(tag)) {
                currentXmlTag = tag;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String glossyPaperTag = PaperXmlTag.GLOSSY_PAPER.getValue();
        String notGlossyPaperTag = PaperXmlTag.NOT_GLOSSY_PAPER.getValue();

        if (glossyPaperTag.equals(qName) || notGlossyPaperTag.equals(qName)) {
            papers.add(currentPaper);
            currentPaper = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length);

        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case TITLE -> currentPaper.setTitle(data);
                case NUMBER_OF_PAGES -> currentPaper.setNumberOfPages(Integer.parseInt(data));
                case PRICE -> currentPaper.setPrice(Integer.parseInt(data));
                case MONTHLY -> currentPaper.setMonthly(Boolean.parseBoolean(data));
                case COLOR -> currentPaper.setColor(Boolean.parseBoolean(data));
                case PUBLICATION_DATE -> currentPaper.setPublicationDate(MonthDay.parse(data));

                case GLOSSY_PAPER_TYPE -> {
                    GlossyPaper paper = (GlossyPaper) currentPaper;
                    String constantName = toConstantName(data);
                    paper.setGlossyPaperType(GlossyPaperType.valueOf(constantName));
                }

                case NOT_GLOSSY_PAPER_TYPE -> {
                    NotGlossyPaper paper = (NotGlossyPaper) currentPaper;
                    String constantName = toConstantName(data);
                    paper.getNotGlossyPaperType(NotGlossyPaperType.valueOf(constantName));
                }

                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }

        currentXmlTag = null;
    }

    private String toConstantName(String string) {
        return string.strip()
                .replace(HYPHEN, UNDERSCORE)
                .toUpperCase();
    }
}

