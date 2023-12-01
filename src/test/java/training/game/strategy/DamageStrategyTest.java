package training.game.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class DamageStrategyTest {

    private DamageStrategy testDamageStrategy;

    @Test
    void testWeakDamageStrategyShouldBe50() {
        //GIVEN
        testDamageStrategy = new WeakDamageStrategy();
        int expected = 50;
        //WHEN
        int actual = testDamageStrategy.getEnemyDamage();
        //THEN
        Assertions.assertEquals(expected, actual, "WeakDamageStrategy should be " + expected + " but the outcome was " + actual + ".");
    }

    @Test
    void testStrongDamageStrategyShouldBe100() {
        //GIVEN
        testDamageStrategy = new StrongDamageStrategy();
        int expected = 100;
        //WHEN
        int actual = testDamageStrategy.getEnemyDamage();
        //THEN
        Assertions.assertEquals(expected, actual, "StrongDamageStrategy should be " + expected + " but the outcome was " + actual + ".");
    }

}
