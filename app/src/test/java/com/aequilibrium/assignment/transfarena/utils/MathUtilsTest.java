package com.aequilibrium.assignment.transfarena.utils;

import com.aequilibrium.assignment.transfarena.model.Transformer;

import org.junit.Assert;
import org.junit.Test;

public class MathUtilsTest {

    @Test
    public void verifyOverallRatingCalculation() {
        Transformer transformer = new Transformer("id", "name", new Transformer.PowerParameters(1, 1, 1, 1, 1, 1, 1, 1), "A");
        Assert.assertEquals(MathUtils.calculateOverallRating(transformer), 5);
    }
}
