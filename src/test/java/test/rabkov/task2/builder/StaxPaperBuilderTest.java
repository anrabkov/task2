package test.rabkov.task2.builder;

import com.rabkov.task2.builder.AbstractPaperBuilder;
import com.rabkov.task2.builder.PaperBuilderFactory;
import com.rabkov.task2.entity.*;
import com.rabkov.task2.exception.PaperException;
import com.rabkov.task2.util.Util;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.time.MonthDay;
import java.util.HashSet;
import java.util.Set;

public class StaxPaperBuilderTest {
    private Set<AbstractPaper> expectedSet;

    @BeforeClass
    public void setUp() {
        expectedSet = new HashSet<>();

        for (int i = 1; i <= 6; i++) {
            GlossyPaper paper = new GlossyPaper(
                    "b" + i,
                    "for everyone",
                    "Example Glossy Paper",
                    3,
                    2,
                    false,
                    true,
                    MonthDay.parse("--02-25"),
                    GlossyPaperType.BOOKLET);
            expectedSet.add(paper);
        }

        for (int i = 1; i <= 6; i++) {
            GlossyPaper paper = new GlossyPaper(
                    "m" + i,
                    "for adults",
                    "Example Glossy Paper",
                    25,
                    10,
                    true,
                    true,
                    MonthDay.parse("--02-25"),
                    GlossyPaperType.MAGAZINE);
            expectedSet.add(paper);
        }

        for (int i = 1; i <= 4; i++) {
            NotGlossyPaper paper = new NotGlossyPaper(
                    "n" + i,
                    "-",
                    "Example Not Glossy Paper",
                    10,
                    4,
                    true,
                    true,
                    MonthDay.parse("--02-25"),
                    NotGlossyPaperType.NEWSPAPER);
            expectedSet.add(paper);
        }
    }


    @DataProvider(name = "builder-provider")
    public Object[][] builderDataProvider() throws PaperException {
        Util util = new Util();
        String xmlPath = util.getPath("data/valid.xml");

        Object[][] objects = new Object[][]{
                {PaperBuilderFactory.createPaperBuilder("STAX"), xmlPath}
        };
        return objects;
    }

    @Test(dataProvider = "builder-provider")
    public void testBuildPapers(AbstractPaperBuilder builder, String xmlPath) throws PaperException {
        builder.buildPapers(xmlPath);
        Set<AbstractPaper> actualSet = builder.getPapers();

        Assert.assertEquals(actualSet, expectedSet);
    }
}

