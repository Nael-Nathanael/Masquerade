package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "subscribed_chatroom")
public class SubscribedChatroom {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "name")
    public String name;

    public SubscribedChatroom(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
