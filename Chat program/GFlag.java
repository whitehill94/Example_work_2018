package P5;

public class GFlag {
    private boolean chatting;

    public GFlag() {
        chatting = true;
    }

    public boolean get() {
        return chatting;
    }

    public void setFalse() {
        chatting = false;
    }

    public void setTrue() {
        chatting = true;
    }
}
