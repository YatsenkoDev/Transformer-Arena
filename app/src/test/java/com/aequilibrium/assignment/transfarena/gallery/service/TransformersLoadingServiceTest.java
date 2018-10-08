package com.aequilibrium.assignment.transfarena.gallery.service;

import com.aequilibrium.assignment.transfarena.api.RestApiClient;
import com.aequilibrium.assignment.transfarena.service.TokenService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class TransformersLoadingServiceTest {

    @Mock
    RestApiClient restApiClient;
    @Mock
    TokenService tokenService;

    private TransformersLoadingService service;

    @Before
    public void setup() {
        service = new TransformersLoadingService(restApiClient, tokenService);
    }

    @Test
    public void shouldRequestToken() {
        //when
        service.loadTransformers(any());

        //then
        Mockito.verify(tokenService, times(1)).getToken(any());
    }

    @Test
    public void shouldInterruptTokenRequest() {
        //when
        service.interrupt();

        //then
        Mockito.verify(tokenService, times(1)).interrupt();
    }
}
