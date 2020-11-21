package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "username_history")
public class UsernameHistory {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    public UsernameHistory(String username) {
        this.username = username;
    }
}
