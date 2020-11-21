package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao.UsernameHistoryDao;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.UsernameHistory;

@Database(entities = {UsernameHistory.class}, version = 4, exportSchema = false)
public abstract class UsernameHistoryDatabase extends RoomDatabase {

    private static UsernameHistoryDatabase INSTANCE;

    public static UsernameHistoryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UsernameHistoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    UsernameHistoryDatabase.class,
                                    "masq-db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UsernameHistoryDao usernameHistoryDao();
}
