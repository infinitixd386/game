package training.game.domain;

public class ProtectedPlayer extends Player {

    public static final char CHARACTER = 'S';
    public ProtectedPlayer() {

    }

    @Override
    public void damage(int damage) {
        super.damage((int) (damage * 0.5));
    }

    @Override
    public char getCharacter() {
        return CHARACTER;
    }
}
