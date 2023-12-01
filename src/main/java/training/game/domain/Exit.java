package training.game.domain;

import training.game.actors.Actor;
import training.game.controller.Result;

public class Exit extends Actor {

    public static final char CHARACTER = 'E';
    public Exit() {

    }

    @Override
    public void interact(Player player) {
        getGameMap().setResult(Result.WIN);
        this.notifyObservers("Exit has been reached.");
    }

    @Override
    public char getCharacter() {
        return CHARACTER;
    }
}
