package com.rabkov.task2.entity;

import java.time.MonthDay;
import java.util.Objects;

public class AbstractPaper {

    public static final String DEFAULT_NOTE = "-";
    

    private String id;
    private String note;
    private String title;
    private int numberOfPages;
    private int price;
    private boolean monthly;
    private boolean color;
    private MonthDay publicationDate;


    public AbstractPaper(){
        publicationDate = MonthDay.now();
    }

    public AbstractPaper(String id, String note, String title, int numberOfPages, int price,
                         boolean monthly, boolean color, MonthDay publicationDate) {
        this.id = id;
        this.note = note;
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.price = price;
        this.monthly = monthly;
        this.color = color;
        this.publicationDate = publicationDate;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public int getPrice() {
        return price;
    }

    public boolean isMonthly() {
        return monthly;
    }

    public boolean isColor() {
        return color;
    }

    public MonthDay getPublicationDate() {
        return publicationDate;
    }

    public String getNote() {
        return note;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMonthly(boolean monthly) {
        this.monthly = monthly;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public void setPublicationDate(MonthDay publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPaper paper = (AbstractPaper) o;
        return numberOfPages == paper.numberOfPages && price == paper.price && monthly == paper.monthly
                && color == paper.color && Objects.equals(id, paper.id) && Objects.equals(note, paper.note)
                && Objects.equals(title, paper.title) && Objects.equals(publicationDate, paper.publicationDate);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = result*prime + id.hashCode();
        result = result*prime + note.hashCode();
        result = result*prime + title.hashCode();
        result = result*prime + Integer.hashCode(numberOfPages);
        result = result*prime + Integer.hashCode(price);
        result = result*prime + Boolean.hashCode(monthly);
        result = result*prime + Boolean.hashCode(color);
        result = result*prime + publicationDate.hashCode();

        return result;
    }



    @Override
    public String toString() {
        return "AbstractPaper{" +
                "id='" + id + '\'' +
                ", note=" + note +
                ", title='" + title + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", price=" + price +
                ", monthly=" + monthly +
                ", color=" + color +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
