package com.aequilibrium.assignment.transfarena.preview.service;

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
public class TransformerDeleteServiceTest {

    @Mock
    RestApiClient restApiClient;
    @Mock
    TokenService tokenService;

    private TransformerDeleteService service;

    @Before
    public void setup() {
        service = new TransformerDeleteService(restApiClient, tokenService);
    }

    @Test
    public void shouldRequestToken() {
        //when
        service.deleteTransformer(any(), "id");

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
