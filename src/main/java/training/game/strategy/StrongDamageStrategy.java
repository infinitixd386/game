package training.game.strategy;

public class StrongDamageStrategy implements DamageStrategy{
    private static final int ENEMY_DAMAGE = 100;

    @Override
    public Integer getEnemyDamage() {
        return ENEMY_DAMAGE;
    }
}
