package training.game.domain;

import training.game.actors.Actor;

public class Bonus extends Actor {

    public static final char CHARACTER = '$';

    public Bonus() {
    }

    @Override
    public void interact(Player player) {
        player.addScore(1);
        getGameMap().removeActor(this);
        this.notifyObservers("Player got a point.");
    }

    @Override
    public char getCharacter() {
        return CHARACTER;
    }
}
