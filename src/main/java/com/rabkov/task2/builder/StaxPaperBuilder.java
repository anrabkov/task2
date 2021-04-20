package com.rabkov.task2.builder;

import com.rabkov.task2.entity.*;
import com.rabkov.task2.exception.PaperException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.time.MonthDay;
import java.util.Optional;

public class StaxPaperBuilder extends AbstractPaperBuilder {

    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';

    private final String GLOSSY_PAPER_TAG = PaperXmlTag.GLOSSY_PAPER.toString();
    private final String NOT_GLOSSY_PAPER_TAG = PaperXmlTag.NOT_GLOSSY_PAPER.toString();

    @Override
    public void buildPapers(String xmlPath) throws PaperException {
        File file = new File(xmlPath);

        if (!file.exists() || file.isDirectory()) {
            throw new PaperException("Unable to open file with path: " + xmlPath);
        }

        XMLStreamReader reader;
        String name;

        try {
            Source source = new StreamSource(xmlPath);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            reader = inputFactory.createXMLStreamReader(source);

            while (reader.hasNext()) {
                int type = reader.next();

                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();

                    if ((name).equals(GLOSSY_PAPER_TAG)) {
                        AbstractPaper paper = new GlossyPaper();
                        buildEntity(paper, reader);
                        papers.add(paper);
                    }

                    if ((toConstantName(name).equals(NOT_GLOSSY_PAPER_TAG))) {
                        AbstractPaper paper = new NotGlossyPaper();
                        buildEntity(paper, reader);
                        papers.add(paper);
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new PaperException("An error occurred during file reading", e);
        }
    }


    private void buildEntity(AbstractPaper paper, XMLStreamReader reader)
            throws XMLStreamException, PaperException {
        String idAttribute = PaperXmlAttribute.ID.toString();
        String noteAttribute = PaperXmlAttribute.NOTE.toString();
        String paperId = reader.getAttributeValue(null, idAttribute);
        String paperNote = reader.getAttributeValue(null, noteAttribute);

        paper.setId(paperId);

        if (paperNote != null) {
            paper.setNote(paperNote);
        }

        String name;

        while (reader.hasNext()) {
            int type = reader.next();

            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    String constantName = toConstantName(name);
                    PaperXmlTag tag = PaperXmlTag.valueOf(constantName);
                    initializeField(reader, tag, paper);
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();

                    if (name.equals(GLOSSY_PAPER_TAG) || name.equals(NOT_GLOSSY_PAPER_TAG)) {
                        return;
                    }
                }
            }
        }

        throw new PaperException("Unable to build Device object");
    }

    private void initializeField(XMLStreamReader reader, PaperXmlTag tag, AbstractPaper paper)
            throws XMLStreamException, PaperException {
        String data = getTextContent(reader)
                .orElseThrow(() -> new PaperException("Unable to get text content"));

        switch (tag) {
            case TITLE -> paper.setTitle(data);
            case NUMBER_OF_PAGES -> paper.setNumberOfPages(Integer.parseInt(data));
            case PRICE -> paper.setPrice(Integer.parseInt(data));
            case MONTHLY -> paper.setMonthly(Boolean.parseBoolean(data));
            case COLOR -> paper.setColor(Boolean.parseBoolean(data));
            case PUBLICATION_DATE -> paper.setPublicationDate(MonthDay.parse(data));
            case GLOSSY_PAPER_TYPE -> {
                GlossyPaper glossyPaper = (GlossyPaper) paper;
                glossyPaper.setGlossyPaperType(GlossyPaperType.valueOf(data));
            }
            case NOT_GLOSSY_PAPER_TYPE -> {
                NotGlossyPaper notGlossyPaper = (NotGlossyPaper) paper;
                notGlossyPaper.setNotGlossyPaperType(NotGlossyPaperType.valueOf(data));
            }
            default -> throw new EnumConstantNotPresentException(
                    tag.getDeclaringClass(), tag.name());
        }
    }

    private String toConstantName(String string) {
        return string.strip()
                .replace(HYPHEN, UNDERSCORE)
                .toUpperCase();
    }

    private Optional<String> getTextContent(XMLStreamReader reader) throws XMLStreamException {
        Optional<String> result = Optional.empty();

        if (reader.hasNext()) {
            reader.next();
            String text = reader.getText();
            result = Optional.of(text);
        }

        return result;
    }
}
