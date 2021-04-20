package com.rabkov.task2.entity;

import java.time.MonthDay;


public class NotGlossyPaper extends AbstractPaper{
    private NotGlossyPaperType notGlossyPaperType;

    public NotGlossyPaper(){
        notGlossyPaperType = NotGlossyPaperType.NEWSPAPER;
    }

    public NotGlossyPaper(String id, String note, String title, int numberOfPages, int price, boolean monthly,
                          boolean color, MonthDay publicationDate, NotGlossyPaperType notGlossyPaperType) {
        super(id, note, title, numberOfPages, price, monthly, color, publicationDate);
        this.notGlossyPaperType = notGlossyPaperType;
    }

    public NotGlossyPaperType getNotGlossyPaperType(NotGlossyPaperType notGlossyPaperType) {
        return this.notGlossyPaperType;
    }

    public void setNotGlossyPaperType(NotGlossyPaperType notGlossyPaperType) {
        this.notGlossyPaperType = notGlossyPaperType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NotGlossyPaper that = (NotGlossyPaper) o;
        return notGlossyPaperType == that.notGlossyPaperType;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = result * prime + super.hashCode();
        result = result * prime + notGlossyPaperType.hashCode();

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NotGlossyPaper{");
        sb.append(super.toString());
        sb.append("; notGlossyPaperType = ").append(notGlossyPaperType.getValue());
        sb.append("}");
        String finalString = sb.toString();
        return finalString;
    }
}
