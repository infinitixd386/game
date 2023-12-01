package training.game.observer;

import training.game.actors.Actor;
import training.game.controller.GameMap;
import training.game.domain.*;
import training.game.strategy.WeakDamageStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ObserverTest {

    private Actor testActor;
    private Actor testPlayer;
    private GameMap testGameMap;
    private Observer testMessageObserver;

    @BeforeEach
    void setUp() {
        testPlayer = new Player();
        testMessageObserver = new MessageObserver();
        List<Actor> actors = new ArrayList<>();
        actors.add(testPlayer);
        testGameMap = new GameMap(actors, testMessageObserver);
    }

    @Test
    void testBonusShouldNotifyWithTheRightMessage() {
        //GIVEN
        testActor = new Bonus();
        testGameMap.addActor(testActor);
        testActor.setGameMap(testGameMap);
        String expectedMessage = "Player got a point.";
        testActor.addObserver(testMessageObserver);
        //WHEN
        testActor.interact((Player) testPlayer);
        //THEN
        String actualMessage = ((MessageObserver) testMessageObserver).getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testEnemyShouldNotifyWithTheRightMessage() {
        //GIVEN
        testActor = new Enemy();
        ((Enemy) testActor).setDamageStrategy(new WeakDamageStrategy());
        testGameMap.addActor(testActor);
        testActor.setGameMap(testGameMap);
        String expectedMessage = "Player armor got damaged by 50.";
        testActor.addObserver(testMessageObserver);
        //WHEN
        testActor.interact((Player) testPlayer);
        //THEN
        String actualMessage = ((MessageObserver) testMessageObserver).getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testShieldShouldNotifyWithTheRightMessage() {
        //GIVEN
        testActor = new Shield();
        testGameMap.addActor(testActor);
        testActor.setGameMap(testGameMap);
        String expectedMessage = "Player got the shield.";
        testActor.addObserver(testMessageObserver);
        //WHEN
        testActor.interact((Player) testPlayer);
        //THEN
        String actualMessage = ((MessageObserver) testMessageObserver).getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testExitShouldNotifyWithTheRightMessage() {
        //GIVEN
        testActor = new Exit();
        testGameMap.addActor(testActor);
        testActor.setGameMap(testGameMap);
        String expectedMessage = "Exit has been reached.";
        testActor.addObserver(testMessageObserver);
        //WHEN
        testActor.interact((Player) testPlayer);
        //THEN
        String actualMessage = ((MessageObserver) testMessageObserver).getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
