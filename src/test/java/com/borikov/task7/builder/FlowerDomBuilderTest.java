package com.borikov.task7.builder;

import com.borikov.task7.entity.Flower;
import com.borikov.task7.warehouse.FlowerWarehouse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;

public class FlowerDomBuilderTest {
    private FlowerDomBuilder flowerDomBuilder;

    @BeforeClass
    public void setUp() {
        flowerDomBuilder = new FlowerDomBuilder();
    }

    @AfterClass
    public void tearDown() {
        flowerDomBuilder = null;
    }

    @Test
    public void buildFlowersTest() {
        Set<Flower> expected = FlowerWarehouse.getFlowers();
        flowerDomBuilder.buildSetFlowers("data/greenhouse.xml");
        Set<Flower> actual = flowerDomBuilder.getFlowers();
        assertEquals(actual, expected);
    }
}