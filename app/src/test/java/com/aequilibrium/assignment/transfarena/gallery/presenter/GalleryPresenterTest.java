package com.aequilibrium.assignment.transfarena.gallery.presenter;

import android.content.Context;

import com.aequilibrium.assignment.transfarena.bus.RxBus;
import com.aequilibrium.assignment.transfarena.gallery.service.TransformersLoadingService;
import com.aequilibrium.assignment.transfarena.gallery.ui.activity.GalleryView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.disposables.Disposable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class GalleryPresenterTest {

    @Mock
    Context context;
    @Mock
    RxBus rxBus;
    @Mock
    TransformersLoadingService transformersLoadingService;
    @Mock
    GalleryView galleryView;
    @Mock
    Disposable disposable;

    private GalleryPresenter presenter;

    @Before
    public void setup() {
        presenter = new GalleryPresenter(context, transformersLoadingService, rxBus);
        presenter.setView(galleryView);
    }

    @Test
    public void shouldInterruptServiceOnViewDestroy() {
        //given
        Mockito.when(rxBus.register(any(), any())).thenReturn(disposable);

        //when
        presenter.start();
        presenter.onViewDestroyed();

        //then
        Mockito.verify(transformersLoadingService, times(1)).interrupt();
        Mockito.verify(disposable, times(1)).dispose();
    }

    @Test
    public void shouldStartLoadingOnStart() {
        //when
        presenter.start();

        //then
        Mockito.verify(transformersLoadingService, times(1)).loadTransformers(any());
    }
}
