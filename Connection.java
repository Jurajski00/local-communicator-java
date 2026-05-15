package project;

public class Connection implements Iconnectable {
    private final String stringKey;

    public Connection(User currentUser, User chatPartner) {
        this.stringKey = connect(currentUser, chatPartner);
    }

    public String connect(User currentUser, User chatPartner) {
        int currentId = currentUser.getId();
        int partnerId = chatPartner.getId();
        return (currentId < partnerId) ? (currentId + "&" + partnerId) : (partnerId + "&" + currentId);
    }

    public String getStringKey() {
        return stringKey;
    }
}
