package com.rabkov.task2.builder;


  public enum PaperXmlTag {

    PAPERS("papers"),
    GLOSSY_PAPER("glossy-paper"),
    NOT_GLOSSY_PAPER("not-glossy-paper"),
    TITLE("title"),
    NUMBER_OF_PAGES("number-of-pages"),
    PRICE("price"),
    MONTHLY("monthly"),
    COLOR("color"),
    PUBLICATION_DATE("publication-date"),
    GLOSSY_PAPER_TYPE("glossy-paper-type"),
    NOT_GLOSSY_PAPER_TYPE("not-glossy-paper-type");

    private String value;

    PaperXmlTag(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}

