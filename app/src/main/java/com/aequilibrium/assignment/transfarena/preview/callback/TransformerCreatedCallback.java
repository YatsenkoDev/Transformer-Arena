package com.aequilibrium.assignment.transfarena.preview.callback;

import com.aequilibrium.assignment.transfarena.model.Transformer;

/**
 * Callback for new transformer creation
 */
public interface TransformerCreatedCallback {

    void onTransformerSaved(Transformer transformer);
}
