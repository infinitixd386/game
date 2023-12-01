package training.game.data;

import training.game.actors.Actor;
import training.game.controller.GameMap;
import training.game.domain.Position;
import training.game.factory.ActorFactory;
import training.game.observer.MessageObserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileGameMapLoader implements GameMapLoader {

    private static int MAP_SIZE;
    private final static char EMPTY_FIELD = 'O';
    private char[][] map;
    private int x = 0, y = 0;
    private final String fileName;
    private final ActorFactory actorFactory;

    public FileGameMapLoader(String fileName, ActorFactory actorFactory) {
        this.fileName = fileName;
        this.actorFactory = actorFactory;
    }

    @Override
    public GameMap load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            MAP_SIZE = line.length();
            map = new char[MAP_SIZE][MAP_SIZE];
            do {
                while (y < line.length()) {
                    map[x][y] = line.charAt(y);
                    y++;
                }
                y = 0;
                x++;
            } while ((line = reader.readLine()) != null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return setUpGameMap();
    }

    private GameMap setUpGameMap() {
        List<Actor> actors = new ArrayList<>();
        for (x = 0; x < MAP_SIZE; x++) {
            for (y = 0; y < MAP_SIZE; y++) {
                char c = map[x][y];
                if (!(c == EMPTY_FIELD)) {
                    Actor actor = actorFactory.createActor(c);
                    actor.setPosition(new Position(x, y));
                    if (actor != null) {
                        actors.add(actor);
                    }
                }
            }
        }
        return new GameMap(actors, new MessageObserver());
    }
}
