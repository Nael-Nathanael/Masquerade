package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model;

public class PrivateChatRoom extends ChatRoom {
    private String password;

    public PrivateChatRoom() {
        super();
    }

    public PrivateChatRoom(String id, String name) {
        super(id, name);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
