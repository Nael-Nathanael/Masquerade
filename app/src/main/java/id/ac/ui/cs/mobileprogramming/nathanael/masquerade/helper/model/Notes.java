package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Notes {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "datetime")
    public String datetime = String.valueOf(android.text.format.DateFormat.format("MM/dd/yyyy hh:mm", new java.util.Date()));

    @ColumnInfo(name = "sender")
    public String sender;

    @ColumnInfo(name = "content")
    public String content;

    public Notes(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }
}
