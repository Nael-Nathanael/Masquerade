package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao.SubscribedChatroomDao;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.database.SubscribedChatroomDatabase;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.SubscribedChatroom;

public class SubscribedChatroomRepository {

    private final SubscribedChatroomDao subscribedChatroomDao;
    private final LiveData<List<SubscribedChatroom>> mAllChatrooom;

    public SubscribedChatroomRepository(Application application) {
        SubscribedChatroomDatabase db = SubscribedChatroomDatabase.getDatabase(application);
        subscribedChatroomDao = db.subscribedChatroomDao();
        mAllChatrooom = subscribedChatroomDao.getAll();
    }

    public LiveData<List<SubscribedChatroom>> getmAllChatrooom() {
        return mAllChatrooom;
    }

    public void insert(SubscribedChatroom subscribedChatroom) {
        new insertAsyncTask(subscribedChatroomDao).execute(subscribedChatroom);
    }

    public void delete(String selectedChatroomId) {
        new deleteAsyncTask(subscribedChatroomDao).execute(selectedChatroomId);
    }

    private static class insertAsyncTask extends AsyncTask<SubscribedChatroom, Void, Void> {

        private final SubscribedChatroomDao mAsyncTaskDao;

        insertAsyncTask(SubscribedChatroomDao subscribedChatroomDao) {
            mAsyncTaskDao = subscribedChatroomDao;
        }

        @Override
        protected Void doInBackground(SubscribedChatroom... subscribedChatroom) {
            mAsyncTaskDao.insertAll(subscribedChatroom);
            return null;
        }
    }


    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {

        private final SubscribedChatroomDao mAsyncTaskDao;

        deleteAsyncTask(SubscribedChatroomDao subscribedChatroomDao) {
            mAsyncTaskDao = subscribedChatroomDao;
        }

        @Override
        protected Void doInBackground(String... subscribedChatroomId) {
            mAsyncTaskDao.delete(subscribedChatroomId[0]);
            return null;
        }
    }
}
