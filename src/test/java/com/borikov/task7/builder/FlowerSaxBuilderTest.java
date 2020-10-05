package com.borikov.task7.builder;

import com.borikov.task7.entity.Flower;
import com.borikov.task7.warehouse.FlowerWarehouse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;

public class FlowerSaxBuilderTest {
    private FlowerSaxBuilder flowerSaxBuilder;

    @BeforeClass
    public void setUp() {
        flowerSaxBuilder = new FlowerSaxBuilder();
    }

    @AfterClass
    public void tearDown() {
        flowerSaxBuilder = null;
    }

    @Test
    public void buildFlowersTest() {
        Set<Flower> expected = FlowerWarehouse.getFlowers();
        flowerSaxBuilder.buildSetFlowers("data/greenhouse.xml");
        Set<Flower> actual = flowerSaxBuilder.getFlowers();
        assertEquals(actual, expected);
    }
}