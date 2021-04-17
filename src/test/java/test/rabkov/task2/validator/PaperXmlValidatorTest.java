package test.rabkov.task2.validator;

import com.rabkov.task2.exception.PaperException;
import com.rabkov.task2.validator.PaperValidator;
import org.junit.Test;
import org.testng.Assert;


public class PaperXmlValidatorTest {


    @Test
    public void testValidateXMLFile() throws PaperException {
        boolean actual = PaperValidator.validateXMLFile("data/valid.xml");
        Assert.assertTrue(actual);
    }

    @Test
    public void testValidateXMLFileInvalid() throws PaperException {
        boolean actual = PaperValidator.validateXMLFile("data/invalid.xml");
        Assert.assertFalse(actual);
    }
}

