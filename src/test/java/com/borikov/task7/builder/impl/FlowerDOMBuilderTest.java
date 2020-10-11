package com.borikov.task7.builder.impl;

import com.borikov.task7.builder.AbstractFlowerBuilder;
import com.borikov.task7.entity.Flower;
import com.borikov.task7.warehouse.FlowerWarehouse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;

public class FlowerDOMBuilderTest {
    private AbstractFlowerBuilder builder;

    @BeforeClass
    public void setUp() {
        builder = new FlowerDOMBuilder();
    }

    @AfterClass
    public void tearDown() {
        builder = null;
    }

    @Test
    public void buildFlowersTest() {
        Set<Flower> expected = FlowerWarehouse.getFlowers();
        builder.buildSetFlowers("data/greenhouse.xml");
        Set<Flower> actual = builder.getFlowers();
        assertEquals(actual, expected);
    }
}