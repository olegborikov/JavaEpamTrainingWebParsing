package com.borikov.task7.builder.impl;

import com.borikov.task7.builder.AbstractFlowerBuilder;
import com.borikov.task7.entity.Flower;
import com.borikov.task7.exception.XMLFlowerParserException;
import com.borikov.task7.warehouse.FlowerWarehouse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class FlowerStAXBuilderTest {
    private AbstractFlowerBuilder builder;

    @BeforeClass
    public void setUp() {
        builder = new FlowerStAXBuilder();
    }

    @AfterClass
    public void tearDown() {
        builder = null;
    }

    @Test
    public void buildFlowersTest() {
        try {
            Set<Flower> expected = FlowerWarehouse.getFlowers();
            builder.buildFlowers("data/greenhouse.xml");
            Set<Flower> actual = builder.getFlowers();
            assertEquals(actual, expected);
        } catch (XMLFlowerParserException e) {
            fail("Incorrect input");
        }
    }
}
