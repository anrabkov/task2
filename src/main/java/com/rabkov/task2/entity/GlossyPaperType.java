package com.rabkov.task2.entity;

public enum GlossyPaperType {
    BOOKLET("booklet"),
    MAGAZINE("magazine");

    private String value;

    GlossyPaperType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
