package training.game.data;

import training.game.actors.Actor;
import training.game.controller.GameMap;
import training.game.domain.*;
import training.game.factory.ActorFactory;
import training.game.strategy.WeakDamageStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameMapLoaderTest {

    private GameMapLoader testGameMapLoader;
    private final String TEST_FILE_NAME = "input/gamemap.csv";
    private static final int ARMOR = 100;
    private static final int POINT = 0;

    @BeforeEach
    void setUp() {
        testGameMapLoader = new FileGameMapLoader(TEST_FILE_NAME, new ActorFactory(new WeakDamageStrategy()));
    }

    @Test
    void testInitMethodShouldReturnGameMapWhenCalled() throws IOException {
        //GIVEN
        List<Actor> expectedActors = populateActorsList();
        //WHEN
        GameMap actualGameMap = testGameMapLoader.load();
        expectedActors.forEach(actor -> actor.setGameMap(actualGameMap));
        List<Actor> actualActors = actualGameMap.getActors();
        //THEN
        Assertions.assertEquals(expectedActors, actualActors, "Init method should set up the GameMap with the correct Actors in the right Positions.");
    }

    List<Actor> populateActorsList() {
        List<Actor> testActors = new ArrayList<>();
        Enemy enemy1 = new Enemy();
        Enemy enemy2 = new Enemy();
        Enemy enemy3 = new Enemy();
        Exit exit = new Exit();
        Shield shield = new Shield();
        Bonus bonus = new Bonus();
        Player player = new Player();
        enemy1.setPosition(new Position(0, 2));
        enemy1.setDamageStrategy(new WeakDamageStrategy());
        enemy2.setPosition(new Position(1, 2));
        enemy2.setDamageStrategy(new WeakDamageStrategy());
        enemy3.setPosition(new Position(1, 3));
        enemy3.setDamageStrategy(new WeakDamageStrategy());
        exit.setPosition(new Position(0, 3));
        shield.setPosition(new Position(1, 1));
        bonus.setPosition(new Position(2, 3));
        player.setPosition(new Position(2, 2));
        player.setArmor(ARMOR);
        player.setScore(POINT);
        testActors.add(enemy1);
        testActors.add(exit);
        testActors.add(shield);
        testActors.add(enemy2);
        testActors.add(enemy3);
        testActors.add(player);
        testActors.add(bonus);
        return testActors;
    }

}
