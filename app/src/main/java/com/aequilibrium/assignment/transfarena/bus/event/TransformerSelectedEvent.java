package com.aequilibrium.assignment.transfarena.bus.event;

import com.aequilibrium.assignment.transfarena.model.Transformer;

/**
 * Event of transformer selection
 */
public class TransformerSelectedEvent {

    private final Transformer transformer;

    public TransformerSelectedEvent(Transformer transformer) {
        this.transformer = transformer;
    }

    public Transformer getTransformer() {
        return transformer;
    }
}
