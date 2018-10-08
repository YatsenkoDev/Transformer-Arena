package com.aequilibrium.assignment.transfarena.battle.presenter;

import android.content.Context;

import com.aequilibrium.assignment.transfarena.battle.service.BattleService;
import com.aequilibrium.assignment.transfarena.battle.ui.activity.BattleView;
import com.aequilibrium.assignment.transfarena.model.Transformer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class BattlePresenterTest {

    @Mock
    Context context;
    @Mock
    BattleService battleService;
    @Mock
    Transformer transformer;
    @Mock
    BattleView battleView;

    private BattlePresenter presenter;

    @Before
    public void setup() {
        presenter = new BattlePresenter(context, battleService);
        presenter.setView(battleView);
    }

    @Test
    public void shouldInterruptServiceOnViewDestroy() {
        //when
        presenter.onViewDestroyed();

        //then
        Mockito.verify(battleService, times(1)).interrupt();
    }

    @Test
    public void shouldNotStartBattleCalculation() {
        //given
        presenter.setAutobots(new ArrayList<>());
        presenter.setDecipticonss(new ArrayList<>());

        //when
        presenter.onBeginBattleClicked();

        //then
        Mockito.verify(battleService, never()).calculateBattleResult(any(), any(), any());
        Mockito.verify(battleView, times(1)).showEmptyTeamError();
    }

    @Test
    public void shouldStartBattleCalculation() {
        //given
        presenter.setAutobots(Collections.singletonList(transformer));
        presenter.setDecipticonss(Collections.singletonList(transformer));

        //when
        presenter.onBeginBattleClicked();

        //then
        Mockito.verify(battleService, times(1)).calculateBattleResult(any(), any(), any());
        Mockito.verify(battleView, times(1)).showResultDialog();
    }

}
