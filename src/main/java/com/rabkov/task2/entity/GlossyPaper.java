package com.rabkov.task2.entity;

import java.time.MonthDay;


public class GlossyPaper extends AbstractPaper{
    private GlossyPaperType glossyPaperType;

    public GlossyPaper(){
        glossyPaperType = GlossyPaperType.BOOKLET;
    }

    public GlossyPaper(String id, String note, String title, int numberOfPages, int price, boolean monthly,
                       boolean color, MonthDay publicationDate,
                       GlossyPaperType glossyPaperType) {
        super(id,  note, title, numberOfPages, price, monthly, color, publicationDate);
        this.glossyPaperType = glossyPaperType;
    }

    public GlossyPaperType getGlossyPaperType() {
        return glossyPaperType;
    }

    public void setGlossyPaperType(GlossyPaperType glossyPaperType) {
        this.glossyPaperType = glossyPaperType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GlossyPaper that = (GlossyPaper) o;
        return glossyPaperType == that.glossyPaperType;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = result * prime + super.hashCode();
        result = result * prime + glossyPaperType.hashCode();

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GlossyPaper{");
        sb.append(super.toString());
        sb.append("; glossyPaperType = ").append(glossyPaperType.getValue());
        sb.append("}");
        String finalString = sb.toString();
        return finalString;
    }
}
