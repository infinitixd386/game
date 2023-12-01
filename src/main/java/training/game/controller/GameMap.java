package training.game.controller;

import training.game.actors.Actor;
import training.game.directions.Direction;
import training.game.domain.Player;
import training.game.domain.Position;
import training.game.observer.Observer;

import java.util.*;

public class GameMap {

    private final static int MAP_SIZE = 4;
    private List<Actor> actors;
    private final Observer messageObserver;
    private Result result;

    public GameMap(List<Actor> actors, Observer messageObserver) {
        this.actors = actors;
        this.messageObserver = messageObserver;
        actors.forEach(actor -> actor.setGameMap(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameMap gameMap = (GameMap) o;
        return Objects.equals(actors, gameMap.actors) && Objects.equals(messageObserver, gameMap.messageObserver) && result == gameMap.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actors, messageObserver, result);
    }

    public char[][] getCharMap() {
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                Actor actor = findActor(new Position(x, y));
                if (!(actor == null)) {
                    map[x][y] = actor.getCharacter();
                } else {
                    map[x][y] = 'O';
                }
            }
        }

        return map;
    }

    public Actor findActor(Position position) {
        return actors.stream()
                .filter(a -> (a.getPosition().equals(position)))
                .findAny().orElse(null);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Observer getMessageObserver() {
        return messageObserver;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public Player findPlayer() {
        return (Player) actors.stream()
                .filter(p -> p instanceof Player)
                .findAny().orElse(null);
    }

    public void move(Direction direction) {
        Position currentPosition = findPlayer().getPosition();
        Position movePosition;
        switch (direction) {
            case UP -> {
                movePosition = new Position(currentPosition.getX() - 1, currentPosition.getY());
                movePlayer(movePosition, direction);
            }
            case DOWN -> {
                movePosition = new Position(currentPosition.getX() + 1, currentPosition.getY());
                movePlayer(movePosition, direction);
            }
            case LEFT -> {
                movePosition = new Position(currentPosition.getX(), currentPosition.getY() - 1);
                movePlayer(movePosition, direction);
            }
            case RIGHT -> {
                movePosition = new Position(currentPosition.getX(), currentPosition.getY() + 1);
                movePlayer(movePosition, direction);
            }
        }
    }

    private void movePlayer(Position movePosition, Direction direction) {
        if (isInBounds(movePosition)) {
            Actor actor = findActor(movePosition);
            if (actor != null) {
                actor.addObserver(messageObserver);
                actor.interact(findPlayer());
                checkIfPlayerDied();
            }
            updatePositions(movePosition);
        } else {
            System.out.println("The Player can't move " + direction + "!");
        }
    }

    private void updatePositions(Position movePosition) {
        findPlayer().setPosition(movePosition);
    }

    private boolean isInBounds(Position movePosition) {
        return movePosition.getX() >= 0 && movePosition.getX() < MAP_SIZE && movePosition.getY() >= 0 && movePosition.getY() < MAP_SIZE;
    }

    private void checkIfPlayerDied() {
        if (findPlayer().isDead()) {
            this.result = Result.LOSE;
        }
    }
}
