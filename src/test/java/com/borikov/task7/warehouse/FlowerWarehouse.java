package com.borikov.task7.warehouse;

import com.borikov.task7.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FlowerWarehouse {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Set<Flower> flowers;

    private FlowerWarehouse() {
    }

    public static Set<Flower> getFlowers() {
        if (flowers == null) {
            flowers = new HashSet<>();
            try {
                WildFlower wildFlower1 = new WildFlower("fireweed", SoilType.UNPAVED, new VisualParameters("white", "green", 10), MultiplyingType.SEEDS, "Russia");
                DecorativeFlower decorativeFlower1 = new DecorativeFlower("cactus", SoilType.PODZOLIC, new VisualParameters("green", "green", 10), new GrowingTips(25, false, 10), new SimpleDateFormat("yyyy-MM-dd").parse("2015-10-12"));
                DecorativeFlower decorativeFlower2 = new DecorativeFlower("schlumberger", SoilType.PODZOLIC, new VisualParameters("green", "pink", 30), new GrowingTips(15, false, 700), new SimpleDateFormat("yyyy-MM-dd").parse("2017-04-10"));
                WildFlower wildFlower2 = new WildFlower("thrift", SoilType.PODZOLIC, new VisualParameters("blue", "pink", 25), MultiplyingType.SEEDS, "Belarus");
                DecorativeFlower decorativeFlower3 = new DecorativeFlower("lofofora", SoilType.UNPAVED, new VisualParameters("green", "white", 5), new GrowingTips(20, false, 300), new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-10"));
                WildFlower wildFlower3 = new WildFlower("gentian", SoilType.PODZOLIC, new VisualParameters("white", "blue", 35), MultiplyingType.CUTTINGS, "Belarus");
                DecorativeFlower decorativeFlower4 = new DecorativeFlower("peyote", SoilType.SOD_PODZOLIC, new VisualParameters("white", "white", 30), new GrowingTips(15, false, 500), new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-04"));
                WildFlower wildFlower4 = new WildFlower("delphinium", SoilType.SOD_PODZOLIC, new VisualParameters("green", "yellow", 20), MultiplyingType.LEAVES, "Russia");
                DecorativeFlower decorativeFlower5 = new DecorativeFlower("nopal", SoilType.PODZOLIC, new VisualParameters("green", "green", 100), new GrowingTips(27, false, 10), new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-03"));
                WildFlower wildFlower5 = new WildFlower("clover", SoilType.SOD_PODZOLIC, new VisualParameters("green", "purple", 5), MultiplyingType.LEAVES, "German");
                DecorativeFlower decorativeFlower6 = new DecorativeFlower("lophophora", SoilType.UNPAVED, new VisualParameters("white", "green", 25), new GrowingTips(15, true, 75), new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-04"));
                WildFlower wildFlower6 = new WildFlower("aconite", SoilType.UNPAVED, new VisualParameters("green", "purple", 40), MultiplyingType.CUTTINGS, "Ukraine");
                WildFlower wildFlower7 = new WildFlower("altay", SoilType.SOD_PODZOLIC, new VisualParameters("white", "pink", 30), MultiplyingType.LEAVES, "USA");
                DecorativeFlower decorativeFlower7 = new DecorativeFlower("rose", SoilType.UNPAVED, new VisualParameters("white", "red", 40), new GrowingTips(20, true, 100), new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"));
                DecorativeFlower decorativeFlower8 = new DecorativeFlower("lavender", SoilType.SOD_PODZOLIC, new VisualParameters("white", "purple", 10), new GrowingTips(15, true, 1250), new SimpleDateFormat("yyyy-MM-dd").parse("2019-03-01"));
                WildFlower wildFlower8 = new WildFlower("carnation", SoilType.PODZOLIC, new VisualParameters("white", "red", 35), MultiplyingType.LEAVES, "Poland");
                flowers.add(wildFlower1);
                flowers.add(wildFlower2);
                flowers.add(wildFlower3);
                flowers.add(wildFlower4);
                flowers.add(wildFlower5);
                flowers.add(wildFlower6);
                flowers.add(wildFlower7);
                flowers.add(wildFlower8);
                flowers.add(decorativeFlower1);
                flowers.add(decorativeFlower2);
                flowers.add(decorativeFlower3);
                flowers.add(decorativeFlower4);
                flowers.add(decorativeFlower5);
                flowers.add(decorativeFlower6);
                flowers.add(decorativeFlower7);
                flowers.add(decorativeFlower8);
            } catch (ParseException e) {
                LOGGER.log(Level.ERROR, "Error while parsing date", e);
            }
        }
        return Collections.unmodifiableSet(flowers);
    }
}
