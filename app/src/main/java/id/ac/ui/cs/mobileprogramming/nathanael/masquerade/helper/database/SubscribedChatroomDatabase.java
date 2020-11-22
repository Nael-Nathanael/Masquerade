package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao.SubscribedChatroomDao;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.SubscribedChatroom;

@Database(entities = {SubscribedChatroom.class}, version = 2, exportSchema = false)
public abstract class SubscribedChatroomDatabase extends RoomDatabase {

    private static SubscribedChatroomDatabase INSTANCE;

    public static SubscribedChatroomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SubscribedChatroomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    SubscribedChatroomDatabase.class,
                                    "masq-db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract SubscribedChatroomDao subscribedChatroomDao();
}
