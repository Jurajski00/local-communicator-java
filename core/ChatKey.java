package project.core;

public class ChatKey {
    private final String stringKey;

    public ChatKey(User currentUser, User chatPartner) {
        stringKey = generateKey(currentUser, chatPartner);
    }

    private String generateKey(User currentUser, User chatPartner) {
        int currentId = currentUser.getId();
        int partnerId = chatPartner.getId();
        return (currentId < partnerId) ? (currentId + "&" + partnerId) : (partnerId + "&" + currentId);
    }

    public String getStringKey() {
        return stringKey;
    }

    @Override
    public String toString() {
        return stringKey;
    }
}
