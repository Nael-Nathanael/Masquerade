package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ChatRoom {

    public String id;
    public String name;

    public ChatRoom() {
    }

    public ChatRoom(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
