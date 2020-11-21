package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Notes;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    LiveData<List<Notes>> getAll();

    @Insert
    void insertAll(Notes ...notes);

    @Query("SELECT COUNT(id) FROM notes")
    Integer getTotal();
}
