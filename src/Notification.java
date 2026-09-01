abstract class Notification {
    protected final String memberId;
    protected final String message;

    protected Notification(String memberId, String message) {
        this.memberId = memberId;
        this.message = message;
    }

    public abstract void send();
}
