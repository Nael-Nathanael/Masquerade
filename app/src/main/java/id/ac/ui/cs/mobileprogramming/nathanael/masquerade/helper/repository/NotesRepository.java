package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao.NotesDao;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.database.NotesDatabase;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Notes;

public class NotesRepository {

    private final NotesDao notesDao;
    private final LiveData<List<Notes>> mAllNotes;

    public NotesRepository(Application application) {
        NotesDatabase db = NotesDatabase.getDatabase(application);
        notesDao = db.notesDao();
        mAllNotes = notesDao.getAll();
    }

    public LiveData<List<Notes>> getAllNotes() {
        return mAllNotes;
    }

    public void insert(Notes notes) {
        new insertAsyncTask(notesDao).execute(notes);
    }

    private static class insertAsyncTask extends AsyncTask<Notes, Void, Void> {

        private final NotesDao mAsyncTaskDao;

        insertAsyncTask(NotesDao notesDao) {
            mAsyncTaskDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            mAsyncTaskDao.insertAll(notes);
            return null;
        }
    }
}
