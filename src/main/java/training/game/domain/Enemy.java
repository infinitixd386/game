package training.game.domain;

import training.game.actors.Actor;
import training.game.strategy.DamageStrategy;

public class Enemy extends Actor {


    public static final char CHARACTER = '#';
    private DamageStrategy damageStrategy;

    public Enemy() {
    }

    @Override
    public void interact(Player player) {
        int playerArmor = player.getArmor();
        player.damage(damageStrategy.getEnemyDamage());
        player.addScore(1);
        getGameMap().removeActor(this);
        this.notifyObservers("Player armor got damaged by "+(playerArmor-player.getArmor())+".");
    }

    @Override
    public char getCharacter() {
        return CHARACTER;
    }

    public void setDamageStrategy(DamageStrategy damageStrategy) {
        this.damageStrategy = damageStrategy;
    }
}
