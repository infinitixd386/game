package training.game.view;

import training.game.directions.Direction;
import training.game.domain.Player;
import training.game.controller.Result;
import training.game.strategy.DamageStrategy;
import training.game.strategy.StrongDamageStrategy;
import training.game.strategy.WeakDamageStrategy;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleView {
    Scanner reader = new Scanner(System.in);

    public ConsoleView() {
    }

    public void printControls() {
        System.out.println("Game controls:");
        System.out.println("UP W");
        System.out.println("DOWN S");
        System.out.println("LEFT A");
        System.out.println("RIGHT D");
        System.out.println();
    }

    public DamageStrategy readDamageStrategy() {
        while (true) {
            System.out.print("Choose a damage strategy(Weak or Strong): ");
            String strategy = reader.nextLine();
            switch (strategy.toLowerCase()) {
                case "weak" -> {
                    return new WeakDamageStrategy();
                }
                case "strong" -> {
                    return new StrongDamageStrategy();
                }
                default -> System.out.println("Wrong input!");
            }
        }
    }

    public void printGameStart() {
        System.out.println("\nGame has been started!");
        System.out.println();
    }

    public void printPlayerDetails(Player player) {
        System.out.println("Score: " + player.getScore());
        System.out.println("Armor: " + player.getArmor());
        System.out.println();
    }

    public void printGameMap(char[][] map) {
        Arrays.stream(map).forEach(System.out::println);
        System.out.println();
    }

    public Direction readDirection() {
        while (true) {
            System.out.print("Choose a direction: ");
            String direction = reader.nextLine();
            switch (direction.toUpperCase()) {
                case "W" -> {
                    return Direction.UP;
                }
                case "S" -> {
                    return Direction.DOWN;
                }
                case "A" -> {
                    return Direction.LEFT;
                }
                case "D" -> {
                    return Direction.RIGHT;
                }
                default -> System.out.println("Wrong input!");
            }
        }
    }

    public void printResult(Result result) {
        if (result == Result.WIN) {
            System.out.println("Game Win!");
        } else {
            System.out.println("Player dead!");
            System.out.println("Game Lose!");
        }
        System.out.println("Game has been ended!");
    }

}
