package com.aequilibrium.assignment.transfarena.utils;

import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.List;

/**
 * Utils for string operations
 */
public class StringUtils {

    private static final String NAME_SEPARTOR = ", ";

    private StringUtils() {

    }

    /**
     * Generates single string of transformers names
     *
     * @param transformers list of transformers
     * @return names string
     */
    public static String getTransformersNames(List<Transformer> transformers) {
        String separator = "";
        StringBuilder builder = new StringBuilder();
        for (Transformer transformer : transformers) {
            builder.append(separator);
            builder.append(transformer.getName());
            separator = NAME_SEPARTOR;
        }
        return builder.toString();
    }
}
