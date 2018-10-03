package com.aequilibrium.assignment.transfarena.battle.service;

import com.aequilibrium.assignment.transfarena.battle.callback.BattleResultCallback;
import com.aequilibrium.assignment.transfarena.model.Transformer;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.argThat;

@RunWith(MockitoJUnitRunner.class)
public class BattleServiceTest {

    private BattleService battleService;

    @Mock
    BattleResultCallback battleResultCallback;

    @Before
    public void setup() {
        battleService = new BattleService();
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());
    }

    @Test
    public void shouldReturnTotalDestroySameOptimus() {
        //given
        Transformer transformer = new Transformer("id", "Optimus Prime"
                , new Transformer.PowerParameters(1, 1, 1, 1, 1, 1, 1, 1)
                , "A");

        //when
        battleService.calculateBattleResult(Collections.singletonList(transformer), Collections.singletonList(transformer), battleResultCallback);

        //then
        Mockito.verify(battleResultCallback).onBattleResultCalculated(argThat(battleResult -> {
            Assertions.assertThat(battleResult.isTotalDestroy()).isTrue();
            return true;
        }));
    }

    @Test
    public void shouldReturnTotalDestroySamePredaking() {
        //given
        Transformer transformer = new Transformer("id", "Predaking"
                , new Transformer.PowerParameters(1, 1, 1, 1, 1, 1, 1, 1)
                , "D");

        //when
        battleService.calculateBattleResult(Collections.singletonList(transformer), Collections.singletonList(transformer), battleResultCallback);

        //then
        Mockito.verify(battleResultCallback).onBattleResultCalculated(argThat(battleResult -> {
            Assertions.assertThat(battleResult.isTotalDestroy()).isTrue();
            return true;
        }));
    }

    @Test
    public void shouldReturnTotalDestroyOptimusVsPredaking() {
        //given
        Transformer autobot = new Transformer("id", "Optimus Prime"
                , new Transformer.PowerParameters(1, 1, 1, 1, 1, 1, 1, 1)
                , "A");

        Transformer decepticon = new Transformer("id", "Predaking"
                , new Transformer.PowerParameters(1, 1, 1, 1, 1, 1, 1, 1)
                , "D");

        //when
        battleService.calculateBattleResult(Collections.singletonList(autobot), Collections.singletonList(decepticon), battleResultCallback);

        //then
        Mockito.verify(battleResultCallback).onBattleResultCalculated(argThat(battleResult -> {
            Assertions.assertThat(battleResult.isTotalDestroy()).isTrue();
            return true;
        }));
    }

    @Test
    public void shouldReturnTie() {
        //given
        Transformer autobot = new Transformer("id", "autobot"
                , new Transformer.PowerParameters(1, 1, 1, 1, 1, 1, 1, 1)
                , "A");
        Transformer decepticon = new Transformer("id", "decepticon"
                , new Transformer.PowerParameters(1, 1, 1, 1, 1, 1, 1, 1)
                , "D");

        //when
        battleService.calculateBattleResult(Collections.singletonList(autobot), Collections.singletonList(decepticon), battleResultCallback);

        //then
        Mockito.verify(battleResultCallback).onBattleResultCalculated(argThat(battleResult -> {
            Assertions.assertThat(battleResult.isTotalDestroy()).isFalse();
            Assertions.assertThat(battleResult.getAutobotsElimenatedOponentsCounter()).isEqualTo(battleResult.getDecepticonsElimenatedOponentsCounter());
            return true;
        }));
    }

    @Test
    public void shouldWinByStrangeAndCourageDifference() {
        //given
        Transformer autobot = new Transformer("id", "autobot"
                , new Transformer.PowerParameters(7, 10, 10, 10, 10, 6, 10, 10)
                , "A");
        Transformer decepticon = new Transformer("id", "decepticon"
                , new Transformer.PowerParameters(10, 10, 10, 10, 10, 10, 10, 10)
                , "D");
        //when
        battleService.calculateBattleResult(Collections.singletonList(autobot), Collections.singletonList(decepticon), battleResultCallback);

        //then
        Mockito.verify(battleResultCallback).onBattleResultCalculated(argThat(battleResult -> {
            Assertions.assertThat(battleResult.isTotalDestroy()).isFalse();
            Assertions.assertThat(battleResult.getDecepticonsElimenatedOponentsCounter()).isGreaterThan(battleResult.getAutobotsElimenatedOponentsCounter());
            return true;
        }));
    }

    @Test
    public void shouldWinBySkillDifference() {
        //given
        Transformer autobot = new Transformer("id", "autobot"
                , new Transformer.PowerParameters(10, 10, 10, 10, 10, 10, 10, 7)
                , "A");
        Transformer decepticon = new Transformer("id", "decepticon"
                , new Transformer.PowerParameters(10, 10, 10, 10, 10, 10, 10, 10)
                , "D");
        //when
        battleService.calculateBattleResult(Collections.singletonList(autobot), Collections.singletonList(decepticon), battleResultCallback);

        //then
        Mockito.verify(battleResultCallback).onBattleResultCalculated(argThat(battleResult -> {
            Assertions.assertThat(battleResult.isTotalDestroy()).isFalse();
            Assertions.assertThat(battleResult.getDecepticonsElimenatedOponentsCounter()).isGreaterThan(battleResult.getAutobotsElimenatedOponentsCounter());
            return true;
        }));
    }

    @Test
    public void shouldWinRatingDifference() {
        //given
        Transformer autobot = new Transformer("id", "autobot"
                , new Transformer.PowerParameters(9, 9, 9, 9, 9, 9, 9, 9)
                , "A");
        Transformer decepticon = new Transformer("id", "decepticon"
                , new Transformer.PowerParameters(10, 10, 10, 10, 10, 10, 10, 10)
                , "D");
        //when
        battleService.calculateBattleResult(Collections.singletonList(autobot), Collections.singletonList(decepticon), battleResultCallback);

        //then
        Mockito.verify(battleResultCallback).onBattleResultCalculated(argThat(battleResult -> {
            Assertions.assertThat(battleResult.isTotalDestroy()).isFalse();
            Assertions.assertThat(battleResult.getDecepticonsElimenatedOponentsCounter()).isGreaterThan(battleResult.getAutobotsElimenatedOponentsCounter());
            return true;
        }));
    }

    @Test
    public void shouldWinByNameOptimus() {
        //given
        Transformer autobot = new Transformer("id", "Optimus Prime"
                , new Transformer.PowerParameters(1, 1, 1, 1, 1, 1, 1, 1)
                , "A");
        Transformer decepticon = new Transformer("id", "decepticon"
                , new Transformer.PowerParameters(10, 10, 10, 10, 10, 10, 10, 10)
                , "D");
        //when
        battleService.calculateBattleResult(Collections.singletonList(autobot), Collections.singletonList(decepticon), battleResultCallback);

        //then
        Mockito.verify(battleResultCallback).onBattleResultCalculated(argThat(battleResult -> {
            Assertions.assertThat(battleResult.isTotalDestroy()).isFalse();
            Assertions.assertThat(battleResult.getAutobotsElimenatedOponentsCounter()).isGreaterThan(battleResult.getDecepticonsElimenatedOponentsCounter());
            return true;
        }));
    }

    @Test
    public void shouldWinByNamePredaking() {
        //given
        Transformer autobot = new Transformer("id", "Predaking"
                , new Transformer.PowerParameters(1, 1, 1, 1, 1, 1, 1, 1)
                , "A");
        Transformer decepticon = new Transformer("id", "decepticon"
                , new Transformer.PowerParameters(10, 10, 10, 10, 10, 10, 10, 10)
                , "D");
        //when
        battleService.calculateBattleResult(Collections.singletonList(autobot), Collections.singletonList(decepticon), battleResultCallback);

        //then
        Mockito.verify(battleResultCallback).onBattleResultCalculated(argThat(battleResult -> {
            Assertions.assertThat(battleResult.isTotalDestroy()).isFalse();
            Assertions.assertThat(battleResult.getAutobotsElimenatedOponentsCounter()).isGreaterThan(battleResult.getDecepticonsElimenatedOponentsCounter());
            return true;
        }));
    }
}
