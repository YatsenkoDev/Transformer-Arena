package com.aequilibrium.assignment.transfarena.preview.presenter;

import android.content.Context;

import com.aequilibrium.assignment.transfarena.preview.service.TransformerCreatingService;
import com.aequilibrium.assignment.transfarena.preview.service.TransformerDeleteService;
import com.aequilibrium.assignment.transfarena.preview.service.TransformerUpdatingService;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class PreviewPresenterTest {

    @Mock
    Context context;
    @Mock
    TransformerCreatingService transformerCreatingService;
    @Mock
    TransformerUpdatingService transformerUpdatingService;
    @Mock
    TransformerDeleteService transformerDeleteService;
    @Mock
    PreviewView previewView;

    private PreviewPresenter presenter;

    @Before
    public void setup() {
        presenter = new PreviewPresenter(context, transformerCreatingService, transformerUpdatingService, transformerDeleteService);
        presenter.setView(previewView);
    }

    @Test
    public void shouldInterruptServiceOnViewDestroy() {
        //when
        presenter.onViewDestroyed();

        //then
        Mockito.verify(transformerCreatingService, times(1)).interrupt();
        Mockito.verify(transformerUpdatingService, times(1)).interrupt();
        Mockito.verify(transformerDeleteService, times(1)).interrupt();
    }

    @Test
    public void shouldShowEmptyNameError() {
        //given
        Mockito.when(previewView.getName()).thenReturn("");

        //when
        presenter.onSaveClicked();

        //then
        Mockito.verify(previewView, times(1)).showNameRequiredError();
        Mockito.verify(transformerUpdatingService, never()).updateTransformer(any(), any());
        Mockito.verify(transformerCreatingService, never()).createTransformer(any(), any());
    }
}
