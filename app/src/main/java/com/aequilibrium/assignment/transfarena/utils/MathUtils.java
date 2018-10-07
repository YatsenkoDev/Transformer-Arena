package com.aequilibrium.assignment.transfarena.utils;

import com.aequilibrium.assignment.transfarena.model.Transformer;

/**
 * Calculation methods
 */
public class MathUtils {

    private static final Integer MINIMAL_PARAMETER_VALUE = 1;

    private MathUtils() {

    }

    /**
     * Calculates overall transformer rating
     *
     * @param transformer current transformer
     * @return overral rating amount
     */
    public static int calculateOverallRating(Transformer transformer) {
        return checkForNullAndReturn(transformer.getStrength())
                + checkForNullAndReturn(transformer.getIntelligence())
                + checkForNullAndReturn(transformer.getSpeed())
                + checkForNullAndReturn(transformer.getEndurance())
                + checkForNullAndReturn(transformer.getFirepower());
    }

    private static Integer checkForNullAndReturn(Integer parameter) {
        return parameter != null ? parameter : MINIMAL_PARAMETER_VALUE;
    }
}
