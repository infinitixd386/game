package training.game.controller;

import training.game.actors.Actor;
import training.game.directions.Direction;
import training.game.domain.*;
import training.game.observer.MessageObserver;
import training.game.strategy.StrongDamageStrategy;
import training.game.strategy.WeakDamageStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class GameMapTest {

    private GameMap testGameMap;
    private Player testPlayer;
    private static final int ARMOR = 100;
    private static final int POINT = 0;
    private Enemy testEnemy1;
    private char[][] testDefaultGameMap;

    @BeforeEach
    void setUp() {
        Position testDefaultPosition = new Position(1, 1);
        Exit testExit = new Exit();
        testExit.setPosition(new Position(1, 2));
        testPlayer = new Player();
        testPlayer.setPosition(testDefaultPosition);
        testPlayer.setArmor(ARMOR);
        testPlayer.setScore(POINT);
        testEnemy1 = new Enemy();
        testEnemy1.setPosition(new Position(0, 1));
        testEnemy1.setDamageStrategy(new StrongDamageStrategy());
        Enemy testEnemy2 = new Enemy();
        testEnemy2.setPosition(new Position(0, 2));
        testEnemy2.setDamageStrategy(new StrongDamageStrategy());
        Bonus testBonus = new Bonus();
        testBonus.setPosition(new Position(2, 1));
        Shield testShield = new Shield();
        testShield.setPosition(new Position(1, 0));
        List<Actor> actors = new ArrayList<>();
        actors.add(testPlayer);
        actors.add(testEnemy1);
        actors.add(testEnemy2);
        actors.add(testBonus);
        actors.add(testExit);
        actors.add(testShield);
        testGameMap = new GameMap(actors, new MessageObserver());
        testDefaultGameMap = testGameMap.getCharMap();
    }

    @Test
    void testMoveMethodEnemyShouldInteractWithThePlayerAndDamageHimStrongly() {
        //GIVEN
        int expected = ARMOR - 100;
        //WHEN
        testEnemy1.interact(testPlayer);
        int actual = testGameMap.findPlayer().getArmor();
        //THEN
        Assertions.assertEquals(expected, actual, "Players Armor was not the same as expected.");
    }

    @Test
    void testMoveMethodShouldMoveThePlayerUpEnemyShouldInteractWithThePlayerDamageHimWeaklyAndGiveHimABonus() {
        //GIVEN
        testEnemy1.setDamageStrategy(new WeakDamageStrategy());
        testDefaultGameMap = testGameMap.getCharMap();
        int expectedArmor = ARMOR - 50;
        int expectedPoint = 1;
        Position expectedPosition = new Position(0, 1);
        //WHEN
        testGameMap.move(Direction.UP);
        //THEN
        int actualArmor = testGameMap.findPlayer().getArmor();
        int actualPoint = testGameMap.findPlayer().getScore();
        Position actualPosition = testGameMap.findPlayer().getPosition();
        Assertions.assertEquals(expectedArmor, actualArmor, "Players Armor was not the same as expected.");
        Assertions.assertEquals(expectedPoint, actualPoint, "Players Score was not the same as expected.");
        Assertions.assertEquals(expectedPosition, actualPosition, "The GameMaps move method should move the Player " + Direction.UP +
                "\nInitial GameMap:\n" +
                formatMap(testDefaultGameMap) +
                "\nOutcome GameMap:\n" +
                formatMap(testGameMap.getCharMap()));
    }

    @Test
    void testMoveMethodShouldMoveThePlayerDownAndBonusShouldInteractWithThePlayerAndGiveHimABonus() {
        //GIVEN
        int expectedPoint = 1;
        Position expectedPosition = new Position(2, 1);
        //WHEN
        testGameMap.move(Direction.DOWN);
        //THEN
        int actualPoint = testGameMap.findPlayer().getScore();
        Position actualPosition = testGameMap.findPlayer().getPosition();
        Assertions.assertEquals(expectedPoint, actualPoint, "Players Score was not the same as expected.");
        Assertions.assertEquals(expectedPosition, actualPosition, "The GameMaps move method should move the Player " + Direction.DOWN +
                "\nInitial GameMap:\n" +
                formatMap(testDefaultGameMap) +
                "\nOutcome GameMap:\n" +
                formatMap(testGameMap.getCharMap()));
    }

    @Test
    void testMoveMethodShouldMoveThePlayerLeftAndShieldShouldUpgradeThePlayerToProtectedPlayer() {
        //GIVEN
        Player expectedActor = new ProtectedPlayer();
        expectedActor.setPosition(new Position(0, 1));
        expectedActor.setArmor(ARMOR);
        expectedActor.setScore(POINT);
        Position expectedPosition = new Position(1, 0);
        //WHEN
        testGameMap.move(Direction.LEFT);
        //THEN
        Player actualActor = testGameMap.findPlayer();
        Position actualPosition = testGameMap.findPlayer().getPosition();
        Assertions.assertEquals(expectedActor.getClass(), actualActor.getClass(), "ProtectedPlayers expected.");
        Assertions.assertEquals(expectedPosition, actualPosition, "The GameMaps move method should move the Player " + Direction.LEFT +
                "\nInitial GameMap:\n" +
                formatMap(testDefaultGameMap) +
                "\nOutcome GameMap:\n" +
                formatMap(testGameMap.getCharMap()));
    }

    @Test
    void testMoveMethodWeakEnemiesDamageShouldBe50PercentLessIfPlayerIsProtectedPlayer() {
        //GIVEN
        testEnemy1.setDamageStrategy(new WeakDamageStrategy());
        int expected = (int) (ARMOR - 50 * 0.5);
        //WHEN
        testGameMap.move(Direction.LEFT);
        testGameMap.move(Direction.RIGHT);
        testGameMap.move(Direction.UP);
        //THEN
        int actual = testGameMap.findPlayer().getArmor();
        Assertions.assertEquals(expected, actual, "ProtectedPlayers Armor with WeakDamageStrategy expected(50 * 0.5)."
                + "\nMoves: " + Direction.LEFT + ", " + Direction.RIGHT + ", " + Direction.UP +
                "\nInitial GameMap:\n" +
                formatMap(testDefaultGameMap));
    }

    @Test
    void testMoveMethodStrongEnemiesDamageShouldBe50PercentLessIfPlayerIsProtectedPlayer() {
        //GIVEN
        int expected = (int) (ARMOR - 100 * 0.5);
        //WHEN
        testGameMap.move(Direction.LEFT);
        testGameMap.move(Direction.RIGHT);
        testGameMap.move(Direction.UP);
        //THEN
        int actual = testGameMap.findPlayer().getArmor();
        Assertions.assertEquals(expected, actual, "ProtectedPlayers Armor with StrongDamageStrategy expected(100 * 0.5)."
                + "\nMoves: " + Direction.LEFT + ", " + Direction.RIGHT + ", " + Direction.UP +
                "\nInitial GameMap:\n" +
                formatMap(testDefaultGameMap));
    }

    @Test
    void testMoveMethodShouldMoveThePlayerRightAndExitShouldEndTheGameWhenPlayerIsOnTheExitField() {
        //GIVEN
        Result expectedResult = Result.WIN;
        Position expectedPosition = new Position(1, 2);
        //WHEN
        testGameMap.move(Direction.RIGHT);
        //THEN
        Result actualResult = testGameMap.getResult();
        Position actualPosition = testGameMap.findPlayer().getPosition();
        Assertions.assertEquals(expectedResult, actualResult, "The Games Winning Result outcome is wrong.");
        Assertions.assertEquals(expectedPosition, actualPosition, "The GameMaps move method should move the Player " + Direction.RIGHT +
                "\nInitial GameMap:\n" +
                formatMap(testDefaultGameMap) +
                "\nOutcome GameMap:\n" +
                formatMap(testGameMap.getCharMap()));
    }

    @Test
    void testMoveMethodShouldNotThrowExceptionWhenPlayerIsOutOfBounds() {
        //GIVEN
        testGameMap.removeActor(testEnemy1);
        //WHEN
        testGameMap.move(Direction.UP);
        //THEN
        Assertions.assertDoesNotThrow(() -> testGameMap.move(Direction.UP),
                "Move method should not throw " + ArrayIndexOutOfBoundsException.class.getSimpleName()
                        + " exception when Player is out of bounds.");
    }

    @Test
    void testMoveMethodShouldEndTheGameWhenPlayersArmorIsBelow0() {
        //GIVEN
        Result expected = Result.LOSE;
        //WHEN
        testGameMap.move(Direction.UP);
        testGameMap.move(Direction.RIGHT);
        //THEN
        Result actual = testGameMap.getResult();
        Assertions.assertEquals(expected, actual, "The game should end when the Player dies (Armor below 0)."
                + "\nMoves: " + Direction.UP + ", " + Direction.RIGHT +
                "\nInitial GameMap:\n" +
                formatMap(testDefaultGameMap));
    }

    @Test
    void testGetCharMapMethodShouldGenerateTheRightMapWhenCalled() {
        //GIVEN
        char[][] expected = new char[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                expected[x][y] = 'O';
            }
        }
        expected[0][1] = '#';
        expected[0][2] = '#';
        expected[1][0] = '+';
        expected[1][1] = 'P';
        expected[1][2] = 'E';
        expected[2][1] = '$';
        //WHEN
        char[][] actual = testGameMap.getCharMap();
        //THEN
        Assertions.assertArrayEquals(expected, actual, "The generateMap method should return with: \n" +
                formatMap(expected) + "\nbut the outcome was: \n" + formatMap(actual) + ".");
    }

    @Test
    void testFindPlayerMethodShouldFindThePlayerWhenCalled() {
        //GIVEN
        Player expectedPlayer = testPlayer;
        //WHEN
        Player actualPlayer = testGameMap.findPlayer();
        //THEN
        Assertions.assertEquals(expectedPlayer, actualPlayer, "The findPlayer() method should find the expected Player.");
    }

    @Test
    void testFindPlayerMethodShouldFindTheProtectedPlayerWhenCalled() {
        //GIVEN
        Player expectedProtectedPlayer = new ProtectedPlayer();
        testGameMap.removeActor(testPlayer);
        testGameMap.addActor(expectedProtectedPlayer);
        //WHEN
        Player actualProtectedPlayer = testGameMap.findPlayer();
        //THEN
        Assertions.assertEquals(expectedProtectedPlayer, actualProtectedPlayer, "The findPlayer() method should find the expected ProtectedPlayer.");
    }

    private String formatMap(char[][] gameMap) {
        return Arrays
                .stream(gameMap)
                .map(Arrays::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
