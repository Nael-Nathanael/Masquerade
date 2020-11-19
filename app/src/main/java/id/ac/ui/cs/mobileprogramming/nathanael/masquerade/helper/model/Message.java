package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model;

public class Message {
    public String id;
    public String content;
    public String sender;
    public String datetime;

    public Message() {
    }

    public Message(String id, String content, String sender, String datetime) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.datetime = datetime;
    }
}
