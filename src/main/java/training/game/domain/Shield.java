package training.game.domain;

import training.game.actors.Actor;

public class Shield extends Actor {

    public static final char CHARACTER = '+';

    public Shield() {

    }

    @Override
    public void interact(Player player) {
        getGameMap().removeActor(player);
        this.notifyObservers("Player got the shield.");
        player = upgrade(player);
        getGameMap().addActor(player);
        getGameMap().removeActor(this);
    }

    public ProtectedPlayer upgrade(Player player) {
        ProtectedPlayer protectedPlayer = new ProtectedPlayer();
        protectedPlayer.setPosition(player.getPosition());
        protectedPlayer.setArmor(player.getArmor());
        protectedPlayer.setScore(player.getScore());
        return protectedPlayer;
    }

    @Override
    public char getCharacter() {
        return CHARACTER;
    }

}
