package training.game;

import training.game.controller.GameMap;
import training.game.data.FileGameMapLoader;
import training.game.data.GameMapLoader;
import training.game.factory.ActorFactory;
import training.game.view.ConsoleView;

public class Application {
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    private static final String FILE_PATH = "input/gamemap.csv";

    public void run() {
        ConsoleView view = new ConsoleView();

        view.printControls();

        GameMapLoader gameMapLoader = new FileGameMapLoader(FILE_PATH, new ActorFactory(view.readDamageStrategy()));
        GameMap gameMap = gameMapLoader.load();

        view.printGameStart();
        while (gameMap.getResult() == null) {
            view.printPlayerDetails(gameMap.findPlayer());
            view.printGameMap(gameMap.getCharMap());
            gameMap.move(view.readDirection());
        }
        view.printGameMap(gameMap.getCharMap());
        view.printResult(gameMap.getResult());
    }
}
