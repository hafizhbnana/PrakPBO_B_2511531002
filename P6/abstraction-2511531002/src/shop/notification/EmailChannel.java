package shop.notification;

public class EmailChannel extends AbstractChannel {
    @Override
    public String channelName() {
        return "Email";
    }

    protected String resolveRecipient(CustomerContact contact) {
        String email = contact.getEmail();
        if (email.contains("@")) { return email; }
        return "";
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        if (recipient == null || recipient.isBlank()
            || message == null || message.isBlank()) {
            return false;
        }

        System.out.println("[EMAIL to " + recipient + "] " + message);
        return true;
    }
}
