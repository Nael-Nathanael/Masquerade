package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao;

import android.database.Cursor;

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
    void insertAll(Notes... notes);

    @Query("SELECT COUNT(id) as total FROM notes")
    Cursor getTotal();

    @Insert
    long insert(Notes new_notes);

    @Query("SELECT * FROM notes")
    Cursor findAllAsCursor();

    @Query("SELECT * FROM notes ORDER BY id desc LIMIT 1")
    Cursor findLatestAsCursor();

    @Query("SELECT * FROM notes ORDER BY id desc LIMIT :limit")
    Cursor findSeveralLatestAsCursor(int limit);
}
