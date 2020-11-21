package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.UsernameHistory;

@Dao
public interface UsernameHistoryDao {

    @Query("SELECT * FROM username_history")
    LiveData<List<UsernameHistory>> getAll();

    @Insert
    long insert(UsernameHistory new_username);
}
