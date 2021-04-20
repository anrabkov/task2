package com.rabkov.task2.builder;

import com.rabkov.task2.exception.PaperException;
import com.rabkov.task2.parser.DomPaperBuilder;
import com.rabkov.task2.parser.SaxPaperBuilder;
import com.rabkov.task2.parser.StaxPaperBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaperBuilderFactory {
    static Logger logger = LogManager.getLogger();
    private enum ParserType {
        DOM, SAX, STAX
    }

    private PaperBuilderFactory() { }

    public static AbstractPaperBuilder createPaperBuilder(String parserType) throws PaperException {
        try {
            ParserType type = ParserType.valueOf(parserType.toUpperCase());

            return switch (type) {
                case DOM -> new DomPaperBuilder();
                case SAX -> new SaxPaperBuilder();
                case STAX -> new StaxPaperBuilder();
            };
        } catch (IllegalArgumentException e) {
            String errorMsg = "Parser with name '" + parserType + "' not found";
            logger.error(errorMsg);
            throw new PaperException(errorMsg, e);
        }
    }
}
