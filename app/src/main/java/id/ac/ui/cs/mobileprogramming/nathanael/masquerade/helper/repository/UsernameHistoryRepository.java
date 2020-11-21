package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao.UsernameHistoryDao;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.database.UsernameHistoryDatabase;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.UsernameHistory;

public class UsernameHistoryRepository {

    private final UsernameHistoryDao usernameHistoryDao;
    private final LiveData<List<UsernameHistory>> usernameHistories;

    public UsernameHistoryRepository(Application application) {
        UsernameHistoryDatabase db = UsernameHistoryDatabase.getDatabase(application);
        usernameHistoryDao = db.usernameHistoryDao();
        usernameHistories = usernameHistoryDao.getAll();
    }

    public LiveData<List<UsernameHistory>> getAllusernameHistories() {
        return usernameHistories;
    }

    public void insert(UsernameHistory usernameHistory) {
        new insertAsyncTask(usernameHistoryDao).execute(usernameHistory);
    }

    private static class insertAsyncTask extends AsyncTask<UsernameHistory, Void, Void> {

        private final UsernameHistoryDao mAsyncTaskDao;

        insertAsyncTask(UsernameHistoryDao usernameHistory) {
            mAsyncTaskDao = usernameHistory;
        }

        @Override
        protected Void doInBackground(UsernameHistory... usernameHistories) {
            mAsyncTaskDao.insert(usernameHistories[0]);
            return null;
        }
    }
}
