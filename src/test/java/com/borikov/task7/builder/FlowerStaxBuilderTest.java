package com.borikov.task7.builder;

import com.borikov.task7.entity.Flower;
import com.borikov.task7.warehouse.FlowerWarehouse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.*;

public class FlowerStaxBuilderTest {
    private FlowerStaxBuilder flowerStaxBuilder;

    @BeforeClass
    public void setUp() {
        flowerStaxBuilder = new FlowerStaxBuilder();
    }

    @AfterClass
    public void tearDown() {
        flowerStaxBuilder = null;
    }

    @Test
    public void buildFlowersTest() {
        Set<Flower> expected = FlowerWarehouse.getFlowers();
        flowerStaxBuilder.buildSetFlowers("data/greenhouse.xml");
        Set<Flower> actual = flowerStaxBuilder.getFlowers();
        assertEquals(actual, expected);
    }
}