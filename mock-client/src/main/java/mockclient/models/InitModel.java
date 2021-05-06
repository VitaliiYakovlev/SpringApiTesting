package mockclient.models;

import java.util.Queue;

public class InitModel {

    private long delay;
    private Queue<String> message;

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public Queue<String> getMessage() {
        return message;
    }

    public void setMessage(Queue<String> message) {
        this.message = message;
    }

    public InitModel() {
    }

    public InitModel(long delay, Queue<String> message) {
        this.delay = delay;
        this.message = message;
    }
}
