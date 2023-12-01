package training.game.observer;

public class MessageObserver implements Observer {

    String message;

    public MessageObserver() {
    }

    @Override
    public void update(String message) {
        this.message = message;
        System.out.println(message);
    }

    public String getMessage() {
        return message;
    }
}
