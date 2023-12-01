package training.game.actors;

import training.game.controller.GameMap;
import training.game.domain.Player;
import training.game.domain.Position;
import training.game.observer.Observable;

import java.util.Objects;

public abstract class Actor extends Observable {

    private Position position;
    private GameMap gameMap;

    public Actor() {
    }

    public abstract void interact(Player player);

    public abstract char getCharacter();

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return position.equals(actor.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "position=" + position +
                ", gameMap=" + gameMap +
                '}';
    }
}
