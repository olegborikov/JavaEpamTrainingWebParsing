package com.borikov.task7.builder;

import com.borikov.task7.entity.Flower;
import com.borikov.task7.exception.XMLFlowerParserException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractFlowerBuilder {
    protected Set<Flower> flowers = new HashSet<>();

    public Set<Flower> getFlowers() {
        return Collections.unmodifiableSet(flowers);
    }

    public abstract void buildSetFlowers(String fileName) throws XMLFlowerParserException;
}
