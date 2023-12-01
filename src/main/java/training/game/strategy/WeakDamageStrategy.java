package training.game.strategy;

public class WeakDamageStrategy implements DamageStrategy {

    private static final int ENEMY_DAMAGE = 50;

    @Override
    public Integer getEnemyDamage() {
        return ENEMY_DAMAGE;
    }
}
