class OverdueNotification extends Notification {
    public OverdueNotification(String memberId, String message) {
        super(memberId, message);
    }

    @Override
    public void send() {
        System.out.println("[OVERDUE] Member " + memberId + ": " + message);
    }
}
