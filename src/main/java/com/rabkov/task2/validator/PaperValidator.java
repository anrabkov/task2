package com.rabkov.task2.validator;


import com.rabkov.task2.exception.PaperException;
import com.rabkov.task2.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;



public class PaperValidator {
    static Logger logger = LogManager.getLogger();

    public static boolean validateXMLFile(String xmlPath) throws PaperException {
        final String SCHEMA_NAME = "schema/schema.xsd";
        Util util = new Util();
        String schemaPath = util.getPath(SCHEMA_NAME);
        File schemaFile = new File(schemaPath);
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);

        try {
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(util.getPath(xmlPath));
            validator.validate(source);
        } catch (IOException e) {
            String errorMsg = "Cannot open file: " + xmlPath;
            throw new PaperException(errorMsg, e);
        } catch (SAXException e) {
            logger.error("File " + xmlPath + " is not valid: ", e);
            return false;
        }

        return true;
    }
}


