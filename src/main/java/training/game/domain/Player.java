package training.game.domain;

import training.game.actors.Actor;

import java.util.Objects;

public class Player extends Actor {

    public static final char CHARACTER = 'P';
    private int armor;
    private int score;

    public Player() {
    }

    @Override
    public void interact(Player player) {
        throw new UnsupportedOperationException("Player can't interact with himself.");
    }

    @Override
    public char getCharacter() {
        return CHARACTER;
    }

    public void damage(int damage) {
        this.armor -= damage;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getArmor() {
        return this.armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public boolean isDead() {
        return armor < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Player player = (Player) o;
        return armor == player.armor && score == player.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), armor, score);
    }

    @Override
    public String toString() {
        return "Player{" +
                "armor=" + armor +
                ", score=" + score +
                '}';
    }
}
