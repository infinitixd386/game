package training.game.factory;

import training.game.actors.Actor;
import training.game.domain.*;
import training.game.strategy.DamageStrategy;

import java.util.NoSuchElementException;

public class ActorFactory {

    private DamageStrategy damageStrategy;
    private final int ARMOR = 100, SCORE = 0;

    public ActorFactory(DamageStrategy damageStrategy) {
        this.damageStrategy = damageStrategy;
    }

    public Actor createActor(char c) {
        switch (c) {
            case Enemy.CHARACTER -> {
                Enemy enemy = new Enemy();
                enemy.setDamageStrategy(damageStrategy);
                return enemy;
            }
            case Player.CHARACTER -> {
                Player player = new Player();
                player.setArmor(ARMOR);
                player.setScore(SCORE);
                return player;
            }
            case Bonus.CHARACTER -> {
                return new Bonus();
            }
            case Shield.CHARACTER -> {
                return new Shield();
            }
            case Exit.CHARACTER -> {
                return new Exit();
            }
            default ->
                    throw new NoSuchElementException("There is no such element representation for the " + c + " character in the game!");
        }
    }
}
