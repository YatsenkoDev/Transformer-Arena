package com.aequilibrium.assignment.transfarena.gallery.callback;

import com.aequilibrium.assignment.transfarena.model.Transformer;

import java.util.List;

public interface TransformersLoadedCallback {

    void onTransformersLoaded(List<Transformer> transformers);
}
