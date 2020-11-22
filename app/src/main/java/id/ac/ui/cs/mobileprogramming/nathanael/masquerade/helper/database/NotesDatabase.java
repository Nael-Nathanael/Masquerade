package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao.NotesDao;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Notes;

@Database(entities = {Notes.class}, version = 5, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase INSTANCE;

    public static NotesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    NotesDatabase.class,
                                    "masq-db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract NotesDao notesDao();
}
