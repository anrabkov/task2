package com.rabkov.task2.builder;

import com.rabkov.task2.entity.AbstractPaper;
import com.rabkov.task2.exception.PaperException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractPaperBuilder {

    protected Set<AbstractPaper> papers;

    public AbstractPaperBuilder() {
        papers = new HashSet<>();
    }

    public AbstractPaperBuilder(Set<AbstractPaper> papers) {
        this.papers = papers;
    }

    public Set<AbstractPaper> getPapers() {
        return papers;
    }

    public abstract void buildPapers(String xmlPath) throws PaperException;
}
