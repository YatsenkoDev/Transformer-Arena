package com.aequilibrium.assignment.transfarena.utils;

import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.List;

public class StringUtils {

    private static final String NAME_SEPARTOR = ", ";

    private StringUtils() {

    }

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

    public boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
